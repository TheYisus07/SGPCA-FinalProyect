package gui;

import businesslogic.ConstancyDAO;
import businesslogic.EventDAO;
import domain.Constancy;
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
public class ControllerConsultConstancyList implements Initializable {
    private final ConstancyDAO CONSTANCY_DAO = new ConstancyDAO();
    private Event eventObject;
    private final EventDAO EVENT_DAO = new EventDAO();
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private TableView<Constancy> tableViewConstancy;

    @FXML
    private TableColumn<?, ?> tableColumnConstancyTypes;

    @FXML
    private TableColumn<?, ?> tableColumnConstancyNames;

    @FXML
    private Button generateCostancyEvent;

    @FXML
    private Button exitConstancyListButton;
    
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showConsultConstancyGUI();
    } 
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void closeWindow(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvent.fxml"));
            fXMLLoader.load();
            ControllerConsultEvent controllerConsultEvent = fXMLLoader.getController();
            String eventTitle = eventObject.getTittle();
            controllerConsultEvent.getEventTitleSelected( eventTitle);
            controllerConsultEvent.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultConstancyList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showConstancyList(String eventTitle) { 
        
        eventObject = EVENT_DAO.consultEvent(eventTitle);
        ObservableList<Constancy> constancyList = FXCollections.observableArrayList();
        for (int i = 0; i < CONSTANCY_DAO.consultConstancyList().size(); i++) {
            getConstancyList(i, eventTitle, constancyList);
        }
        loadTableViewData(constancyList);
   }
    
    public void getConstancyList(int i, String eventTitle, ObservableList<Constancy> constancyList) {
        String eventTitleExpected = CONSTANCY_DAO.consultConstancyList().get(i).getEventTitle();
        if(eventTitleExpected.equals(eventTitle)){
            constancyList.add(CONSTANCY_DAO.consultConstancyList().get(i));
        } 
    }
    
    public void loadTableViewData(ObservableList<Constancy> constancyList) {
        this.tableColumnConstancyTypes.setCellValueFactory(new PropertyValueFactory("RecognitionType"));
        this.tableColumnConstancyNames.setCellValueFactory(new PropertyValueFactory("InstitutionalMailValidator"));
        tableViewConstancy.setItems(constancyList);
    }
    
    public void showConsultConstancyGUI() {
        tableViewConstancy.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Constancy> observable, Constancy oldValue, Constancy newValue) -> {
            openConsultEventGUI();
        });
    }
    
    public void openConsultEventGUI() {
        try {
            Stage stage =  (Stage) tableViewConstancy.getScene().getWindow();

            stage.hide();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultConstancy.fxml"));
            fXMLLoader.load();
            ControllerConsultConstancy controllerConsultConstancy = fXMLLoader.getController();
            int positionConstancy = tableViewConstancy.getSelectionModel().selectedIndexProperty().intValue();
            controllerConsultConstancy.getEventForThisConstancy(eventObject, positionConstancy);
            controllerConsultConstancy.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();

            Stage stageConsultEvent = new Stage();
            Scene scene = new Scene(root);
            stageConsultEvent.setScene(scene);
            stageConsultEvent.setResizable(false);
            stageConsultEvent.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stageConsultEvent.getIcons().add(new Image("/images/sgpca.png"));
            stageConsultEvent.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEventHistory.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    

    @FXML
    public void openGenerateConstancyGUI(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLGenerateConstancy.fxml"));
            fXMLLoader.load();
            ControllerGenerateConstancy controllerGenerateConstancy = fXMLLoader.getController();
            controllerGenerateConstancy.getEventForThisConstancy(eventObject);
            controllerGenerateConstancy.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultConstancyList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void getOutOfConstancyList(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas regresar al historial?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
        }
    }
}
