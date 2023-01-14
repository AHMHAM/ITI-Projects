package clienttictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable {

    @FXML
    private Button btn_Back;
    @FXML
    private TextField tf_UserName;
    @FXML
    private PasswordField tx_pass;
    @FXML
    private Button btn_SiginIn;
    @FXML
    private Button btn_SiginUp;
    @FXML
    private Label error_label;
    Thread th;
    String msg;
    String reply;
    ClientSide cs;
    String completeReply;
    StringTokenizer tokenizer;
    private static String userName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ClientSide();

    }

    @FXML
    public void handleSignupButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) {

        if (tf_UserName.getText().isEmpty() || tx_pass.getText().isEmpty()) {
            error_label.setText("Please fill the empty fields");
        } else {
            th = new Thread(() -> {

                try {
                    msg = "login" + ";" + tf_UserName.getText() + ";" + tx_pass.getText();
                    cs.ps.println(msg);
                    completeReply = cs.dis.readLine();
                    tokenizer = new StringTokenizer(completeReply, ";");
                    reply = tokenizer.nextToken();
                    if (reply.equals("0")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                error_label.setText("Wrong user Name or password try to signup");
                            }

                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String name = tf_UserName.getText();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("OnlinePlayerList.fxml"));
                                    Parent root = loader.load();
                                    OnlinePlayerListController onlineList = loader.getController();
                                    onlineList.getUserName(name);
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        });

                    }
                } catch (IOException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            th.run();
        }

    }

    public String getLoginName() {
        return userName;
    }
}
