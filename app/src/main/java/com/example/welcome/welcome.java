package com.example.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class welcome extends AppCompatActivity {



    ViewPager viewPager;
    sliderAdapter adapter;
    ImageView[] indicators;
    LinearLayout indicatorContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.welcome);
        adapter = new sliderAdapter(this);
        viewPager.setAdapter(adapter);
        setupIndicators();
        setCurrentIndicator(0);
        ConstraintLayout registerButton = findViewById(R.id.register);
        ConstraintLayout signInButton = findViewById(R.id.signIn);
        ConstraintLayout chatButton = findViewById(R.id.chat);
        // Set click listeners
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the registration activity
                openRegisterActivity();
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the mainactivity_chat
                Intent intent = new Intent(welcome.this, MainActivity_Chat.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the sign-in activity (login activity)
                openSignInActivity();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageSelected(int position) {
                setCurrentIndicator(position);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setupIndicators() {
       indicators = new ImageView[adapter.getCount()];
       indicatorContainer = findViewById(R.id.indicatorsContainer);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(getDrawable(R.drawable.indecator1));
            indicators[i].setLayoutParams(layoutParams);
            indicatorContainer.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = indicatorContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorContainer.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(getDrawable(R.drawable.indecator));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.indecator1));
            }
        }
    }
    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openSignInActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



}