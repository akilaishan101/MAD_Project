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

public class food_edit extends AppCompatActivity {


    String f_name,f_price,f_sid,f_ID;
    byte[] f_image;
    DBHelper dbHelper;
    TextView fid,fname,fprice;
    Button edit;
    ImageView imgfood;

    String FOOD_ID,SHOP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
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

        dbHelper = new DBHelper(this);


        fid = findViewById(R.id.fid);

        fname = findViewById(R.id.fname);
        fprice = findViewById(R.id.fprice);

        imgfood = findViewById(R.id.fimg);
        edit = findViewById(R.id.edit);

        //get shopID
        Intent thisIntent = getIntent();
        FOOD_ID = thisIntent.getStringExtra("foodID");
        SHOP_ID = thisIntent.getStringExtra("Shop_ID");
        VeiwProfile(FOOD_ID);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect_food = new Intent(food_edit.this,foodprofile.class);
                redirect_food.putExtra("foodID",FOOD_ID);
                redirect_food.putExtra("Shop_ID",SHOP_ID);
                startActivity(redirect_food);
            }
        });

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

                    f_image = (cursor.getBlob(4));


                    break;

                }

            } while (cursor.moveToNext());
            fid.setText(f_ID);
            fname.setText(f_name);
            fprice.setText(f_price);
            Bitmap bit = BitmapFactory.decodeByteArray(f_image,0,f_image.length);
            imgfood.setImageBitmap(bit);


        }

    }

}
