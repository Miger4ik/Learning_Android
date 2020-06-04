package com.example.pizzarecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CardForLayout> recyclerViewItems = new ArrayList<>();

        recyclerViewItems.add(new CardForLayout(R.drawable.ic_launcher_foreground, "text1", "text1"));
        recyclerViewItems.add(new CardForLayout(R.drawable.ic_launcher_foreground, "text2", "text2"));
        recyclerViewItems.add(new CardForLayout(R.drawable.ic_launcher_foreground, "text3", "text3"));
        recyclerViewItems.add(new CardForLayout(R.drawable.ic_launcher_foreground, "text4", "text4"));
        recyclerViewItems.add(new CardForLayout(R.drawable.ic_launcher_foreground, "text5", "text5"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(recyclerViewItems);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
