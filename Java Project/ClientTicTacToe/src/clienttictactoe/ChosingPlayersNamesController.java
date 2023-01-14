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

public class ChosingPlayersNamesController implements Initializable {

    @FXML
    private Button choose_names_back_btn_;
    @FXML
    private TextField player_o_name;
    @FXML
    private TextField player_x_name;
    @FXML
    private Button play_btn;
    @FXML
    private Button btn_Back1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlePlayButtonAction(ActionEvent event) throws IOException {

        String playeroName = player_o_name.getText();
        String playerxName = player_x_name.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Board scr.fxml"));
        Parent root = loader.load();
        BoardScrController board_scr_controller = loader.getController();
        board_scr_controller.intializeLabels("TWOPLAYERS", playerxName, playeroName);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
