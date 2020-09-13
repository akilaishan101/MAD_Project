package com.example.mad_bestplace;

import android.content.Intent;
import android.os.Bundle;

import com.example.mad_bestplace.Database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class food_feed_line extends AppCompatActivity {

    ListView listView;
    ArrayList<foods> list;
    food_adapter adapter = null;
    DBHelper dbHelper = new DBHelper(this);
    String Shop_ID,USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed_line);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView = (ListView) findViewById(R.id.listfood1);
        list = new ArrayList<>();

        Intent thisIntent = getIntent();
        Shop_ID = thisIntent.getStringExtra("Shop_ID");
        USER = thisIntent.getStringExtra("user");



        if (USER.equals("Customers")){

        }else if (USER.equals("shop")){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    foods food = list.get(position);

                    Intent intent = new Intent(food_feed_line.this,food_edit.class);
                    intent.putExtra("foodID",food.getFid());
                    intent.putExtra("Shop_ID",Shop_ID);
                    intent.putExtra("user",USER);
                    startActivity(intent);

                }
            });

            loaddata(Shop_ID);
        }



    }

    private void searchdata(String newText) {

        list = dbHelper.foodData(newText);
        adapter = new food_adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loaddata(Shop_ID);
    }

    public void loaddata(String sid){

        list = dbHelper.getAllfoodData(sid);
        adapter = new food_adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
