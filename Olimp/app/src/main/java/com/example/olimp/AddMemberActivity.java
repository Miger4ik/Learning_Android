package com.example.olimp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olimp.data.ClubOlympusContract;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportNameEditText;
    private Spinner genderSpinner;
    private int genderValue = 0;

    private ArrayAdapter spinnerArrayAdapter;

    private static final int EDIT_MEMBER_LOADER = 321;
    private Uri currentMemberUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();

        currentMemberUri = intent.getData();

        if (currentMemberUri == null) {
            setTitle("Add a Member");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the Member");
            getSupportLoaderManager().initLoader(EDIT_MEMBER_LOADER, null, this);
        }

        findAllView();
        addValuesInSpinner();
        setClickListeners();
    }

    private void setClickListeners() {
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderValue = (int) parent.getItemIdAtPosition(position); // отримую id вибраного елементу
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findAllView() {
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        sportNameEditText = findViewById(R.id.groupNameEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
    }

    private void addValuesInSpinner () {
        createArrayAdapter();
        genderSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void createArrayAdapter() {
        spinnerArrayAdapter = ArrayAdapter.createFromResource(AddMemberActivity.this, R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_member: {
                saveMember();
                return  true;
            }
            case  R.id.delete_member: {
                showDeleteDialog();
                return  true;
            }
            case  android.R.id.home:{
                NavUtils.navigateUpFromSameTask(AddMemberActivity.this);
                return  true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportNameEditText.toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "input the first Name", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "input the last Name", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(sport)) {
            Toast.makeText(getApplicationContext(), "input the sport", Toast.LENGTH_LONG).show();
            return;
        } else if (genderValue == ClubOlympusContract.MemberEntry.GENDER_UNKNOWN) {
            Toast.makeText(getApplicationContext(), "choose the gender", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME, lastName);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_SPORT, sport);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_GENDER, genderValue);

        if (currentMemberUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(ClubOlympusContract.MemberEntry.CONTENT_URI, contentValues);

            if (uri == null) {
                Toast.makeText(getApplicationContext(), "data not saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanged = getContentResolver().update(currentMemberUri, contentValues, null, null);
            if (rowsChanged == 0) {
                Toast.makeText(getApplicationContext(), "data not updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "data updated", Toast.LENGTH_LONG).show();
            }
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
            ClubOlympusContract.MemberEntry._ID,
            ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME,
            ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME,
            ClubOlympusContract.MemberEntry.COLUMN_GENDER,
            ClubOlympusContract.MemberEntry.COLUMN_SPORT
        };

        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(), currentMemberUri, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int firstNameColumnIndex = data.getColumnIndex(ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME);
            int lastNameColumnIndex = data.getColumnIndex(ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME);
            int genderColumnIndex = data.getColumnIndex(ClubOlympusContract.MemberEntry.COLUMN_GENDER);
            int sportColumnIndex = data.getColumnIndex(ClubOlympusContract.MemberEntry.COLUMN_SPORT);

            String firstName = data.getString(firstNameColumnIndex);
            String lastName = data.getString(lastNameColumnIndex);
            int gender = data.getInt(genderColumnIndex);
            String sport = data.getString(sportColumnIndex);

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            sportNameEditText.setText(sport);

            switch (gender) {
                case ClubOlympusContract.MemberEntry.GENDER_UNKNOWN: {
                    genderSpinner.setSelection(ClubOlympusContract.MemberEntry.GENDER_UNKNOWN);
                    break;
                }
                case ClubOlympusContract.MemberEntry.GENDER_MALE: {
                    genderSpinner.setSelection(ClubOlympusContract.MemberEntry.GENDER_MALE);
                    break;
                }
                case ClubOlympusContract.MemberEntry.GENDER_FEMALE: {
                    genderSpinner.setSelection(ClubOlympusContract.MemberEntry.GENDER_FEMALE);
                    break;
                }
                default: {}
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want delete the member");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMember();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteMember() {
        if (currentMemberUri != null) {
            int rowsDeleted = getContentResolver().delete(currentMemberUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(getApplicationContext(), "data not deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "data is deleted", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentMemberUri == null) {
            MenuItem menuItem = menu.findItem(R.id.delete_member);
            menuItem.setVisible(false);
        }
        return true;
    }
}