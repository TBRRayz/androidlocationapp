package com.example.bryan.bigapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bryan on 01-4-2017.
 */

public class Activity_first extends AppCompatActivity {


    public Button bttn1;

    public void init(){

        bttn1 = (Button)findViewById(R.id.bttn1);
        //functie voor de button op scherm 1
        bttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toy = new Intent(Activity_first.this, Activity_second.class);

                startActivity(toy);
            }
        });
    };

    @Override
    // de oncreate functie
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        init();
    }
}
