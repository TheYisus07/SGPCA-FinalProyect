package gui;

import businesslogic.BusinessException;
import businesslogic.GeneralCurriculumDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.GeneralCurriculum;
import domain.Member;
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
public class ControllerModifyGeneralCurriculum implements Initializable {
    private final GeneralCurriculumDAO GENERAL_CURRICULUM_DAO = new GeneralCurriculumDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO(); 
    
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXButton buttonExitGeneralCurriculum;
    
    @FXML
    private JFXTextField textFieldTitle;
    
    @FXML
    private JFXTextField textFieldDependencyInstitute;
    
    @FXML
    private JFXTextField textFieldFacultyOfTheInstitution;
    
    @FXML
    private JFXTextField textFieldDegreeOfConsolidation;
    
    @FXML
    private JFXTextField textFieldNumberOfParticipants;
    
    @FXML
    private JFXTextField textFieldNumberOfColaborators;
    
    @FXML 
    private JFXTextField textFieldKeycode;
    
    @FXML
    private JFXTextField textFieldLGAC;
    
    @FXML
    private JFXTextArea textAreaGeneralPorpuse;
    
    @FXML
    private JFXTextArea textAreaMission;
    
    @FXML
    private JFXTextArea textAreaVision;
    
    @FXML
    private JFXDatePicker datePickerRegistrationDate;
    
