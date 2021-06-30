package gui;

import businesslogic.BusinessException;
import businesslogic.BookDAO;
import businesslogic.EvidenceDAO;
import businesslogic.MemberDAO;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.Book;
import domain.Evidence;
import domain.Member;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
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

public class ControllerConsultBook implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final BookDAO BOOK_DAO = new BookDAO();
    
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
    private JFXTextField textFieldBookImpact;
    
    @FXML
    private Label labelIdEvidenceBook;

    @FXML
    private JFXTextField textFieldBookEditorial;

    @FXML
    private JFXTextField textFieldBookNumberEditorial;

    @FXML
    private JFXDatePicker dataPickerBookStartDate;

    @FXML
    private JFXDatePicker dataPickerBookEndDate;

    @FXML
    private JFXTextField textFieldBookYear;

    @FXML
    private JFXTextField textFieldBookTitle;

    @FXML
    private JFXTextField textFieldProject;

    @FXML
    private JFXTextField textFieldCategory;

    @FXML
    private JFXTextField textFieldStatus;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);        
    }
    
    public void getIdEvidence(int idEvidence) {
        labelIdEvidenceBook.setText(idEvidence + "");
        fillConsultedBookFields();
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void fillConsultedBookFields() {
        int idEvidence = Integer.parseInt(labelIdEvidenceBook.getText());
        Evidence evidence = null;
        Book book = null;                
        try {
            evidence = EVIDENCE_DAO.consultEvidenceById(idEvidence);
            book = BOOK_DAO.consultArticle(idEvidence);
        } catch (BusinessException ex) {
            Logger.getLogger(ControllerConsultBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        textFieldBookTitle.setText(evidence.getTitle());
        textFieldBookImpact.setText(evidence.getImpactOnCA());
        textFieldStatus.setText(evidence.getActualStatus());
        if (evidence.getProjectTitle() == null) {
            textFieldProject.setText("Sin proyecto asociado");
        } else {
            textFieldProject.setText(evidence.getProjectTitle());
        }  
        textFieldCategory.setText("Libro");
        textFieldBookEditorial.setText(book.getEditorial());
        textFieldBookYear.setText(book.getPublicationYear() + "");
        textFieldBookNumberEditorial.setText(book.getEditionNumber() + "");
        dataPickerBookStartDate.setValue(convertToLocalDate(book.getStartDate()));
        dataPickerBookEndDate.setValue(convertToLocalDate(book.getEndDate()));
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
    public void showGuiExitQueryBook(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de consulta de Libro");        
        alert.setHeaderText("¿Desea salir de la consulta del Libro?");        
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
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        if (decryptedPassword == null) {
            if (memberLoggin.getCurp().equals(passwordMember)) {
                try {
                    int idEvidence = Integer.parseInt(labelIdEvidenceBook.getText());
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
                    int idEvidence = Integer.parseInt(labelIdEvidenceBook.getText());
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
            Logger.getLogger(ControllerConsultBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}