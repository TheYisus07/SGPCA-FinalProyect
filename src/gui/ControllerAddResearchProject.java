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

public class ControllerAddResearchProject implements Initializable {
     
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private JFXButton buttonAddProjectExit;
    @FXML
    private JFXTextField textFieldAddProjectParticipants;
    @FXML
    private JFXDatePicker datePickerAddProyectStartDate;
    @FXML
    private JFXDatePicker datePickerAddProjectFinishDate;
    @FXML
    private Label labelLoggedInUser;
    @FXML
    private Label labelLoggedInUserEmail;
    @FXML
    private JFXTextField textFieldAddProyecTitle;
    @FXML
    private JFXTextField textFieldAddProjectLgac;
    @FXML
    private JFXTextArea textAreaAddProjectDescription;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToAddProject();
        datePickerAddProyectStartDate.setValue(convertToLocalDate(new Date()));
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
       
    public void validFieldsToAddProject() {
        textFieldAddProyecTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddProyecTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddProyecTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddProyecTitle.validate();
            }     
        });
        datePickerAddProjectFinishDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerAddProjectFinishDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerAddProjectFinishDate.validate();
            }     
        });
        textFieldAddProjectLgac.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddProjectLgac.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddProjectLgac.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddProjectLgac.validate();
            }     
        });
        textFieldAddProjectParticipants.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddProjectParticipants.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddProjectParticipants.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddProjectParticipants.validate();
            }     
        });
        textAreaAddProjectDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddProjectDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddProjectDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddProjectDescription.validate();
            }     
        });           
    }
    
    public void showGuiProjectAddedSuccessfully(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Proyecto");        
        alert.setHeaderText("El Proyecto ha sido almacenado");        
        alert.showAndWait();
        showGuiConsultResearchProyects(actionEvent);
    }
    
    @FXML
    public void addNewProyect(ActionEvent actionEvent) {
        String projectTitle = null;
        Date projectStartDate = new Date();
        Date projectFinishDate = null;
        String projectLgac = null;        
        String projectParticipants = null;
        String projectDescription = null;
        String memberFullName = labelLoggedInUser.getText();
        String[] memberFullNames;
        boolean validDate = false;
        boolean existsProject = false; 
        if (textFieldAddProyecTitle.validate() && (!textFieldAddProyecTitle.getText().trim().equals(""))) {
            projectTitle = textFieldAddProyecTitle.getText(); 
            String expectedProjectTitle = null;
            try {
                expectedProjectTitle = PROJECT_DAO.consultProjectByTitle(projectTitle).getTitle();
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            existsProject = projectTitle.equals(expectedProjectTitle);
        }
        if (textFieldAddProjectLgac.validate() && (!textFieldAddProjectLgac.getText().trim().equals(""))) {
            projectLgac = textFieldAddProjectLgac.getText(); 
        }
        if (datePickerAddProjectFinishDate.validate()) {
            projectFinishDate = convertToDate(datePickerAddProjectFinishDate.getValue());
            if (projectStartDate.before(projectFinishDate)) {
                validDate = true;
            } else {                
                datePickerAddProjectFinishDate.setValue(null);
                datePickerAddProjectFinishDate.validate();
            }
        }
        if (textAreaAddProjectDescription.validate() && (!textAreaAddProjectDescription.getText().trim().equals(""))) {
            projectDescription = textAreaAddProjectDescription.getText();             
        }
              
        
        boolean validParticipants = false;
        if (textFieldAddProjectParticipants.validate() && (!textFieldAddProjectParticipants.getText().trim().equals(""))) {            
            projectParticipants = textFieldAddProjectParticipants.getText();
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
            for (String memberFullName1 : memberFullNames) {
                if (arrayListMemberRegistered.contains(memberFullName1)) {
                    matchCounter++;
                }
            }
            if (matchCounter == memberFullNames.length) {
                validParticipants = true;
            }     
            textFieldAddProjectParticipants.setStyle("-fx-text-fill: RED");
        }    
        
        if ((!existsProject) && (projectTitle != null)  && (projectFinishDate != null) && (projectLgac != null) && (projectDescription != null) && (validDate) && (validParticipants)) {
            Project newProject = new Project(projectTitle, projectStartDate, projectFinishDate, projectLgac, projectDescription, projectParticipants, memberFullName);
            try {
                PROJECT_DAO.addProject(newProject);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            showGuiProjectAddedSuccessfully(actionEvent);
        } else if (existsProject) {
            textFieldAddProyecTitle.setText(null);
        }
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
    
    @FXML 
    public void exitAddNewProject(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de registro de Proyecto");        
        alert.setHeaderText("¿Desea cancelar el registro del Proyecto?");
        alert.setContentText("El Proyecto no se guardará en el sistema.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            Stage stage = (Stage) buttonAddProjectExit.getScene().getWindow();
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
            Logger.getLogger(ControllerAddResearchProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
