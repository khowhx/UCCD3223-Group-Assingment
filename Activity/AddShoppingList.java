package com.edu.shoppinglistapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddShoppingList extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText shoppingListItem, shoppingListCategory;
    private Button saveButton;

    private SQLiteAdapter2 db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);

        db = new SQLiteAdapter2(this);
        byPassActivity();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpDialog();
            }
        });
    }


    private void createPopUpDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        shoppingListItem = view.findViewById(R.id.shoppingList_item);
        shoppingListCategory = view.findViewById(R.id.category);
        saveButton = view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shoppingListItem.getText().toString().isEmpty() && !shoppingListCategory.getText().toString().isEmpty()) {
                    saveShoppingListToDB(v);
                }
            }
        });

    }

    private void saveShoppingListToDB(View v) {
        ShoppingList shoppingList = new ShoppingList();
        String newShoppingList = shoppingListItem.getText().toString();
        String newShoppingListQty = shoppingListCategory.getText().toString();

        Toast.makeText(getApplicationContext(), "Add Successfully", Toast.LENGTH_LONG).show();

        shoppingList.setName(newShoppingList);
        shoppingList.setCty(newShoppingListQty);

        db.addShoppingList(shoppingList);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(AddShoppingList.this, ShoppingListActivity.class));
            }
        }, 1000);

    }

    public void byPassActivity() {
        if (db.getShoppingListCount() > 0) {
            startActivity(new Intent(AddShoppingList.this, ShoppingListActivity.class));
            finish();
        }
    }


}