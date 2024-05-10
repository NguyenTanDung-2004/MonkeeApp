package com.example.monkeeapp.Database.Dung.SQL_DangKy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Dung.utils.encrypt_password;

public class sql_dangky {
    public static int create_id_for_user() throws SQLException {
        Statement statement = connect_database.connect.createStatement();
        String query = "SELECT MAX(CAST(userid AS INT))\n" +
                "FROM [user];";
        ResultSet resultSet = statement.executeQuery(query);
        int result = 1;
        // Hiển thị dữ liệu trong console
        while (resultSet.next()) {
            String max_id = resultSet.getString(1);
            result = Integer.parseInt(max_id);
        }
        return result + 1;
    }
    public static void insert_user(String Email, String Username, String password) throws SQLException {
        int id = create_id_for_user();
        String userID = id + "";
        String password_real = encrypt_password.encryptToSHA1(password);
        String query = "insert into [user] (userid, email, username, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query)) {
            // Thiết lập giá trị cho các tham số
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, Email);
            preparedStatement.setString(3, Username);
            preparedStatement.setString(4, password_real);
            // Thực hiện truy vấn INSERT
            int rowsAffected = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
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
            return true;
        }
        return false;
    }
}
