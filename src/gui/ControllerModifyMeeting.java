package gui;

import businesslogic.AssistantDAO;
import businesslogic.BusinessException;
import businesslogic.DiscussionDAO;
import businesslogic.MeetingDAO;
import businesslogic.MemberDAO;
import businesslogic.PreRequisiteDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Assistant;
import domain.Discussion;
import domain.Meeting;
import domain.Member;
import domain.PreRequisite;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerModifyMeeting implements Initializable {
    
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    private final MeetingDAO MEETING_DAO = new MeetingDAO();
    private final AssistantDAO ASSISTANT_DAO = new AssistantDAO();
    private final PreRequisiteDAO PRE_REQUISITE_DAO = new PreRequisiteDAO();
    private final DiscussionDAO DISCUSSION_DAO = new DiscussionDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    public static enum Rol {
        Lider, TomadorDeTiempo, Secretario;
    };
    
    @FXML
    private TableView<Assistant> tableViewAssistants;
    
    @FXML
    private TableColumn<?, ?> tableColumnAssistantRol;

    @FXML
    private TableColumn<?, ?> tableColumnAssistantName;

    @FXML
    private TableView<PreRequisite> tableViewPreRequisite;
    
    @FXML
    private TableView<Discussion> tableViewDiscussion;

    
    @FXML
    private TableColumn<PreRequisite, String> tableColumnPreRequisiteDescription;

    @FXML
    private TableColumn<PreRequisite, String> tableColumnPreRequisiteManager;

    @FXML
    private TableColumn<PreRequisite, String> tableColumnDeletePreRequisite;

    @FXML
    private TableColumn<Discussion, String> tableColumnEstimatedTimeDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnNumberDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnPointToDiscussDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnDiscussionLeaderDiscussion;
    
    @FXML
    private TableColumn<Discussion, String> tableColumnDeleteDiscussion;

    @FXML
    private JFXButton buttonExitModifyMeeting;

    @FXML
    private JFXButton buttonSave;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private JFXDatePicker datePickerMeetingDate;

    @FXML
    private JFXTextField textFieldProyectName;

    @FXML
    private JFXTextField textFieldPlace;

    @FXML
    private JFXTextField textFieldAffair;

    @FXML
    private ImageView imageLeader;

    @FXML
    private ImageView imageTimeTaker;

    @FXML
    private ImageView imageSecretary;

    @FXML
    private JFXTextArea textAreaPreRequisiteDescription;

    @FXML
    private JFXTextField textFieldPreRequisiteManager;

    @FXML
    private JFXButton buttonAddPreRequisite;

    @FXML
    private JFXButton buttonAddDiscussion;

    @FXML
    private JFXTextField textFieldEstimatedTime;


    @FXML
    private JFXTextField textFieldDiscussionLeader;

    @FXML
    private JFXTextArea textAreaPointToDiscuss;

    @FXML
    private TableView<Member> tableViewAssistantLeader;

    @FXML
    private TableColumn<?, ?> tableColumnAssistantLeader;

    @FXML
    private TableView<Member> tableViewAssistantTimeTaker;

    @FXML
    private TableColumn<?, ?> tableColumnAssistantTimeTaker;

    @FXML
    private TableView<Member> tableViewAssistantSecretary;

    @FXML
    private TableColumn<?, ?> tableColumnAssistantSecretary;
    
    @FXML
    private BorderPane borderPaneLeader;
    
    @FXML
    private BorderPane borderPaneTimeTaker;
    
    @FXML
    private BorderPane borderPaneSecretary;
    
    @FXML
    private BorderPane borderPaneAssistant;
    
    private ObservableList<PreRequisite> preRequisiteList = FXCollections.observableArrayList();
    private ObservableList<Discussion> discussionList = FXCollections.observableArrayList();
    private ObservableList<Assistant> assistantList  = FXCollections.observableArrayList();
    private ObservableList<Member> listOfassitants = FXCollections.observableArrayList();
    
    
    private int noRow;
    private int idMeeting;
    
    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToMeeting();
        loadAssitants();
        selectAssistantLeader();
        selectAssistantTimeTaker();
        selectAssistantSecretary();
    }   
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate) {
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void validFieldsToMeeting() {
        textFieldAffair.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textFieldAffair.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAffair.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAffair.validate();
            }
        });
        textFieldPlace.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldPlace.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldPlace.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldPlace.validate();
            }
        });
        textFieldProyectName.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldProyectName.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldProyectName.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldProyectName.validate();
            }
        });
        textAreaPreRequisiteDescription.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaPreRequisiteDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaPreRequisiteDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaPreRequisiteDescription.validate();
            }
        });
        
        datePickerMeetingDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerMeetingDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerMeetingDate.validate();
            }
        });
        textFieldPreRequisiteManager.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldPreRequisiteManager.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldPreRequisiteManager.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldPreRequisiteManager.validate();
            }
        });
        textFieldEstimatedTime.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldEstimatedTime.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldEstimatedTime.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldEstimatedTime.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldEstimatedTime.validate();
            }
        });
        textAreaPointToDiscuss.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_DESCRIPTION());
        textAreaPointToDiscuss.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaPointToDiscuss.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaPointToDiscuss.validate();
            }
        });
        textFieldDiscussionLeader.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldDiscussionLeader.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldDiscussionLeader.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldDiscussionLeader.validate();
            }
        });
    }
    
    public void getMeetingSelected(int idMeeting) {
        try {
            Meeting meetingConsulted;
            meetingConsulted = MEETING_DAO.searchMeeting(idMeeting);
            String meetingAffair = meetingConsulted.getAffair();
            String meetingPlace = meetingConsulted.getPlace();
            String proyectName = meetingConsulted.getTitleOfProyect();
            LocalDate meetingDate = convertToLocalDate(meetingConsulted.getDate());
            datePickerMeetingDate.setValue(meetingDate);
            textFieldProyectName.setText(proyectName);
            textFieldPlace.setText(meetingPlace);
            textFieldAffair.setText(meetingAffair);
            showAssistantTable(idMeeting);
            showPreRequisiteTable(idMeeting);
            showDiscussionTable(idMeeting);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    
    
    public void showAssistantTable(int idOfThisMeeting) {
        idMeeting = idOfThisMeeting;
        try {
            for (int i = 0; i < ASSISTANT_DAO.getAssitants(idOfThisMeeting).size(); i++){
                assistantList.add(ASSISTANT_DAO.getAssitants(idOfThisMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        filltableViewAssistants();
    }
    
    public void filltableViewAssistants() {
        this.tableColumnAssistantName.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssistantRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tableViewAssistants.setItems(assistantList);
    }
    
    public void showPreRequisiteTable(int idOfThisMeeting) {
       
        try {
            for (int i = 0; i < PRE_REQUISITE_DAO.consultPreRequisites(idOfThisMeeting).size(); i++){
                preRequisiteList.add(PRE_REQUISITE_DAO.consultPreRequisites(idOfThisMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        filltableViewPreRequisite();
    }
    
    public void filltableViewPreRequisite() {
        this.tableColumnPreRequisiteDescription.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnPreRequisiteManager.setCellValueFactory(new PropertyValueFactory("nameParticipant"));
        loadDateOfPreRequisitesTable();
    }
    
    public void showDiscussionTable(int idOfThisMeeting) {
        
        try {
            for (int i = 0; i < DISCUSSION_DAO.consultDiscussions(idOfThisMeeting).size(); i++){
                discussionList.add(DISCUSSION_DAO.consultDiscussions(idOfThisMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        filltableViewDiscussion();
    }
    
    public void filltableViewDiscussion() {
        this.tableColumnEstimatedTimeDiscussion.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        this.tableColumnNumberDiscussion.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnPointToDiscussDiscussion.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnDiscussionLeaderDiscussion.setCellValueFactory(new PropertyValueFactory("leaderName"));
        loadDateOfDiscussionsTable();
    }
    
    @FXML
    void showTableVIewLeader(MouseEvent event) {
        borderPaneLeader.setVisible(true);
        borderPaneTimeTaker.setVisible(false);
        borderPaneSecretary.setVisible(false);
        
        fillAssistantTableView();
    }

    @FXML
    void showTableViewSecretary(MouseEvent event) {
        borderPaneLeader.setVisible(false);
        borderPaneTimeTaker.setVisible(false);
        borderPaneSecretary.setVisible(true);
        fillAssistantTableView();
    }

    @FXML
    void showTableViewTimeTaker(MouseEvent event) {
        borderPaneLeader.setVisible(false);
        borderPaneTimeTaker.setVisible(true);
        borderPaneSecretary.setVisible(false);
        fillAssistantTableView();
    }
        
    public void loadAssitants() {
        try {
            for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {
                listOfassitants.add(MEMBER_DAO.consultMemberList().get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        fillAssistantTableView();
    }
    
    public void fillAssistantTableView() {
        this.tableColumnAssistantLeader.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssistantTimeTaker.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssistantSecretary.setCellValueFactory(new PropertyValueFactory("fullName"));
        
        tableViewAssistantLeader.setItems(listOfassitants);
        tableViewAssistantTimeTaker.setItems(listOfassitants);
        tableViewAssistantSecretary.setItems(listOfassitants);
    }
    
    public void addAssistantByRol(String assistantName, String rol) {
        try {
            Assistant assistant = new Assistant(rol,assistantName,MEETING_DAO.getLastInsertedMeetingId());
            assistantList.add(assistant);
            
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        tableViewAssistantLeader.refresh();
        tableViewAssistantTimeTaker.refresh();
        tableViewAssistantSecretary.refresh();
    }
    
    public void deleteAssistantByRol(String rol) {
        for(int i = 0; i < assistantList.size(); i++){
            if(assistantList.get(i).getRol().equals(rol)){
                assistantList.remove(i);
            }
        }
    }
    
    public void selectAssistantLeader() {
        tableViewAssistantLeader.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.Lider.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
            filltableViewAssistants();
        });
    }
   
    public void selectAssistantTimeTaker() {
        tableViewAssistantTimeTaker.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.TomadorDeTiempo.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
            filltableViewAssistants();
        });
    }
    
    public void selectAssistantSecretary() {
        tableViewAssistantSecretary.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.Secretario.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
            filltableViewAssistants();
        });
    }
    
    @FXML
    public void saveMeetingOnAction(ActionEvent event) {
        boolean discussionValid = false;
        
        if (discussionList.size() > 0) {
            discussionValid = true;
        }
        
        if (discussionValid) {
            scheduleMeeting(event);
            
        } else {
            discussionEmptyAlert();
        }
    }
    
    public void scheduleMeeting(ActionEvent event) {
        boolean meetingValid = changeMeeting();
        if (meetingValid) {
            registerAssisttant();
            registerPrerequisite();
            registerDiscussion();
            closeWindow(event);
        }
    }
    
    
    public LocalDate getActualDate(){
        LocalDate actualDate = LocalDate.now();
        return actualDate;
    }
    
    
    public boolean changeMeeting() {
        boolean meetingValid = false;
        String meetingAffair = null;
        String meetingPlace = null;
        String proyectName = null;
        boolean validDate = false;
        if (textFieldProyectName.validate() && !textFieldProyectName.getText().trim().equals("")) {
            proyectName = this.textFieldProyectName.getText();
        }
        if (textFieldPlace.validate() && !textFieldPlace.getText().trim().equals("")) {
            meetingPlace = this.textFieldPlace.getText();
        }
        if (textFieldAffair.validate() && !textFieldAffair.getText().trim().equals("")) {
            meetingAffair = this.textFieldAffair.getText();
        }
        
        Date meetingDate = null;
        LocalDate date = getActualDate();
        Date actualDate;
        if (datePickerMeetingDate.validate() && datePickerMeetingDate.validate()) {
            actualDate = convertToDate(date);
            meetingDate = convertToDate(datePickerMeetingDate.getValue());
            if (actualDate.before(meetingDate)) {
                validDate = true;
            } else {
                datePickerMeetingDate.setValue(null);
                datePickerMeetingDate.validate();
            }
        } 
        if ((meetingAffair != null) && (meetingPlace != null) && (proyectName != null) && validDate) {
            Meeting meetingObject = new Meeting(meetingDate, proyectName, meetingPlace, meetingAffair, labelLoggedInUser.getText());

            try {
                MEETING_DAO.updateMeeting(meetingObject, idMeeting);
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }
            meetingValid = true;
        } else {
            meetingValid = false;
        }
        return meetingValid;
    }
    
    public void registerAssisttant() {
        try {
            ASSISTANT_DAO.deleteAssistants(idMeeting);
            for (int i = 0; i < assistantList.size(); i++) {
                assistantList.get(i).toString();
                assistantList.get(i).setIdMeeting(idMeeting);
                ASSISTANT_DAO.registerAssistantByRol(assistantList.get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void registerPrerequisite() {
        try {
            PRE_REQUISITE_DAO.deletePrerequisites(idMeeting);
            for (int i = 0; i < preRequisiteList.size(); i++) {
                preRequisiteList.get(i).setIdMeeting(idMeeting);
                PRE_REQUISITE_DAO.registerPreRequisite(preRequisiteList.get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void registerDiscussion() {
        try {
            DISCUSSION_DAO.deleteDiscussions(idMeeting);
            for (int i = 0; i < discussionList.size(); i++) {
                discussionList.get(i).setIdMeeting(idMeeting);
                DISCUSSION_DAO.registerDiscussion(discussionList.get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void discussionEmptyAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Debe existir al menos una discusion en la reunión");
        alert.showAndWait();
    }

    @FXML
    public void addDiscussionOnAction(ActionEvent event) {
        String estimatedTime = null;
        String pointToDiscuss = null;
        String DiscurssionLeader = null;
        int estimatedTimePositive = 0;        
        boolean isValidateEstimatedTime = false;
        int estimatedTimeInt = 0;
        
        if (textAreaPointToDiscuss.validate() && !textAreaPointToDiscuss.getText().trim().equals("")) {
            pointToDiscuss = textAreaPointToDiscuss.getText();
        }
        
        if (textFieldDiscussionLeader.validate() && !textFieldDiscussionLeader.getText().trim().equals("")) {
            DiscurssionLeader = textFieldDiscussionLeader.getText();
        }
        
       
        if (textFieldEstimatedTime.validate() && !textFieldEstimatedTime.getText().trim().equals("") && estimatedTimePositive > 0) {
            try {
                estimatedTimeInt = Integer.parseInt(textFieldEstimatedTime.getText().trim());
                isValidateEstimatedTime = true;
            } catch (NumberFormatException ex) {
                Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            textFieldEstimatedTime.setText("");
            textFieldEstimatedTime.validate();
            isValidateEstimatedTime = false;
        }
        
        if (estimatedTimeInt > 0) {
            isValidateEstimatedTime = true;
        } else {
            textFieldEstimatedTime.setText("");
            textFieldEstimatedTime.validate();
            isValidateEstimatedTime = false;
        }
        
        if ((pointToDiscuss != null) && isValidateEstimatedTime && (DiscurssionLeader != null)) {
            noRow = noRow +1;
            Discussion discussion = new Discussion(estimatedTimeInt,noRow,pointToDiscuss,DiscurssionLeader);
            discussionList.add(discussion);
            loadDateOfDiscussionsTable();
            tableViewDiscussion.setItems(discussionList);
            cleanTextFieldDiscussion();
        }

    }
    
    public void cleanTextFieldDiscussion() {
        textFieldEstimatedTime.setText("");
        textAreaPointToDiscuss.setText("");
        textFieldDiscussionLeader.setText("");
    }
    
    public void loadDateOfDiscussionsTable() {
        Callback<TableColumn<Discussion, String>, TableCell<Discussion, String>> cellFactory = (TableColumn<Discussion, String> param) -> {
            TableCell<Discussion, String> cell = createTableCellImageDiscussion();
            return cell;
        };
        tableColumnDeleteDiscussion.setCellFactory(cellFactory);
        tableViewDiscussion.setItems(discussionList);
    }
    
    public TableCell createTableCellImageDiscussion() {
        final TableCell<Discussion, String> cell = new TableCell<Discussion, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView deleteIcon = createImageView(2);
                    HBox deletebutton = new HBox(deleteIcon);
                    deletebutton.setStyle("-fx-alignment:center");
                    setGraphic(deletebutton);
                    setText(null);
                }
            }
        };
        return cell;
    }

    @FXML
    public void addPreRequisiteOnAction(ActionEvent event) {

        String description = null;
        String responsable = null;
        if (textAreaPreRequisiteDescription.validate() && !textAreaPreRequisiteDescription.getText().trim().equals("")) {
            description = textAreaPreRequisiteDescription.getText();
        }
        
        if (textFieldPreRequisiteManager.validate() && !textFieldPreRequisiteManager.getText().trim().equals("")) {
            responsable = textFieldPreRequisiteManager.getText();
        }
        
        if((description != null) && (responsable != null)){
            PreRequisite preRequisite = new PreRequisite(description,responsable);
            preRequisiteList.add(preRequisite);
            loadDateOfPreRequisitesTable();
            cleanTextFieldPreRequisite();
        }
    }
    
    public void cleanTextFieldPreRequisite() {
        textAreaPreRequisiteDescription.setText("");
        textFieldPreRequisiteManager.setText("");
    }
    
    public void loadDateOfPreRequisitesTable() {        
        Callback<TableColumn<PreRequisite, String>, TableCell<PreRequisite, String>> cellFactory = (TableColumn<PreRequisite, String> param) -> {
            TableCell<PreRequisite, String> cell = createTableCellImagePreRequisite();
            return cell;
        };
        tableColumnDeletePreRequisite.setCellFactory(cellFactory);
        tableViewPreRequisite.setItems(preRequisiteList);
    }
    
    public TableCell createTableCellImagePreRequisite() {
        final TableCell<PreRequisite, String> cell = new TableCell<PreRequisite, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView deleteIcon = createImageView(1);
                    HBox deletebutton = new HBox(deleteIcon);
                    deletebutton.setStyle("-fx-alignment:center");
                    setGraphic(deletebutton);
                    setText(null);
                }
            }
        };
        return cell;
    }
    
    
    public ImageView createImageView(int numTableConfirmation) {
        ImageView deleteIcon = new ImageView("/images/delete.png");
        deleteIcon.setFitHeight(20.0);
        deleteIcon.setFitWidth(20.0);
        deleteIcon.setCursor(Cursor.HAND);
        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
            deleteTableCell(numTableConfirmation);
        });
        return deleteIcon; 
    }
    
    public void deleteTableCell(int numTableConfirmation) {
        if (numTableConfirmation == 1) {
            int i = tableViewPreRequisite.getSelectionModel().getSelectedIndex();
            preRequisiteList.remove(i);
        } else {
            noRow = tableViewDiscussion.getSelectionModel().getSelectedIndex();
            discussionList.remove(noRow);
        }
        
    }

    @FXML
    public void getOutOfModifyMeetingOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar la modificación?");
        alert.setTitle("Salir");
        alert.setContentText("La reunión no se actualizará");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
        }

    }
    
    public void closeWindow(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMeetingHistory.fxml"));
            fXMLLoader.load();
            ControllerConsultMeetingHistory controllerConsultMeetingHistory = fXMLLoader.getController();
            controllerConsultMeetingHistory.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.close();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerModifyMeeting.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControllerModifyMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
