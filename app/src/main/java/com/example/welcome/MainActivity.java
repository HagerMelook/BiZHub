package com.example.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static int TIME = 5000;

    Animation topAnim,botAnim;

    ImageView logo;
    TextView moto,company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        moto = findViewById(R.id.moto);
        company = findViewById(R.id.company);

        logo.setAnimation(topAnim);
        company.setAnimation(botAnim);
        moto.setAnimation(botAnim);

        final Runnable r = () -> {

            Intent intent = new Intent(MainActivity.this,welcome.class);
            startActivity(intent);
            finish();
        };

        new Handler().postDelayed(r,TIME);


    }


}