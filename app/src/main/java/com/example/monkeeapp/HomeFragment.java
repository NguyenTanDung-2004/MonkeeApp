package com.example.monkeeapp;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monkeeapp.Database.connect_database;
import com.example.monkeeapp.Giang.ExpenseAdapter.ExpenseAdapter;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.Giang.interact_with_database.interact_with_expense;
import com.example.monkeeapp.User.user;
import com.example.monkeeapp.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class HomeFragment extends Fragment {
    private ExpenseAdapter expenseAdapter;
    private List<ExpenseView> listExpenses;
    private FragmentHomeBinding binding;
    private View view;
    private TextView txtHello;
    Connection conn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        txtHello = view.findViewById(R.id.txtHello);
        RecyclerView rcvExpense = binding.rcvExpense;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvExpense.setLayoutManager(linearLayoutManager);

        connect_database obj = new connect_database();
        obj.create_connect_database();
        conn = obj.connect;

        setUsername(user.id_user);

        interact_with_expense interact = new interact_with_expense();
        listExpenses = interact.getListExpenses(user.id_user);

        expenseAdapter = new ExpenseAdapter(listExpenses);
        rcvExpense.setAdapter(expenseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvExpense);

        return view;
    }

    private void setUsername(String userId) {
        String username = "";
        try {
            String query = "SELECT Username FROM USER WHERE UserID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("Username");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtHello.setText("Xin chào, " + username);
    }
    // vuot de xoa expense
    ExpenseView deleteExpense;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            deleteExpense = listExpenses.get(position);
            listExpenses.remove(position);
            String id = deleteExpense.getExpenseId();
            double temp = interact_with_expense.getMoney(id);
            interact_with_expense.deleteExpense(id, temp);

            expenseAdapter.notifyDataSetChanged();

            String deleteString = "Đã xóa một chi tiêu";
            Snackbar.make(view, deleteString, Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listExpenses.add(position, deleteExpense);
                    interact_with_expense.restoreExpense(id, temp);
                    expenseAdapter.notifyDataSetChanged();
                }
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addCornerRadius(2,12)
                    .addBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.Giang_red))
                    .addActionIcon(R.drawable.icon_bin)
                    .addSwipeLeftLabel("Xóa")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(view.getContext(), R.color.black))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}