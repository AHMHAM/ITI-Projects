package servertictactoe;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import static servertictactoe.Database.dbConnect;
import static servertictactoe.Database.serverPlayerList;
import static servertictactoe.Database.setOffline;

public class ServerController implements Initializable {

    @FXML
    private Button serverOnOffButton;
    @FXML
    private TableView<Player> tableView;
    @FXML
    private TableColumn<Player, String> tableColumnName;
    @FXML
    private TableColumn<Player, String> tableColumnStatus;
    @FXML
    private TableColumn<Player, Integer> tableColumnScore;
    @FXML
    private TableColumn<Player, Integer> tableColumnNumGames;
    @FXML
    private Circle circleGreen;
    ObservableList playersList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dbConnect();
            playersList = serverPlayerList();
            tableView.setItems(playersList);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnScore.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableColumnNumGames.setCellValueFactory(new PropertyValueFactory<>("numPlayedGames"));
        new ServerSide();
        //serverSide.openSocket();
    }

    public void serverOnOff() {
        if (serverOnOffButton.getText().equals("Server On")) {
            serverOnOffButton.setText("Server Off");
            circleGreen.setVisible(false);
            setOffline();
            //serverSide.closeSocket();
        } else {
            serverOnOffButton.setText("Server On");
            circleGreen.setVisible(true);
            //serverSide.openSocket();
        }
        onRefresh();
    }

    public void onRefresh() {
        Platform.runLater(() -> {
            try {
                ObservableList players = Database.serverPlayerList();
                tableView.setItems(players);
                tableColumnName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
                tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
