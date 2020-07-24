package com.example.retrofitrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Post> posts;
    private ArrayList<PostItem> postItems;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.postRecyclerView);

        posts = new ArrayList<>();
        postItems = new ArrayList<>();

        // підключаємо до recyclerView адаптер створений з arrayList postItems
        recyclerView.setHasFixedSize(true);
        adapter = new PostAdapter(postItems);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                // якщо запрос був успішним і прийшли дані не null
                                // заповнюємо масив posts даними які прийшли
                                // TODO треба забрати лишню передачу з одної колекції в іншу
                                posts.addAll(response.body());

                                // передаємо дані з масиву posts в масив postItems
                                for (Post temp : posts) {
                                    postItems.add(new PostItem(temp));
                                }
                                // обновляємо дані в recycleView
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        } else {
                            switch (response.code()){
                                case 404: {
                                    // сторінка не знайдена
                                    // отримуємо текст помилки
                                    ResponseBody errorBody = response.errorBody();
                                    // виводимо текст помилки в лог
                                    try {
                                        Log.e("Response Error", errorBody.string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                default:{
                                    // інший код помилки
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        // якщо сталась помилка запросу
                    }
                });
    }
}