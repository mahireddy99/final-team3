package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
        Button yesbutton,nobutton;

        yesbutton=(Button)findViewById(R.id.yesbtn);
        nobutton=(Button)findViewById(R.id.nobtn);

        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogOut.this, HomePage.class);
                startActivity(i);
            }
        });
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogOut.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}