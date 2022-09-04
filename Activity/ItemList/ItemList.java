package utar.edu.itemlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemList extends AppCompatActivity {

    private SQLiteAdapter sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item_list);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sql = new SQLiteAdapter(this);
        sql.openToWrite();
        if (sql.isEmpty())
        {
            sql.insert("Item A","UnChecked", "2", "1.50", "1");
            sql.insert("Item B","UnChecked","1", "2.00", "2");
            sql.insert("Item C","UnChecked","1", "3.50", "1");
            sql.insert("Item D","Checked","1", "4.00","1");

        }

        sql.close();


        // read
        List<ContentValues> cvList = new ArrayList<ContentValues>();


        sql.openToRead();
        List<String> id = sql.readID("1");
        List<String> itemName = sql.readItemName("1");
        List<String> checkedStatus = sql.readCheckedStatus("1");
        List<Integer> quantity = sql.readQuantity("1");
        List<String> price = sql.readPrice("1");
        List<String> totalPrice = sql.readTotalPrice("1");
        List<String> ListID = sql.readListID();

        sql.close();


        TableLayout tableLayout = new TableLayout(this);
        List<TableRow> tableRows = new ArrayList<TableRow>();
        List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        List<TextView> tvList = new ArrayList<TextView>();
        List<Button> increaseButtons = new ArrayList<Button>();
        List<Button> decreaseButtons = new ArrayList<Button>();
        List<Button> deleteButtons = new ArrayList<Button>();

        String [] str = {"Finish", "Item", "", "Quantity","", "Price Each", "Total Price"};
        TableRow firstRow = new TableRow(this);
        for (int i = 0; i < str.length; i++)
        {
            TextView tvFirst = new TextView(this);
            tvFirst.setText(str[i]);
            firstRow.addView(tvFirst);
        }
        tableRows.add(firstRow);

        for (int i = 0; i<itemName.size(); i++)
        {
//            ContentValues cv = new ContentValues();
//            cv.put("Column_1", itemName.get(i));
//            cv.put("Checked_Status", checkedStatus.get(i));
            TableRow tableRow = new TableRow(this );
            CheckBox cb = new CheckBox(this);

            if(checkedStatus.get(i).equals("Checked"))
            {
                cb.setChecked(true);
            }
            else
            {
                cb.setChecked(false);
            }

            TextView tv1 = new TextView(this);

            tv1.setText(itemName.get(i));
            if (cb.isChecked())
            {
                tableRow.setBackgroundColor(Color.GREEN);
            }
            else
            {
                tableRow.setBackgroundColor(Color.WHITE);
            }
            tableRow.addView(cb);
            checkBoxes.add(cb);
            tableRow.addView(tv1);

            TextView tv2 = new TextView(this);
            tv2.setText("\t" + quantity.get(i).toString() + "\t");
            TextView tv3 = new TextView(this);
            tv3.setText("RM " + price.get(i) + "\t");
            TextView tv4 = new TextView(this);
            tv4.setText("RM" + totalPrice.get(i)+ "\t");

            Button increase = new Button(this);
            increase.setText("+");
            Button decrease = new Button(this);
            decrease.setText("-");
            tableRow.addView(decrease);
            decreaseButtons.add(decrease);
            tableRow.addView(tv2);
            tableRow.addView(increase);
            increaseButtons.add(increase);

            tableRow.addView(tv3);
            tableRow.addView(tv4);

            Button delete = new Button(this);
            delete.setText("Delete");
            tableRow.addView(delete);
            deleteButtons.add(delete);

            tableRows.add(tableRow);


            int number = i;



            //Update the status of the items
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < checkBoxes.size(); i++)
                    {
                        if (checkBoxes.get(i).isChecked())
                        {
                            tableRows.get(i+1).setBackgroundColor(Color.GREEN);
                        }
                        else
                        {
                            tableRows.get(i+1).setBackgroundColor(Color.WHITE);
                        }
                        sql.openToWrite();
                        sql.updateCheckedStatus(checkBoxes.get(i).isChecked(), id.get(i));
                        sql.close();

                    }
                }
            });
        }

        for (int i = 0; i< increaseButtons.size(); i++)
        {

            int count = i;
           increaseButtons.get(i).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   sql.openToWrite();
                   int number = Integer.valueOf(quantity.get(count));
                   number++;
                   quantity.set(count, number);
                   sql.updateQuantity(id.get(count), number);
                   sql.close();

                   refresh();          }
           });

            decreaseButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sql.openToWrite();
                    int number = Integer.valueOf(quantity.get(count));
                    if (number>0)
                    {
                        number--;
                        quantity.set(count, number);

                    }
                    sql.updateQuantity(id.get(count), number);
                    sql.close();

                    refresh();
                }
            });
            deleteButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sql.openToWrite();
                    sql.deleteRow(id.get(count));
                    sql.close();

                    refresh();

                }
            });
        }


        for (int i = 0; i< tableRows.size();i++)
        {
            tableLayout.addView(tableRows.get(i));
        }

        Button btn = new Button(this);
        btn.setText("Add new Items");
        ll.addView(btn);


        ll.addView(tableLayout);
        setContentView(ll);
    }
    public void refresh()
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}