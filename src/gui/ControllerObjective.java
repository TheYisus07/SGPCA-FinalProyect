package gui;

import businesslogic.BusinessException;
import businesslogic.WorkplanDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import domain.Strategy;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerObjective implements Initializable {
    private final WorkplanDAO WORKPLAN_DAO = new WorkplanDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();
    
    @FXML
    private Label labelObjectiveTitle;
    
    @FXML
    private JFXTextArea textAreaObjectiveDescription;
    
    @FXML
    private JFXButton buttonCloseWindow;
    
    @FXML
    private TableView<Strategy> tableViewObjectiveSelected;
    
    @FXML
    private TableColumn tableColumnObjectiveNumber;
    
    @FXML
    private TableColumn tableColumnObjectiveStrategy;
    
    @FXML
    private TableColumn tableColumnObjectiveGoal;
    
    @FXML
    private TableColumn tableColumnObjectiveAction;
    
    @FXML
    private TableColumn tableColumnObjectiveResult;    
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    
    public void getObjectiveId(int objectiveId) {
        objectiveDetails(objectiveId);
        strategyTableOfTheSelectedObjective(objectiveId);
    }
    
    public void objectiveDetails(int objectiveId) {
        String objectiveTitle = null;
        String objectiveDescription = null;
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                int expectedObjectiveId = WORKPLAN_DAO.consultListOfObjectives().get(i).getObjectiveId();
                if (expectedObjectiveId == objectiveId) {
                    objectiveTitle = WORKPLAN_DAO.consultListOfObjectives().get(i).getTitle();
                    objectiveDescription = WORKPLAN_DAO.consultListOfObjectives().get(i).getDescription();
                }
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        labelObjectiveTitle.setText(objectiveTitle);
        textAreaObjectiveDescription.setText(objectiveDescription);
    }
    
    public void strategyTableOfTheSelectedObjective(int objectiveId) {
        ObservableList<Strategy> strategyList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < WORKPLAN_DAO.consulListOfStrategys().size(); i++) {
                if (WORKPLAN_DAO.consulListOfStrategys().get(i).getObjectiveID() == objectiveId) {
                    strategyList.add(WORKPLAN_DAO.consulListOfStrategys().get(i));
                }
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }    
         
        tableColumnObjectiveNumber.setCellValueFactory(new PropertyValueFactory("Number"));
        tableColumnObjectiveStrategy.setCellValueFactory(new PropertyValueFactory("Description"));
        tableColumnObjectiveGoal.setCellValueFactory(new PropertyValueFactory("Goal"));
        tableColumnObjectiveAction.setCellValueFactory(new PropertyValueFactory("Action"));
        tableColumnObjectiveResult.setCellValueFactory(new PropertyValueFactory("Result"));  
        
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnObjectiveStrategy);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnObjectiveGoal);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnObjectiveAction);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnObjectiveResult);
      
        tableViewObjectiveSelected.setItems(strategyList);
    }
    
    @FXML
    public void closeWindowObjective(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonCloseWindow.getScene().getWindow();
        stage.close();
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
            Logger.getLogger(ControllerObjective.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}