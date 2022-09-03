package com.edu.shoppinglistapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView itemName, category, dateAdded;
    private int groceryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = findViewById(R.id.itemNameDetail);
        category = findViewById(R.id.itemCtyDetail);
        dateAdded = findViewById(R.id.itemDateAddedDetail);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            itemName.setText(bundle.getString("name"));
            category.setText(bundle.getString("category"));
            dateAdded.setText(bundle.getString("date"));

            groceryId = bundle.getInt("id");
        }
    }
}