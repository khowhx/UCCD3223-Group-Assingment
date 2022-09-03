package com.edu.shoppinglistapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ShoppingList> sl;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<ShoppingList> sl) {
        this.context = context;
        this.sl = sl;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        ShoppingList shoppingList = sl.get(position);

        holder.groceryItemName.setText(shoppingList.getName());
        holder.category.setText(shoppingList.getCty());
        holder.dateAdded.setText(shoppingList.getDateAdded());

    }

    @Override
    public int getItemCount() {
        return sl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView groceryItemName;
        public TextView dateAdded;
        public TextView category;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            groceryItemName = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            dateAdded = itemView.findViewById(R.id.dataAdded);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show();
                    int position = getAdapterPosition();
                    ShoppingList shoppingList = sl.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", shoppingList.getId());
                    intent.putExtra("name", shoppingList.getName());
                    intent.putExtra("category", shoppingList.getCty());
                    intent.putExtra("date", shoppingList.getDateAdded());

                    context.startActivity(intent);

                }
            });
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    ShoppingList shoppingList = sl.get(position);
                    editItem(shoppingList);
                    break;
                case R.id.deleteButton:
                    position = getAdapterPosition();
                    shoppingList = sl.get(position);
                    deleteItem(shoppingList.getId());
                    break;
            }
        }


        public void deleteItem(final int id) {
            dialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noBtn = view.findViewById(R.id.noButton);
            Button yesBtn = view.findViewById(R.id.yesButton);

            dialogBuilder.setView(view);
            dialog = dialogBuilder.create();
            dialog.show();

            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteAdapter2 db = new SQLiteAdapter2(context);
                    db.deleteShoppingList(id);
                    sl.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
        }

        public void editItem(final ShoppingList shoppingList) {
            dialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.pop_up, null);

            final EditText groceryItem = view.findViewById(R.id.shoppingList_item);
            final EditText category = view.findViewById(R.id.category);
            final TextView title = view.findViewById(R.id.title);

            title.setText("Edit Grocery");
            groceryItem.setText(shoppingList.getName());

            Button saveButton = (Button) view.findViewById(R.id.saveButton);

            dialogBuilder.setView(view);
            dialog = dialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteAdapter2 db = new SQLiteAdapter2(context);

                    shoppingList.setName(groceryItem.getText().toString());
                    shoppingList.setCty(category.getText().toString());

                    if (!groceryItem.getText().toString().isEmpty() && !category.getText().toString().isEmpty()) {
                        db.updateShoppingList(shoppingList);
                        notifyItemChanged(getAdapterPosition(), shoppingList);
                    } else {
                        Snackbar.make(view, "Add Grocery and category", Snackbar.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        }
    }


}
