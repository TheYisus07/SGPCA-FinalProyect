package gui;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import domain.Member;
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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Javier Blas
 */
public class ControllerConsultMemberList implements Initializable {
    
    private final MemberDAO MEMBER_DAO = new MemberDAO();
        
    @FXML
    private TableView<Member> tableViewConsultMembers;
    
    @FXML
    private TableColumn<Member,String> columnNameMember;
            
    @FXML
    private TableColumn<Member,String> columnPositionMember;
    
    @FXML
    private TableColumn<Member,String> columnDeleteMember;
    
    @FXML
    private Button buttonExitConsultMemberList;
    
    @FXML 
    private TextField textFieldFilterNameMember;
    
    @FXML
    private Label labelLoggedInUser;
    
    @FXML
    private Label labelLoggedInUserEmail;
    
    @FXML 
    private AnchorPane anchorPaneBlackAuthenticate;
    
    @FXML
    private AnchorPane anchorPaneAuthenticate;
    
    @FXML 
    private JFXPasswordField passwordFieldPassword;
    
    @FXML
    private JFXButton buttonAuthenticateContinue;
    
    @FXML 
    private JFXButton buttonAuthenticateCancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showConsultMemberGui();
        showMemberList();
    }   
    
    public void getMemberFullName(String memberFullName, String memberEmail) {
        labelLoggedInUser.setText(memberFullName);
        labelLoggedInUserEmail.setText(memberEmail);
    }
    /**
     * 
     * @param event 
     */
    @FXML
    public void filterName(KeyEvent event) throws BusinessException {
        ObservableList<Member> filterMembers = FXCollections.observableArrayList();
        
        String filterName = textFieldFilterNameMember.getText();
        
        try {
            if (!filterName.isEmpty()) {
            filterMembers.clear();
            compareMemberName(MEMBER_DAO, filterMembers, filterName);
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
    }
    
    public void compareMemberName(MemberDAO MEMBER_DAO, ObservableList<Member> filterMembers, String filterName) throws BusinessException {     
        try {
            for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {                
                String lowerCaseName = MEMBER_DAO.consultMemberList().get(i).getFullName().toLowerCase();
                if (lowerCaseName.contains(filterName.toLowerCase())){
                    filterMembers.add(MEMBER_DAO.consultMemberList().get(i));
                }
            }
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }  
        tableViewConsultMembers.setItems(filterMembers);
    }
    
    @FXML
    public void openRegisterMemberGui(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLRegisterMember.fxml"));
            fXMLLoader.load();            
            ControllerRegisterMember controllerRegisterMember = fXMLLoader.getController();
            controllerRegisterMember.getMemberFullName(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Parent root = fXMLLoader.getRoot();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene); 
            stage.setResizable(false);      
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMemberList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showMemberList() { 
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        loadMember(memberList);    
        try {
            for (int i = 0; i < MEMBER_DAO.consultMemberList().size(); i++) {
                memberList.add(MEMBER_DAO.consultMemberList().get(i));
            }  
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }     
        tableViewConsultMembers.setItems(memberList);   
        tableViewConsultMembers.refresh();
    }
    
    public void loadMember(ObservableList<Member> memberList) {
        
        this.columnNameMember.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.columnPositionMember.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFoctory = (TableColumn<Member, String> param) -> {
            final TableCell<Member, String> cell = new TableCell<Member, String>() {
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
                            int deleteLine = tableViewConsultMembers.getSelectionModel().getSelectedIndex();
                            showGuiAuthenticatePassword();                                                     
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
        columnDeleteMember.setCellFactory(cellFoctory);
    }
    
    public void showGuiMemberConfirmationRemoved() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("El miembro ha sido eliminado");        
        alert.setHeaderText("Su información ya no se encontrará disponible");  
        alert.show();
        passwordFieldPassword.setText("");
        passwordFieldPassword.resetValidation();
        anchorPaneBlackAuthenticate.setVisible(false);
        anchorPaneAuthenticate.setVisible(false);
    }
    
    public void showGuiVerifyPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contraseña erronea");        
        alert.setHeaderText("Verifique su constraseña");  
        alert.show();
    }

    public void showGuiAuthenticatePassword() {
        anchorPaneBlackAuthenticate.setVisible(true);
        anchorPaneAuthenticate.setVisible(true);
    }   
        
    @FXML
    public void authenticatePassword(ActionEvent actionEvent) {   
        int positionOfList = tableViewConsultMembers.getSelectionModel().getSelectedIndex();
        Member member = tableViewConsultMembers.getSelectionModel().getSelectedItem();
        String passwordMember = null;
        if (passwordFieldPassword.validate()) {
            passwordMember = passwordFieldPassword.getText();
        }
        Member memberLoggin = null;
        String decryptedPassword = "";
        try {
            memberLoggin = MEMBER_DAO.consultMember(labelLoggedInUser.getText());
            decryptedPassword = MEMBER_DAO.decryptMemberPassword(memberLoggin.getPassword());
        } catch (BusinessException ex) {
            showAlertDataBaseConnection();
        }
        
        if (decryptedPassword == null) {
            if (memberLoggin.getCurp().equals(passwordMember)) {
                try {
                    MEMBER_DAO.removeMember(member.getFullName());  
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }              
                showMemberList();
                tableViewConsultMembers.refresh();
                showGuiMemberConfirmationRemoved();                
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        } else if (passwordMember != null) {
            if (passwordMember.equals(decryptedPassword)) {
                try {
                    MEMBER_DAO.removeMember(member.getFullName()); 
                } catch (BusinessException ex) {
                    showAlertDataBaseConnection();
                }   
                showMemberList();
                tableViewConsultMembers.refresh();
                showGuiMemberConfirmationRemoved();
            } else {
                passwordFieldPassword.setText(null);
                showGuiVerifyPassword();
            }
        }
    }
    
    @FXML
    public void cancelAuthenticationToDeleteMember(ActionEvent event) {
        anchorPaneBlackAuthenticate.setVisible(false);
        anchorPaneAuthenticate.setVisible(false);
        passwordFieldPassword.setText("");
        passwordFieldPassword.resetValidation();
    }
    
    public void showConsultMemberGui() {     
        tableViewConsultMembers.setOnMousePressed((MouseEvent event) -> {
        String memberFullName = labelLoggedInUser.getText();
        String memberPosition = "";
            try {
                memberPosition = MEMBER_DAO.consultMember(memberFullName).getPosition(); 
                if (memberPosition.equals("Responsable")) {
                   showConsultMember(event);
                } else {
                   showGuiInvalidPermissions();
                } 
            } catch (BusinessException ex) {
                showAlertDataBaseConnection();
            }             
           
        });
    }
    
    public void showConsultMember(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            try {
                Stage stage =  (Stage) tableViewConsultMembers.getScene().getWindow();
                stage.hide();
                FXMLLoader fXMLLoader = new FXMLLoader();
                fXMLLoader.setLocation(getClass().getResource("FXMLConsultMember.fxml"));
                fXMLLoader.load();
                ControllerConsultMember controllerConsultMember = fXMLLoader.getController();
                String memberName = tableViewConsultMembers.getSelectionModel().getSelectedItem().getFullName();                    
                controllerConsultMember.getMemberFullName(labelLoggedInUser.getText(),labelLoggedInUserEmail.getText());
                controllerConsultMember.getMemberSelected(memberName);
                Parent root = fXMLLoader.getRoot();
                Stage stageConsultMember = new Stage();
                Scene scene = new Scene(root);
                stageConsultMember.setScene(scene);
                stageConsultMember.show();
            } catch (IOException ex) {
                Logger.getLogger(ControllerConsultMemberList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    } 
     
    public void showGuiInvalidPermissions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Permiso denegado");        
        alert.setHeaderText("Necesitas ser Responsable del CA para administrar a los miembros");
        alert.setContentText("Si deseas consultar detalles de tu curriculum personal dirigite al menú principal");
        alert.show();
    }
    
    @FXML
    public void exitFromConsultMemberList(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida de la consulta de lista de miembros");        
        alert.setHeaderText("¿Desea salir de la lista de miembros del cuerpo académico?");
        Optional<ButtonType> buttonOk = alert.showAndWait();
        if (buttonOk.get() == ButtonType.OK){
            showGuiGeneralCurriculum(labelLoggedInUser.getText(), labelLoggedInUserEmail.getText());
            Stage stage = (Stage) buttonExitConsultMemberList.getScene().getWindow();
            stage.close();
        }
    }
    
    public void showGuiGeneralCurriculum(String memberFullName, String memberEmail) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.setLocation(getClass().getResource("FXMLConsultGeneralCurriculum.fxml"));
            fXMLLoader.load();
            ControllerConsultGeneralCurriculum controllerConsultGeneralCurriculum = fXMLLoader.getController();
            controllerConsultGeneralCurriculum.getMemberFullName(memberFullName, memberEmail);
            Parent root = fXMLLoader.getRoot();
            Stage stage = new Stage();                 
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Gestor de Productividad del Cuerpo Académico");
            stage.getIcons().add(new Image("/images/sgpca.png"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControllerConsultMemberList.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControllerAddBlueprint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

