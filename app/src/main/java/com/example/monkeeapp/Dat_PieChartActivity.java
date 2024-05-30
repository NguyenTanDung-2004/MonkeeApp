package com.example.monkeeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monkeeapp.Dat.adapter.ArrayAdapterListViewRank;
import com.example.monkeeapp.Dat.sql.StatisticSql;
import com.example.monkeeapp.Dat.util.StatisticItem;
import com.example.monkeeapp.Dat.util.StatisticItemDisplay;
import com.example.monkeeapp.User.user;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.monkeeapp.Database.connect_database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dat_PieChartActivity extends AppCompatActivity {

    int[] images = {R.drawable.icon_eat, R.drawable.icon_shopping, R.drawable.icon_entertainment, R.drawable.icon_phone, R.drawable.icon_education, R.drawable.icon_beautify, R.drawable.icon_vehicle, R.drawable.icon_sport, R.drawable.icon_repair, R.drawable.icon_tourim,
            R.drawable.icon_social, R.drawable.icon_health, R.drawable.icon_home, R.drawable.icon_electricity, R.drawable.icon_gift, R.drawable.icon_pet, R.drawable.icon_donate, R.drawable.icon_child, R.drawable.icon_electronic_device, R.drawable.icon_other};
    String[] names = {"Ăn uống", "Mua sắm", "Giải trí", "Điện thoại", "Giáo dục", "Làm đẹp", "Phương tiện", "Thể thao", "Sửa chữa", "Du lịch",
            "Xã hội", "Sức khỏe", "Nhà", "Điện nước", "Quà tặng", "Thú cưng", "Quyên góp", "Con cái", "Điện tử", "Khác" };
    BigDecimal[] values = {BigDecimal.valueOf(200), BigDecimal.valueOf(400), BigDecimal.valueOf(450)};
    PieChart pieChart, pieChart2;
    List<PieEntry> pieEntriesOutcome = new ArrayList<>();
    List<PieEntry> pieEntriesIncome = new ArrayList<>();
    ListView listViewRank, listViewRank2;
    TextView txtIncome, txtOutcome, txtTotal, txt_date;
    ImageButton btnNextMonth, btnPreMonth, btnBack;
    int month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dat_activity_pie_chart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Display income, out come and total money
        txtIncome = findViewById(R.id.txtincome);
        txtOutcome = findViewById(R.id.txtoutcome);
        txtTotal = findViewById(R.id.txttotal);
        txt_date = findViewById(R.id.txtDate); // "Tháng 2/2024"
        String dateText = txt_date.getText().toString();

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Giả sử văn bản là "Tháng 2/2024"
        String[] parts = dateText.split(" ");
        if (parts.length == 2) {
            String[] dateParts = parts[1].split("/");

            if (dateParts.length == 2) {
                try {
                    month = Integer.parseInt(dateParts[0]); // Lấy tháng
                    year = Integer.parseInt(dateParts[1]);  // Lấy năm

                    // In ra tháng và năm để kiểm tra
                    Log.d("DateInfo", "Month: " + month + ", Year: " + year);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.e("DateError", "Lỗi định dạng số trong chuỗi ngày tháng.");
                }
            } else {
                Log.e("DateError", "Chuỗi ngày tháng không đúng định dạng.");
            }
        } else {
            Log.e("DateError", "Chuỗi ngày tháng không đúng định dạng.");
        }

        // Handle button next or previous month
        btnNextMonth = findViewById(R.id.btnNextMonth);
        btnPreMonth = findViewById(R.id.btnPreMonth);

        // Tab selector
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Phần chi");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Phần thu");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);


        pieChart = findViewById(R.id.piechart);
        pieChart2 = findViewById(R.id.piechart2);
        listViewRank = findViewById(R.id.listViewRank);
        listViewRank2 = findViewById(R.id.listViewRank2);

        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month++;
                if (month > 12) {
                    month = 1;
                    year++;
                }
                updateDateText();
                statistic();
            }
        });

        btnPreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month--;
                if (month < 1) {
                    month = 12;
                    year--;
                }
                updateDateText();
                statistic();
            }
        });

        statistic();
    }

    private void updateDateText() {
        txt_date.setText("Tháng " + month + "/" + year);
    }

    private void statistic(){
        // Execute SQL
        connect_database connectDatabase = new connect_database();
        connectDatabase.create_connect_database();
        StatisticSql sql = new StatisticSql();
        BigDecimal income = sql.getIncome(user.id_user, month, year);
        BigDecimal outcome = sql.getOutcome(user.id_user, month, year);
        if (income != null && outcome != null) {
            BigDecimal total = income.subtract(outcome);
            txtIncome.setText(String.valueOf(income.intValue()));
            txtOutcome.setText(String.valueOf(outcome.intValue()));
            txtTotal.setText(String.valueOf(total.intValue()));
        }
        else if (income != null) {
            txtIncome.setText(String.valueOf(income.intValue()));
            txtOutcome.setText("0");
            txtTotal.setText(String.valueOf(income.intValue()));
        }
        else if (outcome != null) {
            txtIncome.setText("0");
            txtOutcome.setText(String.valueOf(outcome.intValue()));
            txtTotal.setText("-"+ String.valueOf(outcome.intValue()));
        }
        else {
            txtIncome.setText("0");
            txtOutcome.setText("0");
            txtTotal.setText("0");
        }

        // Clear existing entries
        if (pieEntriesOutcome != null) {
            pieEntriesOutcome.clear();
        }
        if (pieEntriesIncome != null) {
            pieEntriesIncome.clear();
        }
        listViewRank.setAdapter(null);
        listViewRank2.setAdapter(null);
        // Remove description label
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart2.setDescription(description);

        // Ranking outcome
        // Get top 3 expense
        List<StatisticItemDisplay> statisticItemDisplays = new ArrayList<>();
        List<StatisticItem> statisticOutcomeItems = sql.getTop3RankOutcome(month, year);
        for (StatisticItem item : statisticOutcomeItems) {
            String type = sql.getType(item.getTitle());
            item.setTitle(type);
            for (int i = 0; i < images.length; i++) {
                if (type.equals(names[i])) {
                    item.setImage(images[i]);
                    break;
                }
            }
            StatisticItemDisplay statisticItemDisplay = new StatisticItemDisplay();
            statisticItemDisplay.setImage(item.getImage());
            statisticItemDisplay.setTitle(item.getTitle());
            statisticItemDisplay.setMoney(item.getMoney().intValue());
            statisticItemDisplays.add(statisticItemDisplay);
        }
        ArrayAdapterListViewRank arrayAdapterListViewRank = new ArrayAdapterListViewRank(Dat_PieChartActivity.this, R.layout.dat_rank_iteminfo, statisticItemDisplays);
        listViewRank.setAdapter(arrayAdapterListViewRank);
        setValuesOutcome(statisticOutcomeItems, outcome);
        setUpChartOutcome();

        // Ranking income
        // Get top 3 income
        List<StatisticItemDisplay> statisticItemDisplays2 = new ArrayList<>();
        List<StatisticItem> statisticIncomeItems = sql.getTop3RankIncome(month, year);
        for (StatisticItem item : statisticIncomeItems) {
            String type = sql.getType(item.getTitle());
            item.setTitle(type);
            for (int i = 0; i < images.length; i++) {
                if (type.equals(names[i])) {
                    item.setImage(images[i]);
                    break;
                }
            }
            StatisticItemDisplay statisticItemDisplay = new StatisticItemDisplay();
            statisticItemDisplay.setImage(item.getImage());
            statisticItemDisplay.setTitle(item.getTitle());
            statisticItemDisplay.setMoney(item.getMoney().intValue());
            statisticItemDisplays2.add(statisticItemDisplay);
        }
        ArrayAdapterListViewRank arrayAdapterListViewRank2 = new ArrayAdapterListViewRank(Dat_PieChartActivity.this, R.layout.dat_rank_iteminfo, statisticItemDisplays2);
        listViewRank2.setAdapter(arrayAdapterListViewRank2);
        setValuesIncome(statisticIncomeItems, income);
        setUpChartIncome();
    }

    private void setUpChartOutcome(){
        PieDataSet pieDataSet = new PieDataSet(pieEntriesOutcome, "");// Null label like pie chart
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieData.setValueTextSize(15f);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);
        // Enable percentage values
        pieChart.setUsePercentValues(true);
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setDrawInside(false);
        legend.setXEntrySpace(25f);
        legend.setFormSize(15f);
        legend.setFormToTextSpace(3f);
        pieChart.invalidate();
    }

    private void setUpChartIncome(){
        PieDataSet pieDataSet = new PieDataSet(pieEntriesIncome, "");// Null label like pie chart
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        pieData.setValueTextSize(15f);
        pieData.setValueFormatter(new PercentFormatter(pieChart2));
        pieChart2.setData(pieData);
        // Enable percentage values
        pieChart2.setUsePercentValues(true);
        Legend legend = pieChart2.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setDrawInside(false);
        legend.setXEntrySpace(25f);
        legend.setFormSize(15f);
        legend.setFormToTextSpace(3f);
        pieChart2.invalidate();
    }

    private void setValuesOutcome(List<StatisticItem> statisticOutcomeItems, BigDecimal total){
        if (pieEntriesOutcome != null) {
            pieEntriesOutcome.clear();
        }

        if (statisticOutcomeItems.size() > 3) {
            assert pieEntriesOutcome != null;
            pieEntriesOutcome.add(new PieEntry(statisticOutcomeItems.get(0).getMoney().intValue(), statisticOutcomeItems.get(0).getTitle()));
            pieEntriesOutcome.add(new PieEntry(statisticOutcomeItems.get(1).getMoney().intValue(), statisticOutcomeItems.get(1).getTitle()));
            pieEntriesOutcome.add(new PieEntry(statisticOutcomeItems.get(2).getMoney().intValue(), statisticOutcomeItems.get(2).getTitle()));
            pieEntriesOutcome.add(new PieEntry(total.subtract(statisticOutcomeItems.get(0).getMoney()).subtract(statisticOutcomeItems.get(1).getMoney()).subtract(statisticOutcomeItems.get(2).getMoney()).intValue(), "Another"));
        } else {
            // Handle the case where there are fewer than 3 items
            for (StatisticItem item : statisticOutcomeItems) {
                pieEntriesOutcome.add(new PieEntry(item.getMoney().intValue(), item.getTitle()));
            }
        }

        if (pieEntriesOutcome.stream().anyMatch(e -> e.getValue() == 0)) {
            pieEntriesOutcome.removeIf(e -> e.getValue() == 0);
        }
    }

    private void setValuesIncome(List<StatisticItem> statisticIncomeItems, BigDecimal total) {
        if (pieEntriesIncome != null) {
            pieEntriesIncome.clear();
        }

        if (statisticIncomeItems.size() >= 3) {
            pieEntriesIncome.add(new PieEntry(statisticIncomeItems.get(0).getMoney().intValue(), statisticIncomeItems.get(0).getTitle()));
            pieEntriesIncome.add(new PieEntry(statisticIncomeItems.get(1).getMoney().intValue(), statisticIncomeItems.get(1).getTitle()));
            pieEntriesIncome.add(new PieEntry(statisticIncomeItems.get(2).getMoney().intValue(), statisticIncomeItems.get(2).getTitle()));
            pieEntriesIncome.add(new PieEntry(total.subtract(statisticIncomeItems.get(0).getMoney()).subtract(statisticIncomeItems.get(1).getMoney()).subtract(statisticIncomeItems.get(2).getMoney()).intValue(), "Another"));
        } else {
            // Handle the case where there are fewer than 3 items
            for (StatisticItem item : statisticIncomeItems) {
                pieEntriesIncome.add(new PieEntry(item.getMoney().intValue(), item.getTitle()));
            }
        }

        if (pieEntriesIncome.stream().anyMatch(e -> e.getValue() == 0)) {
            pieEntriesIncome.removeIf(e -> e.getValue() == 0);
        }
    }
}