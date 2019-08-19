package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AppointmentRequests extends AppCompatActivity {

    CheckBox invite1,invite2,invite3;
    Button acceptedbutton, rejectedbutton,appointrequesthome;
    JSONObject MainObj;
    String Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_request);



        acceptedbutton = (Button)findViewById(R.id.acceptbtn);
        rejectedbutton=(Button)findViewById(R.id.rejectbtn);
        appointrequesthome=(Button)findViewById(R.id.aptreqhome);

        invite1 = findViewById(R.id.invitation1);
        invite2 = findViewById(R.id.invitation2);
        invite3 = findViewById(R.id.invitation3);


        appointrequesthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AppointmentRequests.this, HomePage.class);
                startActivity(i);
            }
        });

        new MyTask(getIntent().getStringExtra("staffid")).execute();

        acceptedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status = "ACCEPTED";
                for(int i=0;i<=2;i++){
                    String StaffId="",SenderId="";
                    try {
                        JSONObject childObject = MainObj.getJSONObject(String.valueOf(i));
                        StaffId = childObject.getString("StaffId");
                        SenderId = childObject.getString("SenderId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    switch (i){
                        case 0:
                            if(invite1.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;
                        case 1:
                            if(invite2.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;
                        case 2:
                            if(invite3.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;

                    }
                }
            }
        });

        rejectedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status = "REJECTED";
                for(int i=0;i<=2;i++){
                    String StaffId="",SenderId="";
                    try {
                        JSONObject childObject = MainObj.getJSONObject(String.valueOf(i));
                        StaffId = childObject.getString("StaffId");
                        SenderId = childObject.getString("SenderId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    switch (i){
                        case 0:
                            if(invite1.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;
                        case 1:
                            if(invite2.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;
                        case 2:
                            if(invite3.isChecked()){
                                new MyTask1(StaffId,Status,SenderId).execute();
                            }
                            break;

                    }
                }
            }
        });
    }


    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String staffid;
        public MyTask(String staffid){
            this.staffid = staffid;

        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/getappointments&"+staffid);

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

            try {
                 MainObj =new JSONObject(result);
                for(int i = 0;i<=2;i++){
                    if(MainObj.has(String.valueOf(i))){
                        JSONObject jsonObject = MainObj.getJSONObject(String.valueOf(i));

                        String Invitation = "You have an invitation With StaffId: "+jsonObject.getString("SenderId")+"\non Date:"+jsonObject.getString("AppointmentDate")+"\nStart Time: "+jsonObject.getString("startTime")+" End Time: "+jsonObject.getString("endTime")+"\nRoom Number: "+jsonObject.getString("roomNumber")+" Category: "+jsonObject.getString("Category");
                        if(i==0){
                            invite1.setVisibility(View.VISIBLE);
                            invite1.setText(Invitation);
                            Invitation = "";
                        }else if(i==1){
                            invite2.setVisibility(View.VISIBLE);
                            invite2.setText(Invitation);
                            Invitation = "";

                        }else if(i==2){
                            invite3.setVisibility(View.VISIBLE);
                            invite3.setText(Invitation);
                            Invitation = "";
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private class MyTask1 extends AsyncTask<Void, Void, String> {
        String message;
        String staffid,senderId,status;
        public MyTask1(String staffid,String status,String senderId){
            this.staffid = staffid;
            this.senderId = senderId;
            this.status = status;

        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;
            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/updateAppointmentStatus&"+staffid+"&"+status+"&"+senderId);

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
                JSONObject object = new JSONObject(response.toString());


                message = object.getString("message");
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
                if(Status.equals("ACCEPTED")){
                    Toast.makeText(getApplicationContext(),"Invitation has been accepted.",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invitation has been rejected.",Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(AppointmentRequests.this, HomePage.class);
                startActivity(i);
                finish();
            }
            else {
                if(Status.equals("ACCEPTED")){
                    Toast.makeText(getApplicationContext(),"Could not accept the invitation!",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Could not reject the invitation!",Toast.LENGTH_LONG).show();
                }

            }

        }

    }


}