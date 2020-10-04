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

public class food_edit extends AppCompatActivity {


    String f_name,f_price,f_sid,f_ID;
    byte[] f_image;
    DBHelper dbHelper;
    TextView fid,fname,fprice;
    Button edit,delete;
    ImageView imgfood;

    String FOOD_ID,SHOP_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DBHelper(this);


        fid = findViewById(R.id.fid);

        fname = findViewById(R.id.fname);
        fprice = findViewById(R.id.fprice);

        imgfood = findViewById(R.id.fimg);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete1);


        //get shopID
        Intent thisIntent = getIntent();
        FOOD_ID = thisIntent.getStringExtra("foodID");
        SHOP_ID = thisIntent.getStringExtra("Shop_ID");
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
                            Toast.makeText(food_edit.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(food_edit.this,food_feed.class);
                            intent.putExtra("Shop_ID",SHOP_ID);
                            intent.putExtra("user","shop");
                            startActivity(intent);

                        } else {
                            Toast.makeText(food_edit.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }



    private void updatefood() {

        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = dbHelper.update_food(fid.getText().toString(),
                                fname.getText().toString(),
                                fprice.getText().toString());

                        if (isUpdate == true) {
                            Toast.makeText(food_edit.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(food_edit.this,food_feed.class);
                            intent.putExtra("foodID",fid.getText().toString());
                            intent.putExtra("Shop_ID",SHOP_ID);
                            intent.putExtra("user","shop");
                            startActivity(intent);

                        } else {
                            Toast.makeText(food_edit.this, "Error", Toast.LENGTH_LONG).show();
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
