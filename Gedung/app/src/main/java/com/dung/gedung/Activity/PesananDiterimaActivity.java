package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dung.gedung.R;

public class PesananDiterimaActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_diterima);
        toolbar = findViewById(R.id.toolbarpesananditerima);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Pesanan Diterima");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PesananDiterimaActivity.this, PemberitahuanActivity.class));
                finish();
            }
        });
    }
}