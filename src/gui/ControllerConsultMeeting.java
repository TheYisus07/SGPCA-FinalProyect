package gui;

import businesslogic.AssistantDAO;
import businesslogic.DiscussionDAO;
import businesslogic.InvitedDAO;
import businesslogic.MeetingDAO;
import businesslogic.MemberDAO;
import businesslogic.MinuteDAO;
import businesslogic.PreRequisiteDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import domain.Assistant;
import domain.Discussion;
import domain.Invited;
import domain.Meeting;
import domain.Member;
import domain.PreRequisite;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultMeeting implements Initializable {
    private final InvitedDAO INVITED_DAO =  new InvitedDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final MinuteDAO MINUTE_DAO = new MinuteDAO();
    private final MeetingDAO MEETING_DAO = new MeetingDAO();
    private final AssistantDAO ASSISTANT_DAO = new AssistantDAO();
    private final PreRequisiteDAO PRE_REQUISITE_DAO = new PreRequisiteDAO();
    private final DiscussionDAO DISCUSSION_DAO = new DiscussionDAO();
    
    @FXML
    private TableView<Invited> tableViewInviteds;

    @FXML
    private TableColumn<Invited, String> tableColumnInvitedConfirmed;
    
    @FXML
    private TableView<Assistant> tableViewAssistants;

    @FXML
    private TableColumn tableColumnAssistantName;

    @FXML
    private TableColumn tableColumnAssitantRol;

    @FXML
    private TableView<PreRequisite> tableViewPreRequisites;

    @FXML
    private TableColumn tableColumnPrerequisiteDescription;

    @FXML
    private TableColumn tableColumnPreRequisiteManager;

    @FXML
    private TableView tableViewDiscussions;

    @FXML
    private TableColumn<?, ?> tableColumnEstimatedTimeDicussion;

    @FXML
    private TableColumn<?, ?> tableColumnNumberColumnDiscussion;

    @FXML
    private TableColumn<?, ?> tableColumnPointToDiscussDiscussion;

    @FXML
    private TableColumn<?, ?> tableColumnDiscussionLeader;

    @FXML
    private JFXCheckBox checkBoxComfirmAssistance;

    @FXML
    private Label labelMeetingDate;

    @FXML
    private Label labelProyectName;

    @FXML
    private Label labelMeetingPlace;

    @FXML
    private Label labelMeetingAffair;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private Label labelAssistanceConfirmed;
    
    @FXML
    private JFXButton startMeetingButton;
    
    @FXML
    private JFXButton buttonShowMinute;   
    
    private int id;
    
    private ObservableList<Invited> invitedList = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        confirmAssistanceOnMouseClicked();
        verifyAssitence();
        confirmPermissionToStarMeeting();
        //confirmAsistanceToStartMeeting();
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
            Logger.getLogger(ControllerConsultMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void openModifyMeetingOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyMeeting.fxml"));
            fXMLLoader.load();
            ControllerModifyMeeting controllerModifyMeeting = fXMLLoader.getController();
            
            controllerModifyMeeting.getMeetingSelected(id);
            controllerModifyMeeting.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void getMeetingSelected(int idMeeting) {
        id = idMeeting;
        Meeting meetingConsulted;
        meetingConsulted = MEETING_DAO.searchMeeting(idMeeting);
        String titleOfProyect = meetingConsulted.getTitleOfProyect();
        String place = meetingConsulted.getPlace();
        String affair = meetingConsulted.getAffair();
        Date meetingDateAux = meetingConsulted.getDate();
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(meetingDateAux));
        labelMeetingDate.setText(registrationDate);
        labelProyectName.setText(titleOfProyect );
        labelMeetingPlace.setText(place);
        labelMeetingAffair.setText(affair);
        showAssistantTable(idMeeting);
        showPreRequisiteTable(idMeeting);
        showDiscussionTable(idMeeting);
        showInvitedConfirmedTable(idMeeting);
        
    }
    
    public void showInvitedConfirmedTable(int idOfThisMeeting) {
        invitedList.clear();
        for (int i = 0; i < INVITED_DAO.getAssitantsName(idOfThisMeeting).size(); i++) {
            invitedList.add(INVITED_DAO.getAssitantsName(idOfThisMeeting).get(i));
        }
        loadTableViewInvited();
        confirmPermissionToStarMeeting();
    }
    
    public void loadTableViewInvited() {
        this.tableColumnInvitedConfirmed.setCellValueFactory(new PropertyValueFactory("fullName"));
        tableViewInviteds.setItems(invitedList);
    }
    
    public boolean searchInvited() {
        boolean compare = false;
        for (int i = 0; i < invitedList.size(); i++) {
            compare = invitedList.get(i).getFullName().equals(labelLoggedInUser.getText());
        }
        return compare;
    }
    
    public void verifyAssitence() {
        boolean compare = searchInvited();
        if (compare) {
            checkBoxComfirmAssistance.setSelected(true);
        }
    }
    
    public void showAssistantTable(int idOfThisMeeting) {
        ObservableList<Assistant> assistantList = FXCollections.observableArrayList();
        for (int i = 0; i < ASSISTANT_DAO.getAssitants(idOfThisMeeting).size(); i++) {
            assistantList.add(ASSISTANT_DAO.getAssitants(idOfThisMeeting).get(i));
            
        }
        loadTableViewAssistantData(assistantList);
    }
    
    public void loadTableViewAssistantData(ObservableList<Assistant> assistantList) {
        this.tableColumnAssistantName.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnAssitantRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tableViewAssistants.setItems(assistantList);
    }
    
    public void showPreRequisiteTable(int idOfThisMeeting) {
        ObservableList<PreRequisite> preRequisiteList = FXCollections.observableArrayList();
        for (int i = 0; i < PRE_REQUISITE_DAO.consultPreRequisites(idOfThisMeeting).size(); i++) {
            preRequisiteList.add(PRE_REQUISITE_DAO.consultPreRequisites(idOfThisMeeting).get(i));
        }
        loadTableViewPreRequisiteData(preRequisiteList);
    }
    
    public void loadTableViewPreRequisiteData(ObservableList<PreRequisite> preRequisiteList) {
        this.tableColumnPrerequisiteDescription.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnPreRequisiteManager.setCellValueFactory(new PropertyValueFactory("nameParticipant"));
        tableViewPreRequisites.setItems(preRequisiteList);
    }
    
    public void showDiscussionTable(int idOfThisMeeting) {
        ObservableList<Discussion> discussionList = FXCollections.observableArrayList();
        for (int i = 0; i < DISCUSSION_DAO.consultDiscussions(idOfThisMeeting).size(); i++) {
            discussionList.add(DISCUSSION_DAO.consultDiscussions(idOfThisMeeting).get(i));
        }
        loadTableViewDiscussionData(discussionList);
    }
    
    public void loadTableViewDiscussionData(ObservableList<Discussion> discussionList) {
        this.tableColumnEstimatedTimeDicussion.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        this.tableColumnNumberColumnDiscussion.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnPointToDiscussDiscussion.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnDiscussionLeader.setCellValueFactory(new PropertyValueFactory("leaderName"));
        tableViewDiscussions.setItems(discussionList);
    }
    
    public String getNameInvited() {
        Member member;
        member = MEMBER_DAO.consultMember(labelLoggedInUser.getText());
        String nameInvited = member.getFullName();
        return nameInvited;
    }
    
    public void confirmAssistanceOnMouseClicked() {
        checkBoxComfirmAssistance.setOnMouseClicked((MouseEvent event) -> {
            compareNameInvited();
            showInvitedConfirmedTable(id);
        });
    }
    
    public void compareNameInvited() {
        String addName = getNameInvited();
        boolean compare = searchInvited();
        if (!compare && checkBoxComfirmAssistance.isSelected()) {
            Invited invited = new Invited(addName, id);
            INVITED_DAO.confirmAssistance(invited);
        } else {
            INVITED_DAO.deleteAssistance(addName, id);
        }
    }
    
    
    @FXML
    public void startMeetingOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLStartMeeting.fxml"));
            fXMLLoader.load();
            ControllerStartMeeting controllerStartMeeting = fXMLLoader.getController();
            
            controllerStartMeeting.getMeetingSelected(id);
            controllerStartMeeting.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    public void showConsultMinuteOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMinute.fxml"));
            fXMLLoader.load();
            ControllerConsultMinute controllerConsultMinute = fXMLLoader.getController();
            
            controllerConsultMinute.getMeetingSelected(id);
            controllerConsultMinute.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
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
            Logger.getLogger(ControllerConsultMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    public void getOutOfConsultMeetingOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas dalir de la reunión?");
        alert.setTitle("Salir");
        Optional<ButtonType> okCancel = alert.showAndWait();
        if (okCancel.get() == ButtonType.OK){
            closeWindow(event);
        }

    }
    
    
    public void confirmPermissionToStarMeeting() {
        
        boolean compare = searchInvited();
        if (compare && (MINUTE_DAO.consultMinute(id).getIdMeeting() == id)) {
            startMeetingButton.setDisable(true);
            buttonShowMinute.setDisable(false);
            checkBoxComfirmAssistance.setDisable(true);
        } else if (compare && (MINUTE_DAO.consultMinute(id).getIdMeeting() != id)) {
            startMeetingButton.setDisable(false);
            buttonShowMinute.setDisable(true);
            checkBoxComfirmAssistance.setDisable(false);
        } else if (!compare && (MINUTE_DAO.consultMinute(id).getIdMeeting() == id)) {
            startMeetingButton.setDisable(true);
            buttonShowMinute.setDisable(false);
            checkBoxComfirmAssistance.setDisable(true);
        } else {
            startMeetingButton.setDisable(true);
            buttonShowMinute.setDisable(true);
            checkBoxComfirmAssistance.setDisable(false);
        }
    }
}
