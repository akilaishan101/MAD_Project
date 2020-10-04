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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Update_delete_shop extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String S_name ,S_comapany,S_address,S_ID,S_Dis;
    DBHelper dbHelper;
    EditText sid,sname,scompany,saddress,sdis;
    String Shop_ID;
    Button update,delete;
    ImageView usimg;
    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_shop);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DBHelper(this);
        Intent thisIntent = getIntent();
        Shop_ID = thisIntent.getStringExtra("ShopID");





        sid = findViewById(R.id.LAB_IDs);
        sname = findViewById(R.id.LAB_NAMEs);
        scompany = findViewById(R.id.LAB_COMPANYs);
        saddress = findViewById(R.id.LAB_ADDRESSs);
        sdis = findViewById(R.id.LAB_DISs);
        usimg = findViewById(R.id.uimg);
        update = findViewById(R.id.PRO_UD2);
        delete = findViewById(R.id.PRO_UD3);

        VeiwProfile(Shop_ID);
        updateData();
        deleteData();

    }
    public void deleteData(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleted_rows = dbHelper.deleteData(sid.getText().toString());
                        if (deleted_rows > 0) {

                            sid.setText("");
                            sname.setText("");
                            scompany.setText("");
                            saddress.setText("");
                            sdis.setText("");
                            usimg.setImageResource(R.mipmap.ic_launcher);
                            Toast.makeText(Update_delete_shop.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Update_delete_shop.this,User_Feed.class);
                            intent.putExtra("user","Shop Owner");
                            startActivity(intent);

                        } else {
                            Toast.makeText(Update_delete_shop.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void updateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = dbHelper.update_shop(sid.getText().toString(),
                                sname.getText().toString(),
                                scompany.getText().toString(),
                                saddress.getText().toString(),
                                sdis.getText().toString());
                        if (isUpdate == true) {
                            Toast.makeText(Update_delete_shop.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Update_delete_shop.this,Shop_Profile.class);
                            intent.putExtra("Shop_ID",sid.getText().toString());
                            startActivity(intent);

                        } else {
                            Toast.makeText(Update_delete_shop.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

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

            Intent intent = new Intent(Update_delete_shop.this,User_Feed.class);

            intent.putExtra("user",User_Feed.USER);
            startActivity(intent);

        } else if (id == R.id.nav_addshop) {
            Intent intent = new Intent(Update_delete_shop.this,Add_Shop.class);
            startActivity(intent);


        }else if (id == R.id.nav_foodSearch) {
            Intent intent = new Intent(Update_delete_shop.this,food_feed.class);
            startActivity(intent);


        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void VeiwProfile(String Shop_IDs){


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
            usimg.setImageBitmap(bit);

        }
    }
}
