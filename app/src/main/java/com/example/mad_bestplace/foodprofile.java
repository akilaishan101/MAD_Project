package com.example.mad_bestplace;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.mad_bestplace.Database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class foodprofile extends AppCompatActivity {

    String FOOD_ID,SHOP_ID;
    String f_name,f_price,f_sid,f_ID;
    DBHelper dbHelper;
    TextView fid,fname,fprice;
    Button update,delete;
    ImageView imgfood;
    byte[] img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodprofile);
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

        Intent thisIntent = getIntent();
        FOOD_ID = thisIntent.getStringExtra("foodID");
        SHOP_ID = thisIntent.getStringExtra("Shop_ID");


        dbHelper = new DBHelper(this);


        fid = findViewById(R.id.fdid);

        fname = findViewById(R.id.fdname);
        fprice = findViewById(R.id.fdprice);

        imgfood = findViewById(R.id.fdimg);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);


        VeiwProfile(FOOD_ID);
        updatefood();
        deletefood();




    }

    private void deletefood() {

        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleted_rows = dbHelper.deletefood(fid.getText().toString());
                        if (deleted_rows > 0) {

                            fid.setText("");
                            fname.setText("");
                            fprice.setText("");
                            imgfood.setImageResource(R.mipmap.ic_launcher);
                            Toast.makeText(foodprofile.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(foodprofile.this,food_feed_line.class);
                            intent.putExtra("Shop_ID",SHOP_ID);
                            intent.putExtra("user","shop");
                            startActivity(intent);

                        } else {
                            Toast.makeText(foodprofile.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }



    private void updatefood() {

        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = dbHelper.update_food(fid.getText().toString(),
                                fname.getText().toString(),
                                fprice.getText().toString());

                        if (isUpdate == true) {
                            Toast.makeText(foodprofile.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(foodprofile.this,food_feed_line.class);
                            intent.putExtra("foodID",fid.getText().toString());
                            intent.putExtra("Shop_ID",SHOP_ID);
                            intent.putExtra("user","shop");
                            startActivity(intent);

                        } else {
                            Toast.makeText(foodprofile.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

    private void VeiwProfile(String food_id) {

        Cursor cursor = dbHelper.get_food_values();
        if (cursor.moveToFirst()) {

            do {

                f_ID = (cursor.getString(0));

                if (f_ID.equals(food_id)) {

                    f_ID = (cursor.getString(0));
                    f_sid = (cursor.getString(1));
                    f_name = (cursor.getString(2));
                    f_price = (cursor.getString(3));

                    img = (cursor.getBlob(4));


                    break;

                }

            } while (cursor.moveToNext());
            fid.setText(f_ID);
            fname.setText(f_name);
            fprice.setText(f_price);

            Bitmap bit = BitmapFactory.decodeByteArray(img,0,img.length);
            imgfood.setImageBitmap(bit);


        }
    }

}
