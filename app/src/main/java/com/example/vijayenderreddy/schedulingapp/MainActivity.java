package com.example.vijayenderreddy.schedulingapp;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtview1, txtview2;
        final EditText STAFFID, PASSWORD;
        Button signbutton, forgetbutton;
        final String stafid, pass;

        txtview1 = (TextView) findViewById(R.id.user);
        txtview2 = (TextView) findViewById(R.id.etpassword);
        STAFFID = (EditText) findViewById(R.id.useredit);
        PASSWORD = (EditText) findViewById(R.id.passwordedit);
        signbutton = (Button) findViewById(R.id.signinbtn);
        forgetbutton = (Button) findViewById(R.id.frgpardbtn);

        signbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent i = new Intent(MainActivity.this, HomePage.class);
                //startActivity(i);
                if(STAFFID.getText().toString().trim().equals("")||PASSWORD.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter valid staff id and password",Toast.LENGTH_LONG).show();
                    return;
                }
               new MyTask(STAFFID.getText().toString().trim(),PASSWORD.getText().toString().trim()).execute();
            }

        });

        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(i);
            }
        });

    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        String message;
        String staffid,password;
        public MyTask(String staffid,String password){
            this.staffid = staffid;
            this.password = password;
        }
        @Override
        protected String doInBackground(Void... params){


            URL url = null;






            try {

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/login&"+staffid+"&"+password);

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
                PreferenceData.setLoggedInUserEmail(getApplicationContext(),staffid);
                Intent i = new Intent(MainActivity.this, HomePage.class);
                i.putExtra("staffid",staffid);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid StaffId or Password",Toast.LENGTH_LONG).show();
            }

        }

    }
}






