package com.example.monkeeapp.Database.QAnh.sql_for_change_password;


import com.example.monkeeapp.Dung.utils.encrypt_password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql_for_change_password {
    public static String get_password(String id, Connection conn) throws SQLException {
        String password = "";
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT Password FROM [USER] WHERE UserID = ?")) {

            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("Password");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return password;
    }

    public static boolean update_password(String id, String newPass, Connection conn){
        String password_real = encrypt_password.encryptToSHA1(newPass);
        boolean isUpdated = false;

        try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE [USER] SET Password = ? WHERE UserID = ?")) {

            preparedStatement.setString(1, password_real);
            preparedStatement.setString(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return isUpdated;

    }
    public static void get_update_password(String id, String newPass, Connection conn){
        String password_real = encrypt_password.encryptToSHA1(newPass);

        try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE [USER] SET Password = ? WHERE UserID = ?")) {
            // Set the values for the parameters
            preparedStatement.setString(1, password_real);
            preparedStatement.setString(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
