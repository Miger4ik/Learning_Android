package com.example.myfirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // create all fields for text view
    private EditText userName;
    private TextView orderPrice;
    private TextView quantity;
    // create all fields for buttons
    private Button addItem;
    private Button minusItem;
    private Button addToCart;
    // fields for inner logic
    private static int quantityItems;
    private static int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find all text element by ID
        userName = findViewById(R.id.user_name_edit_text);
        orderPrice = findViewById(R.id.order_price_text);
        quantity = findViewById(R.id.quantity_text);
        // find all button by ID
        addItem = findViewById(R.id.button_add_item);
        minusItem = findViewById(R.id.button_minus_item);
        addToCart = findViewById(R.id.button_add_to_cart);
        // set value price and quantity in variables
        quantityItems = Integer.parseInt(quantity.getText().toString());
        price = Integer.parseInt(orderPrice.getText().toString());

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementItem();
            }
        });

        minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementItem();
            }
        });
    }
    // method for increment quantity item
    private void incrementItem(){
        quantityItems ++;
        quantity.setText(String.valueOf(quantityItems));
    }
    // method for decrement quantity item
    private void decrementItem(){
        if(quantityItems > 0) {
            quantityItems --;
            quantity.setText(String.valueOf(quantityItems));
        }
    }
}
