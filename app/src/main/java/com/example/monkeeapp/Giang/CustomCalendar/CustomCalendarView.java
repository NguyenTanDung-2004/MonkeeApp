package com.example.monkeeapp.Giang.CustomCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monkeeapp.Giang.ExpenseAdapter.ExpenseAdapter;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CustomCalendarView extends LinearLayout {
    ImageButton previousBtn, nextBtn;
    TextView currentDate;
    GridView gridView;
    GridAdapter gridAdapter;
    TextView selectedDate;
    RecyclerView rcvExpense;
    ExpenseAdapter expenseAdapter;
    TextView txtTongThu;
    TextView txtTongChi;
    TextView txtConLai;
    Date selectedDay;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("vi", "VN"));
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", new Locale("vi", "VN"));
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));
    List<Date> dates = new ArrayList<>();
    List<ExpenseView> expensesList = new ArrayList<>();
    List<ExpenseView> listExpensesForDate = new ArrayList<>();
    View view;

    public CustomCalendarView(Context context) {
        super(context);
    }
    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        InitializeLayout();
        setUpCalendar();
        previousBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
            }
        });
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
            }
        });
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.giang_calendar_layout, this);
        nextBtn = view.findViewById(R.id.nextBtn);
        previousBtn = view.findViewById(R.id.previousBtn);
        gridView = view.findViewById(R.id.gridView);
        currentDate = view.findViewById(R.id.currentDate);
        selectedDate = view.findViewById(R.id.selectedDate);
        rcvExpense = view.findViewById(R.id.rcvExpenseForDate);
        txtConLai = view.findViewById(R.id.txtConLai);
        txtTongChi = view.findViewById(R.id.txtTongChi);
        txtTongThu = view.findViewById(R.id.txtTongThu);

    }

    private void setUpCalendar() {
        String current_date = dateFormat.format(calendar.getTime());
        currentDate.setText(current_date);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) -1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, - firstDayOfMonth);
        expenseForMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        gridAdapter = new GridAdapter(context, dates, calendar, expensesList);
        gridAdapter.setCalendarView(this);
        gridView.setAdapter(gridAdapter);

        setRcvExpenseForDate(gridAdapter.getSelectedPosition());
    }

    private void expenseForMonth(String month, String year) {
        // sql server de lay du lieu
        expensesList.clear();

        expensesList.add(new ExpenseView("icon_eat", "Ăn uống", "01.05.2024", "ăn phở bò", "- 50000"));
        expensesList.add(new ExpenseView("icon_eat", "Ăn uống", "01.05.2024", "ăn cơm tấm", "- 48000"));

        expensesList.add(new ExpenseView("icon_eat", "Ăn uống", "06.05.2024", "ăn phở bò", "- 50000"));
        expensesList.add(new ExpenseView("icon_education", "Tiền thưởng", "06.05.2024", "", "+ 17400000"));

        expensesList.add(new ExpenseView("icon_shopping", "Mua sắm", "29.05.2024", "1 áo 1 quần", "- 500000"));
        expensesList.add(new ExpenseView("icon_eat", "Ăn uống", "29.05.2024", "ăn phở bò", "- 50000"));

        // close sql
    }

    public String dateToString(Date selectedDay) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("vi", "VN"));
        return dayFormat.format(selectedDay);
    }


    public void setRcvExpenseForDate(int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvExpense.setLayoutManager(linearLayoutManager);
        Date date = gridAdapter.getItem(position);
        selectedDate.setText(dateToString(date));

        listExpensesForDate = getExpensesListForDate(dateToString(date));
        updateExpenseStatistics();

        expenseAdapter = new ExpenseAdapter(listExpensesForDate);
        rcvExpense.setAdapter(expenseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvExpense);
    }

    // lay ra chi tieu voi ngay tương ung
    public List<ExpenseView> getExpensesListForDate(String date) {
        List<ExpenseView> list = new ArrayList<>();

        // truy xuat bang ngay trong csdl

        if (date.equals("01.05.2024")) {
            list.add(new ExpenseView("icon_eat", "Ăn uống", date, "ăn phở bò", "- 50000"));
            list.add(new ExpenseView("icon_eat", "Ăn uống", date, "ăn cơm tấm", "- 48000"));
        }

        if (date.equals("06.05.2024")) {
            list.add(new ExpenseView("icon_eat", "Ăn uống", date, "ăn phở bò", "- 50000"));
            list.add(new ExpenseView("icon_education", "Tiền thưởng", date, "", "+ 17400000"));
        }
        if (date.equals("29.05.2024")) {
            list.add(new ExpenseView("icon_shopping", "Mua sắm", date, "1 áo 1 quần", "- 500000"));
            list.add(new ExpenseView("icon_eat", "Ăn uống", date, "ăn phở bò", "- 50000"));
        }

        return list;
    }
    private void updateExpenseStatistics() {
        long thu = getTongThu(listExpensesForDate);
        long chi = getTongChi(listExpensesForDate);
        txtTongThu.setText(String.valueOf(thu));
        txtTongChi.setText(String.valueOf(chi));

        if (thu >= chi) {
            txtConLai.setTextColor(context.getResources().getColor(R.color.Giang_primary));
            txtConLai.setText("+ " + String.valueOf(thu - chi));
        } else {
            txtConLai.setTextColor(context.getResources().getColor(R.color.Giang_green));
            txtConLai.setText("- " + String.valueOf(chi - thu));
        }
    }
    public long getTongThu(List<ExpenseView> list) {
        long sum = 0;
        for (int i = 0;i < list.size(); i++) {
            if (list.get(i).getMoney().split(" ")[0].equals("+"))
                sum = sum + Long.parseLong(list.get(i).getMoney().split(" ")[1]);
        }
        return sum;
    }
    public long getTongChi(List<ExpenseView> list) {
        long sum = 0;
        for (int i = 0;i < list.size(); i++) {
            if (list.get(i).getMoney().split(" ")[0].equals("-"))
                sum = sum + Long.parseLong(list.get(i).getMoney().split(" ")[1]);
        }
        return sum;
    }


    // vuot qua trai de xoa
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
            deleteExpense = listExpensesForDate.get(position);
            listExpensesForDate.remove(position);
            expenseAdapter.notifyDataSetChanged();
            updateExpenseStatistics();
            expenseForMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
            // goi setUpCalendar để thay đổi dữ liệu trng ô gridView
            // setUpCalendar();

            String deleteString = "Đã xóa một chi tiêu";
            Snackbar.make(view, deleteString, Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listExpensesForDate.add(position, deleteExpense);
                    expenseAdapter.notifyDataSetChanged();
                    updateExpenseStatistics();
                    // goi setUpCalendar để thay đổi dữ liệu trng ô gridView
                    // setUpCalendar();
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
