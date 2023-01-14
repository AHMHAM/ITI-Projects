package clienttictactoe;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    private enum GameStatus {
        TERMINATED, PLAYING, WINFIRSTPLAYER, WINSECONDPLAYER, DRAW;
    }

    private enum GameType {
        EASY, MEDIUM, HARD, TWOPLAYERSLOCAL, MULTIPLAYER;
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        updateUIThread.stop();
        currentStatus = FXMLDocumentController.GameStatus.TERMINATED;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void removeVideo(MouseEvent event) {
    }
    private Label label;
    @FXML
    private Label title;
    @FXML
    private Label playerX;
    @FXML
    private Label playerO;
    @FXML
    private Button box1;
    @FXML
    private Button box9;
    @FXML
    private Button box8;
    @FXML
    private Button box7;
    @FXML
    private Button box6;
    @FXML
    private Button box5;
    @FXML
    private Button box4;
    @FXML
    private Button box3;
    @FXML
    private Button box2;
    @FXML
    private Button back_btn_board_sc;
    @FXML
    private MediaView videoPlayer;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File mediaFile;
    private Button targetedBtn;
    private Character targetedBtnIndex;
    private Boolean myTurn = true;
    private Character textOfBtn = 'X';
    private int numberOfClickedBoxes = 0;
    private int firstPlayerScore;
    private int secondPlayerScore;
    private Socket mySocket;
    private PrintStream ps;
    private DataInputStream dis;
    private String receivedMsg;
    private String sendedMsg;
    private Thread updateUIThread;
    GameStatus currentStatus;
    GameType currentGameType;
    private String senderRequistName;
    private String opponentName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init called");
        setStandrdStart();
        targetedBtn = new Button();
        receivedMsg = "";
        senderRequistName = "Youssef";
        opponentName = "Ahmed";
        playerX.setText(senderRequistName);
        playerO.setText(opponentName);
        currentStatus = GameStatus.PLAYING;
        try {
            mySocket = new Socket("127.0.0.1", 5009);
            ps = new PrintStream(mySocket.getOutputStream());
            dis = new DataInputStream(mySocket.getInputStream());

            updateUIThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            receivedMsg = dis.readLine() + "\n";
                            targetedBtnIndex = receivedMsg.charAt(0);
                            textOfBtn = receivedMsg.charAt(1);
                            System.out.println("the data reched" + receivedMsg);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    setTxtToAcertainButton(targetedBtnIndex, textOfBtn);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            updateUIThread.start();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void notifyPressing(ActionEvent event) {
        handleThePressedBtnOnlineMood(event);
    }

    public void handleThePressedBtnOnlineMood(ActionEvent e) {
        sendedMsg = "";
        targetedBtn = (Button) e.getSource();
        targetedBtnIndex = targetedBtn.getId().charAt(3);
        if (currentStatus == GameStatus.PLAYING && myTurn) {
            myTurn = false;
            if (targetedBtn.getText().equals("")) {
                if (textOfBtn == 'X') {
                    textOfBtn = 'O';
                    highlightTheLabel('x');
                } else {
                    textOfBtn = 'X';
                    highlightTheLabel('o');
                }
                targetedBtn.setText("" + textOfBtn);
                sendedMsg = "" + "move" + ";" + opponentName + ";" + targetedBtnIndex + textOfBtn;
                ps.println(sendedMsg);
                reviewTheBoard();
            }
        }
    }

    public void setTxtToAcertainButton(char indexOfBtn, char textOfBtn) {
        if (currentStatus == GameStatus.PLAYING) {
            myTurn = true;
            switch (targetedBtnIndex) {
                case '1':
                    targetedBtn = box1;
                    break;
                case '2':
                    targetedBtn = box2;
                    break;
                case '3':
                    targetedBtn = box3;
                    break;
                case '4':
                    targetedBtn = box4;
                    break;
                case '5':
                    targetedBtn = box5;
                    break;
                case '6':
                    targetedBtn = box6;
                    break;
                case '7':
                    targetedBtn = box7;
                    break;
                case '8':
                    targetedBtn = box8;
                    break;
                case '9':
                    targetedBtn = box9;
                    break;
                default:
                    System.out.println(textOfBtn);

            }
            if (targetedBtn.getText().equals("")) {
                if (textOfBtn == 'X') {
                    highlightTheLabel('x');
                } else {
                    highlightTheLabel('o');
                }
                targetedBtn.setText("" + textOfBtn);
                reviewTheBoard();
            }
        }
    }

    // JUST THE UTILITY OF PLAYING THE GAME
    private boolean isAnyRowNotifyWinningSomeone() {
        boolean flag = false;
        if (box1.getText().equals(box2.getText()) && box2.getText().equals(box3.getText())
                && !box1.getText().equals("")) {
            highlightThusBtns(box1, box2, box3);
            if (box1.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        } else if (box4.getText().equals(box5.getText()) && box5.getText().equals(box6.getText())
                && !box4.getText().equals("")) {
            highlightThusBtns(box4, box5, box6);
            if (box4.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        } else if (box7.getText().equals(box8.getText()) && box8.getText().equals(box9.getText())
                && !box7.getText().equals("")) {
            highlightThusBtns(box7, box8, box9);
            if (box7.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        }
        return flag;
    }

    private boolean isAnyColumnNotifyWinningSomeone() {
        boolean flag = false;
        if (box1.getText().equals(box4.getText()) && box4.getText().equals(box7.getText())
                && !box1.getText().equals("")) {
            highlightThusBtns(box1, box4, box7);
            if (box1.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        } else if (box2.getText().equals(box5.getText()) && box5.getText().equals(box8.getText())
                && !box2.getText().equals("")) {
            highlightThusBtns(box2, box5, box8);
            if (box2.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        } else if (box3.getText().equals(box6.getText()) && box6.getText().equals(box9.getText())
                && !box3.getText().equals("")) {
            highlightThusBtns(box3, box6, box9);
            if (box3.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        }
        return flag;
    }

    private boolean isAnyDiagonalNotifyWinningSomeone() {
        boolean flag = false;
        if (box1.getText().equals(box5.getText()) && box5.getText().equals(box9.getText())
                && !box1.getText().equals("")) {
            highlightThusBtns(box1, box5, box9);
            if (box1.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        } else if (box3.getText().equals(box5.getText()) && box5.getText().equals(box7.getText())
                && !box3.getText().equals("")) {
            highlightThusBtns(box3, box5, box7);
            if (box3.getText().equals("X")) {
                currentStatus = GameStatus.WINFIRSTPLAYER;
            } else {
                currentStatus = GameStatus.WINSECONDPLAYER;
            }
            flag = true;
        }
        return flag;
    }

    private boolean isBoardFull() {
        boolean flag = false;
        if (!box1.getText().equals("") && !box2.getText().equals("") && !box3.getText().equals("")
                && !box4.getText().equals("") && !box5.getText().equals("") && !box6.getText().equals("")
                && !box7.getText().equals("") && !box8.getText().equals("") && !box6.getText().equals("")) {
            flag = true;
        }
        return flag;
    }

    private void reviewTheBoard() {
        if (isAnyRowNotifyWinningSomeone() | isAnyColumnNotifyWinningSomeone()
                | isAnyDiagonalNotifyWinningSomeone()) {
            handleWinningSomeOne();
        } else if (isBoardFull()) {
            handleNoOneWin();
        }
    }

    private void highlightThusBtns(Button b1, Button b2, Button b3) {
        if (b1.getText().equals("X")) {
            b1.setStyle("-fx-text-fill: #febd08;-fx-background-color: #999b99; ");
            b2.setStyle("-fx-text-fill: #febd08;-fx-background-color: #999b99; ");
            b3.setStyle("-fx-text-fill: #febd08;-fx-background-color: #999b99; ");
        } else {
            b1.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #999b99; ");
            b2.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #999b99; ");
            b3.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #999b99; ");
        }
    }

//    public void handleWinningSomeOne() {
//        if (currentStatus == GameStatus.WINFIRSTPLAYER) {
//            firstPlayerScore += 10;
//            highlightTheLabel('x');
//        } else if (currentStatus == GameStatus.WINSECONDPLAYER) {
//            secondPlayerScore += 10;
//            highlightTheLabel('o');
//        }
//   //     playVideo();
//    }
    public void handleWinningSomeOne() {
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("Congratulation");
        if (currentStatus == GameStatus.WINFIRSTPLAYER) {
            firstPlayerScore += 10;
            highlightTheLabel('x');
            a.setContentText("The player  " + senderRequistName + "wines the game");
        } else if (currentStatus == GameStatus.WINSECONDPLAYER) {
            secondPlayerScore += 10;
            a.setContentText("The player  " + opponentName + "wines the game");
            highlightTheLabel('o');
        }
        //    playVideo();
        a.getDialogPane().getButtonTypes().add(ok);
        a.showAndWait();
        setStandrdStart();
    }

    public void handleNoOneWin() {
        playerX.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
        playerO.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle("DRAW..!");
        a.setContentText("The game finished without anyone winning");
        a.getDialogPane().getButtonTypes().add(ok);
        a.showAndWait();
        setStandrdStart();
    }

    private void highlightTheLabel(char ch) {
        if (ch == 'x' || ch == 'X') {
            playerO.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #1F3274; ");
            playerX.setStyle("-fx-text-fill: #febd08;-fx-background-color: #999b99; ");
        } else if (ch == 'o' || ch == 'O') {
            playerX.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
            playerO.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #999b99; ");
        }
    }

    private void setStandrdStart() {
        currentStatus = GameStatus.PLAYING;
        box1.setText("");
        box1.setStyle("-fx-background-color: #1F3274; ");
        box2.setText("");
        box2.setStyle("-fx-background-color: #1F3274; ");
        box3.setText("");
        box3.setStyle("-fx-background-color: #1F3274; ");
        box4.setText("");
        box4.setStyle("-fx-background-color: #1F3274; ");
        box5.setText("");
        box5.setStyle("-fx-background-color: #1F3274; ");
        box6.setText("");
        box6.setStyle("-fx-background-color: #1F3274; ");
        box7.setText("");
        box7.setStyle("-fx-background-color: #1F3274; ");
        box8.setText("");
        box8.setStyle("-fx-background-color: #1F3274; ");
        box9.setText("");
        box9.setStyle("-fx-background-color: #1F3274; ");
        textOfBtn = 'X';
        numberOfClickedBoxes = 0;
        highlightTheLabel('o');
        // removeVideo();
    }

//    public void playVideo() {
//        if (currentStatus == GameStatus.WINFIRSTPLAYER) {
//            mediaFile = new File("src/clienttictactoe/Fireworks.mp4");
//        } else {
//            mediaFile = new File("src/clienttictactoe/Fireworks.mp4");
//        }
//        try {
//            media = new Media(mediaFile.toURI().toURL().toString());
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        mediaPlayer = new MediaPlayer(media);
//        videoPlayer.setMediaPlayer(mediaPlayer);
//        //mediaPlayer.setMute(true);
//        videoPlayer.setVisible(true);
//        videoPlayer.setFitHeight(600);
//        videoPlayer.setFitWidth(1080);
//        mediaPlayer.play();
//    }
//
//    public void removeVideo() {
//        //On mouse click
//        mediaPlayer.stop();
//        videoPlayer.setVisible(false);
//    }
}
