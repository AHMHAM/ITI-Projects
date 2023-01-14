package servertictactoe;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static servertictactoe.Database.con;

public class PlayerHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    private String userName;
    private String passWord;
    private String email;
    private String status;
    Socket socket;
    private int totalScore;
    private int numPlayedGames;
    static Vector<PlayerHandler> players = new Vector<PlayerHandler>();

    //Latest
    public PlayerHandler(Socket cs) {
        try {

            socket = cs;
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());

            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        String onlinePlayers;
        try {
            onlinePlayers = readyingStringforOnlineList();
            ps.println(onlinePlayers);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            try {
                String completeMsg = dis.readLine();
                System.out.println(completeMsg);
                StringTokenizer tokenizer = new StringTokenizer(completeMsg, ";");
                String header = tokenizer.nextToken();
                switch (header) {
                    case "request":

                        break;
                    case "login":
                        String nameL = tokenizer.nextToken();
                        String pswL = tokenizer.nextToken();
                        System.out.println(nameL);
                        System.out.println(pswL);
                        int result = 0;
                        try {
                            result = DataAccessLayer.logIn(nameL, pswL);
                            if (result == 1) {
                                userName = nameL;
                                passWord = pswL;
                                ps.println("1;" + userName);
                                players.add(this);
                            } else {
                                ps.println("0;" + nameL);
                            }
                        } catch (SQLException ex) {
                            socket.close();
                            ex.printStackTrace();
                        }
                        System.out.println(result);
                        break;
                    case "signup":
                        String mail = tokenizer.nextToken();
                        String nameS = tokenizer.nextToken();
                        String pswS = tokenizer.nextToken();
                        int resultS = -1;
                        try {
                            resultS = DataAccessLayer.signUp(mail, nameS, pswS);
                            if (resultS == 1) {
                                ps.println("1");
                                socket.close();

                            } else {

                                socket.close();
                            }

                        } catch (SQLException ex) {
                            ps.println("0");
                        }

                    case "logout":
                        String nameLO = tokenizer.nextToken();
                        int logoutResult = 0;
                        logoutResult = DataAccessLayer.logout(nameLO);
                        System.out.println(logoutResult);
                        if (logoutResult == 1) {
                            ps.println("1");
                            socket.close();
                        } else {
                            ps.println("0");
                            socket.close();
                        }
                        System.out.println(logoutResult);
                        break;
                    case "move":
                        String opponentName = tokenizer.nextToken();
                        System.out.println("opponent name:" + opponentName);
                        String msgContent = tokenizer.nextToken();
                        System.out.println("opponent name:" + msgContent);
                        moveHandling(opponentName, msgContent);
                        break;
                    /*case "onlinePlayers":
                        String onlinePlayers = readyingStringforOnlineList();
                        ps.println(onlinePlayers);*/

                    default:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void sendRequest(String sender, String reciever) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).userName.equals(reciever) && players.get(i).status.equals("online")) {

                players.get(i).ps.println("request;" + sender + reciever);
            }
        }
    }

    private void moveHandling(String opponentName, String moveContent) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).userName.equals(opponentName) && players.get(i).status.equals("PLAYING")) {
                players.get(i).ps.println(moveContent);
            }
        }
    }

    public static String readyingStringforOnlineList() throws SQLException {
        Statement stmt = con.createStatement();
        String onlinePlayers = "onlinePlayers;";
        String queryString = "select * from Player where status in ('Online', 'online')";
        ResultSet rs = stmt.executeQuery(queryString);
        while (rs.next()) {
            String name = rs.getString(1);
            Player p = new Player(name);
            onlinePlayers += p.getName() + ";";
        }
        return onlinePlayers;
    }
}
