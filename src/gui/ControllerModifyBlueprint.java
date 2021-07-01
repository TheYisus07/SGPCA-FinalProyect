package gui;

import businesslogic.BusinessException;
import businesslogic.BlueprintDAO;
import businesslogic.ProjectDAO;
import com.jfoenix.controls.JFXButton;
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

public class ControllerModifyBlueprint implements Initializable {
    
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    private enum ETracing {
        Avalado, Asignado, Terminado, Vigente; 
    }
    
    @FXML
    private JFXComboBox<String> comboBoxModifyBlueprintStatus; 
    
    @FXML
    private JFXComboBox<String> comboBoxModifyBlueprintProject;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextField textFieldModifyBlueprintCodirectors;
    
    @FXML
    private JFXDatePicker datePickerModifyBlueprintStartDate;
    
    @FXML
    private JFXTextField textFieldModifyBlueprintTitle;
    
    @FXML
    private JFXTextField textFieldModifyBlueprintLgac;
    
    @FXML
    private JFXTextArea textAreaModifyBlueprintDescription;
    
    @FXML
    private JFXTextField textFieldModifyBlueprintStudent;
    
    @FXML
    private JFXTextField textFieldModifyBlueprintModality;
    
    @FXML
    private JFXButton buttonModifyBlueprintExit;
    
    @FXML
    private Label labelBlueprintTitle;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToModifyBlueprint();
        fillComboBoxWithPossibleStatus();
        fillComboBoxWithBlueprint();
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
    
    public void getBlueprintTitle(String blueprintTitle) {
        labelBlueprintTitle.setText(blueprintTitle);
        fillBlueprintFields(blueprintTitle);
    }
    
    public void fillComboBoxWithPossibleStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ETracing.Asignado.toString());
        statusList.add(ETracing.Avalado.toString());
        statusList.add(ETracing.Vigente.toString());
        statusList.add(ETracing.Terminado.toString());
        comboBoxModifyBlueprintStatus.setItems(statusList);
    }
    
    public void fillComboBoxWithBlueprint() {
        ObservableList<String> blueprintList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < PROJECT_DAO.consultListOfProjects().size(); i++) {
                blueprintList.add(PROJECT_DAO.consultListOfProjects().get(i).getTitle());
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        comboBoxModifyBlueprintProject.setItems(blueprintList);
    }
    
    public void validFieldsToModifyBlueprint() {
        textFieldModifyBlueprintTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyBlueprintTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyBlueprintTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyBlueprintTitle.validate();
            }     
        });
        datePickerModifyBlueprintStartDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerModifyBlueprintStartDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerModifyBlueprintStartDate.validate();
            }     
        });
        textFieldModifyBlueprintLgac.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyBlueprintLgac.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyBlueprintLgac.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyBlueprintLgac.validate();
            }     
        });
        textFieldModifyBlueprintCodirectors.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyBlueprintCodirectors.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyBlueprintCodirectors.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyBlueprintCodirectors.validate();
            }     
        });
        textFieldModifyBlueprintStudent.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyBlueprintStudent.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyBlueprintStudent.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyBlueprintStudent.validate();
            }     
        });   
        textFieldModifyBlueprintModality.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyBlueprintModality.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyBlueprintModality.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldModifyBlueprintModality.validate();
            }     
        });   
        textAreaModifyBlueprintDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaModifyBlueprintDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaModifyBlueprintDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaModifyBlueprintDescription.validate();
            }     
        }); 
    }
    
    public boolean checkIfTheBlueprintExists() {
        String blueprintTitle = null;
        boolean existsBlueprint = false; 
        if (textFieldModifyBlueprintTitle.validate() && (!textFieldModifyBlueprintTitle.getText().trim().equals(""))) {
            blueprintTitle = textFieldModifyBlueprintTitle.getText(); 
            String expectedBlueprintTitle = null;
            try {
                expectedBlueprintTitle = BLUEPRINT_DAO.consultBlueprintByTitle(blueprintTitle).getTitle();
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            existsBlueprint = blueprintTitle.equals(expectedBlueprintTitle);
            if (blueprintTitle.equals(labelBlueprintTitle.getText())) {
                existsBlueprint = false;
            }
        }   
        return existsBlueprint;
    }
    
    @FXML
    public void modifyBlueprint(ActionEvent actionEvent) {
        String blueprintTitle = null;
        Date blueprintStartDate = null;        
        String blueprintLgac = null;        
        String blueprintCodirectors = null;
        String blueprintStudent = null;
        String blueprintModality = null;
        String blueprintDescription = null;
        
        Blueprint blueprint = null;
        try {
            blueprint = BLUEPRINT_DAO.consultBlueprintByTitle(labelBlueprintTitle.getText());
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        String blueprintStatus = blueprint.getStatus();
        String blueprintProjectTitle = blueprint.getProjectTitle();
               
        if (textFieldModifyBlueprintTitle.validate() && (!textFieldModifyBlueprintTitle.getText().trim().equals(""))) {
            blueprintTitle = textFieldModifyBlueprintTitle.getText(); 
        }
        
        if (textFieldModifyBlueprintLgac.validate() && (!textFieldModifyBlueprintLgac.getText().trim().equals(""))) {
            blueprintLgac = textFieldModifyBlueprintLgac.getText(); 
        }
        if (datePickerModifyBlueprintStartDate.validate()) {
            blueprintStartDate = convertToDate(datePickerModifyBlueprintStartDate.getValue());           
        }
        if (textFieldModifyBlueprintCodirectors.validate() && (!textFieldModifyBlueprintCodirectors.getText().trim().equals(""))) {
            blueprintCodirectors = textFieldModifyBlueprintCodirectors.getText();             
        }
        if (textFieldModifyBlueprintStudent.validate() && (!textFieldModifyBlueprintStudent.getText().trim().equals(""))) {
            blueprintStudent = textFieldModifyBlueprintStudent.getText();             
        }
        if (textFieldModifyBlueprintModality.validate() && (!textFieldModifyBlueprintModality.getText().trim().equals(""))) {
            blueprintModality = textFieldModifyBlueprintModality.getText();             
        }
        if (textAreaModifyBlueprintDescription.validate() && (!textAreaModifyBlueprintDescription.getText().trim().equals(""))) {
            blueprintDescription = textAreaModifyBlueprintDescription.getText();             
        }
                      
        if (!comboBoxModifyBlueprintProject.getSelectionModel().isEmpty()) {
            blueprintProjectTitle = comboBoxModifyBlueprintProject.getSelectionModel().getSelectedItem();
        }   
        if (!comboBoxModifyBlueprintStatus.getSelectionModel().isEmpty()) {
            blueprintStatus = comboBoxModifyBlueprintStatus.getSelectionModel().getSelectedItem();
        }
        boolean existsBlueprint = checkIfTheBlueprintExists();
        if ((!existsBlueprint) && (blueprintTitle != null)  && (blueprintStartDate != null) && (blueprintLgac != null) && (blueprintCodirectors != null) && (blueprintStudent != null) && (blueprintModality != null) && (blueprintDescription != null)) {
            try {
                Blueprint modifyBlueprint = new Blueprint(blueprintTitle, blueprintStartDate, blueprintLgac, blueprintStatus, blueprintModality, blueprintStudent, blueprintDescription, blueprintCodirectors, blueprintProjectTitle);
                BLUEPRINT_DAO.modifyBlueprint(modifyBlueprint, labelBlueprintTitle.getText());
                showGuiBlueprintModifiedSuccessfully(actionEvent);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        } else if (existsBlueprint) {
            textFieldModifyBlueprintTitle.setText(null);
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
    
    public void showGuiBlueprintModifiedSuccessfully(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Proyecto");        
        alert.setHeaderText("El Proyecto ha sido modificado");        
        alert.showAndWait();
        showGuiConsultResearchProyects(actionEvent);
    }
    
    public void fillBlueprintFields(String blueprintTitle) {
        Blueprint blueprint = null;
        try {
            blueprint = BLUEPRINT_DAO.consultBlueprintByTitle(blueprintTitle);
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        textFieldModifyBlueprintTitle.setText(blueprint.getTitle());
        textFieldModifyBlueprintLgac.setText(blueprint.getAssociatedLGAC());
        textFieldModifyBlueprintCodirectors.setText(blueprint.getCodirectors());
        textFieldModifyBlueprintStudent.setText(blueprint.getStudent());
        textFieldModifyBlueprintModality.setText(blueprint.getModality());
        textAreaModifyBlueprintDescription.setText(blueprint.getDescription());
        datePickerModifyBlueprintStartDate.setValue(convertToLocalDate(blueprint.getStartDate()));
    }
    
    @FXML
    public void exitModifyBlueprint(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de modificación de Anteproyecto");        
        alert.setHeaderText("¿Desea cancelar la modificación del Anteproyecto?");
        alert.setContentText("El Anteproyecto no sufrirá ningún cambio.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            Stage stage = (Stage) buttonModifyBlueprintExit.getScene().getWindow();
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
            Logger.getLogger(ControllerModifyBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
