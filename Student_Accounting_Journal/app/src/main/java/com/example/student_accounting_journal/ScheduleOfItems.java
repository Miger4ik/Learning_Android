package com.example.student_accounting_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScheduleOfItems extends AppCompatActivity {
    private Button backItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_of_items);
        backItem = findViewById(R.id.backItem);
        backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackItem = new Intent(ScheduleOfItems.this, MainActivity.class);
                startActivity(intentBackItem);
            }
        });
    }
}
