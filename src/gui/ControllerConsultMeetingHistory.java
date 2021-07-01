package gui;


import businesslogic.BusinessException;
import businesslogic.MeetingDAO;
import domain.Meeting;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultMeetingHistory implements Initializable {
    private final MeetingDAO MEETING_DAO = new MeetingDAO();
    
    @FXML
    private TableView<Meeting> tableViewMeetings;

    @FXML
    private TableColumn tableColumnMeetingProjects;

    @FXML
    private TableColumn tableColumnMeetingDate;

    @FXML
    private TableColumn tableColumnMeetingResponsable;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private Button exitMeetingHistoryButton;

    @FXML
    private Button ScheduleMeetingButton;
    
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showMeetingHistory();
        showConsultMeetingGUI();
    }  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    @FXML
    void openScheduleMeetingGUI(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLScheduleMeeting.fxml"));
            fXMLLoader.load();
            ControllerScheduleMeeting ControllerScheduleMeeting = fXMLLoader.getController();
            ControllerScheduleMeeting.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultMeetingHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showMeetingHistory() {
        ObservableList<Meeting> meetingHistoryList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < MEETING_DAO.checkMeetingHistory().size(); i++){
                meetingHistoryList.add(MEETING_DAO.checkMeetingHistory().get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewMeetingData(meetingHistoryList);
        
    }
    
    public void loadTableViewMeetingData(ObservableList<Meeting> meetingHistoryList) {
        
        this.tableColumnMeetingProjects.setCellValueFactory(new PropertyValueFactory("titleOfProyect"));
        this.tableColumnMeetingDate.setCellValueFactory(new PropertyValueFactory("date"));
        this.tableColumnMeetingResponsable.setCellValueFactory(new PropertyValueFactory("fullName"));
        tableViewMeetings.setItems(meetingHistoryList);
    }
    
    public void showConsultMeetingGUI() {
        tableViewMeetings.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Meeting> observable, Meeting oldValue, Meeting newValue) -> {
            openConsultMeetingGUI(observable);
        });
    }
    
    public void openConsultMeetingGUI(ObservableValue<? extends Meeting> observable) {
        try {
            Stage stage =  (Stage) tableViewMeetings.getScene().getWindow();
            stage.hide();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(ControllerConsultMeetingHistory.this.getClass().getResource("FXMLConsultMeeting.fxml"));
            fXMLLoader.load();
            ControllerConsultMeeting controllerConsultMeeting = fXMLLoader.getController();
            int idMeeting = observable.getValue().getIdMeeting();
            controllerConsultMeeting.getMeetingSelected(idMeeting);
            controllerConsultMeeting.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stageConsultMeeting = new Stage();
            Scene scene = new Scene(root);
            stageConsultMeeting.setScene(scene);
            stageConsultMeeting.setResizable(false);
            stageConsultMeeting.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stageConsultMeeting.getIcons().add(new Image("/images/sgpca.png"));
            stageConsultMeeting.show();
        }catch (IOException ex) {
            Logger.getLogger(ControllerConsultMeetingHistory.class.getName()).log(Level.SEVERE, null, ex);            
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
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMeetingHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    void getOutOfMeetingHistory(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea regresar a la pagina principal?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) exitMeetingHistoryButton.getScene().getWindow();
            stage.close();
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
            Logger.getLogger(ControllerConsultMeetingHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
