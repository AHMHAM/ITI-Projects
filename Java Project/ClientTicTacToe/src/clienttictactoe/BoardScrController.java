package clienttictactoe;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardScrController implements Initializable {

    @FXML
    private Label playerX;
    @FXML
    private Label playerO;
    @FXML
    private Label title;
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
    private Button playAgain;
    @FXML
    private Button back_btn_board_sc;
    @FXML
    private MediaView videoPlayer;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File mediaFile;
    boolean isVideoStart = false;
    private String textOfBtn = "X";
    private Button targetedBtn;
    private static int firstPlayerScore = 0;
    private static int secondPlayerScore = 0;
    private int numberOfClickedBoxes = 0;
    private String winnerName;
    private String ai = "X";
    private String user = "O";
    private String gameType;
    private boolean justStarted = true;
    private Button[] board = new Button[9];
    private Button aiBtn;
    private String loggedUserName;

    private enum GameStatus {
        TERMINATED, PLAYING, WINFIRSTPLAYER, WINSECONDPLAYER, DRAW;
    }

    private enum GameType {
        EASY, MEDIUM, HARD, TWOPLAYERSLOCAL, MULTIPLAYER;
    }

    GameStatus currentStatus;
    GameType currentGameType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentStatus = GameStatus.PLAYING;
        highlightTheLabel('o');
        board[0] = box1;
        board[1] = box2;
        board[2] = box3;
        board[3] = box4;
        board[4] = box5;
        board[5] = box6;
        board[6] = box7;
        board[7] = box8;
        board[8] = box9;
    }

    @FXML
    private void notifyPressing(ActionEvent event) {
        // Sets difficulty or game type after first button press
        if (justStarted == true) {
            gameType = title.getText();
            switch (gameType) {
                case "EASY DIFFICULTY":
                    currentGameType = GameType.EASY;
                    break;
                case "MEDIUM DIFFICULTY":
                    currentGameType = GameType.MEDIUM;
                    break;
                case "HARD DIFFICULTY":
                    currentGameType = GameType.HARD;
                    break;
                case "TWOPLAYERS":
                    currentGameType = GameType.TWOPLAYERSLOCAL;
                    break;
                case "MULTIPLAYER":
                    currentGameType = GameType.MULTIPLAYER;
                    break;
                default:
                    break;
            }
            justStarted = false;
        }

        switch (currentGameType) {
            case EASY:
                handleEasyModeOnPressed(event);
                break;
            case MEDIUM:
                handleMediumModeOnPressed(event);
                break;
            case HARD:

                break;
            case TWOPLAYERSLOCAL:
                handleThePressedBtnTwoPlayersMood(event);
                break;
            case MULTIPLAYER:
                break;
            default:
                break;
        }
        System.out.println(currentStatus);
    }

    @FXML
    private void handlePlayAgainBtn(ActionEvent event) {
        setStandrdStart();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        currentStatus = GameStatus.TERMINATED;
        Parent root = FXMLLoader.load(getClass().getResource("HomePageScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleThePressedBtnTwoPlayersMood(ActionEvent e) {
        if (currentStatus == GameStatus.PLAYING) {
            targetedBtn = (Button) e.getSource();
            if (targetedBtn.getText().equals("")) {
                if (textOfBtn == "X") {
                    targetedBtn.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #1F3274; ");
                    textOfBtn = "O";
                    highlightTheLabel('x');
                } else {
                    targetedBtn.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
                    textOfBtn = "X";
                    highlightTheLabel('o');
                }
                targetedBtn.setText(textOfBtn);
                numberOfClickedBoxes++;
                reviewTheBoard();
            }
        }
    }

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
        if (numberOfClickedBoxes >= 9) {
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

    public void handleWinningSomeOne() {
        if (currentStatus == GameStatus.WINFIRSTPLAYER) {
            firstPlayerScore += 10;
            highlightTheLabel('x');
        } else if (currentStatus == GameStatus.WINSECONDPLAYER) {
            secondPlayerScore += 10;
            highlightTheLabel('o');
        }
        playVideo();
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
        textOfBtn = "X";
        numberOfClickedBoxes = 0;
        highlightTheLabel('o');
        removeVideo();
    }

    public boolean notifiyWining() {

        if (isAnyColumnNotifyWinningSomeone() == true || isAnyDiagonalNotifyWinningSomeone() == true || isAnyRowNotifyWinningSomeone() == true) {
            return true;
        }

        return false;
    }

    public void intializeLabels(String titles, String playerxName, String playeroName) {
        title.setText(titles);
        if (titles.contains("DIFFICULTY")) {
            playerX.setText("Player O\n" + "You");
            playerO.setText("Player X\n" + "Computer");
        } else if (titles.equals("TWOPLAYERS")) {
            playerX.setText("Player X\n" + playerxName);
            playerO.setText("Player O\n" + playeroName);
        } else if (titles.equals("MULTIPLAYER")) {
        }

        playerX.setTextFill(javafx.scene.paint.Color.valueOf("#ff4948"));
        playerO.setTextFill(javafx.scene.paint.Color.valueOf("#ff4948"));
        playerX.setFont(new Font("Bookman Old Style", 35.0));
        playerO.setFont(new Font("Bookman Old Style", 35.0));
    }

    public void playVideo() {
        if (currentStatus == GameStatus.WINFIRSTPLAYER) {
            mediaFile = new File("src/clienttictactoe/Fireworks.mp4");
        } else {
            mediaFile = new File("src/clienttictactoe/Fireworks.mp4");
        }
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(BoardScrController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mediaPlayer = new MediaPlayer(media);
        videoPlayer.setMediaPlayer(mediaPlayer);
        //mediaPlayer.setMute(true);
        videoPlayer.setVisible(true);
        videoPlayer.setFitHeight(600);
        videoPlayer.setFitWidth(1080);
        mediaPlayer.play();
        isVideoStart = true;
    }

    public void removeVideo() {
        // On mouse click
        if (isVideoStart == true) {
            mediaPlayer.stop();
            videoPlayer.setVisible(false);
            isVideoStart = false;
        }
    }

    private Button easyMode() {
        Random random = new Random();
        int index = 0;
        if (!isBoardFull() && !notifiyWining() && !isBoardEmpty()) {
            do {

                index = random.nextInt(9);

            } while (!board[index].getText().equals(""));
        }
        return board[index];
    }

    public void handleEasyModeOnPressed(ActionEvent e) {
        if (currentStatus == GameStatus.PLAYING) {
            targetedBtn = (Button) e.getSource();
            if (targetedBtn.getText().equals("")) {
                targetedBtn.setText(user);
                targetedBtn.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
                highlightTheLabel('o');
                numberOfClickedBoxes++;
                reviewTheBoard();
                if (!isBoardFull() && !notifiyWining() && !isBoardEmpty()) {
                    aiBtn = easyMode();
                    aiBtn.setText(ai);
                    aiBtn.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #1F3274; ");
                    highlightTheLabel('x');
                    numberOfClickedBoxes++;
                    reviewTheBoard();
                }
            }
        }
    }

    private boolean isBoardEmpty() {
        boolean flag = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i].getText().equals(ai) || board[i].getText().equals(user)) {
                flag = false;
            }

        }
        return false;
    }

    private boolean isUserWillWin(Button b1, Button b2, Button b3) {
        boolean flag = false;
        if (b1.getText().equals(b2.getText()) && b1.getText().equals(user) && b3.getText().equals("")) {
            flag = true;
        } else if (b2.getText().equals(b3.getText()) && b2.getText().equals(user) && b1.getText().equals("")) {
            flag = true;
        } else if (b1.getText().equals(b3.getText()) && b1.getText().equals(user) && b2.getText().equals("")) {
            flag = true;
        }
        return flag;
    }

    private Button preventUserWining(Button b1, Button b2, Button b3) {
        Button temp = new Button();
        if (b1.getText().equals(b2.getText()) && b3.getText().equals("")) {
            temp = b3;
        } else if (b2.getText().equals(b3.getText()) && b1.getText().equals("")) {
            temp = b1;
        } else if (b1.getText().equals(b3.getText()) && b2.getText().equals("")) {
            temp = b2;
        }
        return temp;

    }

    private Button mediumMode() {
        Button medium = new Button();
        if (isUserWillWin(board[0], board[1], board[2])) {
            medium = preventUserWining(board[0], board[1], board[2]);
        } else if (isUserWillWin(board[3], board[4], board[5])) {
            medium = preventUserWining(board[3], board[4], board[5]);
        } else if (isUserWillWin(board[6], board[7], board[8])) {
            medium = preventUserWining(board[6], board[7], board[8]);
        } else if (isUserWillWin(board[0], board[3], board[6])) {
            medium = preventUserWining(board[0], board[3], board[6]);
        } else if (isUserWillWin(board[1], board[1], board[7])) {
            medium = preventUserWining(board[1], board[1], board[7]);
        } else if (isUserWillWin(board[2], board[5], board[8])) {
            medium = preventUserWining(board[2], board[5], board[8]);
        } else if (isUserWillWin(board[0], board[4], board[8])) {
            medium = preventUserWining(board[0], board[4], board[8]);
        } else if (isUserWillWin(board[2], board[4], board[6])) {
            medium = preventUserWining(board[2], board[4], board[6]);
        } else {
            medium = easyMode();
        }
        return medium;
    }

    public void handleMediumModeOnPressed(ActionEvent e) {
        if (currentStatus == GameStatus.PLAYING) {
            targetedBtn = (Button) e.getSource();
            if (targetedBtn.getText().equals("")) {
                targetedBtn.setText(user);
                targetedBtn.setStyle("-fx-text-fill: #febd08;-fx-background-color: #1F3274; ");
                highlightTheLabel('o');
                numberOfClickedBoxes++;
                reviewTheBoard();
                if (!isBoardFull() && !notifiyWining() && !isBoardEmpty()) {
                    aiBtn = mediumMode();
                    aiBtn.setText(ai);
                    aiBtn.setStyle("-fx-text-fill: #ff4948;-fx-background-color: #1F3274; ");
                    highlightTheLabel('x');
                    numberOfClickedBoxes++;
                    reviewTheBoard();
                }
            }
        }
    }

    public void getLoginName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

}
