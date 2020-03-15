package com.example.student_accounting_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bit;
    private Button dot;
    private Button dit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bit = findViewById(R.id.bit);
        dot = findViewById(R.id.dot);
        dit = findViewById(R.id.dit);

        bit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInformation = new Intent(MainActivity.this, Information_to_students.class);
                startActivity(intentInformation);
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSchedule = new Intent(MainActivity.this, ScheduleOfItems.class);
                startActivity(intentSchedule);
            }
        });

        dit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNb = new Intent(MainActivity.this, NbInformation.class);
                startActivity(intentNb);
            }
        });
    }

}
