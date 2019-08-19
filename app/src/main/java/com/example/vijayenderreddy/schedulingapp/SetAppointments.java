package com.example.vijayenderreddy.schedulingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetAppointments extends AppCompatActivity {
     Button button;
    static EditText editText;

    String[] category = { "Group Discussion","FINANCE","SEMESTER EXAMS","SCHEDULE","PROJECT","ADMISSIONS" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_appointments);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,category);
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.categedt);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);



        TextView textView,textView1,textView2,textView3,textView4,textView5;
        final EditText editText1,editText2,editText3,editText4,editText5;
        Button invitebutton,button;

       textView=(TextView)findViewById(R.id.appdatetxt);
        textView2=(TextView)findViewById(R.id.starttimtxt);
        textView3=(TextView)findViewById(R.id.endtimtxt);
        textView4=(TextView)findViewById(R.id.roomnotxt);
        textView5=(TextView)findViewById(R.id.categtxt);


        editText1 = findViewById(R.id.aptdateedt);
        editText2=(EditText)findViewById(R.id.starttimeedt);
        editText3=(EditText)findViewById(R.id.endtimeedt);
        editText4=(EditText)findViewById(R.id.roomnoedt);
        editText5 = findViewById(R.id.categedt);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editText3.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        invitebutton=(Button)findViewById(R.id.invitebtn);

        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SetAppointments.this,InviteSelectStaff.class);
                i.putExtra("AptDate",editText1.getText().toString().trim());
                i.putExtra("StartTime",editText2.getText().toString().trim());
                i.putExtra("EndTime",editText3.getText().toString().trim());
                i.putExtra("RoomNo",editText4.getText().toString().trim());
                i.putExtra("Category",editText5.getText().toString().trim());
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });

        button= (Button) findViewById(R.id.datebutton);
        editText= (EditText) findViewById(R.id.aptdateedt);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datepicker");
            }
        });

    }

    public static class DatePickerFragment extends DialogFragment implements    DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int  day) {
            String years=""+year;
            String months=""+(monthOfYear+1);
            String days=""+day;
            if(monthOfYear>=0 && monthOfYear<9){
                months="0"+(monthOfYear+1);
            }
            if(day>0 && day<10){
                days="0"+day;

            }
            editText.setText(days+"/"+months+"/"+years);


        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //use the current date as the default date in the picker
            final Calendar c=Calendar.getInstance();
            int year=c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog=null;
            datePickerDialog=new DatePickerDialog(getActivity(), this, year,  month, day);

            return datePickerDialog;
        }









    }


}