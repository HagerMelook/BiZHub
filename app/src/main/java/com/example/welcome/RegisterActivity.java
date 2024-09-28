package com.example.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private List<User> userList;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText locationEditText;
    private RadioGroup typeRadioGroup;
    private RadioButton typeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.editTextName);
        phoneEditText = findViewById(R.id.editTextPhone);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        locationEditText = findViewById(R.id.editTextLocation);
        typeRadioGroup = findViewById(R.id.radioGroupType);
        ConstraintLayout registerButton = findViewById(R.id.register);
        typeRadioGroup.clearCheck();
        registerButton.setOnClickListener(v -> attemptRegistration());
    }

    private void loadUserList() {
        try {
            FileInputStream fis = openFileInput("users.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            userList = new Gson().fromJson(stringBuilder.toString(), listType);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            userList = new ArrayList<>();
        }
    }

    private void saveUserList() {
        try {
            FileOutputStream fos = openFileOutput("users.json", Context.MODE_PRIVATE);
            String json = new Gson().toJson(userList);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUserRegistered(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private void attemptRegistration() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String type = "";
        type = getSelectedType();
        loadUserList();

        if (isValidRegistration(name, phone, email, password, location, type)) {
            // Check if user already exists
            if (!isUserRegistered(email)) {
                // User does not exist, proceed with registration
                User newUser = new User(name, phone, email, password, location, type);
                type = getSelectedType();
                userList.add(newUser);
                saveUserList();

                // Navigate to the ProfileActivity
                navigateToProfile(name, phone, email, location, type);
            } else {
                // User already exists, show an error message
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Invalid registration, show error message
            Toast.makeText(this, "Invalid registration", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedType() {
        int selectedRadioButtonId = typeRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.radioButtonImporter) {
            return "Importer";
        } else if (selectedRadioButtonId == R.id.radioButtonSupplier) {
            return "Supplier";
        } else {
            return "";
        }
    }

    private boolean isValidRegistration(String name, String phone, String email, String password,
                                        String location, String type) {
        if(TextUtils.isEmpty(name)){
            Toast.makeText(RegisterActivity.this,"Please enter your name",Toast.LENGTH_LONG).show();
            nameEditText.setError("Name field is required");
            nameEditText.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(RegisterActivity.this,"Please enter your phone number",Toast.LENGTH_LONG).show();
            phoneEditText.setError("Phone no. field is required");
            phoneEditText.requestFocus();
            return false;
        }else if(!isValidPhoneNumber(phone)){
            Toast.makeText(RegisterActivity.this,"Please re-enter your phone no.",Toast.LENGTH_LONG).show();
            phoneEditText.setError("Valid phone no. is required");
            phoneEditText.requestFocus();
            return false;
        }else if(isDuplicatePhone(phone)){
            Toast.makeText(RegisterActivity.this,"Please re-enter your phone no.",Toast.LENGTH_LONG).show();
            phoneEditText.setError("This phone no. already exists.");
            phoneEditText.requestFocus();
            return false;

        } else if(TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this,"Please enter your Email",Toast.LENGTH_LONG).show();
            emailEditText.setError("Email field is required");
            emailEditText.requestFocus();
            return false;
        }else if(!isValidEmail(email))
        {
            Toast.makeText(RegisterActivity.this,"Please re-enter your email",Toast.LENGTH_LONG).show();
            emailEditText.setError("Valid email is required");
            emailEditText.requestFocus();
            return false;

        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this,"Please enter a password",Toast.LENGTH_LONG).show();
            passwordEditText.setError("Password field is required");
            passwordEditText.requestFocus();
            return false;

        }else if (isWeakPassword(password)) {
            Toast.makeText(RegisterActivity.this,"Please enter a strong password",Toast.LENGTH_LONG).show();
            passwordEditText.setError("your password is weak!");
            passwordEditText.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(location)){
            Toast.makeText(RegisterActivity.this,"Please enter your location",Toast.LENGTH_LONG).show();
            locationEditText.setError("Location field is required");
            locationEditText.requestFocus();
            return false;
        }else if(!isValidLocation(location)){
            Toast.makeText(RegisterActivity.this,"Please re-enter your location",Toast.LENGTH_LONG).show();
            locationEditText.setError("A valid Location is required");
            locationEditText.requestFocus();
            return false;

        }else if (TextUtils.isEmpty(type)) {
            Toast.makeText(RegisterActivity.this, "Please select the user type", Toast.LENGTH_LONG).show();
            typeRadioGroup.requestFocus();
            return false;
        }
        return true;
    }

    private void navigateToProfile(String name, String phone, String email, String location, String type) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("location", location);
        intent.putExtra("type", type);
        startActivity(intent);
        finish();
    }
    private boolean isValidEmail(String email) {
        // email validation using a regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Validate phone number (exactly 11 digits and starts with "01")
        return phoneNumber.matches("01\\d{9}");
    }
    private boolean isDuplicatePhone(String phone) {
        loadUserList();
        // Check if the phone number is already used by another user
        for (User user : userList) {
            if (user.getPhone().equals(phone)) {
                Toast.makeText(this, "Phone number already registered", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
    private boolean isWeakPassword(String password) {
        return password.length() < 8;
    }
    private boolean isValidLocation(String location) {
        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);

            if (addresses != null && !addresses.isEmpty()) {
                // Valid location
                return true;
            } else {
                // Invalid location
                Toast.makeText(RegisterActivity.this, "Invalid location", Toast.LENGTH_LONG).show();
                locationEditText.setError("Location not found");
                locationEditText.requestFocus();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Error validating location", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
