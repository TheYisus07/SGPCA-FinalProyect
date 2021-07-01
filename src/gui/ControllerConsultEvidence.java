package gui;

import businesslogic.BusinessException;
import businesslogic.ArticleDAO;
import businesslogic.BookDAO;
import businesslogic.ChapterBookDAO;
import businesslogic.EvidenceDAO;
import businesslogic.ReceptionWorkDAO;
import com.jfoenix.controls.JFXButton;
import domain.Article;
import domain.Book;
import domain.ChapterBook;
import domain.Evidence;
import domain.ReceptionWork;
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
import javafx.scene.control.ButtonType;
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

public class ControllerConsultEvidence implements Initializable {
    
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final ArticleDAO ARTICLE_DAO = new ArticleDAO();
    private final BookDAO BOOK_DAO = new BookDAO();
    private final ChapterBookDAO CHAPTER_BOOK_DAO = new ChapterBookDAO();
    private final ReceptionWorkDAO RECEPTION_WORK_DAO = new ReceptionWorkDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();     
    
    @FXML
    private Label labelLoggedInUser;

    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private JFXButton buttonConsultEvidenceExit;

    @FXML
    private TableView<Evidence> tableViewEvidence;

    @FXML
    private TableColumn tableColumnEvidenceTitle;

    @FXML
    private TableColumn tableColumnEvidenceAuthor;

    @FXML
    private TableColumn tableColumnEvidenceRegistrationDate;
        

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedProjectTitle();
        fillOutTheEvidenceTable();
    }       
   
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    @FXML 
    public void showGuiaddNewEvidence(ActionEvent actionEvent) {
        String memberFullName = labelLoggedInUser.getText();        
        String memberEmail = labelLoggedInUserEmail.getText();
        Stage stageOld = (Stage) buttonConsultEvidenceExit.getScene().getWindow();
        stageOld.close();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLAddEvidence.fxml"));
            fXMLLoader.load();
            ControllerAddNewEvidence controllerAddNewEvidence = fXMLLoader.getController();
            controllerAddNewEvidence.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene); 
            stage.setResizable(false);        
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }    
    
    public void showGuiConsultArticle(int idEvidence) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewEvidence.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultArticle.fxml"));
            fXMLLoader.load();
            ControllerConsultArticle controllerConsultArticle = fXMLLoader.getController();
            controllerConsultArticle.getIdEvidence(idEvidence);
            controllerConsultArticle.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);  
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiConsultChapter(int idEvidence) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewEvidence.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultChapterBook.fxml"));
            fXMLLoader.load();
            ControllerConsultChapterBook controllerConsultChapterBook = fXMLLoader.getController();
            controllerConsultChapterBook.getIdEvidence(idEvidence);
            controllerConsultChapterBook.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);  
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiConsultBook(int idEvidence) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewEvidence.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultBook.fxml"));
            fXMLLoader.load();
            ControllerConsultBook controllerConsultBook = fXMLLoader.getController();
            controllerConsultBook.getIdEvidence(idEvidence);
            controllerConsultBook.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);  
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiConsultReceptionWork(int idEvidence) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewEvidence.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultReceptionWork.fxml"));
            fXMLLoader.load();
            ControllerConsultReceptionWork controllerConsultReceptionWork = fXMLLoader.getController();
            controllerConsultReceptionWork.getIdEvidence(idEvidence);
            controllerConsultReceptionWork.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);  
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiEvidenceSelected(int idEvidence) {
        boolean isArticle = false;
        boolean isBook = false;
        boolean isReceptionWork = false;
        boolean isChapterBook = false;
        Article article = null;
        Book book = null;
        ChapterBook chapterBook = null;
        ReceptionWork receptionWork = null;
        try {
            article = ARTICLE_DAO.consultArticle(idEvidence);
            book = BOOK_DAO.consultBook(idEvidence);
            chapterBook = CHAPTER_BOOK_DAO.consultChapterBook(idEvidence);
            receptionWork = RECEPTION_WORK_DAO.consultReceptionWork(idEvidence);
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        if (!(article.getIdEvidence() == 0)) {
            isArticle = true;
        }
        
        if (!(book.getIdEvidence() == 0)) {
            isBook = true;
        }
        
        if (!(chapterBook.getIdEvidence() == 0)) {
            isChapterBook = true;
        }
        
        if (!(receptionWork.getIdEvidence() == 0)) {
            isReceptionWork = true;
        }        
        
        if (isArticle) {
            showGuiConsultArticle(idEvidence);
        } else if (isBook) {
            showGuiConsultBook(idEvidence);
        } else if (isChapterBook) {
            showGuiConsultChapter(idEvidence);            
        } else if (isReceptionWork) {
            showGuiConsultReceptionWork(idEvidence); 
        }        
    }
    
    public void selectedProjectTitle() {
        tableViewEvidence.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Evidence> observable, Evidence oldEvidence, Evidence newEvidence) -> {
            if (tableViewEvidence.getSelectionModel().getSelectedItem() != null) {
                int idEvidence = tableViewEvidence.getSelectionModel().getSelectedItem().getIdEvidence();
                showGuiEvidenceSelected(idEvidence);                   
            }
        });
    }       
    
    public void showGuiWorkplan(String memberFullName, String memberEmail) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLWorkplan.fxml"));
            fXMLLoader.load();
            ControllerWorkplan controllerWorkplan = fXMLLoader.getController();
            controllerWorkplan.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();                 
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPasswordAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void exitFromEvidenceListQuery(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de Producción");        
        alert.setHeaderText("¿Desea salir de la lista de Evidencias del CA?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonConsultEvidenceExit.getScene().getWindow();
            stage.close();
        }
    }
    
    public void fillOutTheEvidenceTable() {
        ObservableList<Evidence> evidenceList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < EVIDENCE_DAO.consultEvidenceList().size(); i++) {              
                evidenceList.add(EVIDENCE_DAO.consultEvidenceList().get(i));
            }
        } catch (BusinessException ex) {        
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnEvidenceTitle);                
        tableColumnEvidenceTitle.setCellValueFactory(new PropertyValueFactory("title"));
        tableColumnEvidenceAuthor.setCellValueFactory(new PropertyValueFactory("memberFullName"));
        tableColumnEvidenceRegistrationDate.setCellValueFactory(new PropertyValueFactory("registrationDate"));
        tableViewEvidence.setItems(evidenceList);
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
            Logger.getLogger(ControllerConsultEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}