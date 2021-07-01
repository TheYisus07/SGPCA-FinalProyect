package gui;

import businesslogic.BusinessException;
import businesslogic.EventDAO;
import domain.Event;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultEvent implements Initializable {
    private final EventDAO EVENT_DAO = new EventDAO();
    
    @FXML
    private Label titleLabel;

    @FXML
    private Label registrationDateLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label placeLabel;

    @FXML
    private Label privacityLabel;

    @FXML
    private Label typeLabel;
    
    @FXML
    private Label responsableLabel;
    
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private Button exitConsultEventButton;

    @FXML
    private Button showConstancyListGUIButton;
   
    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}   
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void closeWindow(ActionEvent event){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEventHistory.fxml"));
            fXMLLoader.load();
            ControllerConsultEventHistory controllerConsultEventHistory = fXMLLoader.getController();
            controllerConsultEventHistory.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getEventTitleSelected( String eventTitle){
        titleLabel.setText(eventTitle + "");
        fillConsultEvent(eventTitle);
    }
    
    public void fillConsultEvent(String eventTitle) {
        try {
            Event eventConsulted;
            eventConsulted = EVENT_DAO.consultEvent(eventTitle);
            String responsable = eventConsulted.getResponsable();
            String type = eventConsulted.getType();
            String place = eventConsulted.getPlace();
            String privacy = eventConsulted.getPrivacy();
            Date registrationDateAUX = eventConsulted.getRegistrationDate();
            Date eventDateAUX = eventConsulted.getEventDate();
            String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(registrationDateAUX));
            String eventDate = (new SimpleDateFormat("yyyy-MM-dd").format(eventDateAUX));
            
            registrationDateLabel.setText(registrationDate);
            eventDateLabel.setText(eventDate);
            placeLabel.setText(place + "");
            privacityLabel.setText(privacy + "");
            typeLabel.setText(type + "");
            responsableLabel.setText(responsable + "");
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
    }
    
    
    @FXML
    public void showModifyEvenGUIOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyEvent.fxml"));
            fXMLLoader.load();
            ControllerModifyEvent controllerModifyEvent = fXMLLoader.getController();
            String eventTitle = titleLabel.getText();
            controllerModifyEvent.fillModifyEventDates(eventTitle);
            controllerModifyEvent.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showConstancyListGUIOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultConstancyList.fxml"));
            fXMLLoader.load();
            String eventTitle = titleLabel.getText();
            ControllerConsultConstancyList controllerConsultConstancyList = fXMLLoader.getController();
            controllerConsultConstancyList.showConstancyList(eventTitle);
            controllerConsultConstancyList.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    public void getOutOfConsultEventOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas regresar al historial?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
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
            stage.getIcons().add(new javafx.scene.image.Image("/images/sgpca.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
