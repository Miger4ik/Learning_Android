package com.example.retrofitrecyclerview;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    // базовий URL з якого будемо отримувати дані
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    // об'єкт Retrofit для роботи
    private Retrofit mRetrofit;

    // оголошуємо конструктор
    private NetworkService() {
        // код для занесення в лог всіх дій
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        // створюємо retrofit об'єкт
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    // метод для отримання об'єкту даного класу
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    // отримуємо реалізацію нашого інтерфейсу за допомогою Retrofit
    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