    @FXML
    private Label labelKeyCodeSecret;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToRegisterMember();
        getActualDate();
    }    
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void getActualDate() {
        LocalDate actualDate = LocalDate.now();
        datePickerRegistrationDate.setValue(actualDate);
    }
    
    public void validFieldsToRegisterMember() {
        textFieldTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldTitle.validate();
            }
        });
        
        textFieldDegreeOfConsolidation.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldDegreeOfConsolidation.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldDegreeOfConsolidation.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldDegreeOfConsolidation.validate();
            }
        });
        
        datePickerRegistrationDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerRegistrationDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerRegistrationDate.validate();
            }
        });
        
        textFieldKeycode.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldKeycode.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldKeycode.validate();
            }
        });
        
        textFieldLGAC.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldLGAC.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldLGAC.validate();
            }
        });
           
        textAreaGeneralPorpuse.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_BIG_DESCRIPTION());        
        textAreaGeneralPorpuse.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaGeneralPorpuse.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaGeneralPorpuse.validate();
            }
        });
              
        textAreaMission.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());        
        textAreaMission.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaMission.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaMission.validate();
            }
        });
        
        textAreaVision.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());        
        textAreaVision.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaVision.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaVision.validate();
            }
        });
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
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultGeneralCurriculum.fxml"));
            fXMLLoader.load();
            ControllerConsultGeneralCurriculum controllerConsultGeneralCurriculum = fXMLLoader.getController();
            controllerConsultGeneralCurriculum.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerModifyGeneralCurriculum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void counterMembers() {
        int numberOfMembers = 0;
        int numberOfColaborators = 0;
        
        try {
            for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {
            Member member = MEMBER_DAO.consultMemberList().get(i);
                if (member.getPosition().equals("Responsable") || member.getPosition().equals("Integrante")) {
                    numberOfMembers++;
                } else {
                    numberOfColaborators++;   
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        
        textFieldNumberOfParticipants.setText(numberOfMembers + "");
        textFieldNumberOfColaborators.setText(numberOfColaborators + "");
    }
    
    public void fillLabelKeyCode(String keycode) {
        labelKeyCodeSecret.setText(keycode);
    }
    
    public void fillModifyGeneralCurriculum(String keycode){        
        textFieldKeycode.setText(keycode);
       
        GeneralCurriculum generalCurriculumConsulted =  null;
        try {
            generalCurriculumConsulted = GENERAL_CURRICULUM_DAO.consultGeneralCurriculum(keycode);
            String title = generalCurriculumConsulted.getTitle();       
            String dependencyInstitute = generalCurriculumConsulted.getDependencyInstitute();
            String facultyOfTheInstitution = generalCurriculumConsulted.getFacultyOfTheInstitution();
            String degreeOfConsolidation = generalCurriculumConsulted.getDegreeOfConsolidation();
            counterMembers();
            String lgac = generalCurriculumConsulted.getLgac();
            String generalPorpuse = generalCurriculumConsulted.getGeneralPurpose();
            String mission = generalCurriculumConsulted.getMission();
            String vision = generalCurriculumConsulted.getVision();

            textFieldTitle.setText(title);
            textFieldDependencyInstitute.setText(dependencyInstitute);
            textFieldFacultyOfTheInstitution.setText(facultyOfTheInstitution);
            textFieldDegreeOfConsolidation.setText(degreeOfConsolidation);
            textFieldLGAC.setText(lgac);
            textAreaGeneralPorpuse.setText(generalPorpuse);
            textAreaMission.setText(mission);
            textAreaVision.setText(vision);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
             
    }
    
    @FXML
    public void updateGeneralCurrículumOnAction(ActionEvent event) {
        
        String generalCurriculumTitle = null;
        String generalCurriculumDependencyInstitute = null;
        String generalCurriculumFacultyOfTheInstitution = null;
        String generalCurriculumDegreeOfConsolidation = null;
        String generalCurriculumKeyCode = null;
        String generalCurriculumLgac = null;
        String generalCurriculumGeneralPorpuse = null;
        String generalCurriculumMission = null;
        String generalCurriculumVision = null;
        Date generalCurriculumDateRegister = convertToDate(datePickerRegistrationDate.getValue());

        if (textFieldTitle.validate() && !textFieldTitle.getText().trim().equals("")) {
            generalCurriculumTitle = textFieldTitle.getText();
        }

        if (textFieldDependencyInstitute.validate() && !textFieldDependencyInstitute.getText().trim().equals("")) {
            generalCurriculumDependencyInstitute = textFieldDependencyInstitute.getText();
        }

        if (textFieldFacultyOfTheInstitution.validate() && !textFieldFacultyOfTheInstitution.getText().trim().equals("")) {
            generalCurriculumFacultyOfTheInstitution = textFieldFacultyOfTheInstitution.getText();
        }

        if (textFieldDegreeOfConsolidation.validate() && !textFieldDegreeOfConsolidation.getText().trim().equals("")) {
            generalCurriculumDegreeOfConsolidation = textFieldDegreeOfConsolidation.getText();
        }

        if (textFieldKeycode.validate() && !textFieldKeycode.getText().trim().equals("")) {
            generalCurriculumKeyCode = textFieldKeycode.getText();
        }

        if (textFieldLGAC.validate() && !textFieldLGAC.getText().trim().equals("")) {
            generalCurriculumLgac = textFieldLGAC.getText();
        }

        if (textAreaGeneralPorpuse.validate() && !textAreaGeneralPorpuse.getText().trim().equals("")) {
            generalCurriculumGeneralPorpuse = textAreaGeneralPorpuse.getText();
        }

        if (textAreaMission.validate() && !textAreaMission.getText().trim().equals("")) {
            generalCurriculumMission = textAreaMission.getText();
        }

        if (textAreaVision.validate() && !textAreaVision.getText().trim().equals("")) {
            generalCurriculumVision = textAreaVision.getText();
        }
        
        if ((generalCurriculumTitle != null) && (generalCurriculumDependencyInstitute != null) && (generalCurriculumFacultyOfTheInstitution != null) && (generalCurriculumDegreeOfConsolidation != null) && (generalCurriculumKeyCode != null) && (generalCurriculumLgac != null) && (generalCurriculumGeneralPorpuse != null) && (generalCurriculumMission != null) && (generalCurriculumVision != null)) {
            GeneralCurriculum generalCurriculumObject = new GeneralCurriculum(generalCurriculumTitle, generalCurriculumDependencyInstitute, generalCurriculumFacultyOfTheInstitution, generalCurriculumDegreeOfConsolidation, Integer.parseInt(textFieldNumberOfParticipants.getText()), Integer.parseInt(textFieldNumberOfColaborators.getText()), generalCurriculumDateRegister, generalCurriculumLgac, generalCurriculumGeneralPorpuse, generalCurriculumMission, generalCurriculumVision, generalCurriculumKeyCode);
            recordStatus(generalCurriculumObject);
        } 
       
    }
    
    public void recordStatus(GeneralCurriculum generalCurriculumObject) {
        String keyCode = labelKeyCodeSecret.getText();
        
        int confirmationModify = 0;
                
        try {
            confirmationModify = GENERAL_CURRICULUM_DAO.modifyGeneralCurriculum(generalCurriculumObject, keyCode);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        if (confirmationModify == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("El curriculum ha sido modificado exitosamente");
            alert.showAndWait();
        }
    }    
    
    @FXML
    public void exitModifyGeneralCurriculum(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de la consulta de lista de miembros");        
        alert.setHeaderText("¿Desea salir de la edición del curriculum general?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiGeneralCurriculum(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonExitGeneralCurriculum.getScene().getWindow();
            stage.close();
        }
    }
    
    public void showGuiGeneralCurriculum(String memberFullName, String memberEmail) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultGeneralCurriculum.fxml"));
            fXMLLoader.load();
            ControllerConsultGeneralCurriculum controllerConsultGeneralCurriculum = fXMLLoader.getController();
            controllerConsultGeneralCurriculum.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();                 
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerModifyGeneralCurriculum.class.getName()).log(Level.SEVERE, null, ex);
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
