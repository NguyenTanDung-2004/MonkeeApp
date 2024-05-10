package com.example.monkeeapp.Database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect_database {
    String classes = "net.sourceforge.jtds.jdbc.Driver";
    protected static String serverName = "192.168.1.2:1433"; // định dạng là <ipv4>:<port>
    protected static String db = "monkee";
    protected static String user = "sa";
    protected static String password = "12345";
    public static Connection connect;
    public void create_connect_database(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classes).newInstance();
            String urlConn = "jdbc:jtds:sqlserver://"+serverName+";databaseName="+db+";user="+user+";password="+password+";";
            this.connect = DriverManager.getConnection(urlConn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
