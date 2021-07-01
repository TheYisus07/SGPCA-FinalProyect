package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import businesslogic.WorkplanDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Member;
import domain.Objective;
import domain.Strategy;
import domain.Workplan;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author daniCV
 */

public class ControllerWorkplan implements Initializable {
    
    private final WorkplanDAO WORKPLAN_DAO = new WorkplanDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();
    private ObservableList<Objective> listOfAllObjectives = FXCollections.observableArrayList();
    private ObservableList<Strategy> listStrategysOfObjectiveSelected = FXCollections.observableArrayList();
    private ArrayList<Strategy> arrayListOfStrategiesToEliminate = new ArrayList<>(); 
    private ArrayList<Strategy> arrayListNewStrategies = new ArrayList<>();
    private double x = 0;
    private double y = 0;
    
    @FXML
    private AnchorPane anchorPaneLateralMenu;
    
    @FXML
    private AnchorPane anchorPaneMainWorplan;
    
    @FXML
    private AnchorPane anchorPaneManageWorkplan;
    
    @FXML
    private AnchorPane anchorPaneWhiteWorkplan;
    
    @FXML
    private AnchorPane anchorPaneBlackWorkplan;
    
    @FXML
    private AnchorPane anchorPaneArchievedObjectives;
    
    @FXML
    private AnchorPane anchorPanelAddWorkplan;
    
    @FXML
    private AnchorPane anchorPaneModifyObjective;
    
    @FXML
    private JFXButton buttonManageWorkplan;
    
    @FXML
    private JFXButton buttonMenuCurriculumGeneral;
    
    @FXML
    private JFXButton buttonMenuCurriculumPersonal;
    
    @FXML
    private JFXButton buttonMenuEventos;
    
    @FXML
    private JFXButton buttonMenuReuniones;
    
    @FXML
    private JFXButton buttonMenuProduccion;
    
    @FXML
    private JFXButton buttonMenuProyectos;
    
    @FXML
    private JFXButton buttonMenuExit;
    
    @FXML 
    private JFXButton buttonArchievedObjectives;
    
    @FXML 
    private JFXButton buttonBackArchievedObjectives;
    
    @FXML
    private JFXComboBox<String> comboBoxWorkplanNames;  
    
    @FXML
    private JFXDatePicker datePickerManagedWorkplanStartDate;
    
    @FXML
    private JFXDatePicker datePickerManagedWorkplanFinishDate;
    
    @FXML
    private JFXDatePicker datePickerAddWorkplanEndDate;
    
    @FXML
    private JFXDatePicker datePickerAddWorkplanStartDate;
    
    @FXML
    private ImageView imagenViewMainLateralMenu;
    
    @FXML
    private ImageView imagenViewMainBackLateralMenu;
    
    @FXML
    private Label labelAcademicGroup;
    
    @FXML
    private Label labelMemberName;
    
    @FXML
    private Label labelMemberEmail;
    
    @FXML
    private Label labelMemberPosition;
    
    @FXML
    private Label labelFirtsWorkplanKeycode;
    
    @FXML
    private Label labelObjectiveId;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private TableView<Objective> tableViewObjectives;
    
    @FXML
    private TableColumn tableColumnObjectiveNames;
    
    @FXML 
    private TableView<Objective> tableViewArchievedObjectives;
    
    @FXML 
    private TableColumn tableColumnArchievedTitleObjectives;
    
    @FXML
    private TableView<Objective> tableViewAllObjectives;
    
    @FXML
    private TableColumn<Objective, String> tableColumnAllObjectiveTitles;
    
    @FXML
    private TableColumn<Objective, String> tableColumnAllObjectiveStatus;
    
    @FXML
    private TableColumn<Objective, String> tableColumnAllObjectiveModify;
    
    @FXML
    private TableView<Strategy> tableViewModifyStrategy;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyNumber;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyDescription;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyGoal;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyAction;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyResult;
    
    @FXML
    private TableColumn<Strategy, String> tableColumnModifyStrategyDelete;
    
    @FXML
    private JFXTextArea textAreaModifyObjectiveDescription;
    
    @FXML
    private JFXTextField textFieldModifyObjectiveTitle;
    
    @FXML
    private JFXTextArea textAreaAddStrategyGoal;
    
    @FXML
    private JFXTextArea textAreaAddStrategyAction;
    
    @FXML
    private JFXTextArea textAreaAddStrategyNumber;
    
    @FXML
    private JFXTextArea textAreaAddStrategyResult;
    
    @FXML
    private JFXTextArea textAreaAddStrategyDescription;
    
    @FXML
    private JFXTextField textFieldManagedWorkplanKeycode;
    
    @FXML
    private JFXTextField textFieldAddWorkplanKeycode;  
    
    @FXML
    private JFXRadioButton radioButtonStatusPending;
    
