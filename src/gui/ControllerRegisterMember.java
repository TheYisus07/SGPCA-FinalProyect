package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import domain.Member;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
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
 * @author Javier Blas
 */
public class ControllerRegisterMember implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private Member member = new Member();
    
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML 
    private JFXComboBox comboBoxMemberPosition;
    
    @FXML 
    private JFXComboBox comboBoxMemberTypeOfTeaching;
    
    @FXML 
    private JFXComboBox comboBoxMemberPRODEP;
    
    @FXML
    private JFXTextField textFieldMemberName;
    
    @FXML
    private JFXTextField textFieldMemberCURP;
    
    @FXML
    private JFXTextField textFieldMemberPhoneNumber;
    
    @FXML
    private JFXTextField textFieldMemberInstitutionalEmail;
    
    @FXML
    private JFXTextField textFieldMemberDiscipline;
    
    @FXML
    private JFXTextField textFieldMemberGradeStudy;
    
    @FXML
    private JFXTextField textFieldMemberAreaStudy;
    
    @FXML
    private JFXTextField textFieldMemberLGAC;
    
    @FXML
    private JFXTextField textFieldMemberIES;
    
    @FXML
    private Label labelMemberKeyCode;
    
    @FXML
    private JFXDatePicker datePickerMemberDateOfBirth;
    
    private ObservableList<Member> memberList;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxWihtMemberPosition();
        fillComboBoxWihtMemberTypeOfTeaching();
        fillComboBoxWihtMemberPRODEP();
        validFieldsToRegisterMember();
        
        memberList = FXCollections.observableArrayList();
    }  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        fillLabelKeycode();
    }
    
    public void validFieldsToRegisterMember() {
        textFieldMemberName.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberName.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberName.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberName.validate();
            }
        });
        
        textFieldMemberCURP.getValidators().add(TEXT_FIELD_VALIDATION.getCURP_VALIDATOR());
        textFieldMemberCURP.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_CURP());
        textFieldMemberCURP.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberCURP.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberCURP.validate();
            }
        });
        
        textFieldMemberGradeStudy.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberGradeStudy.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberGradeStudy.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberGradeStudy.validate();
            }
        });
        
        textFieldMemberIES.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberIES.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberIES.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberIES.validate();
            }
        });
        
        comboBoxMemberPosition.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxMemberPosition.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxMemberPosition.validate();
            }
        });
        
        datePickerMemberDateOfBirth.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerMemberDateOfBirth.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerMemberDateOfBirth.validate();
            }
        });
        
        comboBoxMemberTypeOfTeaching.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxMemberTypeOfTeaching.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxMemberTypeOfTeaching.validate();
            }
        }); 
        
        textFieldMemberInstitutionalEmail.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberInstitutionalEmail.getValidators().add(TEXT_FIELD_VALIDATION.getEMAIL_VALIDATOR());
        textFieldMemberInstitutionalEmail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberInstitutionalEmail.validate();
            }
        });
        
        textFieldMemberPhoneNumber.getValidators().add(TEXT_FIELD_VALIDATION.getPHONE_VALIDATOR());
        textFieldMemberPhoneNumber.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_PHONE());
        textFieldMemberPhoneNumber.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberPhoneNumber.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldMemberPhoneNumber.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberPhoneNumber.validate();
            }
        });
        
        textFieldMemberDiscipline.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberDiscipline.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberDiscipline.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberDiscipline.validate();
            }
        });
        
        textFieldMemberAreaStudy.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberAreaStudy.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberAreaStudy.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberAreaStudy.validate();
            }
        });
        
        textFieldMemberLGAC.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldMemberLGAC.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMemberLGAC.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMemberLGAC.validate();
            }
        });
        
        comboBoxMemberPRODEP.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxMemberPRODEP.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxMemberPRODEP.validate();
            }
        });
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public Date convertToDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void fillComboBoxWihtMemberPosition() {
        ObservableList<String> listPositions = FXCollections.observableArrayList("Integrante","Colaborador");
        comboBoxMemberPosition.setItems(listPositions);
    }
 
    public void fillComboBoxWihtMemberTypeOfTeaching() {
        ObservableList<String> listTypeOfTeaching = FXCollections.observableArrayList("Tiempo completo","Medio tiempo","Investigación");
        comboBoxMemberTypeOfTeaching.setItems(listTypeOfTeaching);
    }
    
    public void fillComboBoxWihtMemberPRODEP() {
        ObservableList<String> listPRODEP = FXCollections.observableArrayList("Sí","No");
        comboBoxMemberPRODEP.setItems(listPRODEP);
    }
    
    public void fillLabelKeycode() {
        String nameMember = labelLoggedInUser.getText();
        String keyCodeMember = "";
        try {
            keyCodeMember = MEMBER_DAO.consultMember(nameMember).getKeycodeAcademicGroup();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }  
        labelMemberKeyCode.setText(keyCodeMember);
    }
    
    @FXML
    public void RegisterMemberButton(ActionEvent event){
        String memberName = null;
        String memberCURP = null;
        long memberPhoneNumber = 0;
        String memberInstitutionalEmail = null;
        String memberDiscipline = null;
        String memberGradeStudy = null;
        String memberAreaStudy = null;
        String memberTypeOfTeaching = null;
        String memberLGAC = null;
        String memberIES = null;
        String memberPRODEP = null;
        String memberPosition = null;  
        String memberKeyCode = labelMemberKeyCode.getText();
        Date memberDate = null;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
        Date memberDateMinimum = null;
        try {
            memberDateMinimum = formatDate.parse("2002/01/01");
        } catch (ParseException ex) {
            Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean isValidatePhoneNumber = false;
        boolean validDate = false;
        
        
        if (datePickerMemberDateOfBirth.validate()) {
            memberDate = convertToDate(datePickerMemberDateOfBirth.getValue());
            if (memberDateMinimum.after(memberDate)) {
                validDate = true;
            } else {
                datePickerMemberDateOfBirth.setValue(null);
                datePickerMemberDateOfBirth.validate();
            }
        }
        
        if (textFieldMemberName.validate() && !textFieldMemberName.getText().trim().equals("")) {
            memberName = textFieldMemberName.getText();
        }
        
        if (comboBoxMemberPosition.validate() && comboBoxMemberPosition.getSelectionModel() != null) {
            memberPosition = (String) comboBoxMemberPosition.getSelectionModel().getSelectedItem();
        }
        
        if (comboBoxMemberPRODEP.validate() && comboBoxMemberPRODEP.getSelectionModel() != null) {
            memberPRODEP = (String) comboBoxMemberPRODEP.getSelectionModel().getSelectedItem();
        }
        
        if (comboBoxMemberTypeOfTeaching.validate() && comboBoxMemberTypeOfTeaching.getSelectionModel() != null) {
            memberTypeOfTeaching = (String) comboBoxMemberPRODEP.getSelectionModel().getSelectedItem();
        }
        
        if (textFieldMemberCURP.validate() && !textFieldMemberCURP.getText().trim().equals("")) {
            memberCURP = textFieldMemberCURP.getText();
        }

        if (textFieldMemberPhoneNumber.validate()) {
            try {
                memberPhoneNumber = Long.parseLong(textFieldMemberPhoneNumber.getText().trim());
                isValidatePhoneNumber = true;
            } catch (NumberFormatException ex) {
                Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
            }  
        } else {
            textFieldMemberPhoneNumber.setText(null);
            textFieldMemberPhoneNumber.validate();
        }
                     
        if (textFieldMemberInstitutionalEmail.validate() && !textFieldMemberInstitutionalEmail.getText().trim().equals("")) {
            memberInstitutionalEmail = textFieldMemberInstitutionalEmail.getText();
        }
        
        if (textFieldMemberDiscipline.validate() && !textFieldMemberDiscipline.getText().trim().equals("")) {
            memberDiscipline = textFieldMemberDiscipline.getText();
        }
        
        if (textFieldMemberGradeStudy.validate() && !textFieldMemberGradeStudy.getText().trim().equals("")) {
            memberGradeStudy = textFieldMemberGradeStudy.getText();
        }
        
        if (textFieldMemberAreaStudy.validate() && !textFieldMemberAreaStudy.getText().trim().equals("")) {
            memberAreaStudy = textFieldMemberAreaStudy.getText();
        }
        
        if (textFieldMemberLGAC.validate() && !textFieldMemberLGAC.getText().trim().equals("")) {
            memberLGAC = textFieldMemberLGAC.getText();
        }
        
        if (textFieldMemberIES.validate() && !textFieldMemberIES.getText().trim().equals("")) {
            memberIES = textFieldMemberIES.getText();
        }
               
        if ((memberName != null) &&(memberCURP != null) && (isValidatePhoneNumber) && (memberInstitutionalEmail != null) && (memberDiscipline != null) && (memberGradeStudy != null) && (memberAreaStudy != null) && (memberLGAC != null) && (memberIES != null) && validDate) {
            String memberPhoneNumberString = String.valueOf(memberPhoneNumber);
            member = new Member(memberName, memberDate, memberCURP, memberPhoneNumberString, memberInstitutionalEmail, memberDiscipline, memberGradeStudy, memberAreaStudy, memberTypeOfTeaching, memberLGAC, memberIES, memberPRODEP, memberPosition, memberKeyCode);
            recordStatus(memberName);
            closeWindow(event);
        } 
    }
    
    public void recordStatus(String memberName) {
        Member memberConsult = null;
        try {
            memberConsult = MEMBER_DAO.consultMember(memberName);  
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }        
        String memberNameConsult = memberConsult.getFullName();
        
        if (!memberName.equals(memberNameConsult) ) {
           successfulRegistration();
        } else {
           existingMember();
        }
    }
    
    public void successfulRegistration() {
        int confirmationRegister = 0;
        this.memberList.add(member);
        try {
            confirmationRegister = MEMBER_DAO.registerMember(member);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }  
        if (confirmationRegister == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("El miembro ha sido registrado exitosamente");
            alert.showAndWait();
        } 
    }
    
    public void existingMember() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("El miembro ya existe");
        alert.showAndWait();
    }       
    
    @FXML
    public void cancelOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cancelar registro");        
        alert.setHeaderText("¿Desea salir del registro de miembro?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiMemberList(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText(), event);
        }
    }
    
    public void showGuiMemberList(String memberFullName, String memberEmail, ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMemberList.fxml"));
            fXMLLoader.load();
            ControllerConsultMemberList controllerConsultMemberList = fXMLLoader.getController();
            controllerConsultMemberList.getMemberFullName(memberFullName, memberEmail);
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
            Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeWindow(ActionEvent event){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMemberList.fxml"));
            fXMLLoader.load();
            ControllerConsultMemberList controllerConsultMemberList = fXMLLoader.getController();
            controllerConsultMemberList.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
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