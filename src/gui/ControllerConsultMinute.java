package gui;

import businesslogic.AgreementDAO;
import businesslogic.ApprovedDAO;
import businesslogic.AssistantDAO;
import businesslogic.BusinessException;
import businesslogic.DiscussionDAO;
import businesslogic.MeetingDAO;
import businesslogic.MemberDAO;
import businesslogic.MinuteDAO;
import businesslogic.PreRequisiteDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Agreement;
import domain.Approved;
import domain.Assistant;
import domain.Discussion;
import domain.Meeting;
import domain.Member;
import domain.Minute;
import domain.PreRequisite;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultMinute implements Initializable {
    private final MinuteDAO MINUTE_DAO = new MinuteDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final AssistantDAO ASSISTANT_DAO = new AssistantDAO();
    private final PreRequisiteDAO PRE_REQUISITE_DAO = new PreRequisiteDAO();
    private final DiscussionDAO DISCUSSION_DAO = new DiscussionDAO();
    private final AgreementDAO AGREEMENT_DAO = new AgreementDAO();
    private final MeetingDAO MEETING_DAO = new MeetingDAO();
    private final ApprovedDAO APPROVED_DAO =  new ApprovedDAO();
    
    @FXML
    private TableView<Assistant> tableViewAssistant;
    
    @FXML
    private TableColumn<Assistant, String> tableColumnMember;

    @FXML
    private TableColumn<Assistant, String> tableColumnRol;

    @FXML
    private TableView<PreRequisite> tableViewPreRequisite;

    @FXML
    private TableColumn<PreRequisite, String> tableColumnDescription;

    @FXML
    private TableColumn<PreRequisite, String> tableColumnManagerPrerequisite;

    @FXML
    private TableView<Discussion> tableViewDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnStartHour;

    @FXML
    private TableColumn<Discussion, String> tableColumnEndHour;

    @FXML
    private TableColumn<Discussion, String> tableColumnEstimatedTime;

    @FXML
    private TableColumn<Discussion, String> tableColumnRealTime;

    @FXML
    private TableColumn<Discussion, String> tableColumnNumberDiscussion;

    @FXML
    private TableColumn<Discussion, String> tableColumnPointToDiscuss;

    @FXML
    private TableColumn<Discussion, String> tableColumnLeader;

    @FXML
    private TableView<Agreement> tableViewAgreement;

    @FXML
    private TableColumn<?, ?> tableColumnNumberAgreement;

    @FXML
    private TableColumn<?, ?> tableColumnAgreement;

    @FXML
    private TableColumn<?, ?> tableColumnManagerAgreement;

    @FXML
    private TableColumn<?, ?> tableColumnDate;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextArea textAreaNote;

    @FXML
    private JFXTextArea textAreaPending;

    @FXML
    private JFXTextField textFieldMeetingDate;

    @FXML
    private JFXTextField textFieldProyectName;

    @FXML
    private JFXTextField textFieldPlace;

    @FXML
    private JFXTextField textFieldAffair;

    @FXML
    private JFXButton buttonDownload;

    @FXML
    private JFXButton buttonExit;

    @FXML
    private JFXCheckBox checkBoxApprove;
    
    @FXML
    private JFXButton buttonModifyMinute;

    @FXML
    private TableView<Approved> tableViewApproved;

    @FXML
    private TableColumn<?, ?> tableColumnMemberApproved;
    
    
    private ObservableList<Approved> approvedList = FXCollections.observableArrayList();
    
    private int id;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}  
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
        confirmApprovedOnMouseClicked();
        verifyApprovedMinute();
    }
    
    
    public void getMeetingSelected(int idMeeting) {
        showApprovedConfirmedTable(idMeeting);
        loadMeetingData(idMeeting);
        loadAssistantTableView(idMeeting);
        loadPreRequisiteTableView(idMeeting);
        loadDicussionTableView(idMeeting);
        loadAgreementTableView(idMeeting);
        loadMinuteData(idMeeting);
    }
    
    public void showApprovedConfirmedTable(int idOfThisMeeting) {
        id = idOfThisMeeting;
        approvedList.clear();
        try {
            for (int i = 0; i < APPROVED_DAO.getMembersApprove(idOfThisMeeting).size(); i++){
                approvedList.add(APPROVED_DAO.getMembersApprove(idOfThisMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewApprovedData();
    }
    
    public void loadTableViewApprovedData() {
        this.tableColumnMemberApproved.setCellValueFactory(new PropertyValueFactory("memberApprove"));
        tableViewApproved.setItems(approvedList);
    }
    
    public boolean searchApproved() {
        boolean compare = false;
        for (int i = 0; i < approvedList.size(); i++){
            compare = approvedList.get(i).getMemberApprove().equals(labelLoggedInUser.getText());
        }
        return compare;
    }
    
    public void verifyApprovedMinute() {
        boolean compare = searchApproved();
        if(compare){
            checkBoxApprove.setSelected(true);
        }
    }
    
    public String getNameApproved() {
        String nameApproved = null;
        try {
            Member member;
            member = MEMBER_DAO.consultMember(labelLoggedInUser.getText());
            nameApproved = member.getFullName();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        return nameApproved;
    }
    
    public void confirmApprovedOnMouseClicked() {
        checkBoxApprove.setOnMouseClicked((MouseEvent event) -> {
            compareNameApproved();
            showApprovedConfirmedTable(id);
        });
    }
    
    public void compareNameApproved() {
        String addName = getNameApproved();
        boolean compare = searchApproved();
        try {
            if(!compare&&checkBoxApprove.isSelected()) {
                Approved approved = new Approved(id, addName);
                APPROVED_DAO.approvedMeeting(approved);
            }else{
                APPROVED_DAO.deleteApproved(addName, id);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
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
            stage.getIcons().add(new javafx.scene.image.Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMinute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void downloadMinuteOnAction(ActionEvent event) {
        String meetingDate = textFieldMeetingDate.getText();
        String proyectName = textFieldProyectName.getText();
        String place = textFieldPlace.getText();
        String affair = textFieldAffair.getText();
        String note = textAreaNote.getText();
        String pending = textAreaPending.getText();
        String meetingText = ("Fecha de la reunión: " + meetingDate + "\nNombre el proyecto: " + proyectName + "\nLugar de la reunión: " + place + "\nAsunto: " + affair);
        Document document = new Document();
        
        String user = System.getProperty("user.home");
        try {
            FileOutputStream minutePDF = new FileOutputStream(user + "/Downloads/" + proyectName + ".pdf");
            PdfWriter.getInstance(document,minutePDF).setInitialLeading(20);
            document.open();
            Image logoUv = Image.getInstance("src\\images\\Uv_Logo.png");
            logoUv.scaleToFit(80, 80);
            logoUv.setAbsolutePosition(450,750);
            document.add(logoUv);
            
            Paragraph meetingData = new Paragraph();
            meetingData.setAlignment(Paragraph.ALIGN_MIDDLE);     
            meetingData.add("\n" + meetingText + "\n\n\n\n\n"); 
            document.add(meetingData);
            
            Paragraph space = new Paragraph();    
            space.add("\n\n");
            
            PdfPTable assistantTable = new PdfPTable(2);
            assistantTable.addCell("Integrante del CA");
            assistantTable.addCell("Rol");
            for (int i = 0; i < ASSISTANT_DAO.getAssitants(id).size(); i++) {
                assistantTable.addCell(ASSISTANT_DAO.getAssitants(id).get(i).getFullName());
                assistantTable.addCell(ASSISTANT_DAO.getAssitants(id).get(i).getRol());
            }
            document.add(assistantTable);
            
            document.add(space);
            
            PdfPTable prerequisiteTable = new PdfPTable(2);
            prerequisiteTable.addCell("Descripción");
            prerequisiteTable.addCell("¿Quién?");
            for (int i = 0; i < PRE_REQUISITE_DAO.consultPreRequisites(id).size(); i++) {
                prerequisiteTable.addCell(PRE_REQUISITE_DAO.consultPreRequisites(id).get(i).getDescription());
                prerequisiteTable.addCell(PRE_REQUISITE_DAO.consultPreRequisites(id).get(i).getNameParticipant());
            }
            document.add(prerequisiteTable);
            
            document.add(space);
            
            PdfPTable discussionTable = new PdfPTable(6);
            discussionTable.addCell("IHora inicio");
            discussionTable.addCell("Hora fin");
            discussionTable.addCell("Tiempo estimado");
            discussionTable.addCell("No");
            discussionTable.addCell("Punto a tratar");
            discussionTable.addCell("Lider");
            for (int i = 0; i < DISCUSSION_DAO.consultDiscussionsUpdated(id).size(); i++) {
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getStartHour());
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getEndHour());
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getEstimatedTime() + "");
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getNumber() + "");
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getDescription());
                discussionTable.addCell(DISCUSSION_DAO.consultDiscussionsUpdated(id).get(i).getLeaderName());
            }
            document.add(discussionTable);
            
            document.add(space);
            
            PdfPTable agreementTable = new PdfPTable(4);
            agreementTable.addCell("No");
            agreementTable.addCell("Acuerdo");
            agreementTable.addCell("¿Quién?");
            agreementTable.addCell("¿Cuando?");
            for (int i = 0; i < AGREEMENT_DAO.consultAgreement(id).size(); i++) {
                agreementTable.addCell(AGREEMENT_DAO.consultAgreement(id).get(i).getNumber() + "");
                agreementTable.addCell(AGREEMENT_DAO.consultAgreement(id).get(i).getDescription());
                agreementTable.addCell(AGREEMENT_DAO.consultAgreement(id).get(i).getManager());
                agreementTable.addCell(AGREEMENT_DAO.consultAgreement(id).get(i).getDate());
            }
            document.add(agreementTable);
            
            Paragraph meetingNote = new Paragraph();
            meetingNote.setAlignment(Paragraph.ALIGN_MIDDLE);     
            meetingNote.add("\n\n" + note); 
            document.add(meetingNote);
            
            Paragraph meetingPending = new Paragraph();
            meetingPending.setAlignment(Paragraph.ALIGN_MIDDLE);     
            meetingPending.add("\n\n" + pending); 
            document.add(meetingPending);
            
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(ControllerConsultMinute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMinute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        } finally {
            document.close();
        }
            
    }
    
    public void loadAssistantTableView(int idMeeting) {
        ObservableList<Assistant> assistantList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < ASSISTANT_DAO.getAssitants(idMeeting).size(); i++){
                assistantList.add(ASSISTANT_DAO.getAssitants(idMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewAssistantData(assistantList);
    }
    
    public void loadTableViewAssistantData(ObservableList<Assistant> assistantList) {
        this.tableColumnMember.setCellValueFactory(new PropertyValueFactory("fullName"));
        this.tableColumnRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tableViewAssistant.setItems(assistantList);
    }
    
    public void loadPreRequisiteTableView(int idMeeting) {
        ObservableList<PreRequisite> preRequisiteList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < PRE_REQUISITE_DAO.consultPreRequisites(idMeeting).size(); i++){
                preRequisiteList.add(PRE_REQUISITE_DAO.consultPreRequisites(idMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewPreRequisite(preRequisiteList);
    }
    
    public void loadTableViewPreRequisite(ObservableList<PreRequisite> preRequisiteList) {
        this.tableColumnDescription.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnManagerPrerequisite.setCellValueFactory(new PropertyValueFactory("nameParticipant"));
        tableViewPreRequisite.setItems(preRequisiteList);
    }
    
    public void loadDicussionTableView(int idMeeting) {
        ObservableList<Discussion> discussionList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < DISCUSSION_DAO.consultDiscussionsUpdated(idMeeting).size(); i++){
                discussionList.add(DISCUSSION_DAO.consultDiscussionsUpdated(idMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewDiscussionData(discussionList);
    }
    public void loadTableViewDiscussionData(ObservableList<Discussion> discussionList) {
        this.tableColumnStartHour.setCellValueFactory(new PropertyValueFactory("startHour"));
        this.tableColumnEndHour.setCellValueFactory(new PropertyValueFactory("endHour"));
        this.tableColumnEstimatedTime.setCellValueFactory(new PropertyValueFactory("estimatedTime"));
        this.tableColumnNumberDiscussion.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnPointToDiscuss.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnLeader.setCellValueFactory(new PropertyValueFactory("leaderName"));
        tableViewDiscussion.setItems(discussionList);
    }
    
    public void loadAgreementTableView(int idMeeting) {
        ObservableList<Agreement> agreementList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < AGREEMENT_DAO.consultAgreement(idMeeting).size(); i++){
                agreementList.add(AGREEMENT_DAO.consultAgreement(idMeeting).get(i));
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        loadTableViewAgreementData(agreementList);
    }
    
    public void loadTableViewAgreementData(ObservableList<Agreement> agreementList) {
        this.tableColumnNumberAgreement.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnManagerAgreement.setCellValueFactory(new PropertyValueFactory("manager"));
        this.tableColumnDate.setCellValueFactory(new PropertyValueFactory("date"));
        tableViewAgreement.setItems(agreementList);
    }
    
    public void loadMeetingData(int idMeeting) {
        try {
            Meeting meetingConsulted;
            meetingConsulted = MEETING_DAO.searchMeeting(idMeeting);
            String titleOfProyect = meetingConsulted.getTitleOfProyect();
            String place = meetingConsulted.getPlace();
            String affair = meetingConsulted.getAffair();
            Date meetingDateAux = meetingConsulted.getDate();
            String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(meetingDateAux));
            textFieldMeetingDate.setText(registrationDate);
            textFieldProyectName.setText(titleOfProyect );
            textFieldPlace.setText(place);
            textFieldAffair.setText(affair);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void loadMinuteData(int idMeeting) {
        try {
            Minute MinuteConsulted;
            MinuteConsulted = MINUTE_DAO.consultMinute(idMeeting);
            String note = MinuteConsulted.getNote();
            String pending = MinuteConsulted.getPending();
            textAreaNote.setText("NOTAS: \n" + note);
            textAreaPending.setText("Pendientes: \n" + pending);
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    
    @FXML
    void openModifyMinuteGuiOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLModifyMinute.fxml"));
            fXMLLoader.load();
            ControllerModifyMinute controllerModifyMinute = fXMLLoader.getController();
            
            controllerModifyMinute.getMeetingSelected(id);
            controllerModifyMinute.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new javafx.scene.image.Image("/images/sgpca.png"));
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMinute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void getOutOfConsultMinuteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de minuta?");
        alert.setTitle("Confirmación de salida");
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
            Logger.getLogger(ControllerConsultMinute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
