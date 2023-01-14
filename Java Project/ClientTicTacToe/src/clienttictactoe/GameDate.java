package clienttictactoe;

import java.time.LocalDateTime;

public class GameDate {

    private String game_date;

    public GameDate() {
        LocalDateTime now = LocalDateTime.now();
        String game_date = now.toString();
        String date = (String) game_date.toString().subSequence(0, 16);
        String rec_date = date.replaceAll(":", "-");
        this.game_date = rec_date;
    }

    public String getGame_date() {
        return game_date;
    }

    public void setGame_date() {
        LocalDateTime now = LocalDateTime.now();
        String game_date = now.toString();
        String date = (String) game_date.toString().subSequence(0, 16);
        String reac_date = date.replaceAll(":", "-");
    }
}
