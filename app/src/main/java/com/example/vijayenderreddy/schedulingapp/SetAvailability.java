package com.example.vijayenderreddy.schedulingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetAvailability extends AppCompatActivity {

    TextView AvlbSTime,AvlbETime;
    static TextView AvlbSelectDate;
    ImageButton selectDateBtn,selectSTimeBtn,selectETimeBtn;

    Calendar mcurrentTime;
    int currentHour,currentMinute;
    String ampm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_availability);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        Button  setavailabilitybtn,avabilityhomebtn;

        AvlbSelectDate=(TextView)findViewById(R.id.Avlbselectdate);
        AvlbSTime=(TextView)findViewById(R.id.AvlbSTime);
        AvlbETime=(TextView)findViewById(R.id.AvlbETime);
        setavailabilitybtn=(Button)findViewById(R.id.setavailabilitybtn);
        avabilityhomebtn=(Button)findViewById(R.id.setavailabhomebtn);
        selectDateBtn = findViewById(R.id.AvlbSelectDatebtn);
        selectSTimeBtn = findViewById(R.id.AvlbSTimebtn);
        selectETimeBtn = findViewById(R.id.AvlbETimeBtn);

        mcurrentTime = Calendar.getInstance();
        currentHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        currentMinute = mcurrentTime.get(Calendar.MINUTE);

        selectSTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour>=12){
                            ampm="PM";
                        } else{
                            ampm="AM";
                        }
                        AvlbSTime.setText(String.format("%02d:%02d",selectedHour,selectedMinute)+ampm);
                    }
                }, currentHour, currentMinute, false);
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();
            }
        });

        selectETimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour>=12){
                            ampm="PM";
                        } else{
                            ampm="AM";
                        }
                        AvlbETime.setText(String.format("%02d:%02d",selectedHour,selectedMinute)+ampm);
                    }
                }, currentHour, currentMinute, true);
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();
            }
        });

        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment=new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datepicker");
            }
        });

        setavailabilitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((AvlbSelectDate.getText().toString().trim()).equals("Select Date")|(AvlbSTime.getText().toString().trim()).equals("Select Start Time")|(AvlbETime.getText().toString().trim()).equals("Select End Time")){
                    Toast.makeText(getApplicationContext(),"Enter Valid Details",Toast.LENGTH_LONG).show();
                    return;
                }

                String stime= (AvlbSTime.getText().toString().trim()).replace(":","");
                String etime= (AvlbETime.getText().toString().trim()).replace(":","");
                Date date = new Date();
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = date_format.parse(AvlbSelectDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String day = (String) DateFormat.format("EEEE", date);
                if(getIntent()!=null) {
                    new MyTask(PreferenceData.getLoggedInEmailUser(getApplicationContext()), stime, etime, AvlbSelectDate.getText().toString(), day.toUpperCase()).execute();
                }
                else {
                    Toast.makeText(getApplicationContext(),"You Must Login First.",Toast.LENGTH_LONG).show();
                }
            }
        });

        avabilityhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SetAvailability.this, HomePage.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
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
            AvlbSelectDate.setText(years+"-"+months+"-"+days);
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

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String staffid,StartTime,EndTime,AVAILABILEDATE,AVAILABILEDAY;
        public MyTask(String staffid,String StartTime,String EndTime,String AVAILABILEDATE,String AVAILABILEDAY){
            this.staffid = staffid;
            this.StartTime = StartTime;
            this.EndTime = EndTime;
            this.AVAILABILEDATE = AVAILABILEDATE;
            this.AVAILABILEDAY = AVAILABILEDAY;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;






            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/setaAvilability&"+staffid+"&"+StartTime+"&"+EndTime+"&"+AVAILABILEDATE+"&"+AVAILABILEDAY);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);

                InputStreamReader myInput= new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject obj =new JSONObject(response.toString());
                message = obj.getString("message");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return message;

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            if(result.equals("Successfull")){
                Toast.makeText(getApplicationContext(),"Availability has been set.",Toast.LENGTH_LONG).show();
                Intent i = new Intent(SetAvailability.this, HomePage.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Could not set the availability",Toast.LENGTH_LONG).show();
            }


        }

    }


}