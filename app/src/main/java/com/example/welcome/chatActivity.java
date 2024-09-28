package com.example.welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class chatActivity extends AppCompatActivity {
    EditText getMessage;
    ImageButton sendButton;
    CardView sendMessageCard;
    ImageView userImage;
    TextView userName;
    private String messageContent;
    Intent intent;
    public String senderUId,receiverUId;
    ImageButton backButton;
    RecyclerView messageRecyclerView;
    String CurrentTime;
    Calendar calenderInst;
    SimpleDateFormat simpleDateFormat;
    ArrayList<Message> messageArrayList;
    MessageAdaptor messageAdaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageArrayList = new ArrayList<>();
        getMessage = findViewById(R.id.message);
        sendMessageCard = findViewById(R.id.cardViewOfButton);
        sendButton = findViewById(R.id.sendMessageImage);
        userName = findViewById(R.id.username);
        userImage = findViewById(R.id.userImageInChat);
        backButton = findViewById(R.id.backbuttonofchat);

        intent = getIntent();
        senderUId=getIntent().getStringExtra("sendername");;
        calenderInst = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String user_Name = getIntent().getStringExtra("user_name");
        userName.setText(user_Name);
        Bitmap bitmap = intent.getParcelableExtra("Bitmap");
        userImage.setImageBitmap(bitmap);
        receiverUId=getIntent().getStringExtra("user_name");
        loadUserContactMsgList();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserContactMsgList();
                finish();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageContent = getMessage.getText().toString();
                if (messageContent.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();

                } else {
                    Date date=new Date();
                    CurrentTime=simpleDateFormat.format(calenderInst.getTime());
                    if(messageArrayList.size()%5==0)
                        messageArrayList.add(new Message(messageContent,receiverUId,senderUId,CurrentTime,date.getTime()));
                    messageArrayList.add(new Message(messageContent,senderUId,receiverUId,CurrentTime,date.getTime()));
                    getMessage.setText(null);
                    saveUserContactMsgList();
                    loadUserContactMsgList();

                }
            }
        });
        messageRecyclerView = findViewById(R.id.recyclerViewOfChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageAdaptor = new MessageAdaptor(chatActivity.this, messageArrayList,senderUId);
        messageRecyclerView.setAdapter(messageAdaptor);
    }
    private void saveUserContactMsgList() {
        try {
            FileOutputStream fos = openFileOutput(senderUId+receiverUId+".json", Context.MODE_PRIVATE);
            String json = new Gson().toJson(messageArrayList);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadUserContactMsgList() {
        try {
            FileInputStream fis = openFileInput(senderUId+receiverUId+".json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Type listType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            messageArrayList = new Gson().fromJson(stringBuilder.toString(), listType);
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
            messageArrayList = new ArrayList<>();
              }
        }

    @Override
    public void onStart() {
        super.onStart();
        messageAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messageAdaptor!=null)
        {
            messageAdaptor.notifyDataSetChanged();
        }
    }
//    EditText editText = new androidx.appcompat.widget.AppCompatEditText(this) {
//        @Override
//        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//            final InputConnection ic = super.onCreateInputConnection(outAttrs);
//            EditorInfoCompat.setContentMimeTypes(outAttrs, new String[]{"image/png"});
//            final InputConnectionCompat.OnCommitContentListener callback =
//                    new InputConnectionCompat.OnCommitContentListener() {
//                        @Override
//                        public boolean onCommitContent(@NonNull InputContentInfoCompat inputContentInfo, int flags, @Nullable Bundle opts) {
//                            if(((flags & InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION)!=0)){
//                                try{
//                                    inputContentInfo.requestPermission();
//                                }
//                                catch (Exception e){
//                                    return false;
//                                }
//                            }
//                            return true;
//                        }
//                    };
//            return InputConnectionCompat.createWrapper(ic,outAttrs,callback);
//        }
//        public void onStartInputView(EditorInfo info, boolean restarting) {
//            EditorInfo editorInfo = null;
//            String[] mimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
//
//            boolean gifSupported = false;
//            for (String mimeType : mimeTypes) {
//                if (ClipDescription.compareMimeTypes(mimeType, "image/gif")) {
//                    gifSupported = true;
//                }
//            }
//
//            if (gifSupported) {
//                // The target editor supports GIFs. Enable the corresponding content.
//            } else {
//                // The target editor doesn't support GIFs. Disable the corresponding
//                // content.
//            }
//        }
//
//    };
}