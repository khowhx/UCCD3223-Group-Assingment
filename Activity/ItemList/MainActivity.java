package utar.edu.itemlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = new LinearLayout(this);
        Button btn = new Button(this);
        Intent itemList = new Intent(MainActivity.this, ItemList.class);
        btn.setText("Items");
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                startActivity(itemList);
            }
        });
        ll.addView(btn);
        setContentView(ll);

    }
}