package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList.add("post1");
        arrayList.add("post2");
        arrayList.add("post3");
        arrayList.add("post4");
        arrayList.add("post5");
        arrayList.add("post6");
        arrayList.add("post7");
        arrayList.add("post8");
        arrayList.add("post9");
        arrayList.add("post10");
        arrayList.add("post11");
        arrayList.add("post12");
        arrayList.add("post13");
        arrayList.add("post14");
        arrayList.add("post15");
        arrayList.add("post16");
        arrayList.add("post17");
        arrayList.add("post18");
        arrayList.add("post19");
        arrayList.add("post20");

        listView = findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
