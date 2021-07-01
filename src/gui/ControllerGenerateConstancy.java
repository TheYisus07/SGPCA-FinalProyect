package gui;

import businesslogic.BusinessException;
import businesslogic.ConstancyDAO;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Constancy;
import domain.Event;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerGenerateConstancy implements Initializable {
    private final ConstancyDAO CONSTANCY_DAO = new ConstancyDAO();
    private Event eventObject;
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextField textFieldRecognitionType;

    @FXML
    private JFXTextField textFieldInstitutionalMailReceivers;

    @FXML
    private JFXTextField textFieldInstitutionalMailValidator;

    @FXML
    private JFXTextField textFieldInstitutionalMailRedPient;

    @FXML
    private JFXTextArea textAreaDescription;

    @FXML
    private JFXTextArea textAreaRegulatoryNote;

    @FXML
    private Button buttonExitGenerateConstancy;

    @FXML
    private Button buttonGenerateConstancy;
    
    ObservableList<Constancy> constancyList;
    
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        constancyList = FXCollections.observableArrayList();
        validFieldsToAddWorkPlan();
    }  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void validFieldsToAddWorkPlan() {
        textFieldRecognitionType.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldRecognitionType.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldRecognitionType.validate();
            }
        });
        textFieldRecognitionType.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldRecognitionType.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldRecognitionType.validate();
            }
        });
        textFieldInstitutionalMailReceivers.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldInstitutionalMailReceivers.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailReceivers.validate();
            }
        });
        textFieldInstitutionalMailReceivers.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldInstitutionalMailReceivers.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailReceivers.validate();
            }
        });
        textFieldInstitutionalMailValidator.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldInstitutionalMailValidator.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailValidator.validate();
            }
        });
        textFieldInstitutionalMailValidator.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldInstitutionalMailValidator.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailValidator.validate();
            }
        });
        textFieldInstitutionalMailRedPient.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldInstitutionalMailRedPient.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailRedPient.validate();
            }
        });
        textFieldInstitutionalMailRedPient.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldInstitutionalMailRedPient.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldInstitutionalMailRedPient.validate();
            }
        });
        textAreaDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaDescription.validate();
            }
        });
        textAreaDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaDescription.validate();
            }
        });
        textAreaRegulatoryNote.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaRegulatoryNote.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaRegulatoryNote.validate();
            }
        });
        textAreaRegulatoryNote.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaRegulatoryNote.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaRegulatoryNote.validate();
            }
        });
    }
   public void closeWindow(ActionEvent event){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvent.fxml"));
            fXMLLoader.load();
            ControllerConsultEvent controllerConsultEvent = fXMLLoader.getController();
            String eventTitle = eventObject.getTittle();
            controllerConsultEvent.getEventTitleSelected( eventTitle);
            controllerConsultEvent.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerGenerateConstancy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   public void getEventForThisConstancy(Event event){
       eventObject = event;
       
   }
    @FXML
    void addConstancyOnAction(ActionEvent event) {
        try {
            String recognitionType = null;
            String description = null;
            String InstitutionalMailReceivers = null;
            String InstitutionalMailValidator = null;
            String InstitutionalMailRedPient = null;
            String regulatoryNote = null;
            String eventRegistred = null;
            
            if (textFieldRecognitionType.validate() && !textFieldRecognitionType.getText().trim().equals("")) {
                recognitionType = textFieldRecognitionType.getText();
            }
            if (textAreaDescription.validate() && !textAreaDescription.getText().trim().equals("")) {
                description = textAreaDescription.getText();
            }
            if (textFieldInstitutionalMailReceivers.validate() && !textFieldInstitutionalMailReceivers.getText().trim().equals("")) {
                InstitutionalMailReceivers = textFieldInstitutionalMailReceivers.getText();
            }
            if (textFieldInstitutionalMailValidator.validate() && !textFieldInstitutionalMailValidator.getText().trim().equals("")) {
                InstitutionalMailValidator = textFieldInstitutionalMailValidator.getText();
            }
            if (textFieldInstitutionalMailRedPient.validate() && !textFieldInstitutionalMailRedPient.getText().trim().equals("")) {
                InstitutionalMailRedPient = textFieldInstitutionalMailRedPient.getText();
            }
            if (textAreaRegulatoryNote.validate() && !textAreaRegulatoryNote.getText().trim().equals("")) {
                regulatoryNote = textAreaRegulatoryNote.getText();
            }
            eventRegistred = eventObject.getTittle();
            if ((recognitionType != null) && (description != null) && (InstitutionalMailReceivers != null) && (InstitutionalMailValidator != null) && (InstitutionalMailRedPient != null) && (regulatoryNote != null)) {
                Constancy constancyObject = new Constancy(recognitionType, description, InstitutionalMailReceivers, InstitutionalMailValidator, InstitutionalMailRedPient, regulatoryNote, eventRegistred);
                this.constancyList.add(constancyObject);
                CONSTANCY_DAO.generateConstancy(constancyObject);
                showConfirmationConstancyAlert();
                closeWindow(event);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void showConfirmationConstancyAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Se ha generado la constancia");
        alert.setTitle("Confirmacion");
        alert.setContentText(null);
        alert.showAndWait();
    }
    
    @FXML
    void getOutOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar el registro?");
        alert.setTitle("Salir");
        alert.setContentText("La constancia no se registrará");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
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
            stage.getIcons().add(new javafx.scene.image.Image("/images/sgpca.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerGenerateConstancy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
