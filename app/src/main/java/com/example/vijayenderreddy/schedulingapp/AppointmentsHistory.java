package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

public class AppointmentsHistory extends AppCompatActivity {
    JSONObject MainObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmentshistory);

        Button apphistoryHome;
        apphistoryHome = (Button)findViewById(R.id.apthtryhome);

        apphistoryHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AppointmentsHistory.this, HomePage.class);
                startActivity(i);
            }
        });


    }
}