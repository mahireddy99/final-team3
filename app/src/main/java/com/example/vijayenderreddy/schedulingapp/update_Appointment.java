package com.example.vijayenderreddy.schedulingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import java.util.Calendar;

public class update_Appointment extends AppCompatActivity {

    TextView StaffName,CategoryName;
    EditText AptRoomNum;
    static TextView AptDate;
    TextView AptSTime,AptETime;
    ImageButton USelectDate,USTimeBtn,UETimeBtn;
    String AptID = "";
    Button UpdateBtn;

    Calendar mcurrentTime;
    int currentHour,currentMinute;
    String ampm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__appointment);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        StaffName = findViewById(R.id.Ustaff_Name);
        CategoryName = findViewById(R.id.UcategoryName);
        AptDate = findViewById(R.id.aptDate);
        AptSTime = findViewById(R.id.AptSTime);
        AptETime = findViewById(R.id.AptETime);
        AptRoomNum = findViewById(R.id.AptRoomNum);
        UpdateBtn = findViewById(R.id.update_apt_button);

        USelectDate = findViewById(R.id.Udatebutton);
        USTimeBtn = findViewById(R.id.USTimeBtn);
        UETimeBtn = findViewById(R.id.UETimebtn);

        mcurrentTime = Calendar.getInstance();
        currentHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        currentMinute = mcurrentTime.get(Calendar.MINUTE);

        USTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        AptSTime.setText(String.format("%02d:%02d",selectedHour,selectedMinute));
                    }
                }, currentHour, currentMinute, false);
                mTimePicker.setTitle("Select Start Time");
                mTimePicker.show();
            }
        });


        UETimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(update_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            if(AptSTime.getText().toString().trim().equals("Select Start Time")){
                                Toast.makeText(getApplicationContext(),"Please select start time first",Toast.LENGTH_LONG).show();

                            }
                            else if(selectedHour<Integer.parseInt(AptSTime.getText().toString().trim().substring(0,2))){
                                Toast.makeText(getApplicationContext(),"Please select valid time",Toast.LENGTH_LONG).show();

                            }
                            else {


                               AptETime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                    }
                }, currentHour, currentMinute, true);
                mTimePicker.setTitle("Select End Time");
                mTimePicker.show();
            }
        });

        USelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment= new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datepicker");
            }
        });

        AptID = getIntent().getStringExtra("AptId");
        StaffName.setText("With "+getIntent().getStringExtra("StaffName"));
        CategoryName.setText("Category: "+getIntent().getStringExtra("Category"));
        AptDate.setText(getIntent().getStringExtra("AptDate"));
        String StartTime = getIntent().getStringExtra("StartTime");
        String EndTime = getIntent().getStringExtra("EndTime");
        if(StartTime.length()==3){
            StartTime = "0"+StartTime;
        }
        if(EndTime.length()==3){
            EndTime = "0"+EndTime;
        }
        StartTime = StartTime.substring(0,2)+":"+StartTime.substring(2,4);
        EndTime = EndTime.substring(0,2)+":"+EndTime.substring(2,4);
        AptSTime.setText(StartTime);
        AptETime.setText(EndTime);
        AptRoomNum.setText(getIntent().getStringExtra("RoomNumber"));

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((AptRoomNum.getText().toString().trim()).equals("")){
                    Toast.makeText(getApplicationContext(),"Enter Valid Details.",Toast.LENGTH_LONG).show();
                    return;
                }
                String UpdateSTime = AptSTime.getText().toString().replace(":","");
                String UpdateETime = AptETime.getText().toString().replace(":","");
                new MyTask(AptID,UpdateSTime,UpdateETime,AptDate.getText().toString(),AptRoomNum.getText().toString()).execute();
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
            AptDate.setText(years+"-"+months+"-"+days);


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
        String aptid,aptstime,aptetime,aptdate,aptroom;
        public MyTask(String aptid,String aptstime,String aptetime,String aptdate,String aptroom){
            this.aptid = aptid;
            this.aptstime = aptstime;
            this.aptetime = aptetime;
            this.aptdate = aptdate;
            this.aptroom = aptroom;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;






            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/updateappointmentwithStaff&"+aptid+"&"+aptdate+"&"+aptstime+"&"+aptetime+"&"+aptroom);

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
                Toast.makeText(getApplicationContext(),"Updated invitation sent Successfully.",Toast.LENGTH_LONG).show();
                Intent i = new Intent(update_Appointment.this, HomePage.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Could not update the appointment.",Toast.LENGTH_LONG).show();
            }


        }

    }

}
