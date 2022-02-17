/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printgudang;


import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author MOBILEDEV
 */
public class CutrePool{
    
    
      String connString;    
      String user;
      String pwd;

      static final int INITIAL_CAPACITY = 50;
      LinkedList<Connection> pool = new LinkedList<Connection>();
      public String getConnString() {
          return connString;
      }
      public String getPwd() {
          return pwd;
      }

      public String getUser() {
          return user;
      }
      
     

      public CutrePool(String connString, String user, String pwd) throws SQLException {
          this.connString = connString;
        
          for (int i = 0; i < INITIAL_CAPACITY; i++) {
               pool.add(DriverManager.getConnection(connString, user, pwd));
          }
          this.user = user;
          this.pwd = pwd;
      }

      public synchronized Connection getConnection() throws SQLException {
          if (pool.isEmpty()) {
              pool.add(DriverManager.getConnection(connString, user, pwd));
          }
          return pool.pop();
      }
    
      public synchronized void returnConnection(Connection connection) {
          pool.push(connection);
      }  
  }
