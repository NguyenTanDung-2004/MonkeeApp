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

import com.example.monkeeapp.Dat.util.HandleBug;
import com.example.monkeeapp.Giang.ExpenseAdapter.ExpenseAdapter;
import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.Giang.interact_with_database.interact_with_expense;
import com.example.monkeeapp.R;
import com.example.monkeeapp.User.user;
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
    public TextView selectedDate;
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
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", new Locale("vi", "VN"));
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));
    List<Date> dates = new ArrayList<>();
    List<ExpenseView> expensesList = new ArrayList<>();
    List<ExpenseView> listExpensesForDate = new ArrayList<>();
    View view;

    public CustomCalendarView(Context context) {
        super(context);
        HandleBug.calendarView = this;
    }
    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        InitializeLayout();
        HandleBug.calendarView = this;
        setUpCalendar(-1);
        previousBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar(-1);
            }
        });
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar(-1);
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

    public void setUpCalendar(int pos) {
        String current_date = dateFormat.format(calendar.getTime());
        currentDate.setText(current_date);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) -1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, - firstDayOfMonth);


        expensesList = interact_with_expense.expenseForMonth(user.id_user, monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        gridAdapter = new GridAdapter(context, dates, calendar, expensesList, pos);
        gridAdapter.setCalendarView(this);
        gridView.setAdapter(gridAdapter);

        setRcvExpenseForDate(gridAdapter.getSelectedPosition());
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

        listExpensesForDate =  interact_with_expense.getExpensesListForDate(user.id_user, dateToString(date));
        updateExpenseStatistics();

        expenseAdapter = new ExpenseAdapter(listExpensesForDate);
        rcvExpense.setAdapter(expenseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvExpense);
    }

    public int getSelectedPosition() {
        return gridAdapter.getSelectedPosition();
    }

    public void updateExpenseStatistics() {
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

            String id = deleteExpense.getExpenseId();
            double temp =  interact_with_expense.getMoney(id);
            interact_with_expense.deleteExpense(id, temp);

            expensesList = interact_with_expense.expenseForMonth(user.id_user, monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));

            expenseAdapter.notifyDataSetChanged();
            updateExpenseStatistics();
            // goi setUpCalendar để thay đổi dữ liệu trng ô gridView
            setUpCalendar(gridAdapter.getSelectedPosition());

            String deleteString = "Đã xóa một chi tiêu";
            Snackbar.make(view, deleteString, Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listExpensesForDate.add(position, deleteExpense);
                    interact_with_expense.restoreExpense(id, temp);
                    expenseAdapter.notifyDataSetChanged();
                    updateExpenseStatistics();
                    setUpCalendar(gridAdapter.getSelectedPosition());
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
