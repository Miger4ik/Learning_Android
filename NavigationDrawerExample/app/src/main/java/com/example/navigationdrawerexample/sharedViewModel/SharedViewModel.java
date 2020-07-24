package com.example.navigationdrawerexample.sharedViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// клас для передачі даних між фрагментами
public class SharedViewModel extends ViewModel {
    // змінна яка буде передаватись
    private final MutableLiveData<String> selected = new MutableLiveData<>();

    // метод який змінює дані
    public void select(String string) {
        selected.setValue(string);
    }

    // метод який повертає дані
    public LiveData<String> getSelected() {
        return selected;
    }
}
