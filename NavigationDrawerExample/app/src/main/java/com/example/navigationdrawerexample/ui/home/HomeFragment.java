package com.example.navigationdrawerexample.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationdrawerexample.R;
import com.example.navigationdrawerexample.sharedViewModel.SharedViewModel;

public class HomeFragment extends Fragment {

    // створюємо пусті об'єкти viewModel
    private HomeViewModel homeViewModel;
    private SharedViewModel sharedViewModel;

    private Button button = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // присвоюємо в пусті об'єкти потрібні значення
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        button = root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // обновлюємо дані в viewModel по кліку на кнопку
                homeViewModel.incrementData();
            }
        });

        // оголошуємо слухач змін
        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            // коли зміни були проведені
            // передаємо нове значення на інший фрагмент за допомогою sharedViewModel
            public void onChanged(Integer integer) {
                sharedViewModel.select("dataFromHome" + integer);
            }
        });

        return root;
    }
}