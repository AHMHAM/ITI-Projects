package servertictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessLayer {
    
   static Connection con;
    
    public static int signUp(String mail, String name, String password) throws SQLException
    {
        int result=0;
        connect();
        System.out.println(con);
        PreparedStatement ps = con.prepareStatement("INSERT INTO PLAYER (EMAIL,USERNAME,PASSWORD) VALUES (?,?,?)");
        ps.setString(1, mail);
        ps.setString(2, name);
        ps.setString(3, password);
        result= ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();
        return result;
}
    
    public static int logIn(String name, String password) throws SQLException
    {
        int result=0;
        connect();
        PreparedStatement ps = con.prepareStatement("SELECT USERNAME , PASSWORD FROM PLAYER  WHERE( USERNAME= ? AND PASSWORD = ? ) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        
         
        if (rs.next())
        {
            ps= con.prepareStatement("UPDATE PLAYER set STATUS=? where USERNAME=?");
            ps.setString(1, "online");
            ps.setString(2, name);
            ps.executeUpdate();
            result = 1;
        }
        con.commit();
        ps.close();
        con.close();
        rs.close();
        return result;
    }
    
    public static int logout(String name) throws SQLException
    {
        int result=0;
        connect();
        PreparedStatement ps = con.prepareStatement("UPDATE PLAYER SET STATUS=? WHERE USERNAME=?");
        ps.setString(1, "offline");
        ps.setString(2, name);
        result= ps.executeUpdate();
        System.out.println(result);
        con.commit();
        ps.close();
        con.close();
        return result;
    }
    
    public static int connectedSuccessfuly(String player1, String player2) throws SQLException
    {
        connect();
        int result=0;
        PreparedStatement ps = con.prepareStatement("UPDATE PLAYER set STATUS=? where USERNAME=?");
        ps.setString(1, "playing");
        ps.setString(2, player1);
        result= ps.executeUpdate();
        
        ps.setString(1, "playing");
        ps.setString(2, player2);
        ps.executeUpdate();
        con.commit();
        ps.close();
        con.close();  
        return result;
    }
    
    private static void connect() throws SQLException{
     DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
      con = DriverManager.getConnection("jdbc:derby://localhost:1527/serverdatabase","root","root");
    }
    
}

