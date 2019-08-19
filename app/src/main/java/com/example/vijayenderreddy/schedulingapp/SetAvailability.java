package com.example.vijayenderreddy.schedulingapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetAvailability extends AppCompatActivity {
    String[] days ={"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_availability);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,days);
        AutoCompleteTextView dayTextView = (AutoCompleteTextView) findViewById(R.id.availdayedt);
        dayTextView.setThreshold(1);
        dayTextView.setAdapter(dayAdapter);



        TextView textView,textView1,textView2;
        final EditText editText,editText1;
        Button  setavailabilitybtn,avabilityhomebtn;

        textView=(TextView)findViewById(R.id.availdaytxt);
        textView1=(TextView)findViewById(R.id.availstarttxt);
        textView2=(TextView)findViewById(R.id.availendtxt);


        editText=(EditText)findViewById(R.id.availstartedt);
        editText1=(EditText)findViewById(R.id.availendedt);

        setavailabilitybtn=(Button)findViewById(R.id.setavailabilitybtn);
        avabilityhomebtn=(Button)findViewById(R.id.setavailabhomebtn);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText1.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



        setavailabilitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        avabilityhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SetAvailability.this, HomePage.class);
                startActivity(i);
            }
        });


    }
}