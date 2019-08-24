package com.example.vijayenderreddy.schedulingapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        if(PreferenceData.getLoggedInEmailUser(this).equals("")){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        Button acceptRequestbtn,appointmentRequestbtn,appointmentHistorybtn,createProfilebtn,currentAppointmentbtn,
                   logoutbutton,setAppointmentsbtn,setAvailabilitybtn,viewAvailabilitybtn;

        acceptRequestbtn = (Button)findViewById(R.id.accrequestsbtn);
        appointmentHistorybtn = (Button)findViewById(R.id.apphistorybtn);
        appointmentRequestbtn = (Button)findViewById(R.id.apprequestbtn);
        createProfilebtn = (Button)findViewById(R.id.createprobtn);
        currentAppointmentbtn = (Button)findViewById(R.id.crntappbtn);
        logoutbutton = (Button)findViewById(R.id.logoutbtn);
        setAppointmentsbtn = (Button)findViewById(R.id.setappbtn);
        setAvailabilitybtn = (Button)findViewById(R.id.setavailabhomebtn);
        viewAvailabilitybtn = (Button)findViewById(R.id.viewstaffbtn);




        acceptRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, AcceptedRequests.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });

        appointmentHistorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, AppointmentsHistory.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        appointmentRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, AppointmentRequests.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        createProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, CreateProfile.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        currentAppointmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, CurrentAppointments.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, LogOut.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        setAppointmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, SetAppointments.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        setAvailabilitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, SetAvailability.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });
        viewAvailabilitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, ViewAvailability.class);
                i.putExtra("staffid",getIntent().getStringExtra("staffid"));
                startActivity(i);
            }
        });





    }
}