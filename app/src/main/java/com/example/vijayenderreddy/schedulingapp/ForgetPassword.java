package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        TextView textView,textView1,textView2;
        final EditText editText,editText1,editText2;
        Button submitbutton;

        textView=(TextView)findViewById(R.id.frgtuser);
        textView1=(TextView)findViewById(R.id.frgtpassword);
        textView2=(TextView)findViewById(R.id.frgtconfigpawrd);

        editText=(EditText)findViewById(R.id.frgtidedit);
        editText1=(EditText)findViewById(R.id.frgpassedt);
        editText2=(EditText)findViewById(R.id.confirmedt);


        submitbutton=(Button)findViewById(R.id.fgtpasshome);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!editText.getText().toString().trim().equals("")) && (!editText1.getText().toString().trim().equals("")) && (!editText2.getText().toString().trim().equals(""))) {
                    if((editText1.getText().toString().trim()).equals(editText2.getText().toString().trim())) {
                        new MyTask(editText.getText().toString().trim(),editText1.getText().toString().trim()).execute();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Password does not match with confirm password!",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter valid details!",Toast.LENGTH_LONG).show();
                }
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

                url = new URL("http://10.0.2.2:8080/FinalProject/mad306/team3/forgetpassword&"+staffid+"&"+password);

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
                Toast.makeText(getApplicationContext(),"Password Updated Successfully.",Toast.LENGTH_LONG).show();
                Intent i = new Intent(ForgetPassword.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Staff Id does not exist",Toast.LENGTH_LONG).show();
            }


        }

    }

}