package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton noteButton;
    private ImageButton playButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private SeekBar seekBar;

    private MediaPlayer mediaPlayer;

    private boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        colorSeekBar();
        setOnClickListeners();
        createPlayer();
        settingsSeek();

    }
    private void playButtonClick() {
        if (isPlay) {
            stopMusic();
        } else {
            playMusic();
        }
    }

    private void playMusic() {
        isPlay = true;
        playButton.setBackground(getDrawable(R.drawable.ic_pause_white_24dp));
        mediaPlayer.start();
    }
    private void stopMusic() {
        isPlay = false;
        playButton.setBackground(getDrawable(R.drawable.ic_play_arrow_white_24dp));
        mediaPlayer.pause();
    }
    private void onStartPosition() {
        mediaPlayer.seekTo(0);
        stopMusic();
    }
    private void onEndPosition() {
        mediaPlayer.seekTo(mediaPlayer.getDuration());
        stopMusic();
    }

    private void settingsSeek() {
        seekBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.naga_siren);
    }

    private void colorSeekBar() {
        int color = ContextCompat.getColor(this, R.color.colorAccent);
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void setOnClickListeners() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonClick();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartPosition();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEndPosition();
            }
        });
    }

    private void findAllViews() {
        noteButton = findViewById(R.id.noteButton);
        playButton = findViewById(R.id.playButton);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        seekBar = findViewById(R.id.seekBar);
    }
}
