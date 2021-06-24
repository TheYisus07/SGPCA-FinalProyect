package gui;

import businesslogic.AgreementDAO;
import businesslogic.DiscussionDAO;
import businesslogic.MinuteDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Agreement;
import domain.Discussion;
import domain.Minute;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerStartMeeting implements Initializable {
    private final DiscussionDAO DISCUSSION_DAO = new DiscussionDAO();
    private final AgreementDAO AGREEMENT_DAO = new AgreementDAO();
    private final MinuteDAO MINUTE_DAO = new MinuteDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private JFXButton buttonConcludeMeeting;

    @FXML
    private JFXButton buttonExitStartMeeting;

    @FXML
    private TableView<Discussion> tableViewAgenda;

    @FXML
    private TableColumn<Discussion, String> tableColumnDiscusision;
    
    @FXML
    private TableColumn tableColumnStarHour;

    @FXML
    private TableColumn tableColumnEndHour;

    @FXML
    private TableView<Agreement> tableViewAgreements;

    @FXML
    private TableColumn<Agreement, String> tableColumnNumber;

    @FXML
    private TableColumn<Agreement, String> tableColumnAgreement;

    @FXML
    private TableColumn<Agreement, String> tableColumnManager;

    @FXML
    private TableColumn<Agreement, String> tableColumnDate;

    @FXML
    private TableColumn<Agreement, String> tableColumnDelete;

    @FXML
    private JFXTextArea textAreaNote;

    @FXML
    private JFXTextArea textAreaPending;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private JFXTextField textFieldDescriptionAgreement;

    @FXML
    private JFXTextField textFieldManagerAgreement;

    @FXML
    private JFXButton buttonAddAgreement;

    @FXML
    private JFXTextField textFieldDateAgreement;
    
    @FXML
    private ImageView imageTime;
    
    private int idOfThisMeeting;
    private int idHour = 0;
    private int noRow;
    private ObservableList<Agreement> agreementList = FXCollections.observableArrayList();
    private ObservableList<Discussion> discussionList = FXCollections.observableArrayList();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldsToMeeting();
    } 
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    public void validFieldsToMeeting() {
        textFieldDescriptionAgreement.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldDescriptionAgreement.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldDescriptionAgreement.validate();
            }
        });
        textFieldManagerAgreement.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldManagerAgreement.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldManagerAgreement.validate();
            }
        });
        textFieldDateAgreement.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldDateAgreement.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldDateAgreement.validate();
            }
        });
        textAreaNote.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaNote.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaNote.validate();
            }
        });
        textAreaPending.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textAreaPending.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textAreaPending.validate();
            }
        });
    }
    
    public void starTime() {
        String time = getTime();
        Discussion discussiontableColumnStarHour = discussionList.get(idHour);
        discussiontableColumnStarHour.setStartHour(time);
        
        discussionList.set(idHour, discussiontableColumnStarHour);
        loadDiscussionData();
    }
    
    public String getTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        String time = hour + " : " + minute;
        return time;
    }
    
    @FXML
    public void takeTimeOnMouseClicked(MouseEvent event) {
        String time = getTime();
        
        Discussion discussionTableColumnEndHour = discussionList.get(idHour);
        discussionTableColumnEndHour.setEndHour(time);
        discussionList.set(idHour, discussionTableColumnEndHour);
        idHour = idHour + 1;
        if (idHour<discussionList.size()) {
            Discussion discussiontableColumnStarHour = discussionList.get(idHour);
            discussiontableColumnStarHour.setStartHour(time);
        
            discussionList.set(idHour, discussiontableColumnStarHour);
            
            loadDiscussionData();
        } else {
            buttonConcludeMeeting.setDisable(false);
            imageTime.setDisable(true);
            imageTime.setOpacity(0.5);
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
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerStartMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void getMeetingSelected(int idMeeting) {
        fillDiscussionList(idMeeting);
        starTime();
    }
    
    public void fillDiscussionList(int idMeeting) {
        idOfThisMeeting = idMeeting;
        for (int i = 0; i < DISCUSSION_DAO.consultDiscussions(idMeeting).size(); i++){
            discussionList.add(DISCUSSION_DAO.consultDiscussions(idMeeting).get(i));
        }
    }
    
    public void loadDiscussionData() {
        this.tableColumnDiscusision.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnStarHour.setCellValueFactory(new PropertyValueFactory("startHour"));
        this.tableColumnEndHour.setCellValueFactory(new PropertyValueFactory("endHour"));
        tableViewAgenda.setItems(discussionList);
    }
    
    @FXML
    public void addAgreementOnAction(ActionEvent event) {
        String description = null;
        String manager = null;
        String agreementDate = null;
        
        
        if (textFieldDescriptionAgreement.validate() && !textFieldDescriptionAgreement.getText().trim().equals("")) {
            description = textFieldDescriptionAgreement.getText();
        }
        
        if (textFieldManagerAgreement.validate() && !textFieldManagerAgreement.getText().trim().equals("")) {
            manager = textFieldManagerAgreement.getText();
        }
        
        if (textFieldDateAgreement.validate() && !textFieldDateAgreement.getText().trim().equals("")) {
            agreementDate = textFieldDateAgreement.getText();
        }
        
        if((description != null) && (manager != null) && (agreementDate != null)){
            noRow = noRow +1;
            Agreement agreement = new Agreement(description,manager,agreementDate,noRow,idOfThisMeeting);
            agreementList.add(agreement);
            loadDateOfAgreementTable();
            tableViewAgreements.setItems(agreementList);
            cleanTextFieldAgreement();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No ha llenado todos los campos");
            alert.showAndWait();
        }
    }
    
    public void cleanTextFieldAgreement() {
        textFieldDescriptionAgreement.setText("");
        textFieldManagerAgreement.setText("");
        textFieldDateAgreement.setText("");
    }
    
    public void loadDateOfAgreementTable() {
        fillAgreementTableView();
        Callback<TableColumn<Agreement, String>, TableCell<Agreement, String>> cellFactory = (TableColumn<Agreement, String> param) -> {
            TableCell<Agreement, String> cell = createTableCellImage();
            return cell;
        };
        tableColumnDelete.setCellFactory(cellFactory);
        
    }
    
    public void fillAgreementTableView() {
        this.tableColumnNumber.setCellValueFactory(new PropertyValueFactory("number"));
        this.tableColumnAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        this.tableColumnManager.setCellValueFactory(new PropertyValueFactory("manager"));
        this.tableColumnDate.setCellValueFactory(new PropertyValueFactory("date"));
    }
    
    public TableCell createTableCellImage() {
        final TableCell<Agreement, String> cell = new TableCell<Agreement, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView deleteIcon = createImageView();
                    HBox deletebutton = new HBox(deleteIcon);
                    deletebutton.setStyle("-fx-alignment:center");
                    setGraphic(deletebutton);
                    setText(null);
                }
            }
        };
        return cell;
    }
    
    public ImageView createImageView() {
        ImageView deleteIcon = new ImageView("/images/delete.png");
        deleteIcon.setFitHeight(20.0);
        deleteIcon.setFitWidth(20.0);
        deleteIcon.setCursor(Cursor.HAND);
        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
            deleteTableCell();
        });
        return deleteIcon; 
    }
    
    public void deleteTableCell() {
        noRow = tableViewAgreements.getSelectionModel().getSelectedIndex();
        agreementList.remove(noRow);
        noRow --;
    }

    
    public void showConsultMinuteOnAction(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultMinute.fxml"));
            fXMLLoader.load();
            ControllerConsultMinute controllerConsultMinute = fXMLLoader.getController();
            controllerConsultMinute.getMeetingSelected(idOfThisMeeting);
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
            Logger.getLogger(ControllerStartMeeting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void concludeMeetingOnAction(ActionEvent event) {
        boolean agreementsSaved = saveAgreements();
        if (agreementsSaved) {
            saveMinute();
            saveDiscussionsUpdated();
            showConsultMinuteOnAction(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No ha agregado acuerdos");
            alert.showAndWait();
        }
        
    }
    
    public boolean saveAgreements() {
        boolean result = false;
        if (!agreementList.isEmpty()) {
            for (int i = 0; i < agreementList.size(); i++) {
                AGREEMENT_DAO.registerAgreement(agreementList.get(i));
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }
    
    public void saveMinute() {
        String minuteNote = this.textAreaNote.getText();
        String MinutePending = this.textAreaPending.getText();
        Minute minute = new Minute(minuteNote, MinutePending, idOfThisMeeting);
        MINUTE_DAO.generateMinute(minute);
    }
    
    public void saveDiscussionsUpdated() {
        DISCUSSION_DAO.deleteDiscussions(idOfThisMeeting);
       
        for (int i = 0; i < discussionList.size(); i++) {
            discussionList.get(i).setIdMeeting(idOfThisMeeting);
            DISCUSSION_DAO.updateDiscussion(discussionList.get(i));
        }
    }
    
    @FXML
    public void getOutStartMeetingOnAction(ActionEvent event) {
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
