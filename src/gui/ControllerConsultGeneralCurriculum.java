package gui;

import businesslogic.BusinessException;
import businesslogic.GeneralCurriculumDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.GeneralCurriculum;
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
public class ControllerConsultGeneralCurriculum implements Initializable {

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final GeneralCurriculumDAO GENERAL_CURRICULUM_DAO = new GeneralCurriculumDAO();
    
    @FXML
    private JFXButton buttonExitGeneralCurriculum;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML 
    private JFXTextField textFieldTitle;
    
    @FXML 
    private JFXTextField textFieldDependencyInstitute;
    
    @FXML 
    private JFXTextField textFieldFacultyOfTheInstitution;        
    
    @FXML 
    private JFXTextField textFieldDegreeOfConsolidation;
            
    @FXML 
    private JFXTextField textFieldKeycode;
    
    @FXML 
    private JFXTextField textFieldNumberOfParticipants;
    
    @FXML 
    private JFXTextField textFieldNumberOfColaborators;        
    
    @FXML 
    private JFXTextField textFieldRegistrationDate; 
            
    @FXML 
    private JFXTextField textFieldLGAC;
    
    @FXML 
    private JFXTextArea textAreaGeneralPorpuse;
    
    @FXML 
    private JFXTextArea textAreaMission; 
    
    @FXML 
    private JFXTextArea textAreaVision;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    } 
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        getGeneralCurriculum();
    }
    
    @FXML
    public void openMemberListGui(ActionEvent event) {
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
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultGeneralCurriculum.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void openModifyGeneralCurriculumGui(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyGeneralCurriculum.fxml"));
            fXMLLoader.load();            
            ControllerModifyGeneralCurriculum controllerModifyGeneralCurriculum = fXMLLoader.getController();
            controllerModifyGeneralCurriculum.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            controllerModifyGeneralCurriculum.fillModifyGeneralCurriculum(textFieldKeycode.getText());
            controllerModifyGeneralCurriculum.fillLabelKeyCode(textFieldKeycode.getText());
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
            Logger.getLogger(ControllerConsultGeneralCurriculum.class.getName()).log(Level.SEVERE, null, ex);
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
   
    public void getGeneralCurriculum() {
        String nameMember = labelLoggedInUser.getText();
        Member memberConsulted = null;
        String keyCodeMember = "";
        GeneralCurriculum generalCurriculumConsulted = null;
        
        try {
            memberConsulted = MEMBER_DAO.consultMember(nameMember);
            keyCodeMember = MEMBER_DAO.consultMember(nameMember).getKeycodeAcademicGroup();
            generalCurriculumConsulted = GENERAL_CURRICULUM_DAO.consultGeneralCurriculum(keyCodeMember);
            String title = generalCurriculumConsulted.getTitle();
            String dependency = generalCurriculumConsulted.getDependencyInstitute();
            String faculty = generalCurriculumConsulted.getFacultyOfTheInstitution();
            String consolidation = generalCurriculumConsulted.getDegreeOfConsolidation();
            Date registrationDateAux = generalCurriculumConsulted.getRegistrationDate();
            String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(registrationDateAux));
            String lgac = generalCurriculumConsulted.getLgac();
            String generalPurpose = generalCurriculumConsulted.getGeneralPurpose();
            String mission = generalCurriculumConsulted.getMission();
            String vision = generalCurriculumConsulted.getVision();
            counterMembers();

            textAreaVision.setText(vision);
            textAreaMission.setText(mission);
            textAreaGeneralPorpuse.setText(generalPurpose);
            textFieldKeycode.setText(keyCodeMember);
            textFieldLGAC.setText(lgac);
            textFieldRegistrationDate.setText(registrationDate);
            textFieldDegreeOfConsolidation.setText(consolidation);
            textFieldFacultyOfTheInstitution.setText(faculty);
            textFieldDependencyInstitute.setText(dependency);
            textFieldTitle.setText(title);   
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
       
    }
    
    @FXML
    public void exitFromConsultGeneralCurriculum(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de la consulta de currículum general");        
        alert.setHeaderText("¿Desea salir del currículum general del cuerpo acádemico?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonExitGeneralCurriculum.getScene().getWindow();
            stage.close();
        }
    }
   
    public void showGuiWorkplan(String memberFullName, String memberEmail) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLWorkplan.fxml"));
            fXMLLoader.load();
            ControllerWorkplan controllerWorkplan = fXMLLoader.getController();
            controllerWorkplan.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();                 
            stage.setScene(new Scene(root));
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
