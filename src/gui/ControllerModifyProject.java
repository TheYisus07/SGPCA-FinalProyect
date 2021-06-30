package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import businesslogic.ProjectDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Project;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerModifyProject implements Initializable{
    
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();    
    
    @FXML
    private JFXTextField textFieldModifyProjectParticipants; 
    
    @FXML
    private JFXDatePicker datePickerModifyProyectStartDate;
    
    @FXML
    private JFXDatePicker datePickerModifyProjectFinishDate;
    
    @FXML
    private Label labelProjectTitle;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private Label labelConsultProjectResponsable;
    
    @FXML
    private JFXTextField textFieldModifyProyecTitle;
    
    @FXML
    private JFXTextField textFieldModifyProjectLgac;
    
    @FXML
    private JFXTextArea textAreaModifyProjectDescription;
    
    @FXML
    private JFXButton buttonModifyProjectExit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToModifyProject();        
    }
    
    public void validFieldsToModifyProject() {
        textFieldModifyProyecTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyProyecTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyProyecTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyProyecTitle.validate();
            }     
        });
        datePickerModifyProjectFinishDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerModifyProjectFinishDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerModifyProjectFinishDate.validate();
            }     
        });
        textFieldModifyProjectLgac.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyProjectLgac.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyProjectLgac.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyProjectLgac.validate();
            }     
        });
        textFieldModifyProjectParticipants.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyProjectParticipants.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyProjectParticipants.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyProjectParticipants.validate();
            }     
        });
        textAreaModifyProjectDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaModifyProjectDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaModifyProjectDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaModifyProjectDescription.validate();
            }     
        });           
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void showGuiConsultResearchProyects(ActionEvent actionEvent) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultResearchProjects.fxml"));
            fXMLLoader.load();
            ControllerConsultResearchProjects consultResearchProjects = fXMLLoader.getController();
            consultResearchProjects.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);            
            stage.setResizable(false); 
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerAddResearchProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiProjectModifiedSuccessfully(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Proyecto");        
        alert.setHeaderText("El Proyecto ha sido modificado");        
        alert.showAndWait();
        showGuiConsultResearchProyects(actionEvent);
    }
    
    @FXML
    public void modifyProyect(ActionEvent actionEvent) {
        String projectTitle = null;
        Date projectStartDate = convertToDate(datePickerModifyProyectStartDate.getValue());
        Date projectFinishDate = null;
        String projectLgac = null;        
        String projectParticipants = null;
        String projectDescription = null;
        String[] memberFullNames;
        boolean validDate = false;
        boolean existsProject = false; 
        if (textFieldModifyProyecTitle.validate() && (!textFieldModifyProyecTitle.getText().trim().equals(""))) {
            projectTitle = textFieldModifyProyecTitle.getText(); 
            String expectedProjectTitle = null;
            try {
                expectedProjectTitle = PROJECT_DAO.consultProjectByTitle(projectTitle).getTitle();
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            existsProject = projectTitle.equals(expectedProjectTitle);
            if (projectTitle.equals(labelProjectTitle.getText())) {
                existsProject = false;
            }
        }
        if (textFieldModifyProjectLgac.validate() && (!textFieldModifyProjectLgac.getText().trim().equals(""))) {
            projectLgac = textFieldModifyProjectLgac.getText(); 
        }
        if (datePickerModifyProjectFinishDate.validate()) {
            projectFinishDate = convertToDate(datePickerModifyProjectFinishDate.getValue());
            if (projectStartDate.before(projectFinishDate)) {
                validDate = true;
            } else {                
                datePickerModifyProjectFinishDate.setValue(null);                
                datePickerModifyProjectFinishDate.validate();
            }
        }
        if (textAreaModifyProjectDescription.validate() && (!textAreaModifyProjectDescription.getText().trim().equals(""))) {
            projectDescription = textAreaModifyProjectDescription.getText();             
        }
                      
        boolean validParticipants = false;
        if (textFieldModifyProjectParticipants.validate() && (!textFieldModifyProjectParticipants.getText().trim().equals(""))) {            
            projectParticipants = textFieldModifyProjectParticipants.getText();
            memberFullNames = projectParticipants.split(";");
            ArrayList<String> arrayListMemberRegistered = new ArrayList<>();
            
            try {
                for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {
                    arrayListMemberRegistered.add(MEMBER_DAO.consultMemberList().get(i).getFullName());
                }
            } catch (BusinessException businessException) {
                showAlertDataBaseConnection();
            }
            int matchCounter = 0; 
            for (String memberFullName : memberFullNames) {
                if (arrayListMemberRegistered.contains(memberFullName)) {
                    matchCounter++;
                }
            }
            if (matchCounter == memberFullNames.length) {
                validParticipants = true;
            }     
            if (!validParticipants) {
                textFieldModifyProjectParticipants.setStyle("-fx-text-fill: RED");   
            } else{
                textFieldModifyProjectParticipants.setStyle("-fx-text-fill: BLACK");   
            }                 
        }    
        
        if ((!existsProject) && (projectTitle != null)  && (projectFinishDate != null) && (projectLgac != null) && (projectDescription != null) && (validDate) && (validParticipants)) {
            try {
                Project newProject = new Project(projectTitle, projectFinishDate, projectStartDate, projectLgac, projectDescription, projectParticipants, labelConsultProjectResponsable.getText());
                PROJECT_DAO.modifyProject(newProject, labelProjectTitle.getText());
                showGuiProjectModifiedSuccessfully(actionEvent);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        } else if (existsProject) {
            textFieldModifyProyecTitle.setText(null);
        }
    }
    
    public void fillProjectTextfieldToModify(String projecTitle) {
        Project project = new Project();
        try {
            project = PROJECT_DAO.consultProjectByTitle(projecTitle);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        labelProjectTitle.setText(projecTitle);
        labelConsultProjectResponsable.setText(project.getMemberFullName());
        textFieldModifyProyecTitle.setText(project.getTitle());
        datePickerModifyProyectStartDate.setValue(convertToLocalDate(project.getStartDate()));
        datePickerModifyProjectFinishDate.setValue(convertToLocalDate(project.getEstimedFinishDate()));
        textFieldModifyProjectLgac.setText(project.getAssociatesIGAC());
        textFieldModifyProjectParticipants.setText(project.getParticipants());
        textAreaModifyProjectDescription.setText(project.getDescripcion());
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void getProjectTitle(String projectTitle) {
        labelProjectTitle.setText(projectTitle);
        fillProjectTextfieldToModify(projectTitle);
        Project project = new Project();
        try {
            project = PROJECT_DAO.consultProjectByTitle(projectTitle);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        datePickerModifyProyectStartDate.setValue(convertToLocalDate(project.getStartDate()));
    }    
    
    @FXML
    public void exitModifyProject(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de modificación de Proyecto");        
        alert.setHeaderText("¿Desea cancelar la modificación del Proyecto?");
        alert.setContentText("El Proyecto no sufrirá ningín cambio.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            Stage stage = (Stage) buttonModifyProjectExit.getScene().getWindow();
            stage.close();
            showGuiConsultResearchProyects(actionEvent);
        } 
    }
    
    public void showAlertDataBaseConnection() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLDataBaseError.fxml"));
            fXMLLoader.load();
            fXMLLoader.getController();
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();  
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerModifyProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}