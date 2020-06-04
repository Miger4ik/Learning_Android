package com.example.myfirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // create all fields for text view
    private EditText userName;
    private TextView orderPrice;
    private TextView quantity;
    // create all fields for buttons
    private Button addItem;
    private Button minusItem;
    private Button addToCart;
    private Spinner spinner;
    // fields for inner logic
    private List<String> spinnerArrayList = new ArrayList<>();
    private HashMap<String, Integer> goodsMap = new HashMap<>();
    //
    private String goodsName;
    private static int quantityItems;
    private static int price;
    private static String userNameText;
    //
    ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        createGoodMap();
        createSpinner();

        // set value price and quantity in variables
        quantityItems = Integer.parseInt(quantity.getText().toString());
        price = Integer.parseInt(orderPrice.getText().toString());
        //
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

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameText = userName.getText().toString();
                Order order = new Order();
                order.setUserName(userNameText);
                order.setGoodsName(goodsName);
                order.setQuantity(quantityItems);
                order.setOrderPrice(price * quantityItems);

                Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);

                orderIntent.putExtra("userNameText", order.getUserName());
                orderIntent.putExtra("goodsName", order.getGoodsName());
                orderIntent.putExtra("quantityItems", order.getQuantity());
                orderIntent.putExtra("orderPrice", order.getOrderPrice());

                startActivity(orderIntent);
            }
        });
    }

    void createSpinner () {
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerArrayList.add("guitar");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("keyboard");

        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(spinnerAdapter);
    }

    void createGoodMap () {
        goodsMap.put("guitar", 500);
        goodsMap.put("drums", 1500);
        goodsMap.put("keyboard", 1000);
    }
    //
    void findView () {
        // find all text element by ID
        userName = findViewById(R.id.user_name_edit_text);
        orderPrice = findViewById(R.id.order_price_text);
        quantity = findViewById(R.id.quantity_text);
        // find all button by ID
        addItem = findViewById(R.id.button_add_item);
        minusItem = findViewById(R.id.button_minus_item);
        addToCart = findViewById(R.id.button_add_to_cart);
        spinner = findViewById(R.id.spinner_with_items);
    }

    void changePrice () {
        orderPrice.setText(String.valueOf(price * quantityItems));
    }

    void changeQuantity (int quantityOfInstruments) {
        quantity.setText(String.valueOf(quantityOfInstruments));
    }

    // method for increment quantity item
    private void incrementItem(){
        quantityItems ++;
        changeQuantity(quantityItems);
        changePrice();
    }

    // method for decrement quantity item
    private void decrementItem(){
        if(quantityItems > 0) {
            quantityItems --;
            changeQuantity(quantityItems);
            changePrice();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = goodsMap.get(goodsName);
        changePrice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
