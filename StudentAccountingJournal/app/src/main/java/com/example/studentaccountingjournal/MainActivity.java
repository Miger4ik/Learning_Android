package com.example.studentaccountingjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button informationButton;
    private Button scheduleButton;
    private Button dataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        informationButton = findViewById(R.id.information_button);
        scheduleButton = findViewById(R.id.schedule_button);
        dataButton = findViewById(R.id.data_button);

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Information_to_students.class);
                startActivity(intent);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schedule_of_subjects.class);
                startActivity(intent);
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NB_data.class);
                startActivity(intent);
            }
        });
    }
}
