package com.lyg.factorysmartsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    TextView daftar;
    EditText email, password;
    ImageView ImageSignIn;
    private String valid_email, valid_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
        InitilizeMail();
        InitilizePwd();

        ImageSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() < 8) {
                    Toast.makeText(Login.this, "Password cannot be less than 8 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent main = new Intent(Login.this, MainMenu.class);
                    startActivity(main);
                    finish();
                }
            }
        });

    }

    public void init(){

        daftar = findViewById(R.id.txt_daftar);
        email = findViewById(R.id.EmailText);
        password = findViewById(R.id.pwdText);
        ImageSignIn = findViewById(R.id.ImageSignInOnLogin);

        daftar.setPaintFlags(daftar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void InitilizeMail() {
        // TODO Auto-generated method stub

        email = findViewById(R.id.EmailText);

        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                Is_Valid_Email(email); // pass your EditText Obj here.
            }

            public void Is_Valid_Email(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else if (isEmailValid(edt.getText().toString()) == false) {
                    edt.setError("Invalid Email Address");
                    valid_email = null;
                } else {
                    valid_email = edt.getText().toString();
                }
            }

            boolean isEmailValid(CharSequence email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches();
            } // end of TextWatcher (email)
        } );

    }

    private void InitilizePwd() {
        // TODO Auto-generated method stub

        password = findViewById(R.id.pwdText);

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                Is_Check_Count(password); // pass your EditText Obj here.
            }

            public void Is_Check_Count(EditText edt) {
                if (edt.getText().toString() == null) {
                    edt.setError("Password cannot be less than 8 characters!");
                    valid_pwd = null;
                } else if (edt.getText().toString().length() < 8) {
                    edt.setError("Password cannot be less than 8 characters!");
                    valid_pwd = null;
                } else {
                    valid_pwd = edt.getText().toString();
                }
            }


        } );

    }
}