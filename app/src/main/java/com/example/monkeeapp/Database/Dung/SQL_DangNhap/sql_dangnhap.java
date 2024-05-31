package com.example.monkeeapp.Database.Dung.SQL_DangNhap;

import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Dung.utils.encrypt_password;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_dangnhap {
    public static boolean check_email_exist(String email) throws SQLException { // true is not exist
        Statement statement = connect_database.connect.createStatement();
        String query = "select * from [user] \n" +
                "where [user].Email = '" + email + "'";
        ResultSet resultSet = statement.executeQuery(query);
        int flag = 0;
        // Hiển thị dữ liệu trong console
        while (resultSet.next()) {
            flag = 1;
        }
        if (flag == 0){
            return false;
        }
        return true;
    }
    public static boolean check_password(String password, String email) throws SQLException {
        String encrypt_pass = encrypt_password.encryptToSHA1(password);
        Statement statement = connect_database.connect.createStatement();
        String query = "select [user].password from [user] \n" +
                "where [user].email = '" + email + "'";
        ResultSet resultSet = statement.executeQuery(query);
        String pass_in_db = "";
        // Hiển thị dữ liệu trong console
        while (resultSet.next()) {
            pass_in_db = resultSet.getString(1);
        }
        if (pass_in_db.equals(encrypt_pass)){
            return true;
        }
        return false;
    }
    public static String get_id_from_email (String email) throws SQLException {
        Statement statement = connect_database.connect.createStatement();
        String query = "select userid from [user]\n" +
                "where [user].email = '" + email + "'";
        ResultSet resultSet = statement.executeQuery(query);
        String result = "";
        // Hiển thị dữ liệu trong console
        while (resultSet.next()) {
            result = resultSet.getString(1);
        }
        return result;
    }
}
