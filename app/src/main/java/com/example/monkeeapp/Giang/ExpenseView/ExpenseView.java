package com.example.monkeeapp.Giang.ExpenseView;

public class ExpenseView {
    private String expenseId;
    private String src ;
    private String categoryName;
    private String date;
    private String note;
    private String money;
    public ExpenseView(String id, String src, String name, String date, String note, String money) {
        this.expenseId = id;
        this.src = src;
        this.categoryName = name;
        this.date = date;
        this.note = note;
        this.money = money;
    }
    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
