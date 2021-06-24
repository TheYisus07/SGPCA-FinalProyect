package gui;

import businesslogic.EventDAO;
import domain.Event;
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

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultEventHistory implements Initializable {
    private final EventDAO EVENT_DAO = new EventDAO();
    
    @FXML
    private TableView<Event> tableViewEvents;

    @FXML
    private TableColumn tableColumnEventTypes;
    
    @FXML
    private TableColumn tableColumnEventTitles;

    @FXML
    private TableColumn tableColumnEventDates;
    
    @FXML
    private Button exitEventHistoryButton;

    @FXML
    private Button scheduleEventButton;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showEventHistory();
        showConsultEventGUI();
    }    
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void showEventHistory() { 
        ObservableList<Event> eventHistoryList = FXCollections.observableArrayList();
        for (int i = 0; i < EVENT_DAO.checkEventHistory().size(); i++) {
            eventHistoryList.add(EVENT_DAO.checkEventHistory().get(i));
        }
        loadTableViewData(eventHistoryList);
        
    }
    
    public void loadTableViewData(ObservableList<Event> eventHistoryList) {
        this.tableColumnEventTypes.setCellValueFactory(new PropertyValueFactory("Type"));
        this.tableColumnEventTitles.setCellValueFactory(new PropertyValueFactory("Tittle"));
        this.tableColumnEventDates.setCellValueFactory(new PropertyValueFactory("eventDate"));
        tableViewEvents.setItems(eventHistoryList);
    }
    
    public void showConsultEventGUI() {
        tableViewEvents.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Event> observable, Event oldValue, Event newValue) -> {
            try {
                Stage stage =  (Stage) tableViewEvents.getScene().getWindow();
                
                stage.hide();
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvent.fxml"));
                fXMLLoader.load();
                ControllerConsultEvent controllerConsultEvent = fXMLLoader.getController();
                String eventTitle = observable.getValue().getTittle();
                controllerConsultEvent.getEventTitleSelected(eventTitle);
                controllerConsultEvent.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                Parent root = fXMLLoader.getRoot();
                
                Stage stageConsultEvent = new Stage();
                Scene scene = new Scene(root);
                stageConsultEvent.setResizable(false);
                stageConsultEvent.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
                stageConsultEvent.getIcons().add(new Image("/images/sgpca.png"));
                stageConsultEvent.setScene(scene);
                stageConsultEvent.show();
            } catch (IOException ex) {
                Logger.getLogger(ControllerConsultEventHistory.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
    }

    @FXML
    public void openScheduleEventGUI(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLScheduleEvent.fxml"));
            fXMLLoader.load();
            ControllerScheduleEvent controllerScheduleEvent = fXMLLoader.getController();
            controllerScheduleEvent.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultEventHistory.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControllerConsultEventHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    public void getOutOfEventHistory(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Desea regresar a la pagina principal?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) exitEventHistoryButton.getScene().getWindow();
            stage.close();
        }
    }
}
