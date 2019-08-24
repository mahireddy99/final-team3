package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class AppointmentsHistory extends AppCompatActivity {
    JSONObject MainObj;
    TextView AptHistoryTxt;
    Button apphistoryHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmentshistory);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        AptHistoryTxt = findViewById(R.id.AptHistoryText);
        AptHistoryTxt.setMovementMethod(new ScrollingMovementMethod());
        apphistoryHome = (Button)findViewById(R.id.apthtryhome);

        apphistoryHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AppointmentsHistory.this, HomePage.class);

                startActivity(i);

            }
        });


            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            new MyTask(PreferenceData.getLoggedInEmailUser(this),date).execute();


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

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/appointmenthistory&"+senderID+"&"+AptDate);

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
                AptHistoryTxt.setText("You do not have any previous appointments.");
                return;
            }
            try {
                MainObj =new JSONObject(result);
                String totalApt = "";
                for(int i = 0;i<MainObj.length();i++){
                    String Invitation = "";
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
                        Invitation = "You had an Appointment With "+jsonObject.getString("StaffName")+"\nAppointment Date: "+jsonObject.getString("AppointmentDate")+"\nStart Time: "+StartTime+" End Time: "+EndTime+"\nRoom Number: "+jsonObject.getString("roomNumber")+"\nCategory: "+jsonObject.getString("Category");
                    }

                    totalApt = totalApt+"\n\n"+Invitation;

                }
                AptHistoryTxt.setText(totalApt.substring(2));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}