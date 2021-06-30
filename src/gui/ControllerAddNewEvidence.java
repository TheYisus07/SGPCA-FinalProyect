package gui;

import businesslogic.BusinessException;
import businesslogic.ArticleDAO;
import businesslogic.BlueprintDAO;
import businesslogic.BookDAO;
import businesslogic.ChapterBookDAO;
import businesslogic.EvidenceDAO;
import businesslogic.MemberDAO;
import businesslogic.ProjectDAO;
import businesslogic.ReceptionWorkDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import domain.Article;
import domain.Book;
import domain.ChapterBook;
import domain.Evidence;
import domain.ReceptionWork;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author daniCV
 */

public class ControllerAddNewEvidence implements Initializable {
    
    private final ArticleDAO ARTICLE_DAO = new ArticleDAO();
    private final ChapterBookDAO CHAPTER_BOOK_DAO = new ChapterBookDAO();
    private final BookDAO BOOK_DAO = new BookDAO();
    private final ReceptionWorkDAO RECEPTION_WORK_DAO = new ReceptionWorkDAO();
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final EvidenceDAO EVIDENCE_DAO = new EvidenceDAO();
    private final MemberDAO MEMBER_DAO = new MemberDAO();
    private final TextFieldValidation TEXT_FIELD_VALIDATION = new TextFieldValidation();
    
    @FXML
    private Label labelLoggedInUser;

    @FXML
    private Label labelLoggedInUserEmail;

    @FXML
    private Label labelEvidenceCategory;

    @FXML
    private JFXComboBox<String> comboBoxEvidenceCategory;

    @FXML
    private AnchorPane anchoPanerAddChapterBook;
    
    @FXML
    private JFXTextField textFieldAddEvidenceTitle;
    
    @FXML
    private JFXTextField textFieldAddChapterAuthor;

    @FXML
    private JFXTextField textFieldAddChapterHomePage;

    @FXML
    private JFXTextField textFieldAddChapterEndPage;

    @FXML
    private JFXTextField textFieldAddChapterYear;

    @FXML
    private JFXTextField textFieldAddEvidenceImpact;

    @FXML
    private AnchorPane anchoPanerAddArticle;

    @FXML
    private JFXTextField textFieldAddArticleAuthor;

    @FXML
    private JFXTextField textFieldAddArticleMegazineName;

    @FXML
    private JFXTextField textFieldAddArticleEndPage;

    @FXML
    private JFXDatePicker textFieldAddArticlePublicationDate;

    @FXML
    private JFXTextField textFieldAddArticleCountry;

    @FXML
    private JFXTextField textFieldAddArticleVolumen;

    @FXML
    private JFXTextField textFieldAddArticleIssn;

    @FXML
    private JFXTextField textFieldAddArticleAuthorMail;

    @FXML
    private JFXComboBox<String> comboBoxEvidenceProject;
    
    @FXML
    private JFXComboBox<String> comboBoxEvidenceStatus;

    @FXML
    private Label labelAsociatedProject;

    @FXML
    private AnchorPane anchoPanerAddBook;

    @FXML
    private JFXTextField textFieldAddBookEditorial;
    
    @FXML
    private JFXTextField textFieldAddBookNumberEditorial;

    @FXML
    private JFXTextField textFieldAddBookYear;

    @FXML
    private JFXDatePicker dataPickerAddBookEndDate;
    
    @FXML
    private JFXDatePicker dataPickerAddBookStartDate;    

    @FXML
    private AnchorPane anchoPanerAddReceptionWork;

    @FXML
    private JFXTextField textFieldAddReceptionWorkDirection;

    @FXML
    private JFXTextField textFieldAddReceptionWorkNumberStudents;

    @FXML
    private JFXDatePicker dataPickerReceptionWorkStartDate;

    @FXML
    private JFXDatePicker dataPickerAddReceptionWorkFinalDate;

    @FXML
    private JFXTextField textFieldAddReceptionWorkGrade;

    @FXML
    private JFXComboBox<String> comboBoxAddReceptionWorkBlueprint;

    @FXML
    private JFXTextField textFieldAddReceptionWorkKind;

    @FXML
    private AnchorPane anchoPanerAddEvidenceCategory;

