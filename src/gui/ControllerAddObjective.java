package gui;

import businesslogic.BusinessException;
import businesslogic.WorkplanDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Objective;
import domain.Strategy;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * 
 * @author daniCV
 */

public class ControllerAddObjective implements Initializable {
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    private final WorkplanDAO WORKPLAN_DAO = new WorkplanDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();
    private final ObservableList<Strategy> LIST_OF_STRATEGIES;
    
    @FXML
    private JFXButton buttonCloseGuiAddNewStrategy;  
    
    @FXML
    private JFXButton buttonSaveGuiAddNewStrategy;
    
    @FXML
    private Label labelWorkplanKeycode;
    
    @FXML
    private TableView<Strategy> tableViewAddStrategy;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyNumber;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyDescription;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyGoal;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyAction;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyResult;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnAddStrategyDelete;  
    
    @FXML
    private JFXTextArea textAreaAddObjectiveDescription;
    
    @FXML
    private JFXTextField textFieldAddObjectiveTitle;
    
    @FXML
    private JFXTextArea textAreaAddStrategyNumber;
    
    @FXML
    private JFXTextArea textAreaAddStrategyDescription;
    
    @FXML
    private JFXTextArea textAreaAddStrategyGoal;
    
    @FXML
    private JFXTextArea textAreaAddStrategyAction;
    
    @FXML
    private JFXTextArea textAreaAddStrategyResult;

    public ControllerAddObjective() {
        this.LIST_OF_STRATEGIES = FXCollections.observableArrayList();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToAddObjective();
    }
    
    public void getWorkplanKeycode(String workplanKeycode) {
        labelWorkplanKeycode.setText(workplanKeycode);
    }
    
