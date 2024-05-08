package com.example.monkeeapp.Database;

import android.content.Context;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void getvalue(Context context) throws SQLException {
        Statement statement = connect_database.connect.createStatement();
        String query = "select * from [user]";
        ResultSet resultSet = statement.executeQuery(query);
        String s = null;
        // Hiển thị dữ liệu trong console
        while (resultSet.next()) {
            s = resultSet.getString(2);
        }
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
