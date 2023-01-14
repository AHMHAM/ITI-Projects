package clienttictactoe;

public class Game {

    private String player_o_name;
    private String player_x_name;
    private String game_date;
    private String game_status;
    private String game_type;

    public Game(String player_o_name, String player_x_name, String game_date, String game_status, String game_type) {
        this.player_o_name = player_o_name;
        this.player_x_name = player_x_name;
        this.game_date = game_date;
        this.game_status = game_status;
        this.game_type = game_type;
    }

    public String getPlayer_o_name() {
        return player_o_name;
    }

    public String getPlayer_x_name() {
        return player_x_name;
    }

    public String getGame_date() {
        return game_date;
    }

    public String getGame_status() {
        return game_status;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setPlayer_o_name(String player_o_name) {
        this.player_o_name = player_o_name;
    }

    public void setPlayer_x_name(String player_x_name) {
        this.player_x_name = player_x_name;
    }

    public void setGame_date(String game_date) {
        this.game_date = game_date;
    }

    public void setGame_status(String game_status) {
        this.game_status = game_status;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

}
