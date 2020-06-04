package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView tomView;
    private ImageView jerryView;
    private TextView recordText;
    private int record = 0;

    private boolean isTom = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();

        //tomView.animate().rotation(360).setDuration(3000);

        tomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnimation(3000);
                record++;
                recordText.setText(String.valueOf(record));
            }
        });
    }

    private void findAllView () {
        tomView = findViewById(R.id.imageTom);
        jerryView = findViewById(R.id.imageJerry);
        recordText = findViewById(R.id.recordText);
    }

    private void showTom (long duration) {
        tomView.animate().alpha(1).rotation(0).scaleX(1f).scaleY(1f).setDuration(duration);
    }

    private void hideTom (long duration) {
        tomView.animate().alpha(0).rotation(3600).scaleX(0f).scaleY(0f).setDuration(duration);
    }

    private void showJerry (long duration) {
        jerryView.animate().alpha(1).rotation(3600).scaleX(1f).scaleY(1f).setDuration(duration);
    }

    private void hideJerry (long duration) {
        jerryView.animate().alpha(0).rotation(0).scaleX(0f).scaleY(0f).setDuration(duration);
    }

    private void createAnimation (long duration) {
        if (isTom) {
            showJerry(duration);
            hideTom(duration);
            isTom = false;
        } else {
            showTom(duration);
            hideJerry(duration);
            isTom = true;
        }
    }

}
