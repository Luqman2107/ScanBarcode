package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.dung.gedung.R;

public class UbahPasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        toolbar = findViewById(R.id.toolbarubahpassword);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ubah Password");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UbahPasswordActivity.this, ProfilActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(UbahPasswordActivity.this, ProfilActivity.class);
            startActivity(abc);
            finish();
        }

        this.doubleBackToExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit=false;
            }
        }, 1000);
    }
}