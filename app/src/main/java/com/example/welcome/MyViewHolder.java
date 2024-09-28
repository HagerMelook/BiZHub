package com.example.welcome;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageview;
    TextView nameofuser,emailofuser;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageview = itemView.findViewById(R.id.imageview);
        nameofuser = itemView.findViewById(R.id.nameofuser);
        emailofuser = itemView.findViewById(R.id.emailofuser);

    }
}
