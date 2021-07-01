package gui;

import businesslogic.BusinessException;
import businesslogic.PersonalCurriculumDAO;
import domain.PersonalCurriculum;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Javier Blas
 */
public class ControllerConsultPersonalCurriculum implements Initializable {
    
    private final PersonalCurriculumDAO PERSONAL_CURRICULUM_DAO = new PersonalCurriculumDAO();     
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML 
    private TextField textFieldNameMember;
    
    @FXML 
    private TextField textFieldBirthOfDayMember;
    
    @FXML 
    private TextField textFieldCURPMember;        
    
    @FXML 
    private TextField textFieldPhoneNumberMember;
            
    @FXML 
    private TextField textFieldInstitutionalMailMember;
    
    @FXML 
    private TextField textFieldDisciplineMember;
    
    @FXML 
    private TextField textFieldGradeOfStudyMember;        
    
    @FXML 
    private TextField textFieldAreaOfStudyMember; 
            
    @FXML 
    private TextField textFieldTypeOfTeachingMember;
    
    @FXML 
    private TextField textFieldLGACMember;
    
    @FXML 
    private TextField textFieldIESMember; 
    
    @FXML 
    private TextField textFieldPositionMember;
    
    @FXML 
    private TextField textFieldPRODEPMember;
       
    @FXML 
    private TextField textFieldKeycodeMember;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        getPersonalCurriculum();
    }
    
    public void getPersonalCurriculum() {
        String nameMember = labelLoggedInUser.getText();       
        
        PersonalCurriculum personalCurriculumConsulted = null;
        try {
            personalCurriculumConsulted = PERSONAL_CURRICULUM_DAO.consultPersonalCurriculum(nameMember);
            Date dateOfBirthAux = personalCurriculumConsulted.getDateOfBirth();
            String dateOfBirth = (new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirthAux));
            String curp = personalCurriculumConsulted.getCurp();
            String phoneNumber = personalCurriculumConsulted.getPhoneNumber();
            String institutionalMail = personalCurriculumConsulted.getInstitutionalMail();
            String discipline = personalCurriculumConsulted.getDiscipline();
            String studyGrade = personalCurriculumConsulted.getStudyGrade();
            String studyArea = personalCurriculumConsulted.getStudyArea();
            String typeOfTeaching = personalCurriculumConsulted.getTypeOfTeaching();
            String lgac = personalCurriculumConsulted.getLgac();
            String ies = personalCurriculumConsulted.getIes();
            String prodep = personalCurriculumConsulted.getProdepParticipation();
            String position =  personalCurriculumConsulted.getPositionCA();
            String keycode = personalCurriculumConsulted.getKeycode();

            textFieldNameMember.setText(nameMember);
            textFieldKeycodeMember.setText(keycode);
            textFieldPositionMember.setText(position);
            textFieldPRODEPMember.setText(prodep);
            textFieldIESMember.setText(ies);
            textFieldLGACMember.setText(lgac);
            textFieldTypeOfTeachingMember.setText(typeOfTeaching);
            textFieldAreaOfStudyMember.setText(studyArea);
            textFieldGradeOfStudyMember.setText(studyGrade);
            textFieldDisciplineMember.setText(discipline);
            textFieldInstitutionalMailMember.setText(institutionalMail);
            textFieldPhoneNumberMember.setText(phoneNumber);
            textFieldCURPMember.setText(curp);
            textFieldBirthOfDayMember.setText(dateOfBirth);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
    }
    
    @FXML
    public void showModifyMemberGuiOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyMember.fxml"));
            fXMLLoader.load();            
            ControllerModifyMember controllerModifyMember = fXMLLoader.getController();
            controllerModifyMember.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            String memberName = textFieldNameMember.getText();
            controllerModifyMember.fillModifyMember(memberName);
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene); 
            stage.setResizable(false);      
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultPersonalCurriculum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void exitConsultPersonalCurriculum(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida del currículum personal");        
        alert.setHeaderText("¿Desea salir del currículum personal?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText(), actionEvent);
        }
    }
    
    public void showGuiWorkplan(String memberFullName, String memberEmail, ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLWorkplan.fxml"));
            fXMLLoader.load();
            ControllerWorkplan controllerWorkplan = fXMLLoader.getController();
            controllerWorkplan.getMemberFullName(memberFullName, memberEmail);
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
            Logger.getLogger(ControllerPasswordAssignment.class.getName()).log(Level.SEVERE, null, ex);
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