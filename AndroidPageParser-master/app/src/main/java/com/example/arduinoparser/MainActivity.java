package com.example.arduinoparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView myText;
    String url = "http://ecoboard.in.ua/main/export/5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = findViewById(R.id.myText);

        Connection connection = new Connection();
        connection.execute(url);

    }

    class Connection extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            // створення HashMap для збереження результату
            String json = "";
            // підключення до сайту та вибірка даних з нього
            try {
                json = Jsoup.connect(strings[0]).ignoreContentType(true).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            myText.setText(string);
        }
    }

}
