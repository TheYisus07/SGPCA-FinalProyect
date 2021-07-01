package gui;

import businesslogic.BusinessException;
import businesslogic.BlueprintDAO;
import businesslogic.ProjectDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Blueprint;
import domain.Project;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerConsultProject implements Initializable {
    
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();    
    
    @FXML
    private JFXTextField textFieldConsultProjectParticipants;
    
    @FXML
    private JFXDatePicker datePickerConsultProjectStartDate;
    
    @FXML
    private JFXDatePicker datePickerConsultProjectFinishDate;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private Label labelConsultProjectResponsable;
    
    @FXML
    private JFXTextField textFieldConsultProjectTitle;
    
    @FXML
    private JFXTextField textFieldConsultProjectLgac;
    
    @FXML
    private JFXTextArea textAreaConsultProjectDescription;
    
    @FXML
    private TableView<Blueprint> tableViewConsultProjectBlueprint;
    
    @FXML
    private TableColumn<Blueprint, String> tableColumnConsultProjectBlueprintStatus;
    
    @FXML
    private TableColumn<Blueprint, String> tableColumnConsultProjectBlueprintTitle;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedBlueprintTitle();
    }
    
    public void showGuiConsulResearchProjects(ActionEvent actionEvent) {        
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultResearchProjects.fxml"));
            fXMLLoader.load();            
            ControllerConsultResearchProjects controllerConsultResearchProjects = fXMLLoader.getController();
            controllerConsultResearchProjects.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene); 
            stage.setResizable(false);
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showGuiAddNewBlueprint(ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLAddBlueprint.fxml"));
            fXMLLoader.load();            
            ControllerAddBlueprint controllerAddBlueprint = fXMLLoader.getController();
            controllerAddBlueprint.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);       
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void getProjectTitle(String projectTitle) {
        textFieldConsultProjectTitle.setText(projectTitle);
        fillSelectedProjectTextfields();
        fillBlueprintTable(projectTitle);
    }
    
    public void fillSelectedProjectTextfields() {
        String projectTitle = textFieldConsultProjectTitle.getText();
        Project projectConsulted = null;
        try {
            projectConsulted = PROJECT_DAO.consultProjectByTitle(projectTitle);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        labelConsultProjectResponsable.setText("Registrado por: " + projectConsulted.getMemberFullName());
        datePickerConsultProjectStartDate.setValue(convertToLocalDate(projectConsulted.getStartDate()));
        datePickerConsultProjectFinishDate.setValue(convertToLocalDate(projectConsulted.getEstimedFinishDate()));
        textFieldConsultProjectLgac.setText(projectConsulted.getAssociatesIGAC());
        textFieldConsultProjectParticipants.setText(projectConsulted.getParticipants());
        textAreaConsultProjectDescription.setText(projectConsulted.getDescripcion());
    }
    
    @FXML
    public void modifyProjectSelected(ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyProject.fxml"));
            fXMLLoader.load();            
            ControllerModifyProject controllerModifyProject = fXMLLoader.getController();
            controllerModifyProject.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            controllerModifyProject.getProjectTitle(textFieldConsultProjectTitle.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);     
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillBlueprintTable(String projectTitle) {
        ObservableList<Blueprint> blueprintList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < BLUEPRINT_DAO.consultListOfBlueprint().size(); i++) {
                String expectedProjectTilte = BLUEPRINT_DAO.consultListOfBlueprint().get(i).getProjectTitle();
                if (expectedProjectTilte != null  && expectedProjectTilte.equals(projectTitle)) {
                    blueprintList.add(BLUEPRINT_DAO.consultListOfBlueprint().get(i));
                }                
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnConsultProjectBlueprintTitle);
        tableColumnConsultProjectBlueprintTitle.setCellValueFactory(new PropertyValueFactory("title"));
        tableColumnConsultProjectBlueprintStatus.setCellValueFactory(new PropertyValueFactory("status"));
        tableViewConsultProjectBlueprint.setItems(blueprintList);
    }
    
    public void showGuiBlueprintSelected(String blueprintTitle) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewConsultProjectBlueprint.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultBlueprint.fxml"));
            fXMLLoader.load();
            ControllerConsultBlueprint controllerConsultBlueprint = fXMLLoader.getController();
            controllerConsultBlueprint.getBlueprintTitle(blueprintTitle);
            controllerConsultBlueprint.getMemberFullName(memberFullName, memberEmail);
            controllerConsultBlueprint.getBlueprintTitle(blueprintTitle);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false); 
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void selectedBlueprintTitle() {
        tableViewConsultProjectBlueprint.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Blueprint> observable, Blueprint oldBlueprint, Blueprint newBlueprint) -> {
            if (tableViewConsultProjectBlueprint.getSelectionModel().getSelectedItem() != null) {
                String blueprintTitle = tableViewConsultProjectBlueprint.getSelectionModel().getSelectedItem().getTitle();
                showGuiBlueprintSelected(blueprintTitle);                   
            }
        });
    }
    
    public void showGuiExitQueryProject(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación para salir del Proyecto: " + textFieldConsultProjectTitle.getText());        
        alert.setHeaderText("¿Desea salir de la consulta del proyecto?");        
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsulResearchProjects(actionEvent);
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
            Logger.getLogger(ControllerConsultProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}