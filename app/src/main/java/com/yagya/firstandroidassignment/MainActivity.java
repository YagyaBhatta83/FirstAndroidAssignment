package com.yagya.firstandroidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText adult, children, room;
    Spinner roomtype;
    Button btncalculate;
    TextView checkin, checkout,grandtotal;

    public String roomType[] = {"--Select Room Type--","Deluxe", "Presidential", "Premium"}, result;

    public int noOfRooms, noOfDays, price;
    public double total, vat, grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkin = findViewById(R.id.checkin);
        checkout = findViewById(R.id.checkout);
        roomtype = findViewById(R.id.roomtype);
        adult = findViewById(R.id.adult);
        children = findViewById(R.id.children);
        room = findViewById(R.id.room);
        btncalculate = findViewById(R.id.btnCalculate);
        grandtotal = findViewById(R.id.grandtotal);

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker("dateIn");
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkin.getText().toString().isEmpty()) {
                    checkout.setError("Please Choose Check-In Date!");
                    return;
                } else {
                    loadDatePicker("dateOut");
                }
            }
        });

        ArrayAdapter adapterRoom = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                roomType
        );

        roomtype.setAdapter(adapterRoom);

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


//                        Calculating Number of Days
                    String dateIn = checkin.getText().toString();
                    String dateOut = checkout.getText().toString();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
                    Date in = simpleDateFormat.parse(dateIn);
                    Date out = simpleDateFormat.parse(dateOut);
                    long diff = Math.abs(in.getTime() - out.getTime());
                    long diffInDays = diff / (24 * 60 * 60 * 1000);

                    noOfDays = Integer.parseInt(diffInDays + "");
                    noOfRooms = Integer.parseInt(room.getText() + "");
                    if (roomtype.getSelectedItem() == "Deluxe") {
                        price = 2000;
                    } else if (roomtype.getSelectedItem() == "Premium") {
                        price = 4000;
                    } else {
                        price = 5000;
                    }

                    total = noOfDays * noOfRooms * price;
                    vat = 0.13 * total;
                    grandTotal = total + vat;
                    result = "Total : Rs." + total + "\nVAT(13%) : Rs." + vat + "\nGrand Total : Rs." + grandTotal;

                    grandtotal.setText(result);
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });

    }

    private void loadDatePicker(final String btnType) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;

                if (btnType == "dateIn") {
                    checkin.setText(date);
                } else {

                    checkout.setText(date);
                }
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}