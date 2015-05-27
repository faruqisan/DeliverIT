package MySQL.Koneksi;

import java.sql.*;
import javax.swing.JOptionPane;

public class KoneksiMySQL {
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String dbUrl ="jdbc:mysql://localhost/deliverit_main";
    String dbUn="root";
    String dbPs="";
    
    public Connection getConnection(){
        Connection con =null;
        
        try{
            Class.forName(JDBC_DRIVER);
            con=DriverManager.getConnection(dbUrl,dbUn,dbPs);
        }catch(ClassNotFoundException e){
            
            JOptionPane.showMessageDialog(null, "Server Database Error");
        }catch(SQLException e){
            
            new ConnectionMessage().setVisible(true);
        }
        
        return con;
    }
    
}
