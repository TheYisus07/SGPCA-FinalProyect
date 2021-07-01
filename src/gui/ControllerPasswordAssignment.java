package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerPasswordAssignment implements Initializable {

    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private JFXPasswordField passwordFieldNewPassword; 
    
    @FXML
    private JFXPasswordField passwordFieldPasswordConfirmation;
    
    @FXML
    private Label labelMemberFullName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void getMemberFullName(String memberFullName) {
        labelMemberFullName.setText(memberFullName);
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
    
    public void closeGuiNewPassword(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();               
    }
    
    
    public void showGuiPasswordChangeConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de cambio de contraseña");        
        alert.setHeaderText(labelMemberFullName.getText() + ", ha actualizado su contraseña");
        alert.setContentText("Ahora puede ingresar al SGP-CA con su nueva contraseña");
        alert.showAndWait();        
    }
    
    public void validFieldsToLogin() {
        passwordFieldNewPassword.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        passwordFieldNewPassword.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        passwordFieldNewPassword.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                passwordFieldNewPassword.validate();
            }     
        });
        passwordFieldPasswordConfirmation.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        passwordFieldPasswordConfirmation.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        passwordFieldPasswordConfirmation.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                passwordFieldPasswordConfirmation.validate();
            }     
        });
    }
    
    public void comparePasswords(ActionEvent actionEvent) {
        String newPassword = null;
        String confirmationPassword = null;
        boolean samePasswords = false;
        String memberFullName = labelMemberFullName.getText();
        Member memberLogin = null;
        try {
            memberLogin = MEMBER_DAO.consultMember(memberFullName);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        String memberEmail = memberLogin.getInstitutionalMail();
        
        if (passwordFieldNewPassword.validate()) {
            newPassword = passwordFieldNewPassword.getText(); 
        }
        if (passwordFieldPasswordConfirmation.validate()) {
            confirmationPassword = passwordFieldPasswordConfirmation.getText();
        }        
        if (newPassword.equals(confirmationPassword)) {
            samePasswords = true; 
        }               
        if (samePasswords) {            
            try {
                MEMBER_DAO.updatePasswordOfMember(newPassword, memberFullName);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            showGuiPasswordChangeConfirmation();
            closeGuiNewPassword(actionEvent);
            showGuiWorkplan(memberFullName, memberEmail);
        } else {
            passwordFieldPasswordConfirmation.setText(null);
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
            Logger.getLogger(ControllerPasswordAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
