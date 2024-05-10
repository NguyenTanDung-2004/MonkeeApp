package com.example.monkeeapp.Database.Dung.SQL_QuenMatKhau;

import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Dung.utils.encrypt_password;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_quenmatkhau {
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
    public static void update_password(String email, String password){
        String password_real = encrypt_password.encryptToSHA1(password);
        String query = "update [user]\n" +
                "set [user].password = '" + password_real + "'" +
                "\n" +
                "where [user].email = '" + email + "'";
        try (PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query)) {
            // Thiết lập giá trị cho các tham số
            int rowsAffected = preparedStatement.executeUpdate();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

}
}
