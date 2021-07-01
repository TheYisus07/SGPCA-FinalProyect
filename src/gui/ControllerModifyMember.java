package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import domain.Member;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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
 * FXML Controller class
 *
 * @author Javier Blas
 */
public class ControllerModifyMember implements Initializable {
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private Member memberObject = new Member();
    
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML 
    private JFXComboBox comboBoxMemberPosition;
    
    @FXML 
    private JFXComboBox comboBoxMemberTypeOfTeaching;
            
    @FXML 
    private JFXComboBox comboBoxMemberPRODEP;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
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
    
    @FXML
    private JFXButton buttonCancelModify;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxWihtMemberPosition();
        fillComboBoxWihtMemberTypeOfTeaching();
        fillComboBoxWihtMemberPRODEP();
        validFieldsToRegisterMember();
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

    public void fillComboBoxWihtMemberPosition(){
        ObservableList<String> listPositions = FXCollections.observableArrayList("Integrante","Colaborador");
        comboBoxMemberPosition.setItems(listPositions);
    }
 
    public void fillComboBoxWihtMemberTypeOfTeaching(){
        ObservableList<String> listTypeOfTeaching = FXCollections.observableArrayList("Tiempo completo","Medio tiempo","Investigación");
        comboBoxMemberTypeOfTeaching.setItems(listTypeOfTeaching);
    }
    
    public void fillComboBoxWihtMemberPRODEP(){
        ObservableList<String> listPRODEP = FXCollections.observableArrayList("Sí","No");
        comboBoxMemberPRODEP.setItems(listPRODEP);
    }
    
