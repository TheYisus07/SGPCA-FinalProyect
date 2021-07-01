package gui;

import businesslogic.BusinessException;
import businesslogic.ChapterBookDAO;
import businesslogic.EvidenceDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.ChapterBook;
import domain.Evidence;
import domain.Member;
import java.io.IOException;
import java.net.URL;
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

public class ControllerConsultChapterBook implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final ChapterBookDAO CHAPTER_BOOK_DAO = new ChapterBookDAO();
    
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
    private JFXTextField textFieldChapterAuthor;

    @FXML
    private JFXTextField textFieldChapterHomePage;

    @FXML
    private JFXTextField textFieldChapterEndPage;

    @FXML
    private JFXTextField textFieldChapterYear;

    @FXML
    private JFXTextField textFieldChapterImpact;

    @FXML
    private JFXTextField textFieldChapterTitle;

    @FXML
    private Label labelIdChapterBook;

    @FXML
    private JFXTextField textFieldChapterProject;

    @FXML
    private JFXTextField textFieldChapterCategory;

    @FXML
    private JFXTextField textFieldChapterStatus;        

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);        
    }
    
    public void fillConsultedChapterFields() {        
        int idEvidence = Integer.parseInt(labelIdChapterBook.getText());
        Evidence evidence = null;
        ChapterBook chapterBook = null;
        try {
            evidence = EVIDENCE_DAO.consultEvidenceById(idEvidence);
            chapterBook = CHAPTER_BOOK_DAO.consultChapterBook(idEvidence);
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        textFieldChapterTitle.setText(evidence.getTitle());
        textFieldChapterImpact.setText(evidence.getImpactOnCA());
        if (evidence.getProjectTitle() == null) {
            textFieldChapterProject.setText("Sin proyecto asociado");
        } else {
            textFieldChapterProject.setText(evidence.getProjectTitle());
        }        
        textFieldChapterCategory.setText("Capítulo de Libro");
        textFieldChapterStatus.setText(evidence.getActualStatus());
        textFieldChapterAuthor.setText(chapterBook.getAuthor());
        textFieldChapterYear.setText(chapterBook.getYearPublication() + "");
        textFieldChapterHomePage.setText(chapterBook.getHomePage() + "");
        textFieldChapterEndPage.setText(chapterBook.getEndPage() + "");
    }
    
    public void getIdEvidence(int idEvidence) {
        labelIdChapterBook.setText(idEvidence + "");
        fillConsultedChapterFields();
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
    public void showGuiExitQueryChapter(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de consulta de Artículo");        
        alert.setHeaderText("¿Desea salir de la consulta del Artículo?");        
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
                int idEvidence = Integer.parseInt(labelIdChapterBook.getText());
                try {
                    EVIDENCE_DAO.deleteEvidence(idEvidence);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
                showGuiEvidenceConfirmationRemoved(actionEvent);               
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        } else if (passwordMember != null) {
            if (passwordMember.equals(decryptedPassword)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdChapterBook.getText());
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
            Logger.getLogger(ControllerConsultChapterBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}