    public enum ETracing {
        Avalado, Asignado, Terminado, Vigente; 
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validFieldToAddEvidence();
        validFieldsToAddArticle();
        validFieldsToAddBook();
        validFieldsToAddChapterBook();
        validFieldsToAddReceptionWork();
        fillComboBoxWithEvidenceStatus();
        fillComboBoxWithCategories();
        fillComboBoxWithProjectTitles();
    }
    
    public void showGuiThereIsNoRecordOfBlueprints() {
        anchoPanerAddReceptionWork.setVisible(false);
        anchoPanerAddEvidenceCategory.setVisible(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sin registros de Anteproyectos");        
        alert.setHeaderText("El Trabajo Recepcional no puede ser asociado con ningún Anteproyecto");
        alert.setContentText("Registra primero un Anteproyecto. (Trabajo Recepcional - Categoria inválida)");
        alert.show();
    }        
    
    public void fillComboBoxWithEvidenceStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ETracing.Avalado.toString());
        statusList.add(ETracing.Asignado.toString());
        statusList.add(ETracing.Terminado.toString());
        statusList.add(ETracing.Vigente.toString());
        comboBoxEvidenceStatus.setItems(statusList);
    }
    
    public void fillComboBoxWithCategories() {
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Artículo");
        categoryList.add("Capítulo de Libro");
        categoryList.add("Libro");
        categoryList.add("Trabajo Recepcional");
        comboBoxEvidenceCategory.setItems(categoryList);
    }
    
    public boolean fillComboBoxWithBlueprintTitles() {
        boolean thereAreBlueprints = false;
        ObservableList<String> blueprintTitleList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < BLUEPRINT_DAO.consultListOfBlueprint().size(); i++) {
                blueprintTitleList.add(BLUEPRINT_DAO.consultListOfBlueprint().get(i).getTitle());
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        comboBoxAddReceptionWorkBlueprint.setItems(blueprintTitleList);
        if (!blueprintTitleList.isEmpty()) {       
            thereAreBlueprints = true;
        }
        
        return thereAreBlueprints;
    }
    
    public void fillComboBoxWithProjectTitles() {       
        try {
            ObservableList<String> projectTitleList = FXCollections.observableArrayList();
            for (int i = 0; i < PROJECT_DAO.consultListOfProjects().size(); i++) {
                projectTitleList.add(PROJECT_DAO.consultListOfProjects().get(i).getTitle());  
                comboBoxEvidenceProject.setItems(projectTitleList);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }   
    }
    
    public void validFieldToAddEvidence() {
        textFieldAddEvidenceTitle.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddEvidenceTitle.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddEvidenceTitle.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddEvidenceTitle.validate();
            }     
        });
        
        textFieldAddEvidenceImpact.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddEvidenceImpact.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddEvidenceImpact.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddEvidenceImpact.validate();
            }     
        });
        
        comboBoxEvidenceCategory.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxEvidenceCategory.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxEvidenceCategory.validate();
            }     
        });
        
        comboBoxEvidenceStatus.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxEvidenceStatus.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxEvidenceStatus.validate();
            }     
        });
    }
    
    public void validFieldsToAddArticle() {
        textFieldAddArticleAuthor.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddArticleAuthor.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleAuthor.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleAuthor.validate();
            }     
        });
        textFieldAddArticleAuthorMail.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddArticleAuthorMail.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleAuthorMail.getValidators().add(TEXT_FIELD_VALIDATION.getEMAIL_VALIDATOR());
        textFieldAddArticleAuthorMail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleAuthorMail.validate();
            }     
        });
        textFieldAddArticleCountry.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddArticleCountry.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleCountry.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleCountry.validate();
            }     
        });
        textFieldAddArticleEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddArticleEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddArticleEndPage.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleEndPage.validate();
            }     
        });
        textFieldAddArticleIssn.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddArticleIssn.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleIssn.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddArticleIssn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleIssn.validate();
            }     
        });
        textFieldAddArticleMegazineName.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddArticleMegazineName.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleMegazineName.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleMegazineName.validate();
            }     
        });
        textFieldAddArticlePublicationDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticlePublicationDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticlePublicationDate.validate();
            }     
        });        
        textFieldAddArticleVolumen.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddArticleVolumen.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddArticleVolumen.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddArticleVolumen.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddArticleVolumen.validate();
            }     
        });
    }
    
    public void validFieldsToAddChapterBook() {
        textFieldAddChapterAuthor.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddChapterAuthor.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddChapterAuthor.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddChapterAuthor.validate();
            }     
        });
        textFieldAddChapterEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddChapterEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddChapterEndPage.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddChapterEndPage.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddChapterEndPage.validate();
            }     
        });
        textFieldAddChapterHomePage.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddChapterHomePage.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddChapterHomePage.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddChapterHomePage.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddChapterHomePage.validate();
            }     
        });
        
        textFieldAddChapterYear.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddChapterYear.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddChapterYear.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddChapterYear.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddChapterYear.validate();
            }     
        });
    }

    public void validFieldsToAddBook() {
        dataPickerAddBookEndDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        dataPickerAddBookEndDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                dataPickerAddBookEndDate.validate();
            }     
        });
        
        dataPickerAddBookStartDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        dataPickerAddBookStartDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                dataPickerAddBookStartDate.validate();
            }     
        });
        textFieldAddBookNumberEditorial.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddBookNumberEditorial.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBookNumberEditorial.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddBookNumberEditorial.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBookNumberEditorial.validate();
            }     
        });
        textFieldAddBookEditorial.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddBookEditorial.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBookEditorial.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBookEditorial.validate();
            }     
        });       
        textFieldAddBookYear.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());
        textFieldAddBookYear.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddBookYear.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddBookYear.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddBookYear.validate();
            }     
        });        
    }
    
    public void validFieldsToAddReceptionWork() {        
        comboBoxAddReceptionWorkBlueprint.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        comboBoxAddReceptionWorkBlueprint.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                comboBoxAddReceptionWorkBlueprint.validate();
            }     
        });
        
        dataPickerAddReceptionWorkFinalDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        dataPickerAddReceptionWorkFinalDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                dataPickerAddReceptionWorkFinalDate.validate();
            }     
        });
        
        dataPickerReceptionWorkStartDate.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        dataPickerReceptionWorkStartDate.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                dataPickerReceptionWorkStartDate.validate();
            }     
        });
        textFieldAddReceptionWorkDirection.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddReceptionWorkDirection.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddReceptionWorkDirection.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddReceptionWorkDirection.validate();
            }     
        });
        textFieldAddReceptionWorkGrade.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddReceptionWorkGrade.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddReceptionWorkGrade.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddReceptionWorkGrade.validate();
            }     
        });
        textFieldAddReceptionWorkKind.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_TITLE());
        textFieldAddReceptionWorkKind.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddReceptionWorkKind.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddReceptionWorkKind.validate();
            }     
        });
        textFieldAddReceptionWorkNumberStudents.getValidators().add(TEXT_FIELD_VALIDATION.getSTRING_LENGTH_VALIDATOR_INT());        
        textFieldAddReceptionWorkNumberStudents.getValidators().add(TEXT_FIELD_VALIDATION.getREQUIRED_FIELD_VALIDATOR());
        textFieldAddReceptionWorkNumberStudents.getValidators().add(TEXT_FIELD_VALIDATION.getNUMBER_VALIDATOR());
        textFieldAddReceptionWorkNumberStudents.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                textFieldAddReceptionWorkNumberStudents.validate();
            }     
        });
    }
    
    public LocalDate convertToLocalDate(Date date) {
        return LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }
    
    public Date convertToDate(LocalDate localDate){
        return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }    
    
    public void showGuiConsultEvidence(ActionEvent actionEvent) {
        String memberFullName = labelLoggedInUser.getText();        
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvidence.fxml"));
            fXMLLoader.load();
            ControllerConsultEvidence controllerConsultEvidence = fXMLLoader.getController();
            controllerConsultEvidence.getMemberFullName(memberFullName, memberEmail);
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
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }                    
    }
    
    @FXML
    public void exitFromAddEvidence(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida del registro de Evidencias");        
        alert.setHeaderText("¿Desea salir del registro de la Evidencia");
        alert.setContentText("La Evidencia no quedará registrada en el sistema");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiConsultEvidence(actionEvent);
        }
    }
    
    public void showProjectCombobox() {
        labelAsociatedProject.setVisible(true);
        comboBoxEvidenceProject.setVisible(true);
    }
    
    public void showFormAccordingToCategory(String evidenceCategory) {
        switch (evidenceCategory) {
            case "Artículo":
                if (!anchoPanerAddArticle.isVisible()) {
                    labelEvidenceCategory.setText("Registrar Artículo");
                    anchoPanerAddArticle.setVisible(true);
                    anchoPanerAddEvidenceCategory.setVisible(false);
                    anchoPanerAddChapterBook.setVisible(false);
                    anchoPanerAddBook.setVisible(false);
                    anchoPanerAddReceptionWork.setVisible(false);
                    showProjectCombobox();
                }
            break;
            
            case "Capítulo de Libro":
                if (!anchoPanerAddChapterBook.isVisible()) {
                    labelEvidenceCategory.setText("Registrar Capítulo de Libro");
                    anchoPanerAddChapterBook.setVisible(true);
                    anchoPanerAddArticle.setVisible(false);
                    anchoPanerAddEvidenceCategory.setVisible(false);                    
                    anchoPanerAddBook.setVisible(false);
                    anchoPanerAddReceptionWork.setVisible(false);
                    showProjectCombobox();
                }
            break;
            
            case "Libro":
                if (!anchoPanerAddBook.isVisible()) {
                    labelEvidenceCategory.setText("Registrar Libro");
                    anchoPanerAddBook.setVisible(true);
                    anchoPanerAddChapterBook.setVisible(false);
                    anchoPanerAddArticle.setVisible(false);
                    anchoPanerAddEvidenceCategory.setVisible(false);                                        
                    anchoPanerAddReceptionWork.setVisible(false);
                    showProjectCombobox();
                }
            break;
            
            case "Trabajo Recepcional":
                if (!anchoPanerAddReceptionWork.isVisible()) {
                    labelEvidenceCategory.setText("Registrar Trabajo Recepcional");
                    boolean existsBlueprints = fillComboBoxWithBlueprintTitles();
                    if (!existsBlueprints) {
                        showGuiThereIsNoRecordOfBlueprints();
                    } else {
                        anchoPanerAddReceptionWork.setVisible(true);
                        anchoPanerAddBook.setVisible(false);
                        anchoPanerAddChapterBook.setVisible(false);
                        anchoPanerAddArticle.setVisible(false);
                        anchoPanerAddEvidenceCategory.setVisible(false);     
                        labelAsociatedProject.setVisible(false);
                        comboBoxEvidenceProject.setVisible(false);
                    }
                }
            break;
            
            default:
            break;
        }
    }
    
    @FXML
    public void getCategoryOfEvidence(ActionEvent actionEvent) {
        boolean notSelectedCategory = comboBoxEvidenceCategory.getSelectionModel().isEmpty();
        if (!notSelectedCategory) {
            String evidenceCategory = comboBoxEvidenceCategory.getValue();
            showFormAccordingToCategory(evidenceCategory);            
        }
    }
    
    public void showGuiConsulEvidence(ActionEvent actionEvent) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultEvidence.fxml"));
            fXMLLoader.load();            
            ControllerConsultEvidence controllerConsultEvidence = fXMLLoader.getController();
            controllerConsultEvidence.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setResizable(false);   
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addEvidence() {        
        Evidence evidence = new Evidence();
        int successfulResult = 0;
        String evidenceCategory = null;        
        String evidenceStatus = null;
        String evidenceImpact = null;
        String evidenceTitle = null;
        String evidenceMember = labelLoggedInUser.getText();
        String evidenceAcademicGroupKeyCode = null;
        try {
            evidenceAcademicGroupKeyCode = MEMBER_DAO.consultMember(evidenceMember).getKeycodeAcademicGroup();
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        String evidenceProjectTitle = null;
        
        if (comboBoxEvidenceCategory.getSelectionModel().getSelectedItem() != null) {
            evidenceCategory = comboBoxEvidenceCategory.getSelectionModel().getSelectedItem();
        }
        
        if (comboBoxEvidenceStatus.getSelectionModel() != null) {
            evidenceStatus = comboBoxEvidenceStatus.getSelectionModel().getSelectedItem();
            evidence.setActualStatus(evidenceStatus);
        }
        
        if ((textFieldAddEvidenceImpact.validate()) && (!textFieldAddEvidenceImpact.getText().trim().equals(""))) {
            evidenceImpact = textFieldAddEvidenceImpact.getText();
            evidence.setImpactOnCA(evidenceImpact);
        }
        
        if ((textFieldAddEvidenceTitle.validate()) && (!textFieldAddEvidenceTitle.getText().trim().equals(""))) {
            evidenceTitle = textFieldAddEvidenceTitle.getText();
            evidence.setTitle(evidenceTitle);
        }
        
        if (comboBoxEvidenceProject.getSelectionModel().getSelectedItem() != null) {
            evidenceProjectTitle = comboBoxEvidenceProject.getSelectionModel().getSelectedItem();
            evidence.setProjectTitle(evidenceProjectTitle);
        }
        
        evidence.setRegistrationDate(new Date());
        evidence.setMemberFullName(evidenceMember);
        evidence.setKeycodeAcademicGroup(evidenceAcademicGroupKeyCode);
        
        try {
            if ((evidenceCategory != null) && (evidenceStatus != null) && (evidenceImpact != null) && (evidenceTitle != null) && (evidenceProjectTitle != null)) {            
                successfulResult = EVIDENCE_DAO.addNewEvidenceWithProject(evidence);
            } else if ((evidenceCategory != null) && (evidenceStatus != null) && (evidenceImpact != null) && (evidenceTitle != null)) {            
                successfulResult = EVIDENCE_DAO.addNewEvidenceWithoutProject(evidence);
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        return successfulResult;
    }
    
    public void showGuiSuccessfullyAddedEvidence(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Evidencia agrega");        
        alert.setHeaderText("La evidencia fue agregada exitosamente");
        alert.setContentText("Puede consultarla en la lista de Evidencias");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){                        
            showGuiConsulEvidence(actionEvent);
        } 
    }
    
    public void showGuiToSelectCategory() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sin categoria");        
        alert.setHeaderText("Selecciona una categoría válida para agregar una evidencia");
        alert.showAndWait();        
    }
    
    public int addArticle() {
        Article article = new Article();
        int successfulResultArticle = 0;
        String articleAuthor = null;
        Date articleRegistrationDate = null;
        String megazineName = null;
        String articleAuthorEmail = null;
        int articleVolumen = 0;
        boolean isVolumenNumber = false;
        int articleEndPage = 0;
        boolean isEndPageNumber = false;
        String articleCountry = null;
        String articleIssn = null;
        
        if ((textFieldAddArticleAuthor.validate()) && (!textFieldAddArticleAuthor.getText().trim().equals(""))) {
            articleAuthor = textFieldAddArticleAuthor.getText();
            article.setAuthor(articleAuthor);
        }
        
        if (textFieldAddArticlePublicationDate.validate()) {
            articleRegistrationDate = convertToDate(textFieldAddArticlePublicationDate.getValue());
            article.setPublicationDate(articleRegistrationDate);            
        }
        
        if ((textFieldAddArticleMegazineName.validate()) && (!textFieldAddArticleMegazineName.getText().trim().equals(""))) {
            megazineName = textFieldAddArticleMegazineName.getText();
            article.setMegazineName(megazineName);
        }
        
        if ((textFieldAddArticleAuthorMail.validate()) && (!textFieldAddArticleAuthorMail.getText().trim().equals(""))) {
            articleAuthorEmail = textFieldAddArticleAuthorMail.getText();
            article.setMail(articleAuthorEmail);
        }
        
        if (textFieldAddArticleVolumen.validate()) {
            try {
                articleVolumen = Integer.parseInt(textFieldAddArticleVolumen.getText().trim());
                article.setVolumen(articleVolumen);
                isVolumenNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        if (textFieldAddArticleEndPage.validate()) {
            try {
                articleEndPage = Integer.parseInt(textFieldAddArticleEndPage.getText().trim());
                article.setEndPage(articleEndPage);
                isEndPageNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }        
        
        if ((textFieldAddArticleCountry.validate()) && (!textFieldAddArticleCountry.getText().trim().equals(""))) {
            articleCountry = textFieldAddArticleCountry.getText();
            article.setCountry(articleCountry);
        }
        
        if ((textFieldAddArticleIssn.validate()) && (!textFieldAddArticleIssn.getText().trim().equals(""))) {
            articleIssn = textFieldAddArticleIssn.getText();
            article.setIssn(articleIssn);
        }
        
        if ((articleAuthor != null) && (articleRegistrationDate != null) && (megazineName != null) && (articleAuthorEmail != null) && (isEndPageNumber) && (isVolumenNumber) && (articleCountry != null) && (articleIssn != null)) {
            if (addEvidence() == 1) {
                try {
                    int idEvidence = EVIDENCE_DAO.getLastInsertEvidenceId();
                    article.setIdEvidence(idEvidence);
                    successfulResultArticle = ARTICLE_DAO.addNewArticle(article);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            }            
        }
                
        return successfulResultArticle;
    }
    
    public int addChapterBook() {
        ChapterBook chapterBook = new ChapterBook();
        int successfulResultChapter = 0;
        String chapterBookAuthor = null;
        int chapterBookYear = 0;
        boolean isYearNumber = false;
        int chapterBookHomePage = 0;
        boolean isHomePageNumber = false; 
        int chapterBookEndPage = 0;
        boolean isEndPage = false;
        
        if ((textFieldAddChapterAuthor.validate()) && (!textFieldAddChapterAuthor.getText().trim().equals(""))) {
            chapterBookAuthor = textFieldAddChapterAuthor.getText();
            chapterBook.setAuthor(chapterBookAuthor);
        }
        
        if (textFieldAddChapterYear.validate()) {
            try {
                chapterBookYear = Integer.parseInt(textFieldAddChapterYear.getText().trim());
                chapterBook.setYearPublication(chapterBookYear);
                isYearNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }  
        
        if (textFieldAddChapterHomePage.validate()) {
            try {
                chapterBookHomePage = Integer.parseInt(textFieldAddChapterHomePage.getText().trim());
                chapterBook.setHomePage(chapterBookHomePage);
                isHomePageNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }  
        
        if (textFieldAddChapterEndPage.validate()) {
            try {
                chapterBookEndPage = Integer.parseInt(textFieldAddChapterEndPage.getText().trim());
                chapterBook.setEndPage(chapterBookEndPage);
                isEndPage = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }  
        
        if ((chapterBookAuthor != null) && (isYearNumber) && (isHomePageNumber) && (isEndPage)) {
            if (addEvidence() == 1) {
                try {
                    int idEvidence = EVIDENCE_DAO.getLastInsertEvidenceId();
                    chapterBook.setIdEvidence(idEvidence);
                    successfulResultChapter = CHAPTER_BOOK_DAO.addNewChapterBook(chapterBook);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            }          
        }
        
        return  successfulResultChapter;
    }
    
    public int addBook() {
        Book book = new Book();
        int successfulResultBook = 0;
        String bookEditorial = null;
        Date bookEndDate = null;
        Date bookStarDate = null;
        int bookPublicationYear = 0;
        boolean isYearNumber = false;
        int bookEditionNumber = 0;
        boolean isEditionNumber = false;
        
        if ((textFieldAddBookEditorial.validate()) && (!textFieldAddBookEditorial.getText().trim().equals(""))) {
            bookEditorial = textFieldAddBookEditorial.getText();
            book.setEditorial(bookEditorial);
        }
        
        if ((textFieldAddBookYear.validate())) {
            try {
                bookPublicationYear = Integer.parseInt(textFieldAddBookYear.getText().trim());
                book.setPublicationYear(bookPublicationYear);
                isYearNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        if ((textFieldAddBookNumberEditorial.validate())) {
            try {
                bookEditionNumber = Integer.parseInt(textFieldAddBookNumberEditorial.getText().trim());
                book.setEditionNumber(bookEditionNumber);
                isEditionNumber = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        if (dataPickerAddBookEndDate.validate()) {
            bookEndDate = convertToDate(dataPickerAddBookEndDate.getValue());
            book.setEndDate(bookEndDate);
        }
        
        if (dataPickerAddBookStartDate.validate()) {
            bookStarDate = convertToDate(dataPickerAddBookStartDate.getValue());
            book.setStartDate(bookStarDate);
        }
        
        if ((bookEditorial != null) && (isYearNumber) && (isEditionNumber) && (bookEndDate != null) && (bookStarDate != null)) {
            if (addEvidence() == 1) {
                try {
                    int idEvidence = EVIDENCE_DAO.getLastInsertEvidenceId();
                    book.setIdEvidence(idEvidence);
                    successfulResultBook = BOOK_DAO.addNewBook(book);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            }         
        }
        
        return  successfulResultBook;
    }
    
    public int addReceptionalWork() {
        ReceptionWork receptionWork = new ReceptionWork();
        int successfulResultReceptionWork = 0;
        String receptionWorkDirection = null;
        Date receptionWorkFinalDate = null;
        Date receptionWorkStartDate = null;
        String receptionWorkGrade = null;
        int receptionWorkNumberStudents = 0;
        boolean isNumberStudents = false;
        String receptionWorkKind = null;
        String receptionWorkBlueprint = null;
        
        if ((textFieldAddReceptionWorkDirection.validate()) && (!textFieldAddReceptionWorkDirection.getText().trim().equals(""))) {
            receptionWorkDirection = textFieldAddReceptionWorkDirection.getText();
            receptionWork.setDirection(receptionWorkDirection);
        }
        
        if ((textFieldAddReceptionWorkKind.validate()) && (!textFieldAddReceptionWorkKind.getText().trim().equals(""))) {
            receptionWorkKind = textFieldAddReceptionWorkKind.getText();
            receptionWork.setKind(receptionWorkKind);
        }
        
        if ((textFieldAddReceptionWorkGrade.validate()) && (!textFieldAddReceptionWorkGrade.getText().trim().equals(""))) {
            receptionWorkGrade = textFieldAddReceptionWorkGrade.getText();
            receptionWork.setGrade(receptionWorkGrade);
        }
        
        if (dataPickerReceptionWorkStartDate.validate()) {
            receptionWorkStartDate = convertToDate(dataPickerReceptionWorkStartDate.getValue());
            receptionWork.setStartDate(receptionWorkStartDate);
        }
        
        if (dataPickerAddReceptionWorkFinalDate.validate()) {
            receptionWorkFinalDate = convertToDate(dataPickerAddReceptionWorkFinalDate.getValue());
            receptionWork.setFinalDate(receptionWorkFinalDate);
        }
        
        if ((textFieldAddReceptionWorkNumberStudents.validate())) {
            try {
                receptionWorkNumberStudents = Integer.parseInt(textFieldAddReceptionWorkNumberStudents.getText().trim());
                receptionWork.setNumberStudents(receptionWorkNumberStudents);
                isNumberStudents = true;
            } catch(NumberFormatException exception) {
                Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        if (comboBoxAddReceptionWorkBlueprint.getSelectionModel().getSelectedItem() != null) {
            receptionWorkBlueprint = comboBoxAddReceptionWorkBlueprint.getSelectionModel().getSelectedItem();
            receptionWork.setBlueprintTitle(receptionWorkBlueprint);
        }
        
        if ((receptionWorkDirection != null) && (receptionWorkFinalDate != null) && (receptionWorkStartDate != null) && (receptionWorkGrade != null) && (isNumberStudents) && (receptionWorkBlueprint != null) && (receptionWorkKind != null)) {
            if (addEvidence() == 1) {
                try {
                    int idEvidence = EVIDENCE_DAO.getLastInsertEvidenceId();
                    receptionWork.setIdEvidence(idEvidence);
                    successfulResultReceptionWork = RECEPTION_WORK_DAO.addReceptionWork(receptionWork);
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }
            } 
        }        
        return successfulResultReceptionWork;
    }
    
    @FXML 
    public void defineWhatKindOfEvidenceToAdd(ActionEvent actionEvent) {                
        int succesfulResultCategory = 0;
        if (anchoPanerAddArticle.isVisible()) {            
            succesfulResultCategory = addArticle();                                                   
            if (succesfulResultCategory == 1) {
                showGuiSuccessfullyAddedEvidence(actionEvent);
            }
        } else if (anchoPanerAddChapterBook.isVisible()) {
            succesfulResultCategory = addChapterBook();                                                   
            if (succesfulResultCategory == 1) {
                showGuiSuccessfullyAddedEvidence(actionEvent);
            }
        } else if (anchoPanerAddBook.isVisible()) {
            succesfulResultCategory = addBook();                                                   
            if (succesfulResultCategory == 1) {
                showGuiSuccessfullyAddedEvidence(actionEvent);
            }
        } else if (anchoPanerAddReceptionWork.isVisible()) {
            succesfulResultCategory = addReceptionalWork();                                                   
            if (succesfulResultCategory == 1) {
                showGuiSuccessfullyAddedEvidence(actionEvent);
            }     
        } else {
            showGuiToSelectCategory();
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
            Logger.getLogger(ControllerAddNewEvidence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}