package com.example.navigationdrawerexample.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationdrawerexample.R;
import com.example.navigationdrawerexample.sharedViewModel.SharedViewModel;

public class GalleryFragment extends Fragment {
    private View root = null;
    private TextView text = null;

    // створюємо пусті об'єкти viewModel для зчитуванння даних з них
    private GalleryViewModel galleryViewModel = null;
    private SharedViewModel sharedViewModel = null;

    // створюємо об'єкт в якому будуть зберігатись дані зміни будь якого viewModel
    MediatorLiveData<String> mediatorLiveData = null;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // отримуємо об'єкти viewModel
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        findAllView();

        // отримуємо об'єкти даних з viewModel
        LiveData<String> data = galleryViewModel.getData();
        LiveData<String> sharedData = sharedViewModel.getSelected();

        // створюємо контейнер для збереження даних які приходять з всіх viewModel
        mediatorLiveData = new MediatorLiveData<>();

        // оголошуємо подію яка буде виконуватись коли один з viewModel відіслав свої дані
        mediatorLiveData.addSource(data, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mediatorLiveData.setValue(s);
            }
        });

        mediatorLiveData.addSource(sharedData, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mediatorLiveData.setValue(s);
            }
        });

        // підключаємо слухача до контейнеру даних
        // якщо дані в контейнері змінюються останнє значення яке в нього прийшло виводимо на екран
        mediatorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                text.setText(s);
            }
        });

        return root;
    }

    private void findAllView() {
        text = root.findViewById(R.id.text_gallery);
    }
}