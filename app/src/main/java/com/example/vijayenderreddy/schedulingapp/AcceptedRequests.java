package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AcceptedRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_requests);

        Button acceptedrequestHomeBtn;

       acceptedrequestHomeBtn = (Button) findViewById(R.id.homeACR);

        acceptedrequestHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AcceptedRequests.this, HomePage.class);
                startActivity(i);
            }
        });

    }
}