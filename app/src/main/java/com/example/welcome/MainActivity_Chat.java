package com.example.welcome;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity_Chat extends AppCompatActivity {
    List<User_chat> userChatContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        userChatContacts = new ArrayList<>();
        userChatContacts.add(new User_chat("Hager Melook","hagermelook@gmail.com",R.drawable.hager));
        userChatContacts.add(new User_chat("Nouran Ashraf","NouranAshraf@gmail.com",R.drawable.nouran));
        userChatContacts.add(new User_chat("Rana Mohammed","RanaMohammed@gmail.com",R.drawable.rana));
        saveUserContactsList();
        loadUserContactsList();

        RecyclerView recyclerView = findViewById(R.id.recyclerviewmain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), userChatContacts));
    }
    private void saveUserContactsList() {
        try {
            FileOutputStream fos = openFileOutput("userContacts.json", Context.MODE_PRIVATE);
            String json = new Gson().toJson(userChatContacts);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadUserContactsList() {
        try {
            FileInputStream fis = openFileInput("userContacts.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Type listType = new TypeToken<ArrayList<User_chat>>() {}.getType();
            userChatContacts = new Gson().fromJson(stringBuilder.toString(), listType);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            userChatContacts = new ArrayList<>();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
