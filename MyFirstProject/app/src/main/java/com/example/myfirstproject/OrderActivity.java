package com.example.myfirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private Intent orderIntent;
    private TextView orderTextView;
    private Button submitButton;
    private String userName;
    private String goodsName;
    private int quantity;
    private double orderPrice;

    private String[] addresses = {"miger4ik1590@gmail.com"};
    private String subject = "Order from Music Shop";
    private String mailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Your Order");

        orderTextView = findViewById(R.id.orderTextView);
        submitButton = findViewById(R.id.submitOrder);

        orderIntent = getIntent();

        userName = orderIntent.getStringExtra("userNameText");
        goodsName = orderIntent.getStringExtra("goodsName");
        quantity = orderIntent.getIntExtra("quantityItems", 0);
        orderPrice = orderIntent.getDoubleExtra("orderPrice", 0);

        mailText = userName + ", "+ goodsName + ", " + quantity + ", " + orderPrice;
        orderTextView.setText(mailText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail(addresses, subject, mailText);
            }
        });

    }

    public void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent((Intent.ACTION_SEND));
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
