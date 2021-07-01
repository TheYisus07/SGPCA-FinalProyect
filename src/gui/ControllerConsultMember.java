package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import domain.Member;
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
import javafx.scene.control.Button;
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
public class ControllerConsultMember implements Initializable {
    private final MemberDAO MEMBER_DAO = new MemberDAO();
 
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
    
    @FXML
    private Button buttonExitMember;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
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
            Logger.getLogger(ControllerConsultMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void getOutOfMemberConsult(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea regresar a la lista de miembro?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            Stage stage = (Stage) buttonExitMember.getScene().getWindow();
            stage.close();
        }
    }
    
    public void closeWindow(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLConsultMemberList.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.show();          
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getMemberSelected(String nameMember){
        textFieldNameMember.setText(nameMember);
        Member memberConsulted = null;
        
        try {
            memberConsulted = MEMBER_DAO.consultMember(nameMember);
            Date dateOfBirthAux = memberConsulted.getDateOfBirth();
            String dateOfBirth = (new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirthAux));
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
            String keycode = memberConsulted.getKeycodeAcademicGroup();

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
    public void exitFromConsultMember(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de la consulta de miembro");        
        alert.setHeaderText("¿Desea salir de la consulta del miembro?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiConsultMemberList(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonExitMember.getScene().getWindow();
            stage.close();
        }
    }
    
    public void showGuiConsultMemberList(String memberFullName, String memberEmail) {
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
            Logger.getLogger(ControllerConsultMember.class.getName()).log(Level.SEVERE, null, ex);
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
