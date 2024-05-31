package com.example.monkeeapp.Database.QAnh.sql_for_setting;

import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.monkeeapp.QAnh.utils.SaveImg.SaveImg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sql_for_setting {
    public static String get_ava(String id, Connection conn){
        String ava = "";
        try {
            String query = "SELECT UrlAvatar FROM [USER] WHERE UserID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ava = resultSet.getString("UrlAvatar");
            }
        } catch (SQLException e) {
        }
        return ava;

    }

    public static String get_username(String id, Connection conn) {
        String username = "";
        try {
            String query = "SELECT Username FROM [USER] WHERE UserID = ?";
            try(PreparedStatement preparedStatement = conn.prepareStatement(query)){
                preparedStatement.setString(1, id);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(resultSet.next()){
                        username = resultSet.getString("Username");
                    }
                }
            }
        } catch (SQLException e) {
        }
        return username;
    }

    public static void update_info(String id, EditText name, ImageView img, Connection conn){
        String nameString = name.getText().toString();
        byte[] imgBytes = SaveImg.ImageView_to_Byte(img);
        String imgString = Base64.encodeToString(imgBytes, Base64.DEFAULT);
        try(PreparedStatement preparedStatement = conn.prepareStatement("UPDATE [USER] SET Username = ?, UrlAvatar = ? WHERE UserID = ?")) {
            preparedStatement.setString(1, nameString);
            preparedStatement.setString(2, imgString);
            preparedStatement.setString(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            // Close the prepared statement
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean check_update(String id, EditText name, ImageView img, Connection conn){
        String nameString = name.getText().toString();
        byte[] imgBytes = SaveImg.ImageView_to_Byte(img);
        String imgString = Base64.encodeToString(imgBytes, Base64.DEFAULT);
        try(PreparedStatement preparedStatement = conn.prepareStatement("UPDATE [USER] SET Username = ?, UrlAvatar = ? WHERE UserID = ?")) {
            preparedStatement.setString(1, nameString);
            preparedStatement.setString(2, imgString);
            preparedStatement.setString(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            // Close the prepared statement
            preparedStatement.close();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
