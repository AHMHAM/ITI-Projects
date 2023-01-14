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
import javafx.stage.Stage;

public class HomePageScreenController implements Initializable {

    @FXML
    private Button singlePlayerBTN;
    @FXML
    private Button twoPlayersBTN;
    @FXML
    private Button playOnlineBTN;
    @FXML
    private Button gameRecordBTN;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSinglePlayerBtnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DiffecultyLevelScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleTwoPlayersBtnAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ChosingPlayersNames.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handlePlayOnlineBtnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePageScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleGameRecordBtnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameRecords.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
