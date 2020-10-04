package com.example.mad_bestplace;

import android.content.Intent;
import android.os.Bundle;

import com.example.mad_bestplace.Database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class User_Feed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ListView listView;
    ArrayList <shops> list;
    shop_adapter adapter = null;
    DBHelper dbHelper = new DBHelper(this);
    public static String USER;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        listView = (ListView) findViewById(R.id.list);
        list = new ArrayList<>();

        Intent thisIntent = getIntent();
        USER = thisIntent.getStringExtra("user");

        search = (SearchView) findViewById(R.id.shopsearch);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchshopdata(newText);
                return false;
            }
        });

        if(USER.equals("Shop Owner")) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    shops shop = list.get(position);

                    Intent intent = new Intent(User_Feed.this, Shop_Profile.class);
                    intent.putExtra("Shop_ID", shop.getSid());

                    startActivity(intent);

                }
            });

            loaddata();
        }else if(USER.equals("Customers")){

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    shops shop = list.get(position);

                    Intent intent = new Intent(User_Feed.this, food_feed.class);
                    intent.putExtra("Shop_ID", shop.getSid());
                    intent.putExtra("user",USER);
                    startActivity(intent);

                }
            });
            loaddata();
        }
    }

    private void searchshopdata(String newText) {
        list = dbHelper.getsearchshopData(newText);
        adapter = new shop_adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddata();
    }

    public void loaddata(){

        list = dbHelper.getAllData();
        adapter = new shop_adapter(this,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shopfeed){

            Intent intent = new Intent(User_Feed.this,User_Feed.class);

            intent.putExtra("user",USER);
            startActivity(intent);

        } else if (id == R.id.nav_addshop) {
            Intent intent = new Intent(User_Feed.this,Add_Shop.class);
            startActivity(intent);


        }else if (id == R.id.nav_foodSearch) {
            Intent intent = new Intent(User_Feed.this,food_feed.class);
            startActivity(intent);


        } else if (id == R.id.nav_tools) {


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
