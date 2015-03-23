package KonekDB;

import java.sql.*;

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
            System.out.println(e.getMessage());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return con;
    }
    
}
