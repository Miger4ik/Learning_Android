package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment {
    private Button myButton;
    private TextView myTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, null);
        myButton = view.findViewById(R.id.button2);
        myTextView = view.findViewById(R.id.textFrom1);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText("all OK");
            }
        });

        return view;
    }
}
