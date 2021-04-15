package com.lyg.factorysmartsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation fadein;
    ImageView logo;
    TextView HeadTitle, VersionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fadein = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        logo = findViewById(R.id.LogoOnSplash);
        HeadTitle = findViewById(R.id.HeaderTitleOnSplash);
        VersionTitle = findViewById(R.id.VersionTitleOnSplash);

        logo.setAnimation(fadein);
        HeadTitle.setAnimation(fadein);
        VersionTitle.setAnimation(fadein);

        // Splashscreen method
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 startActivity(new Intent(SplashScreen.this, Login.class));
                 finish();

                 Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

             }

         }, 3000);
    }
}