package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startLocationUpdatesButton;
    private Button stopLocationUpdatesButton;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLocationUpdatesButton = findViewById(R.id.startLocationUpdatesButton);
        stopLocationUpdatesButton = findViewById(R.id.stopLocationUpdatesButton);
        locationTextView = findViewById(R.id.locationTextView);


    }
}
