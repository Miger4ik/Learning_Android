package com.example.olimp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olimp.data.ClubOlympusContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MEMBER_LOADER = 123;
    MemberCursorAdapter memberCursorAdapter;

    private FloatingActionButton floatingActionButton;

    private ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllView();

        setClickListeners();

        memberCursorAdapter = new MemberCursorAdapter(getApplicationContext(), null, false);
        dataListView.setAdapter(memberCursorAdapter);

        getSupportLoaderManager().initLoader(MEMBER_LOADER, null, this);
    }

    private void findAllView() {
        floatingActionButton = findViewById(R.id.floatingActionButton);
        dataListView = findViewById(R.id.dataListView);
    }

    private void setClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                Uri currentMemberUri = ContentUris.withAppendedId(ClubOlympusContract.MemberEntry.CONTENT_URI, id);
                intent.setData(currentMemberUri);
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                ClubOlympusContract.MemberEntry._ID,
                ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME,
                ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME,
                ClubOlympusContract.MemberEntry.COLUMN_SPORT
        };

        CursorLoader cursorLoader =  new CursorLoader(getApplicationContext(),
                ClubOlympusContract.MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        memberCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        memberCursorAdapter.swapCursor(null);

    }
}