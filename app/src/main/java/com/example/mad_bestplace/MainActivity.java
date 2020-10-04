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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText edit_username,edit_password;
    Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        dbHelper = new DBHelper(this);

        edit_username = findViewById(R.id.LOGIN_USERNAME);
        edit_password = findViewById(R.id.LOGIN_PASSWORD);

        signup = findViewById(R.id.REG_BTN);
        login = findViewById(R.id.LOGIN_BTN);

        LoginCheck();

        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent redirect_reg = new Intent(MainActivity.this,SignUp.class);
                        startActivity(redirect_reg);
                    }
                }

        );


    }


    public void LoginCheck() {
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String password = dbHelper.login_Check(edit_username.getText().toString());
                        String passCheck = edit_password.getText().toString();
                        String username = edit_username.getText().toString();
                        String type = dbHelper.passType();
                        String shopowner = "Shop Owner",customer = "Customers";

                        if (username.length() == 0 || username == null ) {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Login Fail")
                                    .setContentText("Username field Empty!")
                                    .show();
                        }else if(passCheck.length() == 0 || passCheck == null){

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Login Fail")
                                    .setContentText("Password field Empty!")
                                    .show();
                        }
                        else{

                            if (password.equals(passCheck)) {
                                Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                                if (type.equals(shopowner)) {
                                    Intent redirect_user_feed = new Intent(MainActivity.this, User_Feed.class);
                                    redirect_user_feed.putExtra("user", shopowner);
                                    startActivity(redirect_user_feed);
                                } else {
                                    Intent redirect_user_feed = new Intent(MainActivity.this, User_Feed.class);
                                    redirect_user_feed.putExtra("user", customer);
                                    startActivity(redirect_user_feed);
                                }

                            } else {

                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Login fail")
                                        .setContentText("User not found!")
                                        .show();
                            }

                        }

                    }
                }
        );
    }
}