    public void fillLabelKeycode() {
        String nameMember = labelLoggedInUser.getText();
        Member memberConsulted = null;
        String keyCodeMember = "";
        try {
            memberConsulted = MEMBER_DAO.consultMember(nameMember);
            keyCodeMember = MEMBER_DAO.consultMember(nameMember).getKeycodeAcademicGroup();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }  
        labelMemberKeyCode.setText(keyCodeMember);
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
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
            Logger.getLogger(ControllerScheduleMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillModifyMember(String memberName){        
        textFieldMemberName.setPromptText(memberName + "");
        textFieldMemberName.setText(memberName + "");
        
        Member memberConsulted = null;
        try {
            memberConsulted = MEMBER_DAO.consultMember(memberName);
            LocalDate dateOfBirth = convertToLocalDate(memberConsulted.getDateOfBirth());
            String curp = memberConsulted.getCurp();
            String phoneNumber = memberConsulted.getPhoneNumber();
            String institutionalMail = memberConsulted.getInstitutionalMail();
            String discipline = memberConsulted.getDiscipline();
            String studyGrade = memberConsulted.getStudyGrade();
            String studyArea = memberConsulted.getStudyArea();
            String typeOfTeaching = memberConsulted.getTypeOfTeaching();
            String lgac = memberConsulted.getLgac();
            String ies = memberConsulted.getIes();
            String prodep = memberConsulted.getProdepParticipation();
            String position =  memberConsulted.getPosition();

            comboBoxMemberPosition.setValue(position);
            comboBoxMemberPRODEP.setValue(prodep);
            textFieldMemberIES.setText(ies);
            textFieldMemberLGAC.setText(lgac);
            comboBoxMemberTypeOfTeaching.setValue(typeOfTeaching);
            textFieldMemberAreaStudy.setText(studyArea);
            textFieldMemberGradeStudy.setText(studyGrade);
            textFieldMemberDiscipline.setText(discipline);
            textFieldMemberInstitutionalEmail.setText(institutionalMail);
            textFieldMemberPhoneNumber.setText(phoneNumber);
            textFieldMemberCURP.setText(curp);
            datePickerMemberDateOfBirth.setValue(dateOfBirth);   
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }  
      
    }
    
    @FXML
    public void updateMemberOnAction(ActionEvent event) {
        try{
            String memberNameOld = null;
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
            
            memberNameOld = textFieldMemberName.getPromptText();
            
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
                memberTypeOfTeaching = (String) comboBoxMemberTypeOfTeaching.getSelectionModel().getSelectedItem();
            }

            if (textFieldMemberCURP.validate() && !textFieldMemberCURP.getText().trim().equals("")) {
                memberCURP = textFieldMemberCURP.getText();
            }

            if (textFieldMemberPhoneNumber.validate()) {
                try {
                    memberPhoneNumber = Long.parseLong(textFieldMemberPhoneNumber.getText().trim());
                } catch (NumberFormatException ex) {
                    Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
                }
                isValidatePhoneNumber = true;
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
            
            boolean savedSuccessfully = false;
            
            if ((memberName != null) && (memberCURP != null) && (isValidatePhoneNumber) && (memberInstitutionalEmail != null) && (memberDiscipline != null) && (memberGradeStudy != null) && (memberAreaStudy != null) && (memberLGAC != null) && (memberIES != null) && validDate) {
                String memberPhoneNumberString = String.valueOf(memberPhoneNumber);
                memberObject = new Member(memberName, memberDate, memberCURP, memberPhoneNumberString, memberInstitutionalEmail, memberDiscipline, memberGradeStudy, memberAreaStudy, memberTypeOfTeaching, memberLGAC, memberIES, memberPRODEP, memberPosition, memberKeyCode);
                savedSuccessfully = saveMember(memberNameOld, memberObject);
            } 
            
            if (savedSuccessfully) {
                closeWindow(event);
            }
            
            } catch(NullPointerException nullPointerException) {
                Logger.getLogger(ControllerModifyEvent.class.getName()).log(Level.SEVERE, null, nullPointerException);
            }
    }
    
    public boolean saveMember(String memberNameOld, Member memberObject) {
        String nameCurrent = memberObject.getFullName();
        boolean result = false;
        boolean memberNameFound = searchMemberNameDuplicated(nameCurrent, memberNameOld);
        if (memberNameFound) {
            try {
                MEMBER_DAO.modifyMember(memberObject, memberNameOld);
                showCofirmationAlert();
                result = true;
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }            
        } else if (memberNameFound==false) {
            showMemberNameFoundAlert();
            result = false;
        }
        return result;
    }
    
    public boolean searchMemberNameDuplicated(String nameCurrent, String memberNameOld) {
        boolean nameValid = false;
        String memberFullName = null;
        try {
            memberFullName = MEMBER_DAO.consultMember(nameCurrent).getFullName();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
  
        if (!nameCurrent.equals(memberNameOld) && nameCurrent.equals(memberFullName)) {
            nameValid = false;
        } else if (nameCurrent.equals(memberNameOld) && nameCurrent.equals(memberFullName)) {
            nameValid = true;
        } else if (nameCurrent.equals(memberNameOld) && !nameCurrent.equals(memberFullName)) {
            nameValid = true;
        } else if (!nameCurrent.equals(memberNameOld) && !nameCurrent.equals(memberFullName)) {
            nameValid = true;
        }
        
        return nameValid;
    }
    
     public void showCofirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("El miembro ha sido modificado exitosamente");
        alert.setTitle("Confirmacion");
        alert.setContentText(null);
        alert.showAndWait();
    }
    
    public void showMemberNameFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Ya existe un miembro con este nombre");
        alert.showAndWait();
    }
    
    @FXML
    public void cancelOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de cancelar modificar");        
        alert.setHeaderText("¿Desea salir de la edición del miembro?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiMemberList(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonCancelModify.getScene().getWindow();
            stage.close();
        }
    }
    
    public void showGuiMemberList(String memberFullName, String memberEmail) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMemberList.fxml"));
            fXMLLoader.load();
            ControllerConsultMemberList controllerConsultMemberList = fXMLLoader.getController();
            controllerConsultMemberList.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();                 
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerModifyMember.class.getName()).log(Level.SEVERE, null, ex);
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
