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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Add_Shop extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper dbHelper;
    EditText edit_id, edit_name, edit_cmpany, edit_address, edit_discription;
    Button add,saddpic;
    ImageView simage;

    DBHelper DB = new DBHelper(this);
    final int REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__shop);
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

        edit_id = (findViewById(R.id.EDIT_SID));
        edit_name = (findViewById(R.id.EDIT_SNAME));
        edit_cmpany = (findViewById(R.id.EDIT_SCOMPNAY));
        edit_address = (findViewById(R.id.EDIT_SADDRESS));
        edit_discription = (findViewById(R.id.EDIT_SDISCRIPTION));

        simage = findViewById(R.id.Simg);
        add = findViewById(R.id.ADD_SHOP);
        saddpic = findViewById(R.id.SHOP_ADD_PIC);



        saddpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Add_Shop.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST
                );
            }
        });

        AddShop();
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
                simage.setImageBitmap(bitmap);
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

            Intent intent = new Intent(Add_Shop.this,User_Feed.class);

            intent.putExtra("user",User_Feed.USER);
            startActivity(intent);

        } else if (id == R.id.nav_addshop) {
            Intent intent = new Intent(Add_Shop.this,Add_Shop.class);
            startActivity(intent);


        }else if (id == R.id.nav_foodSearch) {
            Intent intent = new Intent(Add_Shop.this,food_feed.class);
            startActivity(intent);


        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void AddShop() {
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Us = edit_id.getText().toString();
                        String name = edit_name.getText().toString();
                        String company = edit_cmpany.getText().toString();
                        String add =  edit_address.getText().toString();
                        String pass = edit_discription.getText().toString() ;

                        int vali =0;

                        if (Us.length() == 0 || Us == null) {
                            vali = 1;

                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("ShopId field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;
                        }
                        if (name.length() == 0 || name == null){
                            vali = 1;
                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Shop name field Empty!")
                                    .show();
                        }
                        else {
                            vali = 0;

                        }
                        if (company.length() == 0 || name == null){
                            vali =1;
                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Address field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;

                        }
                        if (add.length() == 0|| add ==null){
                            vali =1;
                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Phone field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;

                        }
                        if (pass.length() == 0|| pass ==null){
                            vali =1;
                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Description field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;

                        }

                        if (vali == 0){

                            DB.insertData(
                                    edit_id.getText().toString().trim(),
                                    edit_name.getText().toString().trim(),
                                    edit_cmpany.getText().toString().trim(),
                                    edit_address.getText().toString().trim(),
                                    edit_discription.getText().toString().trim(),
                                    imageviwetobyte(simage)
                            );

                            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Add_Shop.this,Shop_Profile.class);
                            intent.putExtra("Shop_ID",edit_id.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            new SweetAlertDialog(Add_Shop.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Not added shop data!")
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
