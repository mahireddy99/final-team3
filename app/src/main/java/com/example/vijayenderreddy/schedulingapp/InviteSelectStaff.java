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

public class InviteSelectStaff extends AppCompatActivity {

    CheckBox staff1,staff2,staff3,staff4,staff5,staff6,staff7;
    String ADate,STime,ETime,RoomNum,Cate,SenderID;
    Button send_Invite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_select_staff);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        staff1 = findViewById(R.id.staff1);
        staff2 = findViewById(R.id.staff2);
        staff3 = findViewById(R.id.staff3);
        staff4 = findViewById(R.id.staff4);
        staff5 = findViewById(R.id.staff5);
        staff6 = findViewById(R.id.staff6);
        staff7 = findViewById(R.id.staff7);

        if((staff1.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff1.setVisibility(View.GONE);
        }
        else if((staff2.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff2.setVisibility(View.GONE);
        }
        else if((staff3.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff3.setVisibility(View.GONE);
        }
        else if((staff4.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff4.setVisibility(View.GONE);
        }
        else if((staff5.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff5.setVisibility(View.GONE);
        }
        else if((staff6.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff6.setVisibility(View.GONE);
        }
        else if((staff7.getText().toString().trim()).contains(getIntent().getStringExtra("staffid"))){
            staff7.setVisibility(View.GONE);
        }
        send_Invite = findViewById(R.id.sendInvitationbtn);

        ADate = getIntent().getStringExtra("AptDate");
        STime = getIntent().getStringExtra("StartTime");
        ETime = getIntent().getStringExtra("EndTime");
        RoomNum = getIntent().getStringExtra("RoomNo");
        Cate = getIntent().getStringExtra("Category");
        SenderID = PreferenceData.getLoggedInEmailUser(this);


        send_Invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!staff1.isChecked()&&!staff2.isChecked()&&!staff3.isChecked()&&!staff4.isChecked()&&!staff5.isChecked()&&!staff6.isChecked()&&!staff7.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please select at least one Staff",Toast.LENGTH_LONG).show();
                    return;
                }
                for(int i=1;i<=7;i++){
                    switch (i){
                        case 1:
                            if(staff1.isChecked()){
                                new MyTask(SenderID,String.valueOf(1794805),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 2:
                            if(staff2.isChecked()){
                                new MyTask(SenderID,String.valueOf(172602),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 3:
                            if(staff3.isChecked()){
                                new MyTask(SenderID,String.valueOf(178000),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 4:
                            if(staff4.isChecked()){
                                new MyTask(SenderID,String.valueOf(179000),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 5:
                            if(staff5.isChecked()){
                                new MyTask(SenderID,String.valueOf(1794278),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 6:
                            if(staff6.isChecked()){
                                new MyTask(SenderID,String.valueOf(1795598),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                        case 7:
                            if(staff7.isChecked()){
                                new MyTask(SenderID,String.valueOf(1791234),STime,ETime,RoomNum,ADate,Cate).execute();
                            }
                            break;
                    }
                }
            }
        });



    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String SenderID,staffid,StartTime,EndTime,RoomNo,AppDate,Category;
        public MyTask(String SenderID,String staffid,String StartTime,String EndTime,String RoomNo,String AppDate,String Category){
            this.SenderID = SenderID;
            this.staffid = staffid;
            this.StartTime = StartTime;
            this.EndTime = EndTime;
            this.RoomNo = RoomNo;
            this.AppDate = AppDate;
            this.Category = Category;


        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;






            try {

                //url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/setappointment&"+StartTime+"&"+EndTime+"&"+AppDate+"&"+RoomNo+"&"+staffid+"&"+Category);

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/setappointment&"+SenderID.toString().trim()+"&"+StartTime.toString().trim()+"&"+EndTime.toString().trim()+"&"+AppDate.toString().trim()+"&"+RoomNo.toString().trim()+"&"+staffid.toString().trim()+"&"+Category.toString().trim());
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

            if(result.equals("Successful")){
                Toast.makeText(getApplicationContext(),"Invitations has been sent.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(InviteSelectStaff.this,HomePage.class));
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Could not send the invitations.",Toast.LENGTH_LONG).show();
            }

        }

    }
}
