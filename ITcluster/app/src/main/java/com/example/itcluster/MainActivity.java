package com.example.itcluster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 100;
    private final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 101;

    private Button mainButton;
    private Button listButton;
    private Button myDeviceButton;
    private Button equalsButton;
    private Button visitPageButton;
    private Button aboutProgramButton;

    private void findAllViews () {
        mainButton = findViewById(R.id.main_button);
        listButton = findViewById(R.id.list_button);
        myDeviceButton = findViewById(R.id.my_device_button);
        equalsButton = findViewById(R.id.equals_button);
        visitPageButton = findViewById(R.id.visit_page_button);
        aboutProgramButton = findViewById(R.id.about_program_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
        }

        // find all button on screen
        findAllViews();
        // set all button on click listeners
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(mainMenuIntent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listMenuIntent = new Intent(MainActivity.this, ListMenu.class);
                startActivity(listMenuIntent);
            }
        });

        myDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myDeviceMenuIntent = new Intent(MainActivity.this, MyDeviceMenu.class);
                startActivity(myDeviceMenuIntent);
            }
        });

        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent equalsMenuIntent = new Intent(MainActivity.this, EqualsMenu.class);
                startActivity(equalsMenuIntent);
            }
        });

        visitPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitPageMenuIntent = new Intent(MainActivity.this, VisitPageMenu.class);
                startActivity(visitPageMenuIntent);
            }
        });

        aboutProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutProgramMenuIntent = new Intent(MainActivity.this, AboutProgramMenu.class);
                startActivity(aboutProgramMenuIntent);
            }
        });
    }
}
