package gui;

import businesslogic.BusinessException;
import businesslogic.BlueprintDAO;
import businesslogic.ProjectDAO;
import com.jfoenix.controls.JFXButton;
import domain.Blueprint;
import domain.Project;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * 
 * @author daniCV
 */

public class ControllerConsultResearchProjects implements Initializable {
    
    private final ProjectDAO PROJECT_DAO = new ProjectDAO();  
    private final BlueprintDAO BLUEPRINT_DAO = new BlueprintDAO();
    private final FixTextToCell FIX_TEXT_TO_CELL = new FixTextToCell();
    
    @FXML 
    private JFXButton buttonConsultResearchProjectsExit;    
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML
    private TableView<Project> tableViewProject;
    
    @FXML
    private TableColumn<Project, String> tableColumnProjectTitle;
    
    @FXML
    private AnchorPane anchorPaneBlueprint;

    @FXML
    private JFXButton buttonMainBlueprint;

    @FXML
    private TableView<Blueprint> tableViewBlueprint;

    @FXML
    private TableColumn<Blueprint, String> tableColumnBlueprintTitle;

    @FXML
    private JFXButton buttonBackBlueprint;

    @FXML
    private AnchorPane anchorPaneBlackProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableOfBlueprint();
        fillOutResearchProjectsTable();
        selectedProjectTitle();
        selectedBlueprintTitle();
        fillOutTheBlueprintTable();
    }       
   
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    
    @FXML 
    public void showGuiaddNewResearchProject(ActionEvent actionEvent) {
        String memberFullName = labelLoggedInUser.getText();        
        String memberEmail = labelLoggedInUserEmail.getText();
        Stage stageOld = (Stage) buttonConsultResearchProjectsExit.getScene().getWindow();
        stageOld.close();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLAddProject.fxml"));
            fXMLLoader.load();
            ControllerAddResearchProject controllerAddResearchProject = fXMLLoader.getController();
            controllerAddResearchProject.getMemberFullName(memberFullName, memberEmail);
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
    
    public void showGuiProjectSelected(String projectTitle) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewProject.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultProject.fxml"));
            fXMLLoader.load();
            ControllerConsultProject controllerConsultProject = fXMLLoader.getController();
            controllerConsultProject.getProjectTitle(projectTitle);
            controllerConsultProject.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);  
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showGuiBlueprintSelected(String blueprintTitle) {
        String memberFullName = labelLoggedInUser.getText();
        String memberEmail = labelLoggedInUserEmail.getText();
        try {
            Stage stageOld = (Stage) tableViewProject.getScene().getWindow();
            stageOld.close();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultBlueprint.fxml"));
            fXMLLoader.load();
            ControllerConsultBlueprint controllerConsultBlueprint = fXMLLoader.getController();
            controllerConsultBlueprint.getBlueprintTitle(blueprintTitle);
            controllerConsultBlueprint.getMemberFullName(memberFullName, memberEmail);
            controllerConsultBlueprint.getBlueprintTitle(blueprintTitle);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Sistema Gestor de Proddenctividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerWorkplan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectedProjectTitle() {
        tableViewProject.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Project> observable, Project oldProject, Project newProject) -> {
            if (tableViewProject.getSelectionModel().getSelectedItem() != null) {
                String projectTitle = tableViewProject.getSelectionModel().getSelectedItem().getTitle();
                showGuiProjectSelected(projectTitle);                   
            }
        });
    }
    
    public void selectedBlueprintTitle() {
        tableViewBlueprint.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Blueprint> observable, Blueprint oldBlueprint, Blueprint newBlueprint) -> {
            if (tableViewBlueprint.getSelectionModel().getSelectedItem() != null) {
                String blueprintTitle = tableViewBlueprint.getSelectionModel().getSelectedItem().getTitle();
                showGuiBlueprintSelected(blueprintTitle);                   
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
    public void exitFromProjectListQuery(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de la consulta de lista de Proyectos");        
        alert.setHeaderText("¿Desea salir de la lista de proyectos de investigación?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiWorkplan(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonConsultResearchProjectsExit.getScene().getWindow();
            stage.close();
        }
    }
    
    public void fillOutTheBlueprintTable() {
        ObservableList<Blueprint> blueprintList = FXCollections.observableArrayList();        
        try {
            for (int i = 0; i < BLUEPRINT_DAO.consultListOfBlueprint().size(); i++) {                        
               blueprintList.add(BLUEPRINT_DAO.consultListOfBlueprint().get(i));            
            }        
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnProjectTitle);                
        tableColumnBlueprintTitle.setCellValueFactory(new PropertyValueFactory("title"));
        tableViewBlueprint.setItems(blueprintList);
    }
    
    public void fillOutResearchProjectsTable() {
        ObservableList<Project> projectList = FXCollections.observableArrayList();
        try {
            for (int i = 0; i < PROJECT_DAO.consultListOfProjects().size(); i++) {                        
            projectList.add(PROJECT_DAO.consultListOfProjects().get(i));            
            }
        } catch (BusinessException businessException) {
            showAlertDataBaseConnection();
        }
        FIX_TEXT_TO_CELL.fitTextToCell(tableColumnProjectTitle);        
        tableColumnProjectTitle.setCellValueFactory(new PropertyValueFactory("title"));
        tableViewProject.setItems(projectList);
    }
    
    public void hideBlueprint() {
        anchorPaneBlackProject.setVisible(false);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneBlueprint);
        translateTransition1.setByX(1200);
        translateTransition1.play();
    }
    
    public void showBlueprintTable() {    
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), anchorPaneBlueprint);
        translateTransition1.setByX(-1200);
        translateTransition1.play();
    }
    
    public void tableOfBlueprint() {
        hideBlueprint();
        buttonBackBlueprint.setOnMouseClicked(event -> {      
            anchorPaneBlackProject.setVisible(true);
            buttonBackBlueprint.setVisible(false);
            showBlueprintTable();
        });

        buttonMainBlueprint.setOnMouseClicked(event -> {
            hideBlueprint();
            buttonBackBlueprint.setVisible(true);
        });
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
            Logger.getLogger(ControllerConsultResearchProjects.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}