package com.example.monkeeapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monkeeapp.Dat.adapter.MyArrayAdapter;
import com.example.monkeeapp.Dat.sql.ExpenseSql;
import com.example.monkeeapp.Dat.util.Expense;
import com.example.monkeeapp.Dat.util.Type;
import com.example.monkeeapp.Database.connect_database;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Dat_AddExpenseActivity extends AppCompatActivity {
    int[] image = {R.drawable.icon_eat, R.drawable.icon_shopping, R.drawable.icon_entertainment, R.drawable.icon_phone, R.drawable.icon_education, R.drawable.icon_beautify, R.drawable.icon_vehicle, R.drawable.icon_sport, R.drawable.icon_repair, R.drawable.icon_tourim,
            R.drawable.icon_social, R.drawable.icon_health, R.drawable.icon_home, R.drawable.icon_electricity, R.drawable.icon_gift, R.drawable.icon_pet, R.drawable.icon_donate, R.drawable.icon_child, R.drawable.icon_electronic_device, R.drawable.icon_other};
    String[] name = {"Ăn uống", "Mua sắm", "Giải trí", "Điện thoại", "Giáo dục", "Làm đẹp", "Phương tiện", "Thể thao", "Sửa chữa", "Du lịch",
            "Xã hội", "Sức khỏe", "Nhà", "Điện nước", "Quà tặng", "Thú cưng", "Quyên góp", "Con cái", "Điện tử", "Khác" };
    GridView gv;
    ArrayList<Type> myList = new ArrayList<>();
    EditText edt_date, edt_note, edt_money;
    MyArrayAdapter myArrayAdapter;
    LinearLayout previousSelectedLayout = null;
    Button btn_save,btn_thu, btn_chi;
    ImageButton btn_date, btn_back;
    ExpenseSql expenseSql;
    String choose, categoryId, userId;
    TextView txtTitle;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dat_add_expense_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        try {
            edt_date = findViewById(R.id.edtDate);
            edt_note = findViewById(R.id.edit_note);
            edt_money = findViewById(R.id.edtMoney);
            btn_save = findViewById(R.id.btnSave);
            btn_chi = findViewById(R.id.btnChi);
            btn_thu = findViewById(R.id.btnThu);
            txtTitle = findViewById(R.id.txtTitle);
            btn_date = findViewById(R.id.btndate);
            btn_back = findViewById(R.id.btnBack);

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btn_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogDate();
                }
            });

            for (int i = 0; i < image.length; i++) {
                myList.add(new Type(image[i], name[i]));
            }
            myArrayAdapter = new MyArrayAdapter(Dat_AddExpenseActivity.this, R.layout.dat_sub_activity, myList);
            gv = findViewById(R.id.gv_expense);
            gv.setAdapter(myArrayAdapter);

            // Khởi tạo kết nối cơ sở dữ liệu
            connect_database dbConnection = new connect_database();
            dbConnection.create_connect_database();

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Đặt lại màu nền của layout trước đó nếu có
                    if (previousSelectedLayout != null) {
                        previousSelectedLayout.setBackgroundResource(R.drawable.dat_border_linear);  // Màu nền mặc định
                    }

                    // Lấy layout hiện tại và thay đổi màu nền
                    LinearLayout currentLayout = view.findViewById(R.id.ln_sub1);
                    currentLayout.setBackgroundResource(R.drawable.dat_border_pink);  // Màu nền khi được chọn

                    // Cập nhật biến lưu trữ layout đã chọn trước đó
                    previousSelectedLayout = currentLayout;

                    choose = name[position];
                    ExpenseSql expenseSql = new ExpenseSql();
                    categoryId = expenseSql.getCategoryId(choose);
                    userId = expenseSql.getUserId("dat");

                    // Hiển thị thông báo
                    Toast.makeText(Dat_AddExpenseActivity.this, userId + " " + categoryId, Toast.LENGTH_SHORT).show();
                }
            });

            btn_thu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_chi.setTextColor(getResources().getColor(R.color.black));
                    btn_chi.setBackgroundResource(R.drawable.dat_btn_change_white);
                    btn_thu.setTextColor(getResources().getColor(R.color.white));
                    btn_thu.setBackgroundResource(R.drawable.dat_btn_change_pink);
                    flag = 1;
                }
            });

            btn_chi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_thu.setTextColor(getResources().getColor(R.color.black));
                    btn_thu.setBackgroundResource(R.drawable.dat_btn_add_expense);
                    btn_chi.setTextColor(getResources().getColor(R.color.white));
                    btn_chi.setBackgroundResource(R.drawable.dat_btn_square);
                    flag = 0;
                }
            });
            btn_save.setOnClickListener(new View.OnClickListener() {
                String money;
                @Override
                public void onClick(View v) {
                    try {
                        money = edt_money.getText().toString().replaceAll(" ", "");
                        //Lưu vào database
                        Expense expense = new Expense();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String date;
                        try {
                            date = simpleDateFormat1.format(Objects.requireNonNull(simpleDateFormat.parse(edt_date.getText().toString())));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        expense.setDate(Date.valueOf(date));
                        expense.setNote(edt_note.getText().toString());
                        if (flag == 0) {
                            expense.setType("CHI");
                        } else {
                            expense.setType("THU");
                        }
                        try {
                            ExpenseSql expenseSql = new ExpenseSql();
                            boolean result = expenseSql.add_expense(expense.getId(), userId, categoryId, expense.getDate(), expense.getNote(), BigDecimal.valueOf(Long.parseLong(money)), expense.getType());
                            if (result) {
                                Toast.makeText(Dat_AddExpenseActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            Toast.makeText(Dat_AddExpenseActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(Dat_AddExpenseActivity.this, "Hãy điền đầy đủ các mục", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            edt_money.addTextChangedListener(new TextWatcher() {
                private String current = "";
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(current)) {
                        edt_money.removeTextChangedListener(this);

                        String cleanString = s.toString().replaceAll("[^\\d]", "");

                        if (!cleanString.isEmpty()) {
                            double parsed = Double.parseDouble(cleanString);
                            DecimalFormat formatter = new DecimalFormat("#,###");
                            formatter.setDecimalFormatSymbols(new DecimalFormatSymbols() {
                                {
                                    setGroupingSeparator(' ');
                                }
                            });
                            String formatted = formatter.format(parsed);

                            current = formatted;
                            edt_money.setText(formatted);
                            edt_money.setSelection(formatted.length());
                        }

                        edt_money.addTextChangedListener(this);
                    }
                }
            });
        }
        catch (Exception e){
            Toast.makeText(Dat_AddExpenseActivity.this, "Hãy điền đầy đủ các mục", Toast.LENGTH_SHORT).show();
        }
    }
    private void openDialogDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString;
                String dayString;
                if (dayOfMonth < 10){
                    dayString = "0" + dayOfMonth;
                }
                else {
                    dayString = "" + dayOfMonth;
                }

                if (month <10){
                    monthString = "0" + (month + 1);
                }
                else {
                    monthString = "" + (month + 1);
                }
                edt_date.setText(dayString + "/" + monthString + "/" + year);
            }
        },2024, 5, 31);

        datePickerDialog.show();
    }
}