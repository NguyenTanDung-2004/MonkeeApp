package com.example.monkeeapp.Dat.sql;

import com.example.monkeeapp.Dat.util.StatisticItem;
import com.example.monkeeapp.Database.connect_database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticSql {
    public StatisticSql() {
    }

    public BigDecimal getIncome(String userId, int month, int year){
        String sql = "SELECT SUM(Money) AS TONGTHU FROM EXPENSE WHERE UserID = ? and Type = ? and MONTH(Date) = ? and YEAR(Date) = ?";
        try {
            PreparedStatement ps = connect_database.connect.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, "THU");
            ps.setInt(3, month);
            ps.setInt(4, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TONGTHU");
            }
            return BigDecimal.valueOf(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getOutcome(String userId, int month, int year){
        String sql = "SELECT SUM(Money) AS TONGCHI FROM EXPENSE WHERE UserID = ? and Type = ? and MONTH(Date) = ? and YEAR(Date) = ?";
        try {
            PreparedStatement ps = connect_database.connect.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, "CHI");
            ps.setInt(3, month);
            ps.setInt(4, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TONGCHI");
            }
            return BigDecimal.valueOf(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<StatisticItem> getTop3RankOutcome(int month, int year){
        String sql = "SELECT TOP 3 CategoryID,SUM(Money) AS TotalMoney FROM EXPENSE WHERE Type = ? and MONTH(Date) = ? and YEAR(Date) = ? GROUP BY CategoryID ORDER BY TotalMoney DESC";
        try {
            PreparedStatement ps = connect_database.connect.prepareStatement(sql);
            ps.setString(1, "CHI");
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            List<StatisticItem> list = new ArrayList<>();
            while (rs.next()){
                list.add(new StatisticItem(0, rs.getString("CategoryID"), rs.getBigDecimal("TotalMoney")));
            }
            return list;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StatisticItem> getTop3RankIncome(int month, int year){
        String sql = "SELECT TOP 3 CategoryID,SUM(Money) AS TotalMoney FROM EXPENSE WHERE Type = ? and MONTH(Date) = ? and YEAR(Date) = ? GROUP BY CategoryID ORDER BY TotalMoney DESC";
        try {
            PreparedStatement ps = connect_database.connect.prepareStatement(sql);
            ps.setString(1, "THU");
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            List<StatisticItem> list = new ArrayList<>();
            while (rs.next()){
                list.add(new StatisticItem(0, rs.getString("CategoryID"), rs.getBigDecimal("TotalMoney")));
            }
            return list;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getType(String categoryId){
        String sql = "SELECT CategoryName FROM CATEGORY WHERE CategoryID = ?";
        try {
            PreparedStatement ps = connect_database.connect.prepareStatement(sql);
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("CategoryName");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Fail";
    }
}
