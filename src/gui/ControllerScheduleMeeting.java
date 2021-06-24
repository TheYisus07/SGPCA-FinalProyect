package gui;

import businesslogic.AssistantDAO;
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
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerScheduleMeeting implements Initializable {
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final MeetingDAO  MEETING_DAO = new MeetingDAO();
    private final DiscussionDAO DISCUSSION_DAO = new DiscussionDAO();
    private final PreRequisiteDAO  PRE_REQUISITE_DAO = new PreRequisiteDAO();
    private final AssistantDAO  ASSISTANT_DAO = new AssistantDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    public static enum Rol {
        Lider, TomadorDeTiempo, Secretario;
    };
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private JFXTextField textFieldMeetingAffair;

    @FXML
    private JFXTextField textFieldMeetingPlace;

    @FXML
    private JFXTextField textFieldProyectName;

    @FXML
    private JFXDatePicker datePickerMeetingDate;

    @FXML
    private TableView<PreRequisite> tableViewPrerequisites;
    
    
    @FXML
    private TableColumn<PreRequisite, String> tableColumnPreRequisiteDescription;
    
    @FXML
    private TableColumn<PreRequisite, String> tableColumnPreRequisiteManager;
    
    @FXML
    private TableColumn<PreRequisite, String> tableColumnButtonDeletePreRequisite;
     
    @FXML
    private JFXTextArea textAreaDescription;

    @FXML
    private JFXTextField textFieldResponsable;
    
    @FXML
    private TableView<Discussion> tableViewDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnEstimatedTime;

    @FXML
    private TableColumn<Discussion, String> tableColumnNo;

    @FXML
    private TableColumn<Discussion, String> tableColumnPointToDiscuss;

    @FXML
    private TableColumn<Discussion, String> tableColumnDiscussionLeader;
    
    @FXML
    private TableColumn<Discussion, String> tableColumnDeleteDiscussion;
    
    @FXML
    private JFXTextField textFieldEstimatedTime;

    @FXML
    private JFXTextField textFieldPointToDiscuss;

    @FXML
    private JFXTextField textFieldDiscussionLeader;

    @FXML
    private JFXButton buttonAddDiscussion;
    
    @FXML
    private ImageView imageLeader;

    @FXML
    private ImageView imageTimeTaker;

    @FXML
    private ImageView imageSecretary;
    
    @FXML
    private BorderPane borderPaneLeader;

    @FXML
    private TableView<Member> tableViewAssitantLeader;

    @FXML
    private TableColumn tableColumnAssitantLeader;
    
    @FXML
    private BorderPane borderPaneTimeTaker;

    @FXML
    private TableView<Member> tableViewAssitantTimeTaker;

    @FXML
    private TableColumn tableColumnAssistantTimeTaker;
    
    @FXML
    private BorderPane borderPaneSecretary;

    @FXML
    private TableView<Member> tableViewAssitantSecretary;

    @FXML
    private TableColumn tableColumnAssitantSecretary;
    
    
    
    private ObservableList<Meeting> meetingList;
    private ObservableList<PreRequisite> preRequisiteList = FXCollections.observableArrayList();
    private ObservableList<Discussion> discussionList = FXCollections.observableArrayList();
    private ObservableList<Assistant> assistantList  = FXCollections.observableArrayList();
    private ObservableList<Member> listOfassitants = FXCollections.observableArrayList();
    
    private int idMeeting;
    private int noRow;
    
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToMeeting();
        meetingList = FXCollections.observableArrayList();
        showAssitants();
        selectAssistantLeader();
        selectAssistantTimeTaker();
        selectAssistantSecretary();
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void validFieldsToMeeting() {
        textFieldMeetingAffair.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMeetingAffair.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMeetingAffair.validate();
            }
        });
        textFieldMeetingPlace.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldMeetingPlace.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldMeetingPlace.validate();
            }
        });
        textFieldProyectName.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldProyectName.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldProyectName.validate();
            }
        });
        textAreaDescription.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaDescription.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaDescription.validate();
            }
        });
        datePickerMeetingDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        datePickerMeetingDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                datePickerMeetingDate.validate();
            }
        });
        textFieldResponsable.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldResponsable.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldResponsable.validate();
            }
        });
        
        textFieldEstimatedTime.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldEstimatedTime.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldEstimatedTime.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldEstimatedTime.validate();
            }
        });
        textFieldPointToDiscuss.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldPointToDiscuss.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldPointToDiscuss.validate();
            }
        });
        textFieldDiscussionLeader.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldDiscussionLeader.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldDiscussionLeader.validate();
            }
        });
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
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerScheduleMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showTableVIewLeader(MouseEvent event) {
        borderPaneLeader.setVisible(true);
        borderPaneTimeTaker.setVisible(false);
        borderPaneSecretary.setVisible(false);
        fillAssistantTableView();
    }

    @FXML
    public void showTableViewSecretary(MouseEvent event) {
        borderPaneLeader.setVisible(false);
        borderPaneTimeTaker.setVisible(false);
        borderPaneSecretary.setVisible(true);
        fillAssistantTableView();
    }

    @FXML
    public void showTableViewTimeTaker(MouseEvent event) {
        borderPaneLeader.setVisible(false);
        borderPaneTimeTaker.setVisible(true);
        borderPaneSecretary.setVisible(false);
        fillAssistantTableView();
    }
    
    public void showAssitants() {
        for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {
            listOfassitants.add(MEMBER_DAO.consultMemberList().get(i));
        }
        fillAssistantTableView();
    }
    
    public void fillAssistantTableView() {
        this.tableColumnAssitantLeader.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssistantTimeTaker.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssitantSecretary.setCellValueFactory(new PropertyValueFactory("fullName"));
        
        tableViewAssitantLeader.setItems(listOfassitants);
        tableViewAssitantTimeTaker.setItems(listOfassitants);
        tableViewAssitantSecretary.setItems(listOfassitants);
    }
    
    public void addAssistantByRol(String assistantName, String rol) {
        Assistant assistant = new Assistant(rol,assistantName,MEETING_DAO.getLastInsertedMeetingId());
        assistantList.add(assistant);
        tableViewAssitantLeader.refresh();
        tableViewAssitantTimeTaker.refresh();
        tableViewAssitantSecretary.refresh();
    }
    
    public void deleteAssistantByRol(String rol) {
        for(int i = 0; i < assistantList.size(); i++){
            if(assistantList.get(i).getRol().equals(rol)){
                assistantList.remove(i);
            }
        }
    }
    
    public void selectAssistantLeader() {
        tableViewAssitantLeader.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.Lider.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
        });
    }
   
    public void selectAssistantTimeTaker() {
        tableViewAssitantTimeTaker.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.TomadorDeTiempo.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
        });
    }
    
    public void selectAssistantSecretary() {
        tableViewAssitantSecretary.getSelectionModel().selectedItemProperty().addListener ((ObservableValue<? extends Member> observable, Member oldValue, Member newValue) -> {
            String assistantName = observable.getValue().getFullName();
            String rol = Rol.Secretario.toString();
            deleteAssistantByRol(rol);
            addAssistantByRol(assistantName, rol);
        });
    }
    
    
    @FXML
    public void scheduleMeetingOnAction(ActionEvent event) {
        
        
        boolean assistantValid = false;
        boolean discussionValid = false;
        
        
        if (assistantList.size() >= 3) {
            registerAssisttant();
            assistantValid = true;
        } else {
            assistantEmptyAlert();
            assistantValid = false;
        }
        if (discussionList.size() > 0) {
            discussionValid = true;
        }
        
        if (assistantValid && discussionValid) {
            scheduleMeeting(event);
        } else if (discussionValid == false) {
            discussionEmptyAlert();
        } else {
            nullDataAlert();
        }
    }
    public void scheduleMeeting(ActionEvent event) {
        boolean meetingValid = addMeeting();
        if (meetingValid) {
            registerAssisttant();
            registerPrerequisite();
            registerDiscussion();
            closeWindow(event);
        } else {
            nullDataAlert();
        }
    }
    
    public void registerAssisttant() {
        for (int i = 0; i < assistantList.size(); i++) {
            assistantList.get(i).toString();
            assistantList.get(i).setIdMeeting(MEETING_DAO.getLastInsertedMeetingId());
            ASSISTANT_DAO.registerAssistantByRol(assistantList.get(i));
        }
    }
    public void registerPrerequisite() {
        for (int i = 0; i < preRequisiteList.size(); i++) {
            preRequisiteList.get(i).setIdMeeting(MEETING_DAO.getLastInsertedMeetingId());
            PRE_REQUISITE_DAO.registerPreRequisite(preRequisiteList.get(i));
        }
    }
    
    public void registerDiscussion() {
        for (int i = 0; i < discussionList.size(); i++) {
            discussionList.get(i).setIdMeeting(MEETING_DAO.getLastInsertedMeetingId());
            DISCUSSION_DAO.registerDiscussion(discussionList.get(i));
        }
    }
    
    public void assistantEmptyAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("No ha Escogido 3 asistentes");
        alert.showAndWait();
    }
    
    public void discussionEmptyAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Debe existir al menos una discusion en la reunión");
        alert.showAndWait();
    }
    
    
    public LocalDate getActualDate(){
        LocalDate actualDate = LocalDate.now();
        return actualDate;
    }
    
    
    public java.util.Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public boolean addMeeting() {
        boolean meetingValid = false;
        String meetingAffair = null;
        String meetingPlace = null;
        String proyectName = null;
        boolean validDate = false;
        if (textFieldProyectName.validate() && !textFieldProyectName.getText().trim().equals("")) {
            proyectName = this.textFieldProyectName.getText();
        }
        if (textFieldMeetingPlace.validate() && !textFieldMeetingPlace.getText().trim().equals("")) {
            meetingPlace = this.textFieldMeetingPlace.getText();
        }
        if (textFieldMeetingAffair.validate() && !textFieldMeetingAffair.getText().trim().equals("")) {
            meetingAffair = this.textFieldMeetingAffair.getText();
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
            this.meetingList.add(meetingObject);
            MEETING_DAO.scheduleMeeting(meetingObject);
            meetingValid = true;
        } else {
            meetingValid = false;
        }
        return meetingValid;
    }
    
    
    @FXML
    public void addPreRequisiteOnAction(ActionEvent event) {
        
        String description = null;
        String responsable = null;
        
        if (textAreaDescription.validate() && !textAreaDescription.getText().trim().equals("")) {
            description = textAreaDescription.getText();
        }
        
        if (textFieldResponsable.validate() && !textFieldResponsable.getText().trim().equals("")) {
            responsable = textFieldResponsable.getText();
        }
        
        if((description != null) && (responsable != null)){
            PreRequisite preRequisite = new PreRequisite(description,responsable);
            preRequisiteList.add(preRequisite);

            tableViewPrerequisites.setItems(preRequisiteList);
            loadDateOfPreRequisitesTable();
            cleanTextFieldPreRequisite();
        }else{
            
            nullDataAlert();
        }
    }
    
    public void cleanTextFieldPreRequisite() {
        textAreaDescription.setText("");
        textFieldResponsable.setText("");
    }
    
    public void loadDateOfPreRequisitesTable() {
        fillTableViewPrerequisite();
        Callback<TableColumn<PreRequisite, String>, TableCell<PreRequisite, String>> cellFactory = (TableColumn<PreRequisite, String> param) -> {
            TableCell<PreRequisite, String> cell = createTableCellImagePreRequisite();
            return cell;
        };
        tableColumnButtonDeletePreRequisite.setCellFactory(cellFactory);
    }
    
    public void fillTableViewPrerequisite() {
        this.tableColumnPreRequisiteDescription.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnPreRequisiteManager.setCellValueFactory(new PropertyValueFactory("nameParticipant"));
    }
    
    @FXML
    public void addDiscussionOnAction(ActionEvent event) {
        String estimatedTime = null;
        String pointToDiscuss = null;
        String DiscurssionLeader = null;
        
        boolean isValidateEstimatedTime = false;
        int estimatedTimeInt = 0;
        
        if (textFieldPointToDiscuss.validate() && !textFieldPointToDiscuss.getText().trim().equals("")) {
            pointToDiscuss = textFieldPointToDiscuss.getText();
        }
        
        if (textFieldDiscussionLeader.validate() && !textFieldDiscussionLeader.getText().trim().equals("")) {
            DiscurssionLeader = textFieldDiscussionLeader.getText();
        }
        
        if (textFieldEstimatedTime.validate() && !textFieldEstimatedTime.getText().trim().equals("")) {
            try {
                estimatedTimeInt = Integer.parseInt(textFieldEstimatedTime.getText().trim());
            } catch (NumberFormatException ex) {
                Logger.getLogger(ControllerRegisterMember.class.getName()).log(Level.SEVERE, null, ex);
            }
            isValidateEstimatedTime = true;
        } else {
            textFieldEstimatedTime.setText("");
        }
        
        if ((pointToDiscuss != null) && isValidateEstimatedTime && (DiscurssionLeader != null)) {
            noRow = noRow +1;
            Discussion discussion = new Discussion(estimatedTimeInt,noRow,pointToDiscuss,DiscurssionLeader);
            discussionList.add(discussion);
            loadDateOfDiscussionsTable();
            tableViewDiscussion.setItems(discussionList);
            cleanTextFieldDiscussion();
        } else {
            
            nullDataAlert();
        }
    }
    
    public void nullDataAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("No ha llenado todos los campos");
        alert.showAndWait();
    }
    
    public void cleanTextFieldDiscussion() {
        textFieldEstimatedTime.setText("");
        textFieldPointToDiscuss.setText("");
        textFieldDiscussionLeader.setText("");
    }
    
    public void loadDateOfDiscussionsTable() {
        filltableViewDiscussion();
        Callback<TableColumn<Discussion, String>, TableCell<Discussion, String>> cellFactory = (TableColumn<Discussion, String> param) -> {
            TableCell<Discussion, String> cell = createTableCellImageDiscussion();
            return cell;
        };
        tableColumnDeleteDiscussion.setCellFactory(cellFactory);
    }
    
    public void filltableViewDiscussion() {
        this.tableColumnEstimatedTime.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        this.tableColumnNo.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnPointToDiscuss.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnDiscussionLeader.setCellValueFactory(new PropertyValueFactory("leaderName"));
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
            int i = tableViewPrerequisites.getSelectionModel().getSelectedIndex();
            preRequisiteList.remove(i);
        } else {
            noRow = tableViewDiscussion.getSelectionModel().getSelectedIndex();
            discussionList.remove(noRow);
        }
        
    }
    
    @FXML
    public void getOutOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas cancelar el registro?");
        alert.setTitle("Salir");
        alert.setContentText("La reunión no se registrará");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
        }
    }
}