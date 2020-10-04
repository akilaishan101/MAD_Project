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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    DBHelper dbHelper;
    EditText edit_username,edit_email,edit_phone,edit_message;
    Button signup;
    String TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        dbHelper = new DBHelper(this);

        edit_username = findViewById(R.id.EDIT_SIGNUP_USERNAME);
        edit_email = findViewById(R.id.EDIT_SIGNUP_EMAIL);
        edit_phone = findViewById(R.id.EDIT_SIGNUP_PHONE);
        edit_message = findViewById(R.id.EDIT_SIGNUP_ADDRESS);


        signup = findViewById(R.id.SIGNUP_BTN);

        Send();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        TEXT = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void Send() {
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Us = edit_username.getText().toString();
                        String emaiL = edit_email.getText().toString();
                        String phone = edit_phone.getText().toString();
                        String add =  edit_message.getText().toString();


                        int vali =0;

                        if (Us.length() == 0 || Us == null) {
                            vali = 1;
                            Toast.makeText(Feedback.this, "Enter Valid Username!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            vali = 0;
                        }
                        if (emaiL.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$") &&emaiL != null && emaiL.length() != 0){
                            vali = 0;

                        }
                        else {
                            vali = 1;
                            Toast.makeText(Feedback.this, "Enter a Valid Email!", Toast.LENGTH_LONG).show();

                        }
                        if (phone.length() == 10 && phone.matches("[0-9]+")){


                        }
                        else{
                            vali =1;
                            Toast.makeText(Feedback.this, "Enter a Valid Phone Number!", Toast.LENGTH_LONG).show();
                        }
                        if (add.length() == 0|| add ==null){
                            vali =1;
                            Toast.makeText(Feedback.this, "Address Field is Empty!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Feedback.this, "Enter the Same Password!", Toast.LENGTH_LONG).show();
                        }

                        if (vali == 0){
                            boolean isInserted = dbHelper.feedback(edit_username.getText().toString(),
                                    edit_email.getText().toString(),
                                    edit_phone.getText().toString(),
                                    edit_message.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(Feedback.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(Feedback.this, User_Feed.class);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(Feedback.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{

                        }
                    }
                }
        );
    }


}
