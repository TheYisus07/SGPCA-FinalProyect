package gui;

import businesslogic.BusinessException;
import businesslogic.EvidenceDAO;
import businesslogic.MemberDAO;
import businesslogic.ReceptionWorkDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.Evidence;
import domain.Member;
import domain.ReceptionWork;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerConsultReceptionWork implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final ReceptionWorkDAO RECEPTION_WORK_DAO = new ReceptionWorkDAO();
    
    @FXML 
    private AnchorPane anchorPaneBlackAuthenticate;
    
    @FXML
    private AnchorPane anchorPaneAuthenticate;
    
    @FXML 
    private JFXPasswordField passwordFieldPassword;
    
    @FXML
    private Label labelLoggedInUser;

    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private JFXTextField textFieldReceptionWorkImpact;

    @FXML
    private JFXTextField textFieldReceptionWorkDirection;

    @FXML
    private JFXTextField textFieldReceptionWorkNumberStudents;

    @FXML
    private JFXDatePicker dataPickerReceptionWorkStartDate;

    @FXML
    private JFXDatePicker dataPickerReceptionWorkFinalDate;

    @FXML
    private JFXTextField textFieldReceptionWorkGrade;
    
    @FXML
    private JFXTextField textFieldReceptionWorkKind;

    @FXML
    private JFXTextField textFieldReceptionWorkProject;

    @FXML
    private JFXTextField textFieldReceptionWorkitle;

    @FXML
    private JFXTextField textFieldReceptionWorkStatus;

    @FXML
    private JFXTextField textFieldReceptionWorkCategory;
    
    @FXML
    private Label labelIdReceptionWork;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);        
    }
    
    public void getIdEvidence(int idEvidence) {
        labelIdReceptionWork.setText(idEvidence + "");
        fillConsultedReceptionWorkFields();
    } 
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void fillConsultedReceptionWorkFields() {
        int idEvidence = Integer.parseInt(labelIdReceptionWork.getText());
        Evidence evidence = null;
        ReceptionWork receptionWork = null;
        
        try {
            evidence = EVIDENCE_DAO.consultEvidenceById(idEvidence);
            receptionWork = RECEPTION_WORK_DAO.consultReceptionWork(idEvidence);    
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        
        textFieldReceptionWorkitle.setText(evidence.getTitle());
        textFieldReceptionWorkImpact.setText(evidence.getImpactOnCA());
        textFieldReceptionWorkStatus.setText(evidence.getActualStatus());        
        textFieldReceptionWorkCategory.setText("Trabajo Recepcional");
        textFieldReceptionWorkProject.setText(receptionWork.getBlueprintTitle());
        textFieldReceptionWorkDirection.setText(receptionWork.getDirection());
        textFieldReceptionWorkGrade.setText(receptionWork.getGrade());
        textFieldReceptionWorkKind.setText(receptionWork.getKind());
        textFieldReceptionWorkNumberStudents.setText(receptionWork.getNumberStudents() + "");
        dataPickerReceptionWorkStartDate.setValue(convertToLocalDate(receptionWork.getStartDate()));
        dataPickerReceptionWorkFinalDate.setValue(convertToLocalDate(receptionWork.getFinalDate()));
    }
    
    public void showGuiConsultEvidence(ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvidence.fxml"));
            fXMLLoader.load();            
            ControllerConsultEvidence controllerConsultEvidence = fXMLLoader.getController();
            controllerConsultEvidence.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showGuiExitQueryReceptionWork(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de consulta de Trabajo Recepcional");        
        alert.setHeaderText("¿Desea salir de la consulta del Trabajo Recepcional");        
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsultEvidence(actionEvent);
        }
    }
    
    public void showGuiEvidenceConfirmationRemoved(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("La evidencia ha sido removida");        
        alert.setHeaderText("Su información ya no se encontrará disponible");  
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsultEvidence(actionEvent);
        }
    }
    
    public void showGuiVerifyPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contraseña erronea");        
        alert.setHeaderText("Verifique su constraseña");  
        alert.show();
    }
    
    @FXML
    public void showGuiAuthenticatePassword(ActionEvent actionEvent) {
        anchorPaneBlackAuthenticate.setVisible(true);
        anchorPaneAuthenticate.setVisible(true);
    }   
        
    @FXML
    public void authenticatePassword(ActionEvent actionEvent) {   
        String passwordMember = null;
        if (passwordFieldPassword.validate()) {
            passwordMember = passwordFieldPassword.getText();
        }
        Member memberLoggin = null;
        String decryptedPassword = null;
        try {
        memberLoggin = MEMBER_DAO.consultMember(labelLoggedInUser.getText());
        decryptedPassword = MEMBER_DAO.decryptMemberPassword(memberLoggin.getPassword());
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        if (decryptedPassword == null) {
            if (memberLoggin.getCurp().equals(passwordMember)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdReceptionWork.getText());
                    EVIDENCE_DAO.deleteEvidence(idEvidence);               
                    showGuiEvidenceConfirmationRemoved(actionEvent);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        } else if (passwordMember != null) {
            if (passwordMember.equals(decryptedPassword)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdReceptionWork.getText());
                    EVIDENCE_DAO.deleteEvidence(idEvidence);
                    showGuiEvidenceConfirmationRemoved(actionEvent);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        }
    }
    
    @FXML
    public void cancelAuthenticationToDeleteEvidence(ActionEvent event) {
        anchorPaneBlackAuthenticate.setVisible(false);
        anchorPaneAuthenticate.setVisible(false);
        passwordFieldPassword.setText("");
        passwordFieldPassword.resetValidation();
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
            Logger.getLogger(ControllerConsultReceptionWork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
