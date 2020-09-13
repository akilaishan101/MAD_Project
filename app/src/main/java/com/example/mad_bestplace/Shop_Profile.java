package com.example.mad_bestplace;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Shop_Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    String S_name,S_comapany,S_address,S_ID,S_Dis;
    byte[] image;
    DBHelper dbHelper;
    TextView sid,sname,scompany,saddress,sdis;
    Button update_delete,add_food,viwe;
    ImageView imgshop;

    String Shop_ID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__profile);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DBHelper(this);


        sid = findViewById(R.id.LAB_ID);
        sname = findViewById(R.id.LAB_NAME);
        scompany = findViewById(R.id.LAB_COMPANY);
        saddress = findViewById(R.id.LAB_ADDRESS);
        sdis = findViewById(R.id.LAB_DIS);
        imgshop = findViewById(R.id.shopprofile);
        update_delete = findViewById(R.id.PRO_UD);
        add_food = findViewById(R.id.PRO_ADDFOOD);
        viwe = findViewById(R.id.view);
        //get shopID
        Intent thisIntent = getIntent();
        Shop_ID = thisIntent.getStringExtra("Shop_ID");

        VeiwProfile(Shop_ID);

        //update delete
        update_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect_food = new Intent(Shop_Profile.this,Update_delete_shop.class);
                redirect_food.putExtra("ShopID",Shop_ID);
                startActivity(redirect_food);

            }
        });

        add_food.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent redirect_food = new Intent(Shop_Profile.this,Add_Food.class);
                        redirect_food.putExtra("ShopID",Shop_ID);
                        startActivity(redirect_food);
                    }
                }
        );

        viwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shop_Profile.this,food_feed_line.class);
                intent.putExtra("Shop_ID",sid.getText().toString());
                intent.putExtra("user","shop");
                startActivity(intent);
            }
        });

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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_addshop) {

        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void VeiwProfile(String Shop_IDs ){


        Cursor cursor = dbHelper.get_shop_values();
        if (cursor.moveToFirst()) {

            do {

                S_ID = (cursor.getString(0));

                if (S_ID.equals(Shop_IDs)) {

                    S_ID = (cursor.getString(0));
                    S_name = (cursor.getString(1));
                    S_comapany = (cursor.getString(2));
                    S_address = (cursor.getString(3));
                    S_Dis = (cursor.getString(4));
                    image = (cursor.getBlob(5));


                    break;

                }

            } while (cursor.moveToNext());
            sid.setText(Shop_ID);
            sname.setText(S_name);
            scompany.setText(S_comapany);
            saddress.setText(S_address);
            sdis.setText(S_Dis);
            Bitmap bit = BitmapFactory.decodeByteArray(image,0,image.length);
            imgshop.setImageBitmap(bit);


        }
    }

}
