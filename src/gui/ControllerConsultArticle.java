package gui;

import businesslogic.BusinessException;
import businesslogic.ArticleDAO;
import businesslogic.EvidenceDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.Article;
import domain.Evidence;
import domain.Member;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerConsultArticle implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final ArticleDAO ARTICLE_DAO = new ArticleDAO();
       
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
    private JFXTextField textFieldArticleImpact;

    @FXML
    private JFXTextField textFieldArticleAuthor;

    @FXML
    private JFXTextField textFieldArticleMegazineName;

    @FXML
    private JFXTextField textFieldArticleEndPage;

    @FXML
    private JFXDatePicker dataPickerArticlePublicationDate;

    @FXML
    private JFXTextField textFieldArticleCountry;

    @FXML
    private JFXTextField textFieldArticleVolumen;

    @FXML
    private JFXTextField textFieldArticleIssn;

    @FXML
    private JFXTextField textFieldArticleAuthorMail;
    
    @FXML
    private Label labelIdEvidence;

    @FXML
    private JFXTextField textFieldArticleTitle;

    @FXML
    private JFXTextField textFieldArticleProject;

    @FXML
    private JFXTextField textFieldArticleCategory;

    @FXML
    private JFXTextField textFieldArticleStatus;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);        
    }
    
    public void getIdEvidence(int idEvidence) {
        labelIdEvidence.setText(idEvidence + "");
        fillConsultedArticleFields();
    }
    
    public void fillConsultedArticleFields() {
        Evidence evidence = null;
        Article article = null;
        int idEvidence = Integer.parseInt(labelIdEvidence.getText());
        try {
            evidence = EVIDENCE_DAO.consultEvidenceById(idEvidence);                     
            article = ARTICLE_DAO.consultArticle(idEvidence);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        textFieldArticleTitle.setText(evidence.getTitle());
        textFieldArticleImpact.setText(evidence.getImpactOnCA());
        if (evidence.getProjectTitle() == null) {
            textFieldArticleProject.setText("Sin proyecto asociado");
        } else {
            textFieldArticleProject.setText(evidence.getProjectTitle());
        }        
        textFieldArticleCategory.setText("Artículo");
        textFieldArticleStatus.setText(evidence.getActualStatus());
        textFieldArticleAuthor.setText(article.getAuthor());
        textFieldArticleMegazineName.setText(article.getMegazineName());
        textFieldArticleAuthorMail.setText(article.getMail());
        textFieldArticleVolumen.setText(article.getVolumen() + "");
        textFieldArticleEndPage.setText(article.getEndPage() + "");
        textFieldArticleCountry.setText(article.getCountry());
        textFieldArticleIssn.setText(article.getIssn());
        dataPickerArticlePublicationDate.setValue(convertToLocalDate(article.getPublicationDate()));
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
            Logger.getLogger(ControllerConsultArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showGuiExitQueryArticle(ActionEvent actionEvent) {
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
        String decryptedPassword = null;
        Member memberLoggin = null;
        try {
            memberLoggin = MEMBER_DAO.consultMember(labelLoggedInUser.getText());
            decryptedPassword = MEMBER_DAO.decryptMemberPassword(memberLoggin.getPassword());
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        if (decryptedPassword == null) {
            if (memberLoggin.getCurp().equals(passwordMember)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdEvidence.getText());
                    EVIDENCE_DAO.deleteEvidence(idEvidence);               
                    showGuiEvidenceConfirmationRemoved(actionEvent);
                } catch (BusinessException businessException) {
                    showAlertDataBaseConnection();
                }
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        } else if (passwordMember != null) {
            if (passwordMember.equals(decryptedPassword)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdEvidence.getText());
                    EVIDENCE_DAO.deleteEvidence(idEvidence);
                    showGuiEvidenceConfirmationRemoved(actionEvent);
                } catch (BusinessException businessException) {
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
            Logger.getLogger(ControllerConsultArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}