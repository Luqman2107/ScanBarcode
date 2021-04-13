package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dung.gedung.R;
import com.google.android.material.snackbar.Snackbar;

public class IlustrasiSuksesActivity extends AppCompatActivity {
    Button button;

    boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilustrasi_sukses);
        button = findViewById(R.id.btn_kembalipesan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(IlustrasiSuksesActivity.this, BerandaActivity.class);
                startActivity(a);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(IlustrasiSuksesActivity.this, BerandaActivity.class);
            startActivity(abc);
            finish();
        }

        this.doubleBackToExit = true;
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit=false;
            }
        }, 1000);
    }
}