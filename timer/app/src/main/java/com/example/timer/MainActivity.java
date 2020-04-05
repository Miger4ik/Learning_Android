package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setText(count + "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MyAsyncTask myAsyncTask = new MyAsyncTask();
               myAsyncTask.execute(1);
            }
        });


    }

    class MyAsyncTask extends AsyncTask <Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
//            try {
//                Thread.sleep(integers[0]);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                TimeUnit.SECONDS.sleep(integers[0]);
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            button.setText(count + "");
            button.setVisibility(View.VISIBLE);
        }
    }
}
