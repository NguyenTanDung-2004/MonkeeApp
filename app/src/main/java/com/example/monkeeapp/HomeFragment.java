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

import com.example.monkeeapp.Giang.ExpenseAdapter.ExpenseAdapter;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class HomeFragment extends Fragment {
    private ExpenseAdapter expenseAdapter;
    private List<ExpenseView> listExpenses;
    private FragmentHomeBinding binding;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        RecyclerView rcvExpense = binding.rcvExpense;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvExpense.setLayoutManager(linearLayoutManager);

        listExpenses = getListExpenses();
        expenseAdapter = new ExpenseAdapter(listExpenses);
        rcvExpense.setAdapter(expenseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvExpense);

        return view;
    }

    private List<ExpenseView> getListExpenses() {
        List<ExpenseView> list = new ArrayList<>();
        list.add(new ExpenseView("icon_eat", "Ăn uống", "01.05.2024", "ăn phở bò", "- 50000"));
        list.add(new ExpenseView("icon_eat", "Ăn uống", "01.05.2024", "ăn cơm tấm", "- 48000"));
        list.add(new ExpenseView("icon_home", "Nhà ở", "30.04.2024", "tiền điện, nước", "- 71000"));
        list.add(new ExpenseView("icon_vehicle", "Phương tiện", "29.04.2024", "xăng", "- 50000"));
        list.add(new ExpenseView("icon_education", "Giáo dục", "28.04.2024", "mẹ cho", "+ 500000"));
        list.add(new ExpenseView("icon_shopping", "Mua sắm", "27.04.2024", "1 áo 1 quần", "- 500000"));
        list.add(new ExpenseView("icon_education", "Tiền thưởng", "26.04.2024", "", "+ 17400000"));

        return list;
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
            expenseAdapter.notifyDataSetChanged();

            String deleteString = "Đã xóa một chi tiêu";
            Snackbar.make(view, deleteString, Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listExpenses.add(position, deleteExpense);
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