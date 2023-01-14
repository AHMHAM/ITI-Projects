package clienttictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class DiffecultyLevelScreenController implements Initializable {

    @FXML
    private Button easyBtn;
    @FXML
    private Button midiumBtn;
    @FXML
    private Button hardBtn;
    @FXML
    private Button btn_Back;
    @FXML
    private ToggleButton recordBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleEasyBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Board scr.fxml"));
            Parent root = loader.load();
            BoardScrController board_scr_controller = loader.getController();
            board_scr_controller.intializeLabels("EASY DIFFICULTY", "", "");
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMediumBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Board scr.fxml"));
            Parent root = loader.load();
            BoardScrController board_scr_controller = loader.getController();
            board_scr_controller.intializeLabels("MEDIUM DIFFICULTY", "", "");
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleHardBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Board scr.fxml"));
            Parent root = loader.load();
            BoardScrController board_scr_controller = loader.getController();
            board_scr_controller.intializeLabels("HARD DIFFICULTY", "", "");
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleBackBtnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleRecordBtnAction(ActionEvent event) {
    }

}
