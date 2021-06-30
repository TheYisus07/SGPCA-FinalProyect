package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import domain.Member;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerLogin implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
 
    @FXML
    private Label labelInvalidFieldsLogin;

    @FXML
    private JFXTextField textFieldLoginEmail;

    @FXML
    private JFXPasswordField passwordFieldLoginPassword;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToLogin();
    }
    
    public void validFieldsToLogin() {
        textFieldLoginEmail.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldLoginEmail.getValidators().add(TEXT_FIELD_VALIDATION.getEMAIL_VALIDATOR());
        textFieldLoginEmail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldLoginEmail.validate();
            }     
        });
        passwordFieldLoginPassword.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        passwordFieldLoginPassword.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldLoginEmail.validate();
            }     
        });
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
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));           
            stage.show();            
        } catch (IOException ex) {
            Logger.getLogger(ControllerPasswordAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiPasswordAssignment(String memberFullName) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLNewPassword.fxml"));
            fXMLLoader.load();
            ControllerPasswordAssignment controllerPasswordAssignment = fXMLLoader.getController();
            controllerPasswordAssignment.getMemberFullName(memberFullName);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();  
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPasswordAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeGuiLogin(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();               
    }        
    
    @FXML
    public void validateLoginFields(ActionEvent actionEvent) {
        String memberEmail = null;
        String memberCurp = null;
        boolean registeredUser = false; 
        if (textFieldLoginEmail.validate()) {
            memberEmail = textFieldLoginEmail.getText();
        }
        if (passwordFieldLoginPassword.validate()) {
            memberCurp = passwordFieldLoginPassword.getText();
        }
                
        Member memberLogin = new Member();
        
        if (memberEmail != null && memberCurp != null) {     
            try {
                for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {                 
                    String expectedEmail = MEMBER_DAO.consultMemberList().get(i).getInstitutionalMail();
                    if (expectedEmail.equals(memberEmail)) {
                        memberLogin = MEMBER_DAO.consultMemberList().get(i);
                        registeredUser = true;
                    }
                } 
            } catch (BusinessException bussinessException) {                
                showAlertDataBaseConnection();                           
            } 
        } 
         
        loggin(registeredUser, memberLogin, actionEvent); 
    }
    
    public void loggin(boolean registeredUser, Member memberLogin, ActionEvent actionEvent) {
        String memberEmail = textFieldLoginEmail.getText();
        String memberCurp = passwordFieldLoginPassword.getText();
        String memberPassword = memberLogin.getPassword();
        String decryptedPassword = null;
        try {
            decryptedPassword = MEMBER_DAO.decryptMemberPassword(memberPassword);            
        } catch (BusinessException bussinessException) {
            showAlertDataBaseConnection();
        }         
        if (registeredUser) {                       
            if (memberPassword == null) {
                if (memberLogin.getInstitutionalMail().equals(memberEmail) && memberLogin.getCurp().equals(memberCurp)) {
                    showGuiPasswordAssignment(memberLogin.getFullName());
                    closeGuiLogin(actionEvent);
                }
            } else if ((memberLogin.getInstitutionalMail().equals(memberEmail)) && (decryptedPassword.equals(memberCurp))) {                
                showGuiWorkplan(memberLogin.getFullName(), memberEmail);
                closeGuiLogin(actionEvent);
            }
            
        }
        labelInvalidFieldsLogin.setVisible(true);
        labelInvalidFieldsLogin.setText("Correo o contraseña inválidos");
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
            Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}