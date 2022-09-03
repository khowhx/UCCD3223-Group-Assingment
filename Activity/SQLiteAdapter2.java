package com.edu.shoppinglistapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteAdapter2 extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MY_DATABASE";
    public static final String TABLE_NAME = "shoppingListTBL";

    public static final String KEY_ID = "sid";
    public static final String KEY_LIST_ITEM = "shoppingListItem";
    public static final String KEY_CTY = "shoppingListCTY";
    public static final String KEY_DATE = "shoppingListDateAdded";

    private Context ctx;

    public SQLiteAdapter2(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LIST_ITEM + " TEXT,"
                + KEY_CTY + " TEXT,"
                + KEY_DATE + " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_ITEM, shoppingList.getName());
        values.put(KEY_CTY, shoppingList.getCty());
        values.put(KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(TABLE_NAME, null, values);

        Log.d("Saved!", "Saved to db" + values);
    }


    //get Item count
    public int getShoppingListCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    //update record
    public int updateShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_ITEM, shoppingList.getName());
        values.put(KEY_CTY, shoppingList.getCty());
        values.put(KEY_DATE, java.lang.System.currentTimeMillis());

        return db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{
                String.valueOf(shoppingList.getId())
        });
    }

    //delete shopping list
    public void deleteShoppingList(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "= ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //    get all shopping list
    @SuppressLint("Range")
    public List<ShoppingList> getAllShoppingList() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<ShoppingList> shoppingListList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_ID, KEY_LIST_ITEM, KEY_CTY, KEY_DATE
        }, null, null, null, null, KEY_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                shoppingList.setName(cursor.getString(cursor.getColumnIndex(KEY_LIST_ITEM)));
                shoppingList.setCty(cursor.getString(cursor.getColumnIndex(KEY_CTY)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(KEY_DATE))));

                shoppingList.setDateAdded(formatDate);

                shoppingListList.add(shoppingList);
            } while (cursor.moveToNext());
        }
        return shoppingListList;
    }
}
