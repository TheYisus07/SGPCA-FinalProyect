package gui;

import businesslogic.BusinessException;
import businesslogic.BlueprintDAO;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Blueprint;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerConsultBlueprint implements Initializable {
    
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private Label labelBlueprintTitle; 
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintCodirectors;
    
    @FXML
    private JFXDatePicker datePickerConsultBlueprintStartDate;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintTitle;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintLgac;
    
    @FXML
    private JFXTextArea textAreaConsultBlueprintDescription;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintStudent;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintModality;
    
    @FXML
    private JFXTextField textFieldConsultBlueprintStatus;
    
    @FXML
    private JFXTextField textFieldBlueprintProject;
    
    @FXML
    private AnchorPane anchorPaneBlackAuthenticate;
    
    @FXML
    private AnchorPane anchorPaneAuthenticate;
    
    @FXML
    private JFXPasswordField passwordFieldPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToAuthenticationToDeleteBlueprint();
    }
    
    public void validFieldsToAuthenticationToDeleteBlueprint() {
        passwordFieldPassword.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        passwordFieldPassword.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                passwordFieldPassword.validate();
            }     
        });
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void getBlueprintTitle(String blueprintTitle) {
        labelBlueprintTitle.setText(blueprintTitle);
        fillFieldsOfTheBlueprintConsulted(blueprintTitle);
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    } 

    public void showGuiConsulResearchProjects(ActionEvent actionEvent) {        
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultResearchProjects.fxml"));
            fXMLLoader.load();            
            ControllerConsultResearchProjects controllerConsultResearchProjects = fXMLLoader.getController();
            controllerConsultResearchProjects.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void showGuiModifyBlueprint(ActionEvent actionEvent) {    
        String blueprintTitle = labelBlueprintTitle.getText();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyBlueprint.fxml"));
            fXMLLoader.load();            
            ControllerModifyBlueprint controllerModifyBlueprint = fXMLLoader.getController();
            controllerModifyBlueprint.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            controllerModifyBlueprint.getBlueprintTitle(blueprintTitle);
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
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void fillFieldsOfTheBlueprintConsulted(String blueprintTitle) {
        Blueprint blueprint = null;
        try {
            blueprint = BLUEPRINT_DAO.consultBlueprintByTitle(blueprintTitle);
        } catch (BusinessException ex) {
            Logger.getLogger(ControllerConsultBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
        textFieldConsultBlueprintTitle.setText(blueprint.getTitle());
        datePickerConsultBlueprintStartDate.setValue(convertToLocalDate(blueprint.getStartDate()));
        textFieldConsultBlueprintLgac.setText(blueprint.getAssociatedLGAC());
        textFieldConsultBlueprintCodirectors.setText(blueprint.getCodirectors());
        textFieldConsultBlueprintStudent.setText(blueprint.getStudent());
        textFieldConsultBlueprintModality.setText(blueprint.getModality());
        textFieldConsultBlueprintStatus.setText(blueprint.getStatus());
        textAreaConsultBlueprintDescription.setText(blueprint.getDescription());
        if (blueprint.getProjectTitle() != null) {
            textFieldBlueprintProject.setVisible(true);
            textFieldBlueprintProject.setText(blueprint.getProjectTitle());
        }
    }
    
    public void showGuiBlueprintConfirmationRemoved(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("El Anteproyecto: " + labelBlueprintTitle.getText() + " se ha eliminado");        
        alert.setHeaderText("Su información ya no se encontrará disponible");        
        showGuiConsulResearchProjects(actionEvent);
    }
  
    @FXML
    public void verifyPassword(ActionEvent actionEvent) {
        String passwordMember = null;
        if (passwordFieldPassword.validate()) {
            passwordMember = passwordFieldPassword.getText();
        }
        Member member = null;
        String decryptedPassword = null;
        try {
            member = MEMBER_DAO.consultMember(labelLoggedInUser.getText());        
            decryptedPassword = MEMBER_DAO.decryptMemberPassword(member.getPassword());
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        if (decryptedPassword == null) {
            if (member.getCurp().equals(passwordMember)) {
                try {
                    BLUEPRINT_DAO.deleteBlueprintByTitle(labelBlueprintTitle.getText());
                    showGuiBlueprintConfirmationRemoved(actionEvent);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            } else {
                passwordFieldPassword.setText(null);
            }
        } else if (passwordMember != null) {
            if (passwordMember.equals(decryptedPassword)) {
                try {
                    BLUEPRINT_DAO.deleteBlueprintByTitle(labelBlueprintTitle.getText());
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
                showGuiBlueprintConfirmationRemoved(actionEvent);
            } else {
                passwordFieldPassword.setText(null);
            }
        }
    }
    
    public void showGuiAuthenticationToDeleteBlueprint(ActionEvent actionEvent) {
        anchorPaneBlackAuthenticate.setVisible(true);
        anchorPaneAuthenticate.setVisible(true);  
        verifyPassword(actionEvent);
    }
    
    public void cancelAuthenticationToDeleteBlueprint() {
        anchorPaneBlackAuthenticate.setVisible(false);
        anchorPaneAuthenticate.setVisible(false);
        passwordFieldPassword.setText("");
        passwordFieldPassword.resetValidation();
    }
    
    @FXML
    public void showGuiConfirmationToDeleteBlueprint(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación para eliminar el anteproyecto: " + labelBlueprintTitle.getText());        
        alert.setHeaderText("¿Desea eliminar el Anteproyecto del sistema?");        
        alert.setContentText("La información del Anteproyecto no se podrá recuperar.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiAuthenticationToDeleteBlueprint(actionEvent);
        }
    }
    
    public void showGuiExitQueryBlueprint(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación para salir del Anteproyecto: " + labelBlueprintTitle.getText());        
        alert.setHeaderText("¿Desea salir de la consulta del anteproyecto?");        
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsulResearchProjects(actionEvent);
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
            Logger.getLogger(ControllerConsultBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}