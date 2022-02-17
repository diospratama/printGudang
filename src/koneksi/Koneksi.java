/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//digunakan untuk koneksi ke database
package koneksi;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;
import printgudang.Home;
import printgudang.PrintServices;

/**
 *
 * @author MOBILEDEV
 */
public class Koneksi {
    
  
   
    private static Connection cn ;
    

        
      public static java.sql.Connection koneksi() throws SQLException{
        
        Properties properties = new Properties();
        try {
            //load file database.properties
            properties.load(new FileInputStream("database.properties"));
        } catch (IOException ex) {
            System.out.println("Gagal load file database.properties");
        }
 
        String driverName = properties.getProperty("driverName");
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String serverName = properties.getProperty("serverName");
        String port = properties.getProperty("port");
        String databaseName = properties.getProperty("databaseName");
        System.out.println("jdbc:mysql://"+serverName+"/"+databaseName+"/"+user+"/"+password);
        
       
        
        try{
            cn=(Connection) DriverManager.getConnection
                    ("jdbc:mysql://"+serverName+"/"+databaseName,user,password);
           
            
              
        
        
        }catch(SQLException e){
               
              System.out.println("Koneksi database gagal");
              
        }
              
        
        
        return cn;
       
       
    }
}
