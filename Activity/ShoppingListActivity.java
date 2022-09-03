package com.edu.shoppinglistapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ShoppingList> shoppingListList;
    private List<ShoppingList> listItems;
    private SQLiteAdapter2 db;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText shoppingListItem, category;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });


        db = new SQLiteAdapter2(this);
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingListList = new ArrayList<>();
        listItems = new ArrayList<>();

        shoppingListList = db.getAllShoppingList();
        for (ShoppingList g : shoppingListList) {
            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setName(g.getName());
            shoppingList.setCty("Category: " + g.getCty());
            shoppingList.setId(g.getId());
            shoppingList.setDateAdded("Created on: " + g.getDateAdded());

            listItems.add(shoppingList);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void createPopUpDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        shoppingListItem = view.findViewById(R.id.shoppingList_item);
        category = view.findViewById(R.id.category);
        saveButton = view.findViewById(R.id.saveButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shoppingListItem.getText().toString().isEmpty() && !category.getText().toString().isEmpty()) {
                    saveGroceryToDB(v);
                }
            }
        });

    }

    private void saveGroceryToDB(View v) {
        ShoppingList shoppingList = new ShoppingList();
        String newShoppingList = shoppingListItem.getText().toString();
        String newShoppingListQty = category.getText().toString();

        Toast.makeText(getApplicationContext(), "Add Successfully", Toast.LENGTH_LONG).show();

        shoppingList.setName(newShoppingList);
        shoppingList.setCty(newShoppingListQty);

        db.addShoppingList(shoppingList);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ShoppingListActivity.this, ShoppingListActivity.class));
            }
        }, 1000);

    }

    public void byPassActivity() {
        if (db.getShoppingListCount() > 0) {
            startActivity(new Intent(ShoppingListActivity.this, ShoppingListActivity.class));
            finish();
        }
    }

}