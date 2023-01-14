package servertictactoe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSide {

    ServerSocket serverSocket;
    Thread thread;

    public ServerSide() {

        try {
            serverSocket = new ServerSocket(9050);
            thread = new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new PlayerHandler(clientSocket);

                    } catch (IOException ex) {
                        Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    boolean isOpen;
    public void openSocket()
    {
        try {
            serverSocket = new ServerSocket(9050);
            thread = new Thread(() -> {
             isOpen = true;
                while(true){
                    try {
                        if(isOpen == false)
                        {
                            break;
                        }
                        Socket clientSocket = serverSocket.accept();
                        new PlayerHandler(clientSocket);
                    }
                    catch (IOException ex) {
                        Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void closeSocket()
    {
        try {
            isOpen = false;
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
