package com.example.navigationdrawer.ui.fragment.secondMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdrawer.R;

public class SecondMenuFragment extends Fragment {
    private View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second_menu, container, false);



        return view;
    }
}