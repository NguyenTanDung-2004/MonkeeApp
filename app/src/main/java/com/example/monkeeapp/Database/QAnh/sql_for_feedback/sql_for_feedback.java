package com.example.monkeeapp.Database.QAnh.sql_for_feedback;

import com.example.monkeeapp.Database.connect_database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class sql_for_feedback {
    //get feedback list of one use
    public static ArrayList<Integer> getIdFeedback(String id){
        ArrayList<Integer> IdFeedbackList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = (PreparedStatement) connect_database.connect.createStatement();
            String query = "SELECT FeedbackID FROM FEEDBACK WHERE UserID = ?";
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int idFeedback = resultSet.getInt("FeedbackID");
                IdFeedbackList.add(idFeedback);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return IdFeedbackList;
    }
    public static int create_id_feedback(Connection conn){
        int id_fb = 0;
        try {
            String query = "SELECT MAX(FeedbackID) AS MAX_ID FROM FEEDBACK";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id_fb = resultSet.getInt("MAX_ID") + 1;
            } else {
                id_fb = 1;
            }
            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_fb;

    }
    public static boolean insertFeedback(String id, String content, Connection conn){
        int id_feedback_create = sql_for_feedback.create_id_feedback(conn);
        try {
            String query = "INSERT INTO [FEEDBACK](FeedbackID, Content, UserID) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id_feedback_create);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            // Close the prepared statement
            preparedStatement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
