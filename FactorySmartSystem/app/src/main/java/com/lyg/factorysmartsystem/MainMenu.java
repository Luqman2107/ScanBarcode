package com.lyg.factorysmartsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainMenu extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat;
    String Date;
    ImageView ImageExecMenu1;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        init();

        Calendar calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEE, MMM dd, ''yy");

        Date=simpleDateFormat.format(calendar.getTime());
        TextView tvDate = findViewById(R.id.tvDateOnMenu);
        tvDate.setText(Date);

        ImageExecMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View)
            {
                    Intent main = new Intent(MainMenu.this, MainActivity.class);
                    startActivity(main);
            }
        });



    }

    public void init(){

        ImageExecMenu1 = findViewById(R.id.ivExec1);

    }
}