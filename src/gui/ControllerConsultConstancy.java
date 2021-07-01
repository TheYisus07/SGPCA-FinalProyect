package gui;

import businesslogic.BusinessException;
import businesslogic.ConstancyDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import domain.Constancy;
import domain.Event;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ControllerConsultConstancy implements Initializable {
    private Event eventObject = new Event();
    private Constancy constancy = new Constancy();
    ConstancyDAO CONSTANCY_DAO = new  ConstancyDAO();

    @FXML
    private JFXTextField textFieldRecognitionType;

    @FXML
    private JFXTextField textFieldReceptorConstancyMail;

    @FXML
    private JFXTextField textFieldValidatorMail;

    @FXML
    private JFXTextField textFieldReceptorMail;

    @FXML
    private JFXTextArea textFieldDescription;

    @FXML
    private JFXTextArea textFieldReglamentaryNotes;

    @FXML
    private JFXButton buttonDownloadConstancy;

    @FXML
    private JFXButton buttonExitOfConsultConstancy;

    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private Label labelLoggedInUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }

    public void closeWindow(ActionEvent event){
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
            stage.getIcons().add(new javafx.scene.image.Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultConstancy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void downloadConstancyOnAction(ActionEvent event) {
        String recognitionType = constancy.getRecognitionType();
        String description = constancy.getDescription();
        String regulatoryNote = constancy.getRegulatoryNote();
        String eventRegistred = eventObject.getTittle();
        String eventType = eventObject.getType();
        Date eventDate = eventObject.getEventDate();
        
        Document document = new Document();
        String user = System.getProperty("user.home");
        try {
            FileOutputStream ficheroPdf = new FileOutputStream(user + "/Downloads/" + eventRegistred + "_" + recognitionType + ".pdf");
            PdfWriter.getInstance(document,ficheroPdf).setInitialLeading(20);
            document.open();
            
            Image logoUv = Image.getInstance("src\\images\\Uv_Logo.png");
            logoUv.scaleToFit(100, 100);
            logoUv.setAbsolutePosition(50,700);
            document.add(logoUv);
            Image logoLis = Image.getInstance("src\\images\\LIS.png");
            logoLis.scaleToFit(100, 100);
            logoLis.setAbsolutePosition(450,700);
            document.add(logoLis);
            
            Paragraph titleParagraph = new Paragraph();
            titleParagraph.setFont(FontFactory.getFont("arial",16,Font.ITALIC));
            titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);    
            titleParagraph.add("\n\n\n\n\n UNIVERSIDAD VERACRUZANA \n\n\n\n\n\n\n");
            
            Paragraph recognitionTypeParagraph = new Paragraph();
            recognitionTypeParagraph.setAlignment(Paragraph.ALIGN_CENTER);    
            recognitionTypeParagraph.add(recognitionType+ "\n\n\n\n");
            
            Paragraph textParagraph = new Paragraph();
            textParagraph.setAlignment(Paragraph.ALIGN_CENTER);     
            textParagraph.add(description+ "\n\n"); 
            
            Paragraph regulatoryNoteParagraph = new Paragraph();
            regulatoryNoteParagraph.setAlignment(Paragraph.ALIGN_CENTER);     
            regulatoryNoteParagraph.add(regulatoryNote + "\n\n"); 
            
            
            Paragraph dateParagraph = new Paragraph();
            dateParagraph.setAlignment(Paragraph.ALIGN_CENTER);  
            String dateFormat = (new SimpleDateFormat("d MMMM, yyyy ").format(eventDate));
            dateParagraph.add("Xalapa, " + dateFormat);
            
            document.add(titleParagraph);
            document.add(recognitionTypeParagraph);
            document.add(textParagraph);
            document.add(regulatoryNoteParagraph);
            document.add(dateParagraph);
             
        } catch(IOException | DocumentException ex) {
            Logger.getLogger(ControllerConsultConstancy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            document.close();
        }

        closeWindow(event);
    }
    
    public void getEventForThisConstancy(Event event, int positionConstancy){

       eventObject = event;
       String eventTittle = event.getTittle();
       

       ObservableList<Constancy> constancyList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < CONSTANCY_DAO.consultConstancyList().size(); i++) {
                String eventTitleExpected = CONSTANCY_DAO.consultConstancyList().get(i).getEventTitle();
                if(eventTitleExpected.equals(eventTittle)){
                    constancyList.add(CONSTANCY_DAO.consultConstancyList().get(i));
                }
            }} catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
       fillData(constancyList,positionConstancy);
    }
    
    public void fillData(ObservableList<Constancy> constancyList, int positionConstancy) {
        constancy = constancyList.get(positionConstancy);
        String recognitionType = constancy.getRecognitionType();
        String description = constancy.getDescription();
        String InstitutionalMailReceivers = constancy.getInstitutionalMailReceivers();
        String InstitutionalMailValidator = constancy.getInstitutionalMailValidator();
        String InstitutionalMailRedPient = constancy.getInstitutionalMailRedPient();
        String regulatoryNote = constancy.getRegulatoryNote();

        textFieldRecognitionType.setText(recognitionType);
        textFieldReceptorConstancyMail.setText(InstitutionalMailRedPient);
        textFieldValidatorMail.setText(InstitutionalMailReceivers);
        textFieldReceptorMail.setText(InstitutionalMailValidator);
        textFieldDescription.setText(description);
        textFieldReglamentaryNotes.setText(regulatoryNote);
    }

    @FXML
    public void getOutOfConsultConstancyOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Deseas salir de minuta?");
        alert.setTitle("Salir");
        alert.setContentText("Salida de minuta");
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
            Logger.getLogger(ControllerConsultConstancy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