    @FXML
    private JFXRadioButton radioButtonStatusArchieved;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillComboBoxWithWorkplanNames();
        mainLateralMenu();
        tableOfObjectivesArchieved();
        selectedPendingObjective();
        selectedArchievedObjectives();
        chooseFxmlFile();
    }  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        responsibleForTheWorkplan(memberFullName);
        Member member = new Member();
        try {
            member = MEMBER_DAO.consultMember(memberFullName);        
            if (!member.getPosition().equals("Responsable")) {
                buttonManageWorkplan.setDisable(true);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
   
    public void chooseFxmlFile() {  
        FXMLLoader fXMLLoader = new FXMLLoader();
        buttonMenuCurriculumGeneral.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultGeneralCurriculum.fxml", fXMLLoader, event);
        });
        buttonMenuEventos.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultEventHistory.fxml", fXMLLoader, event);
        });        
        buttonMenuReuniones.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultMeetingHistory.fxml", fXMLLoader, event);
        });
        buttonMenuCurriculumPersonal.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultPersonalCurriculum.fxml", fXMLLoader, event);
        });
        buttonMenuProyectos.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultResearchProjects.fxml", fXMLLoader, event);            
        }); 
        buttonMenuProduccion.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLConsultEvidence.fxml", fXMLLoader, event);            
        }); 
        buttonMenuExit.setOnMouseClicked(event -> {
            menuOptionButtons("FXMLLogin.fxml", fXMLLoader, event);             
        });
        
    }
    
    public void sendMemberNameToOtherWindows(String fxmlController, FXMLLoader fXMLLoader) {
        switch (fxmlController) {
                case "FXMLConsultGeneralCurriculum.fxml":
                    ControllerConsultGeneralCurriculum controllerConsultGeneralCurriculum = fXMLLoader.getController();
                    controllerConsultGeneralCurriculum.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                case "FXMLConsultEventHistory.fxml":
                    ControllerConsultEventHistory controllerConsultEventHistory = fXMLLoader.getController();
                    controllerConsultEventHistory.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                case "FXMLConsultMeetingHistory.fxml":
                    ControllerConsultMeetingHistory controllerConsultMeetingHistory = fXMLLoader.getController();
                    controllerConsultMeetingHistory.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                case "FXMLConsultPersonalCurriculum.fxml":
                    ControllerConsultPersonalCurriculum controllerConsultPersonalCurriculum = fXMLLoader.getController();
                    controllerConsultPersonalCurriculum.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                case "FXMLConsultResearchProjects.fxml":
                    ControllerConsultResearchProjects consultResearchProjects = fXMLLoader.getController();
                    consultResearchProjects.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                case "FXMLConsultEvidence.fxml":
                    ControllerConsultEvidence controllerConsultEvidence = fXMLLoader.getController();
                    controllerConsultEvidence.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
                break;
                
                default:
                break;                
            }  
    }
    
    public void menuOptionButtons(String fxmlController, FXMLLoader fXMLLoader, MouseEvent mouseEvent) {
        try {
            fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource(fxmlController));
            fXMLLoader.load();            
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);                         
            sendMemberNameToOtherWindows(fxmlController, fXMLLoader);             
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiWorkplaSuccessfullySaved(String newWorkplanKeycode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de guardado exitoso del Plan de Trabajo");        
        alert.setHeaderText("Se ha logrado registrar el Plan de trabajo correctamente");
        alert.setContentText("Clave del Plan de trabajo: " + newWorkplanKeycode);
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            buttonCancelAddWorkplan();
            buttonArchievedObjectives.setVisible(false);
            searchSelectedOrAddedWorkplanToManage(newWorkplanKeycode);
            anchorPaneManageWorkplan.setVisible(true); 
        }
    }
    
    public void validFieldsToAddWorkPlan() {
        textFieldAddWorkplanKeycode.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddWorkplanKeycode.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddWorkplanKeycode.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddWorkplanKeycode.validate();
            }     
        });
        datePickerAddWorkplanStartDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerAddWorkplanStartDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerAddWorkplanStartDate.validate();
            }     
        });
        datePickerAddWorkplanEndDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerAddWorkplanEndDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerAddWorkplanEndDate.validate();
            }     
        });
    }
    
    public void validFieldsAdministrationWorkplan() {
        textFieldManagedWorkplanKeycode.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldManagedWorkplanKeycode.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldManagedWorkplanKeycode.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
                textFieldManagedWorkplanKeycode.validate();
            }
        });
        
        datePickerManagedWorkplanStartDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerManagedWorkplanStartDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerManagedWorkplanStartDate.validate();
            }     
        });
        
        datePickerManagedWorkplanFinishDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerManagedWorkplanFinishDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerManagedWorkplanFinishDate.validate();
            }     
        });
    }
    
    public void validFieldsAddNewObjective() {
        textFieldModifyObjectiveTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldModifyObjectiveTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldModifyObjectiveTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
                textFieldModifyObjectiveTitle.validate();
            }
        });
        textAreaModifyObjectiveDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaModifyObjectiveDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaModifyObjectiveDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
               textAreaModifyObjectiveDescription.validate();
            }
        });
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyNumber.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textAreaAddStrategyNumber.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
               textAreaAddStrategyNumber.validate();
            }
        });
        textAreaAddStrategyDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
               textAreaAddStrategyDescription.validate();
            }
        });
        textAreaAddStrategyGoal.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyGoal.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyGoal.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
               textAreaAddStrategyGoal.validate();
            }
        });
        textAreaAddStrategyAction.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyAction.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyAction.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
              textAreaAddStrategyAction.validate();
            }
        });
        textAreaAddStrategyResult.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaAddStrategyResult.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaAddStrategyResult.focusedProperty().addListener((observableValue, oldValue, newValue) -> { 
            if (!newValue) {
              textAreaAddStrategyResult.validate();
            }
        });
    }
    
    public void showGuiExistsWorkplan(String newWorkplanKeycode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Plan de Trabajo repetido");        
        alert.setHeaderText("El registro ya se encuentra en la base de datos. ");
        alert.setContentText("Clave del Plan de trabajo: " + newWorkplanKeycode);
        alert.showAndWait();       
    }
    
    @FXML
    public void saveNewWorkplan(ActionEvent actionEvent) {
        String newWorkplanKeycode = null;
        Date newWorkplanStartDate = null;
        Date newWorkplanEndDate = null;
        boolean validDate = false; 
        boolean existsWorkplan = false;
        
        if ((textFieldAddWorkplanKeycode.validate()) && (!textFieldAddWorkplanKeycode.getText().trim().equals(""))) {
            try {
                newWorkplanKeycode = textFieldAddWorkplanKeycode.getText();
                for (int i = 0; i < WORKPLAN_DAO.consultListOfWorkplans().size(); i++) {
                    String expectedWorkplanKeycode = WORKPLAN_DAO.consultListOfWorkplans().get(i).getKeyCode();
                    if (newWorkplanKeycode.equals(expectedWorkplanKeycode)) {
                        showGuiExistsWorkplan(newWorkplanKeycode); 
                        existsWorkplan = true;
                    }
                    textFieldAddWorkplanKeycode.setText("");
                }
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        }
        if (datePickerAddWorkplanStartDate.validate() && datePickerAddWorkplanEndDate.validate()) {
            newWorkplanStartDate = convertToDate(datePickerAddWorkplanStartDate.getValue());
            newWorkplanEndDate = convertToDate(datePickerAddWorkplanEndDate.getValue());
            if (newWorkplanStartDate.before(newWorkplanEndDate)) {
                validDate = true;
            } else {
                datePickerAddWorkplanEndDate.setValue(null);               
                datePickerAddWorkplanEndDate.validate();
            }
        }        
        String memberFullName = labelLoggedInUser.getText();
        if (!existsWorkplan && newWorkplanKeycode != null && validDate) {
            Workplan newWorkplan = new Workplan(newWorkplanKeycode, newWorkplanStartDate, newWorkplanEndDate, memberFullName);
            try {
                WORKPLAN_DAO.addWorkPlan(newWorkplan);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            showGuiWorkplaSuccessfullySaved(newWorkplanKeycode); 
        }
        imagenViewMainLateralMenu.setVisible(false);
    }
    
    public void showGuiAddWorkplan() {
        anchorPaneBlackWorkplan.setVisible(true);
        anchorPanelAddWorkplan.setVisible(true);
        validFieldsToAddWorkPlan();
    }
    
    public void buttonCancelAddWorkplan() {
        textFieldAddWorkplanKeycode.setText(null);
        datePickerAddWorkplanStartDate.setValue(null);
        datePickerAddWorkplanEndDate.setValue(null);
        textFieldAddWorkplanKeycode.resetValidation();
        datePickerAddWorkplanStartDate.resetValidation();
        datePickerAddWorkplanEndDate.resetValidation();
        anchorPanelAddWorkplan.setVisible(false);
        anchorPaneBlackWorkplan.setVisible(false);
        comboBoxWorkplanNames.getItems().clear();
        fillComboBoxWithWorkplanNames();
        tableViewArchievedObjectives.getItems().clear();
    }
    
    public void fillComboBoxWithWorkplanNames() { 
        try {
            ObservableList<String> workplanList = FXCollections.observableArrayList();
            workplanList.add("+Añadir Plan de Trabajo");
            for (int i = 0; i < WORKPLAN_DAO.consultListOfWorkplans().size(); i++) {
                String workplanName = WORKPLAN_DAO.consultListOfWorkplans().get(i).getKeyCode();
                String workplanStartDate = Integer.toString(WORKPLAN_DAO.consultListOfWorkplans().get(i).getStartDate().getYear() + 1900);
                String workplanFinishDate = Integer.toString(WORKPLAN_DAO.consultListOfWorkplans().get(i).getFinishDate().getYear() + 1900);
                String comboBoxInfo = workplanName + " [" + workplanStartDate + "-" + workplanFinishDate + "]";
                workplanList.add(comboBoxInfo);
            } 
            comboBoxWorkplanNames.setItems(workplanList);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public String getWorkplanKeycode() {
        String comboBoxInfo = comboBoxWorkplanNames.getValue();
        String workplanKeycode = null;
        if (comboBoxInfo != null) {
            if (comboBoxInfo.contains("[")) {
                String[] parts = comboBoxInfo.split("\\[");
                workplanKeycode = parts[0].trim();
            }
        }
        return workplanKeycode;
    }
    
    @FXML
    public void selectComboBoxWorkplanKeycodes(ActionEvent actionEvent) {
        tableViewObjectives.getItems().clear();
        String workplanKeycode = getWorkplanKeycode();
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfWorkplans().size(); i++) {
                String expectedWorplanName = WORKPLAN_DAO.consultListOfWorkplans().get(i).getKeyCode();
                if (workplanKeycode == null ? expectedWorplanName == null : workplanKeycode.equals(expectedWorplanName)) {
                    pendingObjectivesOfTheSelectedWorkplan(workplanKeycode);
                    archievedObjectivesTheSelectedWorkplan(workplanKeycode); 
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        if (comboBoxWorkplanNames.getValue() == null ? ("+Añadir Plan de Trabajo") == null : "+Añadir Plan de Trabajo".equals(comboBoxWorkplanNames.getValue())) {
            showGuiAddWorkplan(); 
        } 
    }
    
    public void responsibleForTheWorkplan(String memberName) {
        Member memberLogin = new Member();
        try {
            memberLogin = MEMBER_DAO.consultMember(memberName);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        String memberAcademicGroup = memberLogin.getKeycodeAcademicGroup();
        String memberStudyGrade = memberLogin.getStudyGrade();
        String memberStudyArea = memberLogin.getStudyArea();
        String memberPosition = memberLogin.getPosition();
        labelMemberName.setText(memberStudyGrade);
        labelAcademicGroup.setText(memberAcademicGroup);
        labelMemberEmail.setText(memberStudyArea);
        labelMemberPosition.setText(memberPosition);                    
    }
    
    public void pendingObjectivesOfTheSelectedWorkplan(String workplanName) { 
        ObservableList<Objective> objectivePendingList = FXCollections.observableArrayList();
        String statusPending = "Pendiente";
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                boolean isPendingObjective = statusPending.equals(WORKPLAN_DAO.consultListOfObjectives().get(i).getStatus());
                if (workplanName.equals(WORKPLAN_DAO.consultListOfObjectives().get(i).getWorkplanKeyCode()) && isPendingObjective) {
                    objectivePendingList.add(WORKPLAN_DAO.consultListOfObjectives().get(i));
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnObjectiveNames);
        tableColumnObjectiveNames.setCellValueFactory(new PropertyValueFactory("Title"));
        tableViewObjectives.setItems(objectivePendingList);
    }
    
    public void archievedObjectivesTheSelectedWorkplan(String workplanName) {
        ObservableList<Objective> archievedObjectivesList = FXCollections.observableArrayList();
        String statusArchieved = "Cumplido";
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                boolean isArchievedObjective = statusArchieved.equals(WORKPLAN_DAO.consultListOfObjectives().get(i).getStatus());
                if (workplanName.equals(WORKPLAN_DAO.consultListOfObjectives().get(i).getWorkplanKeyCode()) && isArchievedObjective) {
                    archievedObjectivesList.add(WORKPLAN_DAO.consultListOfObjectives().get(i));
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnArchievedTitleObjectives);
        tableColumnArchievedTitleObjectives.setCellValueFactory(new PropertyValueFactory("Title"));
        tableViewArchievedObjectives.setItems(archievedObjectivesList);
    }
    
    public void hideLateralMenu() {
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), anchorPaneWhiteWorkplan);
        fadeTransition1.setFromValue(0.15);
        fadeTransition1.setToValue(0);
        fadeTransition1.play();
            
        fadeTransition1.setOnFinished(event1 -> {
            anchorPaneWhiteWorkplan.setVisible(false);
        });

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneLateralMenu);
        translateTransition1.setByX(-600);
        translateTransition1.play();
    }
    
    public void showLateralMenu() {
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), anchorPaneWhiteWorkplan);
        fadeTransition1.setFromValue(0);
        fadeTransition1.setToValue(0.15);
        fadeTransition1.play();
            
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneLateralMenu);
        translateTransition1.setByX(+600);
        translateTransition1.play();
    }
    
    public void mainLateralMenu() {
        hideLateralMenu();

        imagenViewMainLateralMenu.setOnMouseClicked(event -> { 
            anchorPaneWhiteWorkplan.setVisible(true);
            imagenViewMainLateralMenu.setVisible(false);
            buttonArchievedObjectives.setDisable(true);
            showLateralMenu();
        });
   
        imagenViewMainBackLateralMenu.setOnMouseClicked(event -> {
            hideLateralMenu();
            buttonArchievedObjectives.setDisable(false);
            imagenViewMainLateralMenu.setVisible(true);
        });
    }
    
    public void hideArchievedObjectives() {
        anchorPaneWhiteWorkplan.setVisible(false);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneArchievedObjectives);
        translateTransition1.setByX(1200);
        translateTransition1.play();
    }
    
    public void showArchievedObjectives() {    
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneArchievedObjectives);
        translateTransition1.setByX(-1200);
        translateTransition1.play();
    }
    
    public void tableOfObjectivesArchieved() {
        hideArchievedObjectives();

        buttonArchievedObjectives.setOnMouseClicked(event -> {       
            anchorPaneWhiteWorkplan.setVisible(true);
            buttonArchievedObjectives.setVisible(false);
            showArchievedObjectives();
        });

        buttonBackArchievedObjectives.setOnMouseClicked(event -> {
            hideArchievedObjectives();
            buttonArchievedObjectives.setVisible(true);
        });
    }
    
    public int getObjectiveIdSelected() {
        int objectiveId = 0;
        boolean notSelectedTablePendingObjectives = tableViewObjectives.getSelectionModel().isEmpty();
        boolean notSelectedTableArchievedObjectives = tableViewArchievedObjectives.getSelectionModel().isEmpty();
        if (!notSelectedTablePendingObjectives) {
            objectiveId = tableViewObjectives.getSelectionModel().getSelectedItem().getObjectiveId();
        } 
        if (!notSelectedTableArchievedObjectives) {
            objectiveId = tableViewArchievedObjectives.getSelectionModel().getSelectedItem().getObjectiveId();
        }
        return objectiveId;
    }
    
    public void showGuiObjective() {
        int objectiveId = getObjectiveIdSelected();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLObjective.fxml"));
            fXMLLoader.load();
            ControllerObjective controllerObjective = fXMLLoader.getController();
            controllerObjective.getObjectiveId(objectiveId);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); 
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });        
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectedPendingObjective() {            
        tableViewObjectives.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Objective> observable, Objective oldObjective, Objective newObjective) -> {
            if (tableViewObjectives.getSelectionModel().getSelectedItem() != null) {
                showGuiObjective();
            }
        });
    }
    
    public void selectedArchievedObjectives() {
        tableViewArchievedObjectives.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Objective> observable, Objective oldObjective, Objective newObjective) -> {
            if (tableViewArchievedObjectives.getSelectionModel().getSelectedItem() != null) {
                showGuiObjective();
            }
        });
    }
    
    @FXML
    public void clearTableSelection(MouseEvent mouseEvent) {
        tableViewObjectives.getSelectionModel().clearSelection();
        tableViewArchievedObjectives.getSelectionModel().clearSelection();
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void showGuiSelectWorkplan() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Plan de Trabajo no seleccionado");
        alert.setHeaderText("Se necesita tener un Plan de Trabajo seleccionado para administrarlo");
        alert.setContentText("Inténtelo nuevamente una vez que haya seleccionado uno.");
        alert.showAndWait();
    }
    
    public void searchSelectedOrAddedWorkplanToManage(String managedWorkplanKeycode) {
        labelFirtsWorkplanKeycode.setText(managedWorkplanKeycode);
        textFieldManagedWorkplanKeycode.setText(managedWorkplanKeycode);
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfWorkplans().size(); i++) {
                String actualWorkplanKeycode = WORKPLAN_DAO.consultListOfWorkplans().get(i).getKeyCode();
                if (managedWorkplanKeycode.equals(actualWorkplanKeycode)) {
                    Date starDate;
                    starDate = WORKPLAN_DAO.consultListOfWorkplans().get(i).getStartDate();
                    Date finishDate;
                    finishDate = WORKPLAN_DAO.consultListOfWorkplans().get(i).getFinishDate();
                    datePickerManagedWorkplanStartDate.setValue(convertToLocalDate(starDate));
                    datePickerManagedWorkplanFinishDate.setValue(convertToLocalDate(finishDate));
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        fillTableWithAllObjectives(managedWorkplanKeycode);
    }
    
    @FXML
    public void showGuiManageWorkplanSelected(ActionEvent actionEvent) {
        boolean notSelectedWorkplan = comboBoxWorkplanNames.getSelectionModel().isEmpty();        
        if (!notSelectedWorkplan) {
            imagenViewMainLateralMenu.setVisible(false);
            buttonArchievedObjectives.setVisible(false);
            anchorPaneMainWorplan.setVisible(false);
            anchorPaneManageWorkplan.setVisible(true);
            validFieldsAdministrationWorkplan();
            String managedWorkplanKeycode = getWorkplanKeycode();
            searchSelectedOrAddedWorkplanToManage(managedWorkplanKeycode);
        } else {           
            showGuiSelectWorkplan();    
        }
    }
    
    public void showGuiSuccessfullyModifiedObjective() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Objetivo modificado");        
        alert.setHeaderText("EL Objetivo ha sido actualizado");
        alert.setContentText("Presione el botón de refrescar para ver los cambios.");
        alert.showAndWait();        
        textAreaAddStrategyNumber.resetValidation();
        textAreaAddStrategyDescription.resetValidation();
        textAreaAddStrategyGoal.resetValidation();
        textAreaAddStrategyAction.resetValidation();
        textAreaAddStrategyResult.resetValidation();
        anchorPaneModifyObjective.setVisible(false);
    }
    
    @FXML
    public void saveModificationOfSelectedObjective(ActionEvent actionEvent) {        
        String objectiveTitle = null;
        String objectiveDescription = null;
        String objectiveStatus = null;
        
        if (textFieldModifyObjectiveTitle.validate() && (!textFieldModifyObjectiveTitle.getText().trim().equals(""))) {
            objectiveTitle = textFieldModifyObjectiveTitle.getText(); 
        }
        if (textAreaModifyObjectiveDescription.validate() && (!textAreaModifyObjectiveDescription.getText().trim().equals(""))) {
            objectiveDescription = textAreaModifyObjectiveDescription.getText();
        }
        if (radioButtonStatusArchieved.isSelected()) {
            objectiveStatus = "Cumplido";
        } else if (radioButtonStatusPending.isSelected()) {
            objectiveStatus = "Pendiente";
        }
        if ((objectiveTitle != null) && (objectiveDescription != null) && (objectiveStatus != null)) {
            try {
                int objectiveId = Integer.parseInt(labelObjectiveId.getText());
                Objective modifiedObjective = new Objective(objectiveTitle, objectiveDescription, objectiveStatus, labelFirtsWorkplanKeycode.getText());
                WORKPLAN_DAO.modifyObjective(modifiedObjective, objectiveId);
                arrayListNewStrategies.removeAll(arrayListOfStrategiesToEliminate);
                for (int i = 0; i < arrayListNewStrategies.size(); i++) {
                    WORKPLAN_DAO.addStrategy(arrayListNewStrategies.get(i));
                }
                for (int i = 0; i < arrayListOfStrategiesToEliminate.size(); i++) {
                    WORKPLAN_DAO.deleteStrategyByObjectiveId(arrayListOfStrategiesToEliminate.get(i).getStrategyId());
                } 
                showGuiSuccessfullyModifiedObjective();
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
    
    @FXML
    public void addStrategiesToTheModifiedObjective(ActionEvent actionEvent) {
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
                Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
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
        int objectiveId = Integer.parseInt(labelObjectiveId.getText().trim());
        if ((isValidadStrategyNumber) && (strategyDescription != null) && (strategyGoal != null) && (strategyAction != null) && (strategyResult != null)) {
            Strategy newStrategyAdded = new Strategy(strategyNumber, strategyGoal, strategyDescription, strategyAction, strategyResult, objectiveId);
            listStrategysOfObjectiveSelected.add(newStrategyAdded);            
            arrayListNewStrategies.add(newStrategyAdded);            
            clearFieldsToAddStrategy();
            loadDataStrategiesTable();
            tableViewModifyStrategy.setItems(listStrategysOfObjectiveSelected);   
        } else {
            textAreaAddStrategyNumber.setText("");
        } 
    }
               
    public void showGuiUpdateObjectiveOfWorkplanSelected(Objective objectiveSelected) { 
        clearFieldsToAddStrategy();
        anchorPaneModifyObjective.setVisible(true);
        validFieldsAddNewObjective();
        textFieldModifyObjectiveTitle.setText(objectiveSelected.getTitle());
        textAreaModifyObjectiveDescription.setText(objectiveSelected.getDescription());
        int objectiveId = objectiveSelected.getObjectiveId();      
        labelObjectiveId.setText(objectiveId + "");
        String objeciveStatus = objectiveSelected.getStatus();
        if (objeciveStatus.equals("Cumplido")) {
            radioButtonStatusArchieved.setSelected(true);
            radioButtonStatusPending.setSelected(false);
        } else if (objeciveStatus.equals("Pendiente")) {
            radioButtonStatusPending.setSelected(true);
            radioButtonStatusArchieved.setSelected(false);
        }
        try {
            for (int i = 0; i < WORKPLAN_DAO.consulListOfStrategys().size(); i++) {
                int expectedObjectiveId = WORKPLAN_DAO.consulListOfStrategys().get(i).getObjectiveID();
                if (objectiveId == expectedObjectiveId) {
                    listStrategysOfObjectiveSelected.add(WORKPLAN_DAO.consulListOfStrategys().get(i));
                }
            }
        } catch (BusinessException ex) { 
            showAlertDataBaseConnection();
        }
        loadDataStrategiesTable();
        tableViewModifyStrategy.setItems(listStrategysOfObjectiveSelected);        
    }
    
    public void loadDataStrategiesTable() {
        tableColumnModifyStrategyNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableColumnModifyStrategyDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnModifyStrategyGoal.setCellValueFactory(new PropertyValueFactory<>("goal"));
        tableColumnModifyStrategyAction.setCellValueFactory(new PropertyValueFactory<>("action"));                                      
        tableColumnModifyStrategyResult.setCellValueFactory(new PropertyValueFactory<>("result"));               
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnModifyStrategyDescription);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnModifyStrategyGoal);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnModifyStrategyAction);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnModifyStrategyResult);          
        
        addDeleteImageToStrategy(); 
    }
    
    public void addDeleteImageToStrategy() {
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
                            Strategy newStrategyRemoved = tableViewModifyStrategy.getSelectionModel().getSelectedItem();
                            arrayListOfStrategiesToEliminate.add(newStrategyRemoved);                              
                            int index = tableViewModifyStrategy.getSelectionModel().getSelectedIndex();
                            listStrategysOfObjectiveSelected.remove(index);                            
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
        tableColumnModifyStrategyDelete.setCellFactory(cellFoctory);
    }
    
    @FXML
    public void exitAddSelectedWorkplanObjective(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir de modificación del Objetivo");        
        alert.setHeaderText("¿Desea cancelar la modificación del objetivo?");
        alert.setContentText("El Objetivo no se modificará.");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            anchorPaneModifyObjective.setVisible(false);
        }
    }
    
    public void fillTableWithAllObjectives(String workplanKeycode) {
        listOfAllObjectives.clear();
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                if (workplanKeycode.equals(WORKPLAN_DAO.consultListOfObjectives().get(i).getWorkplanKeyCode())) {
                    listOfAllObjectives.add(WORKPLAN_DAO.consultListOfObjectives().get(i));
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        tableColumnAllObjectiveTitles.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableColumnAllObjectiveStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        addDeleteImageToObjetiveTable();
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAllObjectiveTitles);
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnAllObjectiveStatus);
        tableViewAllObjectives.setItems(listOfAllObjectives);
    }
    
    public void addDeleteImageToObjetiveTable() {
        Callback<TableColumn<Objective, String>, TableCell<Objective, String>> cellFoctory = (TableColumn<Objective, String> param) -> {
            final TableCell<Objective, String> cell = new TableCell<Objective, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView deleteIcon = new ImageView("/images/modify.png");
                        deleteIcon.setFitHeight(30.0);
                        deleteIcon.setFitWidth(30.0);
                        deleteIcon.setCursor(Cursor.HAND);
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> { 
                            deleteObjectiveOnMouseClicked();
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
        tableColumnAllObjectiveModify.setCellFactory(cellFoctory);
    }
    
    public void deleteObjectiveOnMouseClicked() {
        listStrategysOfObjectiveSelected.clear();
        arrayListOfStrategiesToEliminate.clear();
        arrayListNewStrategies.clear();
        Objective objectiveSelected = tableViewAllObjectives.getSelectionModel().getSelectedItem();
        showGuiUpdateObjectiveOfWorkplanSelected(objectiveSelected);  
    }
    
    public void showGuiWorkplaSuccessfullyUpdated(String administrationWorkplanKeycode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación de Administración del Plan de Trabajo");        
        alert.setHeaderText("Cambios guardados");
        alert.setContentText("Los cambios se realizaron existosamente.");
        alert.showAndWait();
        imagenViewMainLateralMenu.setVisible(true);
        buttonArchievedObjectives.setVisible(true);
        anchorPaneMainWorplan.setVisible(true);
        anchorPaneManageWorkplan.setVisible(false);
        comboBoxWorkplanNames.getItems().clear();
        fillComboBoxWithWorkplanNames();
        tableViewArchievedObjectives.getItems().clear();
        tableViewObjectives.getItems().clear();
    }
    
    @FXML
    public void saveWorkplanAdministration(ActionEvent actionEvent) {          
        String administrationWorkplanKeycode = null;
        String expectedWorkplanKeycode = null;
        Date administrationWorkplanStarDate = null;
        Date administrationWorkplanEndDate = null;
        boolean validDate = false;
        boolean existsWorkplan = false; 
        
        if (textFieldManagedWorkplanKeycode.validate() && (!textFieldManagedWorkplanKeycode.getText().trim().equals(""))) {
            administrationWorkplanKeycode = textFieldManagedWorkplanKeycode.getText();
            try {
                for (int i = 0; i < WORKPLAN_DAO.consultListOfWorkplans().size(); i++) {
                    expectedWorkplanKeycode = WORKPLAN_DAO.consultListOfWorkplans().get(i).getKeyCode();
                    if ((administrationWorkplanKeycode.equals(expectedWorkplanKeycode)) && !(administrationWorkplanKeycode.equals(labelFirtsWorkplanKeycode.getText()))) {
                        existsWorkplan = true;
                        showGuiExistsWorkplan(expectedWorkplanKeycode); 
                    }
                }
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        }
        
        if (datePickerManagedWorkplanStartDate.validate() && datePickerManagedWorkplanFinishDate.validate()) {
            administrationWorkplanStarDate = convertToDate(datePickerManagedWorkplanStartDate.getValue());
            administrationWorkplanEndDate = convertToDate(datePickerManagedWorkplanFinishDate.getValue());
            if (administrationWorkplanStarDate.before(administrationWorkplanEndDate)) {
                validDate = true;
            } else {                
                datePickerManagedWorkplanFinishDate.setValue(null);               
                datePickerManagedWorkplanFinishDate.validate();
            }
        }   
        int workplanObjectiveNumberCounter = 0;
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                String expetedWorkplanKeycode = WORKPLAN_DAO.consultListOfObjectives().get(i).getWorkplanKeyCode();
                if (expetedWorkplanKeycode.equals(labelFirtsWorkplanKeycode.getText())) {
                    workplanObjectiveNumberCounter++;
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        String memberFullName = labelLoggedInUser.getText();
        if (!existsWorkplan && administrationWorkplanKeycode != null && validDate && workplanObjectiveNumberCounter > 0) {
            try {
                Workplan manageWorkplan = new Workplan(administrationWorkplanKeycode, administrationWorkplanStarDate, administrationWorkplanEndDate, memberFullName);
                WORKPLAN_DAO.manageWorkPlan(manageWorkplan, labelFirtsWorkplanKeycode.getText());  
                showGuiWorkplaSuccessfullyUpdated(administrationWorkplanKeycode);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
        } 
        if (workplanObjectiveNumberCounter == 0) {
            showGuiNumberOfObjectives();
        }
    }
    
    public void showGuiAddNewObjective(ActionEvent actionEvent) {        
        String workplanKeycode = textFieldManagedWorkplanKeycode.getText();       
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLAddObjective.fxml"));
            fXMLLoader.load();
            ControllerAddObjective controllerAddObjective = fXMLLoader.getController();
            controllerAddObjective.getWorkplanKeycode(workplanKeycode);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); 
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });        
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void refreshTableOfAllObjectives() {
        tableViewAllObjectives.getItems().clear();
        listOfAllObjectives.clear();
        fillTableWithAllObjectives(labelFirtsWorkplanKeycode.getText());        
    }
   
    @FXML
    public void exitWorkplanManage() {        
        String workplanKeycode = labelFirtsWorkplanKeycode.getText();
        int workplanObjectiveNumberCounter = 0;
        try {
            for (int i = 0; i < WORKPLAN_DAO.consultListOfObjectives().size(); i++) {
                String expetedWorkplanKeycode = WORKPLAN_DAO.consultListOfObjectives().get(i).getWorkplanKeyCode();
                if (expetedWorkplanKeycode.equals(workplanKeycode)) {
                    workplanObjectiveNumberCounter++;
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        if (workplanObjectiveNumberCounter >= 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir de la Administración del Plan de Trabajo");        
            alert.setHeaderText("¿Desea salir?");
            alert.setContentText("Las fechas o la clave del Plan de Trabajo no se actualizarán.");
            Optional<ButtonType> buttonOk = alert.showAndWait();
            if (buttonOk.get() == ButtonType.OK){
                buttonArchievedObjectives.setVisible(true);
                anchorPaneMainWorplan.setVisible(true);
                anchorPaneManageWorkplan.setVisible(false);
                fillComboBoxWithWorkplanNames();
                tableViewArchievedObjectives.getItems().clear();
                tableViewObjectives.getItems().clear();
                comboBoxWorkplanNames.getSelectionModel().clearSelection();
                imagenViewMainLateralMenu.setVisible(true);
            }
        } else {
            showGuiNumberOfObjectives();
        }       
    }
    
    public void showGuiNumberOfObjectives() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Numero de Objetivos del Plan de Trabajo");        
        alert.setHeaderText("El Plan de Trabajo debe tener al menos un Objetivo registrado");
        alert.setContentText("Seleccione el botón 'AGREGAR OBJETIVO' para añadir un nuevo objetivo.");
        alert.showAndWait();
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
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}