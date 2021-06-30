package gui;

import businesslogic.BusinessException;
import businesslogic.BlueprintDAO;
import businesslogic.ProjectDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Blueprint;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerAddBlueprint implements Initializable {
    
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextField textFieldAddBlueprintCodirectors;
    
    @FXML
    private JFXDatePicker datePickerAddBlueprintStartDate;
    
    @FXML
    private JFXTextField textFieldAddBlueprintTitle;
    
    @FXML
    private JFXTextField textFieldAddBlueprintLgac;
    
    @FXML
    private JFXTextArea textAreaAddBlueprintDescription;
    
    @FXML
    private JFXTextField textFieldAddBlueprintStudent;
    
    @FXML
    
    private JFXTextField textFieldAddBlueprintModality;
    
    @FXML
    private JFXComboBox<String> comboBoxAddBlueprintStatus;
    
    @FXML
    private JFXComboBox<String> comboBoxBlueprintProject;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToAddBlueprint();
        Date startDate = new Date();
        datePickerAddBlueprintStartDate.setValue(convertToLocalDate(startDate));
        fillComboBoxWithProjectTitles();
        fillComboBoxWithPossibleStatus();
    }
    
    public void fillComboBoxWithPossibleStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ControllerAddNewEvidence.ETracing.Asignado.toString());
        comboBoxAddBlueprintStatus.setItems(statusList);
    }
    
    public void fillComboBoxWithProjectTitles() {
        ObservableList<String> projectList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < PROJECT_DAO.consultListOfProjects().size(); i++) {
                String projectTitle = PROJECT_DAO.consultListOfProjects().get(i).getTitle();
                projectList.add(projectTitle);    
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        comboBoxBlueprintProject.setItems(projectList); 
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
    
    public void validFieldsToAddBlueprint() {
        textFieldAddBlueprintTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBlueprintTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBlueprintTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBlueprintTitle.validate();
            }     
        });
        textFieldAddBlueprintLgac.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBlueprintLgac.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBlueprintLgac.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBlueprintLgac.validate();
            }     
        });
        textFieldAddBlueprintCodirectors.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBlueprintCodirectors.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBlueprintCodirectors.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBlueprintCodirectors.validate();
            }     
        });
        textFieldAddBlueprintStudent.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBlueprintStudent.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBlueprintStudent.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBlueprintStudent.validate();
            }     
        });
        textFieldAddBlueprintModality.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBlueprintModality.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBlueprintModality.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBlueprintModality.validate();
            }     
        }); 
        comboBoxAddBlueprintStatus.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxAddBlueprintStatus.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxAddBlueprintStatus.validate();
            }     
        });
        textAreaAddBlueprintDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddBlueprintDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddBlueprintDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddBlueprintDescription.validate();
            }     
        });        
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
            Logger.getLogger(ControllerAddBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showGuiExitAddProject(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación para salir de agregar Anteproyecto");        
        alert.setHeaderText("El Anteproyecto no se registrará en el sistema");        
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsulResearchProjects(actionEvent);
        }
    }
    
    public void showGuiBlueprintAddedSuccessfully(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Anteproyecto");        
        alert.setHeaderText("El Anteproyecto ha sido almacenado");        
        alert.showAndWait();
        showGuiConsulResearchProjects(actionEvent);
    }
    
    @FXML
    public void addNewBlueprint(ActionEvent actionEvent) {
        String blueprintTitle = null;
        Date bluprintStartDate = new Date();
        String blueprintLgac = null;        
        String blueprintCodirectors = null;
        String blueprintStudent = null;
        String blueprintModality = null;
        String blueprintStatus = null;
        String blueprintDescription = null;
        String blueprintProject = null;        
        boolean existsBlueprint = false; 
        if (textFieldAddBlueprintTitle.validate() && (!textFieldAddBlueprintTitle.getText().trim().equals(""))) {
            blueprintTitle = textFieldAddBlueprintTitle.getText(); 
            String expectedBlueprintTitle = null;
            try {
                expectedBlueprintTitle = BLUEPRINT_DAO.consultBlueprintByTitle(blueprintTitle).getTitle();
            } catch (BusinessException ex) {                
                showAlertDataBaseConnection();
            }
            existsBlueprint = blueprintTitle.equals(expectedBlueprintTitle);
        }
        if (textFieldAddBlueprintLgac.validate() && (!textFieldAddBlueprintLgac.getText().trim().equals(""))) {
            blueprintLgac = textFieldAddBlueprintLgac.getText(); 
        }             
        if (textFieldAddBlueprintCodirectors.validate() && (!textFieldAddBlueprintCodirectors.getText().trim().equals(""))) {
            blueprintCodirectors = textFieldAddBlueprintCodirectors.getText();             
        }        
        if (textFieldAddBlueprintStudent.validate() && (!textFieldAddBlueprintStudent.getText().trim().equals(""))) {
            blueprintStudent = textFieldAddBlueprintStudent.getText();             
        }       
        if (textFieldAddBlueprintModality.validate() && (!textFieldAddBlueprintModality.getText().trim().equals(""))) {
            blueprintModality = textFieldAddBlueprintModality.getText();             
        }       
        if (comboBoxAddBlueprintStatus.validate()) {
            blueprintStatus = comboBoxAddBlueprintStatus.getSelectionModel().getSelectedItem();
        }       
        if (textAreaAddBlueprintDescription.validate() && (!textAreaAddBlueprintDescription.getText().trim().equals(""))) {
            blueprintDescription = textAreaAddBlueprintDescription.getText();             
        }        
        if (comboBoxBlueprintProject.getSelectionModel() != null) {
            blueprintProject = comboBoxBlueprintProject.getSelectionModel().getSelectedItem();
        }       
        
        if ((!existsBlueprint) && (blueprintTitle != null)  && (blueprintLgac != null) && (blueprintCodirectors != null) && (blueprintStudent != null) && (blueprintModality != null) && (blueprintStatus != null) && (blueprintDescription != null)) {
            Blueprint newBlueprint = new Blueprint(blueprintTitle, bluprintStartDate, blueprintLgac, blueprintStatus, blueprintModality, blueprintStudent, blueprintDescription, blueprintCodirectors);
            if (blueprintProject != null) {
                newBlueprint.setProjectTitle(blueprintProject);
            }    
            try {
                BLUEPRINT_DAO.addBlueprint(newBlueprint);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            showGuiBlueprintAddedSuccessfully(actionEvent);
        } else if (existsBlueprint) {
            textFieldAddBlueprintTitle.setText(null);
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
            Logger.getLogger(ControllerAddBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}