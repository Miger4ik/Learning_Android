package com.example.navigationdrawerexample.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> data;

    public HomeViewModel() {}

    public LiveData<Integer> getData() {
        if (data == null) {
            loadData();
        }
        return data;
    }

    // метод для обновлення інформації
    public void incrementData() {
        if (data == null) {
            loadData();
        }
        data.setValue(data.getValue() + 1);
    }

    private void loadData() {
        data = new MutableLiveData<>();
        data.setValue(0);
    }
}