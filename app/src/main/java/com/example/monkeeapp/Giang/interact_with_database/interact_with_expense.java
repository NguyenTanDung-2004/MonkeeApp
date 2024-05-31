package com.example.monkeeapp.Giang.interact_with_database;

import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class interact_with_expense {
    public static Connection conn;
    public interact_with_expense() {
        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;
    }
    public static String dateToString(java.util.Date selectedDay) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("vi", "VN"));
        return dayFormat.format(selectedDay);
    }
    public static List<ExpenseView> getListExpenses(String userId) {
        List<ExpenseView> expenseList = new ArrayList<>();
        try {
            String query = "SELECT e.ExpenseID, c.UrlIcon, c.CategoryName, e.Date, e.Note, CONCAT(CASE WHEN e.Type = 'THU' THEN '+' ELSE '-' END, ' ', e.Money) AS Money " +
                    "FROM EXPENSE e " +
                    "JOIN CATEGORY c ON e.CategoryID = c.CategoryID " +
                    "WHERE e.UserID = ? AND e.Money <> 0";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String expenseID = resultSet.getString("ExpenseID");
                String urlIcon = resultSet.getString("UrlIcon");
                String categoryName = resultSet.getString("CategoryName");
                String date = dateToString(resultSet.getDate("Date"));
                String note = resultSet.getString("Note");
                String money = resultSet.getString("Money");
                if (money.contains(".")) {
                    money = money.substring(0, money.indexOf("."));
                }
                ExpenseView expenseView = new ExpenseView(expenseID, urlIcon, categoryName, date, note, money);
                expenseList.add(expenseView);
            }

        } catch (SQLException e) {
//        e.printStackTrace();
        }

        return expenseList;
    }
    public static List<ExpenseView> getListExpensesTop10(String userId) {
        List<ExpenseView> expenseList = new ArrayList<>();
        try {
            String query = "SELECT e.ExpenseID, c.UrlIcon, c.CategoryName, e.Date, e.Note, CONCAT(CASE WHEN e.Type = 'THU' THEN '+' ELSE '-' END, ' ', e.Money) AS Money " +
                    "FROM EXPENSE e " +
                    "JOIN CATEGORY c ON e.CategoryID = c.CategoryID " +
                    "WHERE e.UserID = ? AND e.Money <> 0" +
                    "ORDER BY e.Date desc";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String expenseID = resultSet.getString("ExpenseID");
                String urlIcon = resultSet.getString("UrlIcon");
                String categoryName = resultSet.getString("CategoryName");
                String date = dateToString(resultSet.getDate("Date"));
                String note = resultSet.getString("Note");
                String money = resultSet.getString("Money");
                if (money.contains(".")) {
                    money = money.substring(0, money.indexOf("."));
                }
                ExpenseView expenseView = new ExpenseView(expenseID, urlIcon, categoryName, date, note, money);
                expenseList.add(expenseView);
            }

        } catch (SQLException e) {
//            e.printStackTrace();
        }

        return expenseList;
    }

    public static List<ExpenseView> expenseForMonth(String user_id, String month, String year) {
        List<ExpenseView> expenseList = new ArrayList<>();
        try {
            String query = "SELECT  e.ExpenseID, c.UrlIcon, c.CategoryName, e.Date, e.Note, CONCAT(CASE WHEN e.Type = 'THU' THEN '+' ELSE '-' END, ' ', e.Money) AS Money " +
                    "FROM EXPENSE e " +
                    "JOIN CATEGORY c ON e.CategoryID = c.CategoryID " +
                    "WHERE e.UserID = ? AND MONTH(e.Date) = ? AND YEAR(e.Date) = ? AND e.Money <> 0" +
                    "ORDER BY e.Date asc";


            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user_id);
            statement.setInt(2, Integer.parseInt(month)); // Chuyển đổi tháng thành số
            statement.setInt(3, Integer.parseInt(year)); // Chuyển đổi năm thành số

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String expenseID = resultSet.getString("ExpenseID");
                String urlIcon = resultSet.getString("UrlIcon");
                String categoryName = resultSet.getString("CategoryName");
                String date = dateToString(resultSet.getDate("Date"));
                String note = resultSet.getString("Note");
                String money = resultSet.getString("Money");
                if (money.contains(".")) {
                    money = money.substring(0, money.indexOf("."));
                }
                ExpenseView expenseView = new ExpenseView(expenseID, urlIcon, categoryName, date, note, money);
                expenseList.add(expenseView);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseList;
    }
    public static List<ExpenseView> getExpensesListForDate(String user_id, String date) {
        List<ExpenseView> expenseList = new ArrayList<>();
        try {
            String query = "SELECT e.ExpenseID, c.UrlIcon, c.CategoryName, e.Date, e.Note, CONCAT(CASE WHEN e.Type = 'THU' THEN '+' ELSE '-' END, ' ', e.Money) AS Money " +
                    "FROM EXPENSE e " +
                    "JOIN CATEGORY c ON e.CategoryID = c.CategoryID " +
                    "WHERE e.UserID = ? AND DAY(e.Date) = ? AND MONTH(e.Date) = ? AND YEAR(e.Date) = ? AND e.Money <> 0";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user_id);
            statement.setInt(2, Integer.parseInt(date.split("\\.")[0]));
            statement.setInt(3, Integer.parseInt(date.split("\\.")[1]));
            statement.setInt(4, Integer.parseInt(date.split("\\.")[2]));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String expenseID = resultSet.getString("ExpenseID");
                String urlIcon = resultSet.getString("UrlIcon");
                String categoryName = resultSet.getString("CategoryName");
                String expenseDate = dateToString(resultSet.getDate("Date"));
                String note = resultSet.getString("Note");
                String money = resultSet.getString("Money");
                if (money.contains(".")) {
                    money = money.substring(0, money.indexOf("."));
                }
                ExpenseView expenseView = new ExpenseView(expenseID, urlIcon, categoryName, expenseDate, note, money);
                expenseList.add(expenseView);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenseList;
    }
    public static void deleteExpense(String expenseId, double temp) {
        try {
            // Cập nhật money thành 0
            String updateQuery = "UPDATE EXPENSE SET Money = 0 WHERE ExpenseID = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, expenseId);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getUsername(String userId) {
        String username = "";
        try {
            String query = "SELECT Username FROM [USER] WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("Username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
    public static double  getMoney(String expenseId) {
        double temp = 0.0;
        try {
            // Lấy giá trị money từ biến temp
            String selectQuery = "SELECT Money FROM EXPENSE WHERE ExpenseID = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, expenseId);
            ResultSet selectResult = selectStatement.executeQuery();
            if (selectResult.next()) {
                temp = selectResult.getDouble("Money");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }
    public static void restoreExpense(String expenseId, double temp) {
        try {
            // Khôi phục giá trị money cho chi tiêu
            String updateQuery = "UPDATE EXPENSE SET Money = ? WHERE ExpenseID = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setDouble(1, temp);
            updateStatement.setString(2, expenseId);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
