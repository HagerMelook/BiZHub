package com.example.welcome;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find views
        TextView nameTextView = findViewById(R.id.textViewName);
        TextView phoneTextView = findViewById(R.id.textViewPhone);
        TextView emailTextView = findViewById(R.id.textViewEmail);
        TextView locationTextView = findViewById(R.id.textViewLocation);
        TextView typeTextView = findViewById(R.id.textViewType);

        // Retrieve user data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phone");
            String email = intent.getStringExtra("email");
            String location = intent.getStringExtra("location");
            String type = intent.getStringExtra("type");

            // Display user data in TextViews
            displayUserData(nameTextView, "Name", name);
            displayUserData(phoneTextView, "Phone", phone);
            displayUserData(emailTextView, "Email", email);
            displayUserData(locationTextView, "Location", location);
            displayUserData(typeTextView, "type", type);
        }
    }

    private void displayUserData(TextView textView, String label, String value) {

        textView.setText(label + ": " + value);
    }


}
