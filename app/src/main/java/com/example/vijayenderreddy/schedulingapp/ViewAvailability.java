package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewAvailability extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_avialable_staff);

        TextView textView;
        Button viewavailabilitybtn,viewavailhomebtn;

        textView=(TextView)findViewById(R.id.viewavailnametxt);

        viewavailabilitybtn=(Button)findViewById(R.id.viewAvailbtn);
        viewavailhomebtn=(Button)findViewById(R.id.viewavailhome);

        viewavailabilitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewavailhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewAvailability.this, HomePage.class);
                startActivity(i);
            }
        });
    }

}