package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentAppointments extends AppCompatActivity {

    CheckBox currentapt1, currentapt2, currentapt3;
    JSONObject MainObj;
    Button updatebutton, backbutton, deletebtn;
    TextView NoCurrentApt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_appointments);

        if (PreferenceData.getLoggedInEmailUser(this).equals("")) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        NoCurrentApt = findViewById(R.id.nocurapttxt);

        deletebtn = (Button) findViewById(R.id.deletebtn);
        updatebutton = (Button) findViewById(R.id.updatebtn);
        backbutton = (Button) findViewById(R.id.capthome);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CurrentAppointments.this, HomePage.class);
                i.putExtra("staffid", getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });

        currentapt1 = findViewById(R.id.curapt1);
        currentapt2 = findViewById(R.id.curapt2);
        currentapt3 = findViewById(R.id.curapt3);

        currentapt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentapt1.isChecked()) {
                    currentapt2.setChecked(false);
                    currentapt3.setChecked(false);
                }
            }
        });
        currentapt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentapt2.isChecked()) {
                    currentapt1.setChecked(false);
                    currentapt3.setChecked(false);
                }
            }
        });

        currentapt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentapt3.isChecked()) {
                    currentapt1.setChecked(false);
                    currentapt2.setChecked(false);
                }
            }
        });

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        new MyTask(PreferenceData.getLoggedInEmailUser(this), date).execute();

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject childObject = null;
                if (currentapt1.isChecked()) {
                    try {
                        childObject = MainObj.getJSONObject(String.valueOf(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (currentapt2.isChecked()) {
                    try {
                        childObject = MainObj.getJSONObject(String.valueOf(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (currentapt3.isChecked()) {
                    try {
                        childObject = MainObj.getJSONObject(String.valueOf(2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (!currentapt1.isChecked() && !currentapt2.isChecked() && !currentapt3.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Please select an appointment", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(CurrentAppointments.this, update_Appointment.class);
                i.putExtra("userLoggedInId", getIntent().getStringExtra("staffid"));
                String AptId = "", StartTime = "", EndTime = "", AptDate = "", RoomNumber = "", Category = "", StaffName = "";
                try {
                    AptId = childObject.getString("aptId");
                    StartTime = childObject.getString("startTime");
                    EndTime = childObject.getString("endTime");
                    AptDate = childObject.getString("AppointmentDate");
                    RoomNumber = childObject.getString("roomNumber");
                    Category = childObject.getString("Category");
                    StaffName = childObject.getString("StaffName");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.putExtra("AptId", AptId);
                i.putExtra("StartTime", StartTime);
                i.putExtra("EndTime", EndTime);
                i.putExtra("AptDate", AptDate);
                i.putExtra("RoomNumber", RoomNumber);
                i.putExtra("Category", Category);
                i.putExtra("StaffName", StaffName);

                startActivity(i);

            }
        });

    deletebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!currentapt1.isChecked() && !currentapt2.isChecked() && !currentapt3.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please select an appointment", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i <= 2; i++) {
            String APPOINTMENTID = "", APPOINTMENTSTATUS = "PENDING";
            try {
                JSONObject childObject = MainObj.getJSONObject(String.valueOf(i));
                APPOINTMENTID = childObject.getString("aptId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (i) {
                case 0:
                    if (currentapt1.isChecked()) {
                        new MyTask1(APPOINTMENTID, APPOINTMENTSTATUS).execute();
                    }
                    break;
                case 1:
                    if (currentapt2.isChecked()) {
                        new MyTask1(APPOINTMENTID, APPOINTMENTSTATUS).execute();
                    }
                    break;
                case 2:
                    if (currentapt3.isChecked()) {
                        new MyTask1(APPOINTMENTID, APPOINTMENTSTATUS).execute();
                    }
                    break;

            }
        }
        }
    });
}


private class MyTask1 extends AsyncTask<Void, Void, String> {
        String message;
        String APPOINTMENTID,APPOINTMENTSTATUS;
        public MyTask1(String APPOINTMENTID,String APPOINTMENTSTATUS){
            this.APPOINTMENTID = APPOINTMENTID;
            this.APPOINTMENTSTATUS = APPOINTMENTSTATUS;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;


            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/deleteappointment&"+APPOINTMENTID+"&"+APPOINTMENTSTATUS);

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
                Toast.makeText(getApplicationContext(),"deleted successfully",Toast.LENGTH_LONG).show();
                Intent i = new Intent(CurrentAppointments.this, CurrentAppointments.class);
                startActivity(i);
                finish();
            }


        }

    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String senderID;
        String AptDate;
        public MyTask(String senderID,String AptDate){
            this.senderID = senderID;
            this.AptDate = AptDate;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/currentappointmet&"+senderID+"&"+AptDate);

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

                message = response.toString();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            return message;

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result.equals("{}")){
                NoCurrentApt.setVisibility(View.VISIBLE);
                updatebutton.setVisibility(View.GONE);
                deletebtn.setVisibility(View.GONE);
                return;
            }
            try {
                MainObj =new JSONObject(result);
                for(int i = 0;i<=2;i++){
                    if(MainObj.has(String.valueOf(i))){
                        JSONObject jsonObject = MainObj.getJSONObject(String.valueOf(i));
                        String StartTime = jsonObject.getString("startTime");
                        String EndTime = jsonObject.getString("endTime");
                        if(StartTime.length()==3){
                            StartTime = "0"+StartTime;
                        }
                        if(EndTime.length()==3){
                            EndTime = "0"+EndTime;
                        }
                        StartTime = StartTime.substring(0,2)+":"+StartTime.substring(2,4);
                        EndTime = EndTime.substring(0,2)+":"+EndTime.substring(2,4);
                        String Invitation = "You have an Appointment With "+jsonObject.getString("StaffName")+"\nStart Time: "+StartTime+" End Time: "+EndTime+"\nRoom Number: "+jsonObject.getString("roomNumber")+" Category: "+jsonObject.getString("Category");
                        if(i==0){
                            currentapt1.setVisibility(View.VISIBLE);
                            currentapt1.setText(Invitation);
                            Invitation = "";
                        }else if(i==1){
                            currentapt2.setVisibility(View.VISIBLE);
                            currentapt2.setText(Invitation);
                            Invitation = "";

                        }else if(i==2){
                            currentapt3.setVisibility(View.VISIBLE);
                            currentapt3.setText(Invitation);
                            Invitation = "";
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
