package gui;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * 
 * @author daniCV
 */

public class ControllerDataBaseError implements Initializable {
    
    @FXML
    private JFXButton buttonOkDataBaseError;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML 
    public void exitAddNewProject(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonOkDataBaseError.getScene().getWindow();
        stage.close();       
    }
}