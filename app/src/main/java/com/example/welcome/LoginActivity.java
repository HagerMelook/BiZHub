package com.example.welcome;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmailLogin);
        passwordEditText = findViewById(R.id.editTextPasswordLogin);
        ConstraintLayout loginButton = findViewById(R.id.signIn);
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        // Validate input
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            // Email or password is empty, show an error message
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
        } else {
            // Check if user exists
            User user = getUserData(email);

            if (user != null) {
                // Check if entered password matches the stored password
                if (password.equals(user.getPassword())) {
                    // Password matches, navigate to the ProfileActivity
                    navigateToProfile(user);
                } else {
                    // Password does not match, show an error message
                    Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // User does not exist, show an error message
                Toast.makeText(this, "User not registered. Please sign up.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private User getUserData(String email) {

        String filename = "users.json";

        try {
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            // Parse JSON string to an array of User objects
            Gson gson = new Gson();
            User[] users = gson.fromJson(sb.toString(), User[].class);

            if (users != null) {
                for (User user : users) {
                    if (user.getEmail().equals(email)) {
                        return user;
                    }
                }
            }
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void navigateToProfile(User user) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("phone", user.getPhone());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("location", user.getLocation());
        intent.putExtra("type", user.getType());
        startActivity(intent);
        finish();
    }
}
