package com.example.downloadweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String myURL = "http://ecoboard.in.ua/main/export/5";
    private String myParsePage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        try {
            myParsePage = task.execute(myURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Log.d("html", myParsePage);
    }

    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d("back", strings[0]);
            StringBuilder result = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                Log.d("OK", "OK");
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();

                while (line != null) {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String code = jsonObject.getString("code");
                Log.d("jsonCode", code);

                JSONObject hoursObject = jsonObject.getJSONObject("hours");
                JSONArray hoursArray = hoursObject.getJSONArray("hours");
                for (int i = 0; i < hoursArray.length(); i++) {
                    Log.d("jsonHours", hoursArray.get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
