package com.dung.gedung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dung.gedung.Activity.BerandaActivity;
import com.dung.gedung.Activity.JadwalActivity;
import com.dung.gedung.Activity.MasukActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, MasukActivity.class));
                    MainActivity.this.finish();
                }
            }
        };

        thread.start();
    }
}