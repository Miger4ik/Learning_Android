package com.example.savedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            text.setText(savedInstanceState.getString("text"));
        }

        Log.d("method", "onCreate");
        text.append("onCreate" + "\n");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("method", "onStart");
        text.append("onStart" + "\n");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("method", "onResume");
        text.append("onResume" + "\n");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("method", "onPause");
        text.append("onPause" + "\n");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("method", "onStop");
        text.append("onStop" + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("method", "onDestroy");
        text.append("onDestroy" + "\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("method", "onRestart");
        text.append("onRestart" + "\n");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("text", text.getText().toString());
    }
}
