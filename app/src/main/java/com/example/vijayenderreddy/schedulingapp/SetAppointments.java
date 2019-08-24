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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetAppointments extends AppCompatActivity {
     Button invitebutton;
     ImageButton selectDate,selectStartTime,selectEndTime;
    static TextView DateTxt;
    TextView StartTime,EndTime;
    TextView textView,textView1,textView2,textView3,textView4,textView5;
    EditText RoomNum;
    Spinner CategoryDropDown;
    String[] category = { "Group Discusion","Finance","Semister Exams","Schedule","Project","Admissions" };

    Calendar mcurrentTime;
    int currentHour,currentMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_appointments);

        CategoryDropDown = findViewById(R.id.categedt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SetAppointments.this,android.R.layout.simple_list_item_1,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CategoryDropDown.setAdapter(adapter);

       textView=(TextView)findViewById(R.id.appdatetxt);
        textView2=(TextView)findViewById(R.id.starttimtxt);
        textView3=(TextView)findViewById(R.id.endtimtxt);
        textView4=(TextView)findViewById(R.id.roomnotxt);
        textView5=(TextView)findViewById(R.id.categtxt);


        DateTxt= (TextView) findViewById(R.id.aptdateedt);
        StartTime=(TextView)findViewById(R.id.starttimeedt);
        EndTime=(TextView) findViewById(R.id.endtimeedt);
        RoomNum=(EditText)findViewById(R.id.roomnoedt);

        mcurrentTime = Calendar.getInstance();
        currentHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        currentMinute = mcurrentTime.get(Calendar.MINUTE);

        selectStartTime = findViewById(R.id.selectStartTimeBtn);
        selectStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sdf.format(new Date());
                        if (DateTxt.getText().toString().trim().equals("Select Date")){
                            Toast.makeText(getApplicationContext(),"please select date first",Toast.LENGTH_LONG).show();
                        }
                        else if((DateTxt.getText().toString().trim().equals(currentDate)) && selectedHour<=currentHour) {
                            if(selectedMinute<=currentMinute) {
                                Toast.makeText(getApplicationContext(), "Please enter valid time", Toast.LENGTH_LONG).show();
                            }
                            else {
                                StartTime.setText(String.format("%02d:%02d",selectedHour,selectedMinute));
                            }
                            }
                            else {
                                StartTime.setText(String.format("%02d:%02d",selectedHour,selectedMinute));
                            }}
                }, currentHour, currentMinute, true);
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();

            }
        });


        selectEndTime = findViewById(R.id.selectEndTimeBtn);
        selectEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(StartTime.getText().toString().trim().equals("Select Start Time")){
                            Toast.makeText(getApplicationContext(),"Please select start time first",Toast.LENGTH_LONG).show();

                        }
                        else if(selectedHour<Integer.parseInt(StartTime.getText().toString().trim().substring(0,2))){
                            Toast.makeText(getApplicationContext(),"Please select valid time",Toast.LENGTH_LONG).show();

                        }
                        else if(selectedHour==Integer.parseInt(StartTime.getText().toString().trim().substring(0,2))&&selectedMinute<=Integer.parseInt(StartTime.getText().toString().trim().substring(3,5))){
                            Toast.makeText(getApplicationContext(),"Please select valid time",Toast.LENGTH_LONG).show();

                        }
                        else {
                            EndTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        }
                    }
                }, currentHour, currentMinute, true);
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();

            }
        });


        invitebutton=(Button)findViewById(R.id.invitebtn);

        invitebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((DateTxt.getText().toString().trim()).equals("Select Date")|(StartTime.getText().toString().trim()).equals("Select Start Time")|(EndTime.getText().toString().trim()).equals("Select End Time")|(RoomNum.getText().toString().trim()).equals("")|Integer.parseInt(RoomNum.getText().toString())<1){
                    Toast.makeText(getApplicationContext(),"Enter Valid Details",Toast.LENGTH_LONG).show();
                    return;
                }
                String stime= (StartTime.getText().toString().trim()).replace(":","");
                String etime= (EndTime.getText().toString().trim()).replace(":","");
                Intent i = new Intent(SetAppointments.this,InviteSelectStaff.class);
                i.putExtra("AptDate",DateTxt.getText().toString().trim());
                i.putExtra("StartTime",stime);
                i.putExtra("EndTime",etime);
                i.putExtra("RoomNo",RoomNum.getText().toString().trim());
                i.putExtra("Category",String.valueOf(CategoryDropDown.getSelectedItemPosition()+1));
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });

        selectDate= (ImageButton) findViewById(R.id.datebutton);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datepicker");
            }
        });

    }

    private void compareto(ImageButton selectEndTime) {
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
            DateTxt.setText(years+"-"+months+"-"+days);


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
            datePickerDialog .getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

    }


}