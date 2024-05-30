package com.example.monkeeapp.Dat.sql;

import com.example.monkeeapp.Database.connect_database;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseSql {
    public ExpenseSql() {
    }

    public boolean add_expense(String expenseId, String userId, String categoryId, Date date, String note, BigDecimal money, String type) throws SQLException {
        try {
            String query = "insert into EXPENSE values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query);
            // Thiết lập giá trị cho các tham số
            preparedStatement.setString(1, expenseId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, categoryId);
            preparedStatement.setString(4, note);
            preparedStatement.setBigDecimal(5, money);
            preparedStatement.setDate(6, date);
            preparedStatement.setString(7, type);
            // Thực hiện truy vấn INSERT
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update_expense(String expenseId, String categoryId, Date date, String note, BigDecimal money, String type) throws SQLException {
        try {
            String query = "update EXPENSE set Note=?, Money=?, Date=?, Type = ?, CategoryID=? where ExpenseID=?";
            PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query);
            // Thiết lập giá trị cho các tham số
            preparedStatement.setString(1, note);
            preparedStatement.setBigDecimal(2, money);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, categoryId);
            preparedStatement.setString(6, expenseId);
            // Thực hiện truy vấn UPDATE
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCategoryId(String categoryName) {
        String query = "select CategoryID from CATEGORY where CategoryName=?";
        try {
            PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("CategoryID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Fail";
    }

    public String getUserId(String id) {
        String query = "select UserID from [USER] where UserID=?";
        try {
            PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("UserID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Fail";
    }

    public String getCategoryName(String expenseId) {
        String query = "select c.CategoryName from CATEGORY c, EXPENSE e where e.CategoryID = c.CategoryID and e.ExpenseID=?";
        try {
            PreparedStatement preparedStatement = connect_database.connect.prepareStatement(query);
            preparedStatement.setString(1, expenseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("CategoryName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Fail";
    }
}
