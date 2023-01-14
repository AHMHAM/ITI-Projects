package clienttictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class SignUpController implements Initializable {

    @FXML
    private Button btn_Back;
    @FXML
    private TextField tf_UserNmae;
    @FXML
    private TextField tf_Email;
    @FXML
    private PasswordField tf_Password;
    @FXML
    private Button btn_SiginUp;
    @FXML
    private Label error_label;

    Thread th;
    String msg;
    String reply;
    ClientSide cs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cs = new ClientSide();
    }

    @FXML
    public void handleSignupButtonAction(ActionEvent event) throws Exception {

        if (tf_Email.getText().isEmpty() || tf_UserNmae.getText().isEmpty() || tf_Password.getText().isEmpty()) {
            error_label.setText("Please fill the empty feilds");
        } else {

            th = new Thread(() -> {
                try {
                    msg = "signup" + ";" + tf_Email.getText() + ";" + tf_UserNmae.getText() + ";" + tf_Password.getText();
                    cs.ps.println(msg);
                    reply = cs.dis.readLine();
                    if (reply.equals("0")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                error_label.setText("This user name already exists,please try another one");

                            }

                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });

                    }

                } catch (IOException ex) {
                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            th.start();
        }
    }

    @FXML
    public void handlBackButtonAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void init() {

    }

}
