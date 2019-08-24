package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class updateprofile extends AppCompatActivity {

    TextView UPFname,UPLname,UPDesignation;
    EditText UPEmail,UPPhoneNum;
    Button UPbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateprofile);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

        UPFname = findViewById(R.id.UPFname);
        UPLname = findViewById(R.id.UPLname);
        UPDesignation = findViewById(R.id.UPDesignation);
        UPEmail = findViewById(R.id.UPEmail);
        UPPhoneNum = findViewById(R.id.UPPhoneNum);
        UPbtn = findViewById(R.id.UPbtn);

        UPFname.setText(getIntent().getStringExtra("FirstName"));
        UPLname.setText(getIntent().getStringExtra("LastName"));
        UPDesignation.setText(getIntent().getStringExtra("Designation"));
        UPEmail.setText(getIntent().getStringExtra("Email"));
        UPPhoneNum.setText(getIntent().getStringExtra("PhoneNum"));
        UPbtn.setVisibility(View.VISIBLE);

        UPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UPEmail.getText().toString().trim().equals("") | UPPhoneNum.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Details",Toast.LENGTH_LONG).show();
                    return;
                }
                new MyTask(PreferenceData.getLoggedInEmailUser(getApplicationContext()),UPEmail.getText().toString().trim(),UPPhoneNum.getText().toString().trim()).execute();
            }
        });

    }


    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String staffid,email,phonenum;

        public MyTask(String staffid, String email, String phonenum) {
            this.staffid = staffid;
            this.email = email;
            this.phonenum = phonenum;
        }

        @Override
        protected String doInBackground(Void... params){

            URL url = null;

            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/updateprofile&"+staffid+"&"+email+"&"+phonenum);

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
                Toast.makeText(getApplicationContext(),"Profile Updated Successfully!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(updateprofile.this,HomePage.class);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(),"Could not update profile!",Toast.LENGTH_LONG).show();
            }

        }

    }
}
