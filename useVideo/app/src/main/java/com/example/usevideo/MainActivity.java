package com.example.usevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView myVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVideo = findViewById(R.id.videoView);
        myVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.videoplayback);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(myVideo);
        myVideo.setMediaController(mediaController);
        myVideo.start();
    }
}
