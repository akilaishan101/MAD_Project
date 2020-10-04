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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    DBHelper dbHelper;
    EditText edit_username,edit_email,edit_phone,edit_address,edit_password,edit_password1;
    Button signup, login;
    String TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
        edit_address = findViewById(R.id.EDIT_SIGNUP_ADDRESS);
        edit_password = findViewById(R.id.SIGNUP_PASSWORD);
        edit_password1 = findViewById(R.id.SIGNUP_PASSWORD2);

        signup = findViewById(R.id.SIGNUP_BTN);
        login = findViewById(R.id.LOGIN_BTN);

        Register();
        Login();
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.User_Levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        TEXT = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void Login() {
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent2);
                    }
                }
        );
    }

    public void Register() {
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Us = edit_username.getText().toString();
                        String emaiL = edit_email.getText().toString();
                        String phone = edit_phone.getText().toString();
                        String add =  edit_address.getText().toString();
                        String pass = edit_password.getText().toString() ;
                        String pass1 = edit_password1.getText().toString() ;

                        int vali =0;

                        if (Us.length() == 0 || Us == null) {
                            vali = 1;

                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Username field Empty!")
                                    .show();
                        }
                        else{
                            vali = 0;
                        }
                        if (emaiL.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$") &&emaiL != null && emaiL.length() != 0){
                            vali = 0;

                        }
                        else {
                            vali = 1;
                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Email field Empty!")
                                    .show();

                        }
                        if (phone.length() == 10 && phone.matches("[0-9]+")){


                        }
                        else{
                            vali =1;
                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Email field Empty!")
                                    .show();
                        }
                        if (add.length() == 0|| add ==null){
                            vali =1;
                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Email field Empty!")
                                    .show();
                        }
                        if (pass.length() == 0||pass == null ){
                            vali = 1;
                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText(" Enter valid password!")
                                    .show();
                        }
                        else if (pass.equals(pass1)) {

                        }
                        else{
                            new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("SignUp Fail")
                                    .setContentText("Enter same password!")
                                    .show();
                        }

                        if (vali == 0){

                            boolean isInserted = dbHelper.Register_user(edit_username.getText().toString(),
                                    edit_email.getText().toString(),
                                    edit_phone.getText().toString(),
                                    edit_address.getText().toString(),
                                    TEXT,
                                    edit_password.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent1);
                            } else {
                                new SweetAlertDialog(SignUp.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("SignUp Fail")
                                        .setContentText("Not added user!")
                                        .show();
                            }
                        }
                        else{

                        }
                    }
                }
        );
    }


}
