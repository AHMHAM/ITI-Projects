package clienttictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameRecordsController implements Initializable {

    @FXML
    private Button game_recors_back_btn;
    @FXML
    private TextField date_txt_1;
    @FXML
    private Button replay_btn_1;
    @FXML
    private TextField date_txt_2;
    @FXML
    private TextField date_txt_3;
    @FXML
    private TextField date_txt_5;
    @FXML
    private TextField date_txt_4;
    @FXML
    private TextField date_txt_7;
    @FXML
    private TextField date_txt_6;
    @FXML
    private Button replay_btn_4;
    @FXML
    private Button replay_btn_3;
    @FXML
    private Button replay_btn_2;
    @FXML
    private Button replay_btn_5;
    @FXML
    private Button replay_btn_6;
    @FXML
    private Button replay_btn_7;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handlReplayButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Board scr.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlGameRecorsBackBtnAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleReplayButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Board scr.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
