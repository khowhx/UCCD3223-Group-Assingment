package utar.edu.shoppinglistapplication;

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
////        sql.deleteAll();
//        sql.insert("Item A","UnChecked", "2", "-","-", "1");
//        sql.insert("Item B","UnChecked","1", "-","-", "2");
//        sql.insert("Item C","UnChecked","1", "-","-", "1");
//        sql.insert("Item D","Checked","1", "-","-","1");
        sql.close();


        // read
        List<ContentValues> cvList = new ArrayList<ContentValues>();


        sql.openToRead();
        List<String> contentRead = sql.readAll();
        List<String> checkedStatus = sql.readCheckedStatus();
        List<String> id = sql.readID();

        sql.close();


        TableLayout tableLayout = new TableLayout(this);
        List<TableRow> tableRows = new ArrayList<TableRow>();
        List<CheckBox> checkBoxes = new ArrayList<CheckBox>();

        for (int i = 0; i<contentRead.size(); i++)
        {
            ContentValues cv = new ContentValues();
            cv.put("Column_1", contentRead.get(i));
            cv.put("Checked_Status", checkedStatus.get(i));
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

            TextView tv = new TextView(this);

            tv.setText(contentRead.get(i));
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
            tableRow.addView(tv);
            tableRows.add(tableRow);

            ColorDrawable[] colorDrawables = {new ColorDrawable(Color.WHITE), new ColorDrawable(Color.GREEN)};
            TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);

            int number = i;



            //Update the status of the items
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < tableRows.size(); i++)
                    {
                        if (checkBoxes.get(i).isChecked())
                        {
                            tableRows.get(i).setBackgroundColor(Color.GREEN);
                        }
                        else
                        {
                            tableRows.get(i).setBackgroundColor(Color.WHITE);
                        }
                        sql.openToWrite();
                        sql.updateCheckedStatus(checkBoxes.get(i).isChecked(), id.get(i));
                        sql.close();

                    }
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
}