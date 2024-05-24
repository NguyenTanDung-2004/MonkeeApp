package com.example.monkeeapp.Database.QAnh.sql_for_account;

import com.example.monkeeapp.Database.connect_database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql_for_account {
    public static String get_email(String id) throws SQLException{
        String email = "";
        try{
            PreparedStatement preparedStatement = (PreparedStatement) connect_database.connect.createStatement();
            String query = "SELECT Email FROM [user] WHERE UserID = ?";
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                email = resultSet.getNString("Email");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return email;
    }
    public static String get_username(String id) throws SQLException {
        String username = "";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connect_database.connect.createStatement();
            String query = "SELECT Username FROM [USER] WHERE UserID = ?";
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                username = resultSet.getNString("Username");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return username;
    }
    public static String get_password(String id) throws SQLException {
        String password = "";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connect_database.connect.createStatement();
            String query = "SELECT Password FROM [USER] WHERE UserID = ?";
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                password = resultSet.getNString("Password");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return password;
    }
}
