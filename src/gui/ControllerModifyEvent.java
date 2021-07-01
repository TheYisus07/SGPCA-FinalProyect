package gui;

import businesslogic.BusinessException;
import businesslogic.EventDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import domain.Event;
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
public class ControllerModifyEvent implements Initializable {
    private final EventDAO EVENT_DAO = new EventDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private JFXTextField textFieldTitle;

    @FXML
    private JFXTextField textFieldPlace;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private JFXComboBox<String> comboBoxResponsable;

    @FXML
    private JFXComboBox<String> comboBoxType;

    @FXML
    private JFXComboBox<String> comboBoxPrivacy;

    @FXML
    private JFXDatePicker datePickerRegistrationDate;

    @FXML
    private JFXDatePicker datePickerEventDate;

    @FXML
    private Button buttonExitModifyEvent;

    @FXML
    private Button buttonUpDateEvent;
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> typeList = FXCollections.observableArrayList("seminario", "hackaton", "presentación", "conisof");
        comboBoxType.setItems(typeList);
        ObservableList<String> privacyList = FXCollections.observableArrayList("alumnos", "docentes", "alumnos y docentes", "cuerpo academico");
        comboBoxPrivacy.setItems(privacyList);
        fillComboBoxWithMember();
        validFieldsToAddWorkPlan();
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
    
    
    public void validFieldsToAddWorkPlan() {
        textFieldTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldTitle.validate();
            }
        });
        textFieldTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldTitle.validate();
            }
        });
        textFieldPlace.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldPlace.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldPlace.validate();
            }
        });
        textFieldPlace.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldPlace.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldPlace.validate();
            }
        });
        datePickerRegistrationDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerRegistrationDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerRegistrationDate.validate();
            }
        });
        datePickerEventDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerEventDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerEventDate.validate();
            }
        });
        comboBoxType.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxType.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxType.validate();
            }
        });
        comboBoxResponsable.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxResponsable.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxResponsable.validate();
            }
        });
        comboBoxPrivacy.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxPrivacy.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxPrivacy.validate();
            }
        });
    }
    public void closeWindow(ActionEvent event){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEventHistory.fxml"));
            fXMLLoader.load();
            ControllerConsultEventHistory controllerConsultEventHistory = fXMLLoader.getController();
            controllerConsultEventHistory.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultEventHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillComboBoxWithMember(){
        ObservableList<String> responsableList = FXCollections.observableArrayList();
        comboBoxResponsable.setItems(responsableList);
        try {
            for (int index = 0; index < MEMBER_DAO.consultMemberList().size(); index++){
                String memberName = MEMBER_DAO.consultMemberList().get(index).getFullName();
                responsableList.add(memberName);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
    }
    
    public void fillModifyEventDates( String eventTitle){
        try {
            textFieldTitle.setPromptText(eventTitle + "");
            textFieldTitle.setText(eventTitle + "");
            Event eventConsulted;
            eventConsulted = EVENT_DAO.consultEvent(eventTitle);
            String responsable = eventConsulted.getResponsable();
            String type = eventConsulted.getType();
            String place = eventConsulted.getPlace();
            String privacy = eventConsulted.getPrivacy();
            LocalDate registrationDateForValue = convertToLocalDate(eventConsulted.getRegistrationDate());
            LocalDate eventDateForValue = convertToLocalDate(eventConsulted.getEventDate());
            datePickerRegistrationDate.setValue(registrationDateForValue);
            datePickerEventDate.setValue(eventDateForValue);
            textFieldPlace.setText(place + "");
            comboBoxPrivacy.setValue(privacy + "");
            comboBoxType.setValue(type + "");
            comboBoxResponsable.setValue(responsable + "");
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }

    @FXML
    public void upDateEventOnAction(ActionEvent event) {
        String titleOfTheOldEvent = null;
        String title = null;
        String place = null;
        Date registrationDate = null;
        Date eventDate = null;
        String textcomboBoxResponsable = null;
        String textcomboBoxType =null;
        String textcomboBoxPrivacy = null;
        boolean validDate = false;

        titleOfTheOldEvent = textFieldTitle.getPromptText();

        if (textFieldTitle.validate() && !textFieldTitle.getText().trim().equals("")) {
            title = this.textFieldTitle.getText();
        }

        if (textFieldPlace.validate() && !textFieldPlace.getText().trim().equals("")) {
            place = this.textFieldPlace.getText();
        }

        if (datePickerRegistrationDate.validate() && datePickerEventDate.validate()) {
            registrationDate = convertToDate(datePickerRegistrationDate.getValue());
            eventDate = convertToDate(datePickerEventDate.getValue());
            if (registrationDate.before(eventDate)) {
                validDate = true;
            } else {
                datePickerEventDate.setValue(null);
                datePickerEventDate.validate();
            }
        }

        if (comboBoxResponsable.validate() && !comboBoxResponsable.getSelectionModel().getSelectedItem().trim().equals("")) {
            textcomboBoxResponsable = this.comboBoxResponsable.getSelectionModel().getSelectedItem();
        }
        if (comboBoxType.validate() && !comboBoxType.getSelectionModel().getSelectedItem().trim().equals("")) {
            textcomboBoxType = this.comboBoxType.getSelectionModel().getSelectedItem();
        }
        if (comboBoxPrivacy.validate() && !comboBoxPrivacy.getSelectionModel().getSelectedItem().trim().equals("")) {
            textcomboBoxPrivacy = this.comboBoxPrivacy.getSelectionModel().getSelectedItem();
        }
        boolean eventSaved = false;
        if ((title != null) && (place != null) && (registrationDate != null) && (eventDate != null) && validDate) {
            Event eventObject = new Event(title, textcomboBoxType, registrationDate, place, eventDate, textcomboBoxPrivacy, textcomboBoxResponsable);
            eventSaved = saveEvent(titleOfTheOldEvent, eventObject, validDate);
        }
        if (eventSaved) {
            closeWindow(event);
        }      
    }
    
    public boolean saveEvent(String titleOfTheOldEvent, Event eventObject, boolean validDate) {
        String title = eventObject.getTittle();
        boolean result = false;
        boolean titleFound = searchEventTitleDuplicated(title, titleOfTheOldEvent);
        if (titleFound && validDate) {
            try {
                EVENT_DAO.modifyEvent(eventObject,titleOfTheOldEvent);
                showCofirmationAlert();
                result = true;
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        } else if (titleFound == false) {
            showEventFoundAlert();
            result = false;
        } else if (validDate){
            result = false;
        }
        return result;
    }
    
    public boolean searchEventTitleDuplicated(String title, String titleOfTheOldEvent) {
        String titleEventConsulted = null;
        boolean titleValid = false;
        try {
            titleEventConsulted = EVENT_DAO.consultEvent(title).getTittle();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        if (!title.equals(titleOfTheOldEvent) && title.equals(titleEventConsulted)) {
            titleValid = false;
        } else if (title.equals(titleOfTheOldEvent) && title.equals(titleEventConsulted)) {
            titleValid = true;
        } else if (title.equals(titleOfTheOldEvent) && !title.equals(titleEventConsulted)) {
            titleValid = true;
        } else if (!title.equals(titleOfTheOldEvent) && !title.equals(titleEventConsulted)) {
            titleValid = true;
        }
        return titleValid;
    }
    
    public void showCofirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("El Evento ha sido guardado exitosamente");
        alert.setTitle("Confirmacion");
        alert.setContentText(null);
        alert.showAndWait();
    }
    
    public void showEventFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Ya existe un evento con este nombre");
        alert.showAndWait();
    }
    

    @FXML
    public void getOutOfModifyEventOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas regresar al historial?");
        alert.setTitle("Salir");
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
            Logger.getLogger(ControllerModifyEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
