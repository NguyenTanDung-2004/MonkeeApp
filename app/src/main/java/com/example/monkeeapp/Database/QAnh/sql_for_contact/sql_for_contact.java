package com.example.monkeeapp.Database.QAnh.sql_for_contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql_for_contact {
    public static String get_phone(Connection conn){
        String phone = "";
        String id = "Phone"; // This should be dynamically set or passed as a parameter
        try {
            String query = "SELECT Value FROM CONTACT WHERE ID = ?";
            // Using try-with-resources to ensure the PreparedStatement and ResultSet are closed automatically
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        phone = resultSet.getString("Value");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return phone;
    }
    public static String get_address(Connection conn){
        String address = "";
        String id = "Address"; // This should probably be obtained dynamically or passed as a parameter
        try {
            // Using try-with-resources to ensure the statement and result set are closed
            String query = "SELECT Value FROM CONTACT WHERE ID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        address = resultSet.getString("Value");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return address;
    }
    public static String get_email(Connection conn){
        String email = "";
        String id = "Email"; // This should be dynamically set or passed as a parameter
        try {
            String query = "SELECT Value FROM CONTACT WHERE ID = ?";
            // Using try-with-resources to ensure the PreparedStatement and ResultSet are closed automatically
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        email = resultSet.getString("Value");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return email;

    }
}
