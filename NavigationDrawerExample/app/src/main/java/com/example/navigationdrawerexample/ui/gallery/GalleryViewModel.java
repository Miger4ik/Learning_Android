package com.example.navigationdrawerexample.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// створюємо об'єкт viewModel для збереження та обробки даних
public class GalleryViewModel extends ViewModel {
    // змінна над якою ми працюємо
    private MutableLiveData<String> data;

    // конструктор класу
    public GalleryViewModel() {}

    // метод для отримання значення змінної
    public LiveData<String> getData() {
        if (data == null) {
            loadData();
        }
        return data;
    }

    // метод для обновлення значення змінної
    private void loadData() {
        data = new MutableLiveData<>();
        data.setValue("Gallery");
    }
}
