package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AcceptedRequests extends AppCompatActivity {

    TextView AcceptedApt1,AcceptedApt2,AcceptedApt3;
    Button acceptedrequestHomeBtn;
    JSONObject MainObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_requests);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

       acceptedrequestHomeBtn = (Button) findViewById(R.id.homeACR);

        acceptedrequestHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcceptedRequests.this, HomePage.class);
                startActivity(i);
            }
        });

        AcceptedApt1 = findViewById(R.id.acceptedApt1);
        AcceptedApt2 = findViewById(R.id.acceptedApt2);
        AcceptedApt3 = findViewById(R.id.acceptedApt3);

        new MyTask(PreferenceData.getLoggedInEmailUser(this)).execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String senderID;

        public MyTask(String senderID){
            this.senderID = senderID;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/acceptedAppointments&"+senderID);

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
                AcceptedApt1.setVisibility(View.VISIBLE);
                AcceptedApt1.setText("You do not have any accepted appointments");
                AcceptedApt2.setVisibility(View.GONE);
                AcceptedApt3.setVisibility(View.GONE);
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
                        String Invitation = jsonObject.getString("StaffName")+" has accepted your invitation\nAppointment Date: "+jsonObject.getString("AppointmentDate")+"\nStart Time: "+StartTime+" End Time: "+EndTime+"\nRoom Number: "+jsonObject.getString("roomNumber")+"\nCategory: "+jsonObject.getString("Category");
                        if(i==0){
                            AcceptedApt1.setVisibility(View.VISIBLE);
                            AcceptedApt1.setText(Invitation);
                            Invitation = "";
                        }else if(i==1){
                            AcceptedApt2.setVisibility(View.VISIBLE);
                            AcceptedApt2.setText(Invitation);
                            Invitation = "";

                        }else if(i==2){
                            AcceptedApt3.setVisibility(View.VISIBLE);
                            AcceptedApt3.setText(Invitation);
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