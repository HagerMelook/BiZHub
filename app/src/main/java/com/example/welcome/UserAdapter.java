package com.example.welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context context;
    List<User_chat> userChats;


    public UserAdapter(Context context, List<User_chat> userChats) {
        this.context = context;
        this.userChats = userChats;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chatviewlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameofuser.setText(userChats.get(position).getName());
        holder.emailofuser.setText(userChats.get(position).getEmail());
        holder.imageview.setImageResource(userChats.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,chatActivity.class);
                String username = holder.nameofuser.getText().toString();
                String sendername = "unknown";
                intent.putExtra("sendername",sendername);
                holder.imageview.setDrawingCacheEnabled(true);
                Bitmap b=holder.imageview.getDrawingCache();
                intent.putExtra("Bitmap", b);
                intent.putExtra("user_name",username);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userChats.size();
    }
}
