package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SeekBar seekBar;
    private Button button;
    private TextView textView;
    private int startValue = 0;

    private boolean isTimerOn = false;
    private CountDownTimer countDownTimer;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        seekBar.setMax(600);

        setIntervalFromSharedPreferences(sharedPreferences);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress / 60;
                int seconds = progress - minutes * 60;
                updateTextView(minutes, seconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerOn) {
                    countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            int minutes = (int) millisUntilFinished / 1000 / 60;
                            int seconds = (int) (millisUntilFinished / 1000) - minutes * 60;
                            updateTextView(minutes, seconds);
                        }

                        @Override
                        public void onFinish() {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            String melodyName = sharedPreferences.getString("timer_melody", "bell");

                            if (sharedPreferences.getBoolean("enable_sound", true)) {
                                textView.setText("finish, true" + melodyName);
                            } else {
                                textView.setText("finish, else");
                            }
                        }
                    }.start();
                    button.setText("STOP");
                    seekBar.setEnabled(false);
                    isTimerOn = true;
                } else {
                    countDownTimer.cancel();
                    textView.setText("00:" + startValue);
                    seekBar.setProgress(startValue);
                    button.setText("START");
                    seekBar.setEnabled(true);
                    isTimerOn = false;
                }
            }
        });

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void updateTextView(int minutes, int seconds) {

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }

        textView.setText(minutesString + ":" + secondsString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cool_timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.setting_menu: {
                Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(openSettings);
                return true;
            }
            case  R.id.about_menu: {
                Intent openAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(openAbout);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void setIntervalFromSharedPreferences(SharedPreferences sharedPreferences) {
        
        startValue = Integer.parseInt(sharedPreferences.getString("default_interval", "30"));
        int seconds = startValue;
        int minutes = seconds / 60;
        seconds = seconds - (minutes * 60);
        updateTextView(minutes, seconds);
        seekBar.setProgress(startValue);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("default_interval")) {
            setIntervalFromSharedPreferences(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
