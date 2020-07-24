package com.example.dialogwindow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button showDialogButton;
    private boolean flag = true;
    private String message = "Хочете вийти з програми?";
    private String title = "Вихід";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findAllViews();
        addListenerOnButtons();
    }

    private void addListenerOnButtons () {
        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    createAlertDialog(message, title, false).show();
                }
            }
        });
    }

    private AlertDialog createAlertDialog (String message, String title, boolean cancelable) {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);

        aBuilder.setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = aBuilder.create();
        alert.setTitle(title);

        return alert;
    }

    private void findAllViews() {
        showDialogButton = findViewById(R.id.show_dialog_button);
    }


}