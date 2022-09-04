package utar.edu.itemlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter
{
    public static final String MYDATABASE_NAME = "MY_DATABASE";
    public static final int MYDATABASE_VERSION = 1;
    public static final String MYDATABASE_TABLE = "MY_TABLE";
    public static final String KEY_CONTENT = "Item_Name";
    public static final String ID  = "id";
    public static final String KEY_CONTENT_2 = "Checked_Status";
    public static final String KEY_CONTENT_3 = "Quantity";
    public static final String KEY_CONTENT_4 = "PriceEach";
    public static final String KEY_CONTENT_5 = "TotalPrice";
    public static final String KEY_CONTENT_6 = "ListID";
    // create table xxx ();


    public static final String SCRIPT_CREATE_DATABASE = "create table " +
            MYDATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_CONTENT + " text not null, " +
            KEY_CONTENT_2 + " text not null, "+
            KEY_CONTENT_3 + " text not null, "+
            KEY_CONTENT_4 + " text, "+
            KEY_CONTENT_5 + " text, " +
            KEY_CONTENT_6 + " text not null);";




    private Context context;
    public SQLiteAdapter(Context c) {
        context = c;
    }

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    // Open the database by database name to read
    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
                MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws
            android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
                MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        sqLiteHelper.close();
    }

    public long insert(String content, String content2, String content3, String content4, String content6) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONTENT, content);
        contentValues.put(KEY_CONTENT_2, content2);
        contentValues.put(KEY_CONTENT_3, content3);
        if (content4.isEmpty() || content4 == null || content4 == "-")
        {
            contentValues.put(KEY_CONTENT_4, "-");
            contentValues.put(KEY_CONTENT_5, "-");
        }
        else {
            contentValues.put(KEY_CONTENT_4, content4);
            Double total = Double.valueOf(content4) * Integer.valueOf(content3);
            contentValues.put(KEY_CONTENT_5, String.format("%.2f", total));
        }
        contentValues.put(KEY_CONTENT_6, content6);
        return sqLiteDatabase.insert(MYDATABASE_TABLE, null, contentValues);
    }
    public int deleteAll() {
        return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
    }
    public String queueAll() {
        String[] columns = new String[] { KEY_CONTENT };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
                null, null, null, null, null);
        String result = "";
        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT);
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = result + cursor.getString(index_CONTENT) + "\n";
        }
        return result;
    }
    public List<String> readItemName(String listID) {
        Integer id = 0;
        List<String> records = new ArrayList<String>();
        String[] columns = new String[] { KEY_CONTENT };
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT+" from " +MYDATABASE_TABLE + " where " +KEY_CONTENT_6 + " = " + listID,null );
        String result = "";

        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT);
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = cursor.getString(0);
            records.add(result);
        }
        return records;
    }

    public List<String> readID(String listID)
    {
        List<String> records = new ArrayList<String>();

        Cursor cursor = sqLiteDatabase.rawQuery("select id from " +MYDATABASE_TABLE +" where " +KEY_CONTENT_6 + " = " + listID,null );

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            if (cursor!=null)
            {
                String id = Integer.toString(cursor.getInt(0));
                records.add(id);
            }
        }


        return records;
    }

    public List<String> readCheckedStatus(String listID) {
        List<String> records = new ArrayList<String>();
        String[] columns = new String[] { KEY_CONTENT_2 };
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT_2+" from " +MYDATABASE_TABLE + " where " +KEY_CONTENT_6 + " = " + listID,null );
        String result = "";
        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT_2);
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = cursor.getString(0);
            records.add(result);
        }
        return records;
    }

    public List<Integer> readQuantity(String listID) {
        List<Integer> records = new ArrayList<Integer>();
        String[] columns = new String[] { KEY_CONTENT_3 };
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT_3+" from " +MYDATABASE_TABLE + " where " +KEY_CONTENT_6 + " = " + listID,null );
        String result = "";
        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT_3);
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = cursor.getString(0);
            records.add((Integer.parseInt(result)));
        }
        return records;
    }

    public List<String> readPrice(String listID) {
        List<String> records = new ArrayList<String>();
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT_4+" from " +MYDATABASE_TABLE + " where " +KEY_CONTENT_6 + " = " + listID,null );
        String result = "";
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = cursor.getString(0);
            records.add(result);
        }
        return records;
    }
    public List<String> readTotalPrice(String listID) {
    List<String> records = new ArrayList<String>();
    String[] columns = new String[] { KEY_CONTENT_5 };
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT_5+" from " +MYDATABASE_TABLE + " where " +KEY_CONTENT_6 + " = " + listID,null );
        String result = "";
    int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT_5);
    for (cursor.moveToFirst(); !(cursor.isAfterLast());
         cursor.moveToNext()) {
        result = cursor.getString(0);
        records.add(result);
        }
    return records;
    }
    public List<String> readListID() {
        List<String> records = new ArrayList<String>();
        String[] columns = new String[] { KEY_CONTENT_6 };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
                null, null, null, null, null);
        String result = "";
        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT_6);
        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = cursor.getString(index_CONTENT);
            records.add(result);
        }
        return records;
    }

    //Helper class to manage database creation and management
    public class SQLiteHelper extends SQLiteOpenHelper
    {

        public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SCRIPT_CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL((SCRIPT_CREATE_DATABASE));
        }
    }
    public void updateCheckedStatus(Boolean checked, String id) {

//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        if (checked)
        {
            value.put(KEY_CONTENT_2, "Checked");
        }

        else
        {
            value.put(KEY_CONTENT_2, "UnChecked");
        }



        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
//        return sqLiteDatabase.insert(MYDATABASE_TABLE, null, value);
        sqLiteDatabase.update(MYDATABASE_TABLE, value, "id=?", new String[] {id});
        sqLiteDatabase.close();
    }

    public void deleteRow(String id)
    {
        sqLiteDatabase.delete(MYDATABASE_TABLE, "id= ?", new String[] {id});
        sqLiteDatabase.close();
//        SQLiteStatement stmt = sqLiteDatabase.compileStatement("DELETE FROM " + MYDATABASE_TABLE + " WHERE id=?");
//        stmt.bindString(1, id);
//        stmt.execute();
    }

    public void updateQuantity(String id, Integer quantity)
    {
        ContentValues value = new ContentValues();
        value.put(KEY_CONTENT_3, quantity.toString());
        Cursor cursor = sqLiteDatabase.rawQuery("select "+KEY_CONTENT_4+" from " +MYDATABASE_TABLE + " where id =" + id,null );
        cursor.moveToFirst();
        Double total = Double.valueOf(cursor.getString(0)) * quantity;
        value.put(KEY_CONTENT_5, String.format("%.2f", total));

        sqLiteDatabase.update(MYDATABASE_TABLE, value, "id=?", new String[] {id});
        sqLiteDatabase.close();
    }

    public Boolean isEmpty()
    {
        String count = "SELECT count(id) FROM " +MYDATABASE_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if (icount > 0)
        {
            return false;
        }
        return true;
    }



}
