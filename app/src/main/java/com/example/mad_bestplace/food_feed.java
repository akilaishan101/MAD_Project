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
import android.widget.Toast;

import java.util.ArrayList;

public class food_feed extends AppCompatActivity {

    ListView listView;
    ArrayList<foods> list;
    food_adapter adapter = null;
    DBHelper dbHelper = new DBHelper(this);
    String Shop_ID,USER;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        listView = (ListView) findViewById(R.id.foodlist);
        list = new ArrayList<>();

        Intent thisIntent = getIntent();
        Shop_ID = thisIntent.getStringExtra("Shop_ID");
        USER = thisIntent.getStringExtra("user");

        search = (SearchView) findViewById(R.id.searchView);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchdata(newText);
                return false;

            }

        });

        loaddata(Shop_ID);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    foods food = list.get(position);

                    Intent intent = new Intent(food_feed.this,food_edit.class);
                    intent.putExtra("foodID",food.getFid());
                    intent.putExtra("Shop_ID",Shop_ID);
                    intent.putExtra("user",USER);
                    startActivity(intent);

                }
            });


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
    public void loadalldata(){

        list = dbHelper.getAllfData();
        adapter = new food_adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
