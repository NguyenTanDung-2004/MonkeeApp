package com.example.monkeeapp.Giang.CustomCalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monkeeapp.Giang.ExpenseView.ExpenseView;
import com.example.monkeeapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<ExpenseView> expensesList;
    LayoutInflater inflater;
    int selectedPosition;
    private CustomCalendarView calendarView;
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setCalendarView(CustomCalendarView calendarView) {
        this.calendarView = calendarView;
    }
    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<ExpenseView> expensesList, int pos) {
        super(context, R.layout.giang_single_date_layout);

        this.dates = dates;
        this.currentDate = currentDate;
        this.expensesList = expensesList;
        inflater = LayoutInflater.from(context);

        if (pos == -1) {
            Calendar todayCalendar = Calendar.getInstance();
            for (int i = 0; i < dates.size(); i++) {
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(dates.get(i));
                if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                        dateCalendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                        dateCalendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH)) {
                    selectedPosition = i;
                    break;
                }
            }
        } else {
            selectedPosition = pos;
        }

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int  dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.giang_single_date_layout, parent, false);

        }

        TextView day_number = view.findViewById(R.id.calendarDate);
        day_number.setText(String.valueOf(dayNo));
        TextView money_date = view.findViewById(R.id.moneyDate);

        if (position == selectedPosition) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.Giang_red_light));
        } else {
            if (displayMonth == currentMonth && displayYear==currentYear) {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.Giang_light_background));
            }
            else {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.Giang_gray_background));
                day_number.setTextColor(Color.parseColor("#ababab"));
            }
        }

        View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != position) {
                    selectedPosition = position;
                    calendarView.setRcvExpenseForDate(selectedPosition);
                } else {
                    selectedPosition = -1; // Bỏ chọn nếu ngày đã được chọn trước đó được chọn lại
                }
                notifyDataSetChanged();
            }
        });

        Calendar expenseCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < expensesList.size(); i++) {
            expenseCalendar.setTime(stringToDate(expensesList.get(i).getDate()));
            if (dayNo == expenseCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == expenseCalendar.get(Calendar.MONTH) + 1
                    && displayYear == expenseCalendar.get(Calendar.YEAR)) {
                arrayList.add(expensesList.get(i).getMoney());
                long total_money = totalMoney(arrayList);

                String total_money_string = Long.toString(Math.abs(total_money));
                if (total_money_string.length() > 6) {
                    total_money_string = total_money_string.substring(0, 6) + "...";
                }
                money_date.setText(total_money_string);
                if (total_money >= 0) {
                    money_date.setTextColor(view.getResources().getColor(R.color.Giang_primary));
                }
                else {
                    money_date.setTextColor(view.getResources().getColor(R.color.Giang_green));
                }
            }
        }

        return view;
    }
    private Date stringToDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", new Locale("vi", "VN"));
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();;
        }
        return date;
    }
    private long stringToMoney(String s) {
        long value = Long.parseLong(s.split(" ")[1]);

        if (s.split(" ")[0].equals("-"))
            value = value * -1;

        return value;
    }
    private long totalMoney(ArrayList<String> list) {
        long total = 0;
        for (String str : list) {
            long value = stringToMoney(str);
            total += value;
        }

        return total;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return dates.get(position);
    }


}
