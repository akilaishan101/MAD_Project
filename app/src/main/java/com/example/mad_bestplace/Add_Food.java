package com.example.mad_bestplace;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.mad_bestplace.Database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Add_Food extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,price,id;
    Button addfood,addimg;
    ImageView viwe;
    DBHelper DB = new DBHelper(this);
    final int REQUEST = 999;

    String Shop_ID;
    String foodid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__food);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationonreView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationonreView.setNavigationItemSelectedListener(this);

        name = findViewById(R.id.food_name);
        price = findViewById(R.id.food_price);
        addfood =findViewById(R.id.foodbtn);
        addimg =findViewById(R.id.imgbtn);
        viwe =findViewById(R.id.foodviwe);
        id = findViewById(R.id.foodid);
        Intent thisIntent = getIntent();
        Shop_ID = thisIntent.getStringExtra("ShopID");
        foodid = thisIntent.getStringExtra("foodID");

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Add_Food.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST
                );
            }
        });

        Addfood();
        loadfood();
    }

    private void loadfood() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent  = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST );

            }
            else {
                Toast.makeText(getApplicationContext(),"you don't have access",Toast.LENGTH_LONG).show();

                return;   }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode == REQUEST && resultCode == RESULT_OK && data != null ){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                viwe.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

            Intent intent = new Intent(Add_Food.this,User_Feed.class);

            intent.putExtra("user",User_Feed.USER);
            startActivity(intent);

        } else if (id == R.id.nav_addshop) {
            Intent intent = new Intent(Add_Food.this,Add_Shop.class);
            startActivity(intent);


        }else if (id == R.id.nav_foodSearch) {
            Intent intent = new Intent(Add_Food.this,food_feed.class);
            startActivity(intent);


        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Addfood() {
        addfood.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String idw = id.getText().toString();
                        String namew = name.getText().toString();
                        String company = price.getText().toString();

                        int vali =0;

                        if (idw.length() == 0 || idw == null) {
                            vali = 1;

                            new SweetAlertDialog(Add_Food.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("foodId field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;
                        }
                        if (namew.length() == 0 || namew == null){
                            vali = 1;
                            new SweetAlertDialog(Add_Food.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("food name field Empty!")
                                    .show();
                        }
                        else {
                            vali = 0;

                        }
                        if (company.length() == 0 || name == null){
                            vali =1;
                            new SweetAlertDialog(Add_Food.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Price field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;

                        }


                        if (vali == 0){

                            DB.addfoodData(
                                    id.getText().toString().trim(),
                                    Shop_ID,
                                    name.getText().toString().trim(),
                                    price.getText().toString().trim(),
                                    imageviwetobyte(viwe)
                            );

                            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_Food.this,food_feed.class);
                            intent.putExtra("Shop_ID",Shop_ID);
                            intent.putExtra("user","shop");
                            startActivity(intent);
                        }
                        else{
                            new SweetAlertDialog(Add_Food.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Not added food data!")
                                    .show();
                        }

                    }


                }
        );
    }


    private byte[] imageviwetobyte(ImageView image){
        Bitmap bitmap  = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;

    }


}