    public void validFieldsToAddObjective() {
        textFieldAddObjectiveTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddObjectiveTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddObjectiveTitle.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddObjectiveTitle.validate();
            } 
        });
        textAreaAddObjectiveDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddObjectiveDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddObjectiveDescription.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddObjectiveDescription.validate();
            } 
        });
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyNumber.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddStrategyNumber.validate();
            } 
        });
        textAreaAddStrategyDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyDescription.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddStrategyDescription.validate();
            } 
        });
        textAreaAddStrategyGoal.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyGoal.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyGoal.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddStrategyGoal.validate();
            } 
        });
        textAreaAddStrategyAction.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyAction.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyAction.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyAction.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddStrategyAction.validate();
            } 
        });
        textAreaAddStrategyResult.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyResult.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyResult.focusedProperty().addListener((obsevableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaAddStrategyResult.validate();
            } 
        });
    }
    
    public void showGuiObjectiveSuccessfullySaved() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de inserción de Objetivo a" + labelWorkplanKeycode.getText());        
        alert.setHeaderText("El Objetivo ha sido integrado al Plan de Trabajo");
        alert.setContentText("Los cambios se realizaron existosamente.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            Stage stage = (Stage) buttonSaveGuiAddNewStrategy.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    public void addNewObjective(ActionEvent actionEvent) {
        String workplanKeycode = labelWorkplanKeycode.getText();
        String objectiveTitle = null;
        String objectiveDescription = null;        
        
        if (textFieldAddObjectiveTitle.validate()) {
            objectiveTitle = textFieldAddObjectiveTitle.getText();
        }
        if (textAreaAddObjectiveDescription.validate()) {
            objectiveDescription = textAreaAddObjectiveDescription.getText();
        }
        
        if ((objectiveTitle != null) && (objectiveDescription != null)) {
            try {
                Objective objective = new Objective(objectiveTitle, objectiveDescription, "Pendiente", workplanKeycode);
                WORKPLAN_DAO.addObjetive(objective);
                for (int i = 0; i < LIST_OF_STRATEGIES.size(); i++) {
                    LIST_OF_STRATEGIES.get(i).setObjectiveID(WORKPLAN_DAO.getLastInsertedObjectiveId());                
                    WORKPLAN_DAO.addStrategy(LIST_OF_STRATEGIES.get(i));
                }
                showGuiObjectiveSuccessfullySaved();
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        }
    }
    
    public void clearFieldsToAddStrategy() {
        textAreaAddStrategyNumber.setText("");
        textAreaAddStrategyDescription.setText("");
        textAreaAddStrategyGoal.setText("");
        textAreaAddStrategyAction.setText("");
        textAreaAddStrategyResult.setText("");        
        textAreaAddStrategyNumber.resetValidation();
        textAreaAddStrategyDescription.resetValidation();
        textAreaAddStrategyGoal.resetValidation();
        textAreaAddStrategyAction.resetValidation();
        textAreaAddStrategyResult.resetValidation();
    }

    public void addNewStrategyToTable(ActionEvent actionEvent) {
        int strategyNumber = 0;
        boolean isValidadStrategyNumber = false;
        String strategyDescription = null;
        String strategyGoal = null;
        String strategyAction = null;
        String strategyResult = null;        
        
        if (textAreaAddStrategyNumber.validate()) {
            try {
                strategyNumber = Integer.parseInt(textAreaAddStrategyNumber.getText().trim());
            } catch(NumberFormatException ex) {
                Logger.getLogger(ControllerAddObjective.class.getName()).log(Level.SEVERE, null, ex);
            }
            isValidadStrategyNumber = true;
        } 
        if (textAreaAddStrategyDescription.validate() && (!textAreaAddStrategyDescription.getText().trim().equals(""))) {
            strategyDescription = textAreaAddStrategyDescription.getText();
        }
        if (textAreaAddStrategyGoal.validate() && (!textAreaAddStrategyGoal.getText().trim().equals(""))) {
            strategyGoal = textAreaAddStrategyGoal.getText();
        }
        if (textAreaAddStrategyAction.validate() && (!textAreaAddStrategyAction.getText().trim().equals(""))) {
            strategyAction = textAreaAddStrategyAction.getText();
        }
        if (textAreaAddStrategyResult.validate() && (!textAreaAddStrategyResult.getText().trim().equals(""))) {
            strategyResult = textAreaAddStrategyResult.getText();
        }
        if (isValidadStrategyNumber && (strategyDescription != null) && (strategyGoal != null) && (strategyAction != null) && (strategyResult != null)) {
            Strategy newStrategy = new Strategy(strategyNumber, strategyGoal, strategyDescription, strategyAction, strategyResult);
            LIST_OF_STRATEGIES.add(newStrategy);
            loadDateOfStrategiesTable();
            clearFieldsToAddStrategy();
            tableViewAddStrategy.setItems(LIST_OF_STRATEGIES);
        } else {
            textAreaAddStrategyNumber.setText("");
        }
    }

    public void loadDateOfStrategiesTable() {
        tableColumnAddStrategyNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableColumnAddStrategyGoal.setCellValueFactory(new PropertyValueFactory<>("goal"));
        tableColumnAddStrategyDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnAddStrategyAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        tableColumnAddStrategyResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        
        addDeleteImageToObjetiveTable(); 
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAddStrategyGoal);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAddStrategyDescription);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAddStrategyAction);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAddStrategyResult);     
    }
    
    public void addDeleteImageToObjetiveTable() {
        Callback<TableColumn<Strategy, String>, TableCell<Strategy, String>> cellFoctory = (TableColumn<Strategy, String> param) -> {
            final TableCell<Strategy, String> cell = new TableCell<Strategy, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView deleteIcon = new ImageView("/images/delete.png");
                        deleteIcon.setFitHeight(30.0);
                        deleteIcon.setFitWidth(30.0);
                        deleteIcon.setCursor(Cursor.HAND);
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {                 
                            int index = tableViewAddStrategy.getSelectionModel().getSelectedIndex();
                            LIST_OF_STRATEGIES.remove(index);
                        }); 
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        tableColumnAddStrategyDelete.setCellFactory(cellFoctory);
    }
  
    @FXML
    public void cancelAddNewObjective(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir de integración de Objetivo");        
        alert.setHeaderText("¿Desea cancelar la integración del objetivo?");
        alert.setContentText("El Objetivo no se registrará.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            Stage stage = (Stage) buttonCloseGuiAddNewStrategy.getScene().getWindow();
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
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerAddObjective.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}