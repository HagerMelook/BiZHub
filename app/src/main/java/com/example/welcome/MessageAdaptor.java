package com.example.welcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class MessageAdaptor extends RecyclerView.Adapter {
    Context context;
    ArrayList<Message> messageArrayList;
    String senderId;
    int ITEM_SEND = 1;
    int ITEM_RECEIVE = 2;

    public MessageAdaptor(Context context, ArrayList<Message> messageArrayList, String senderUId) {
        this.context = context;
        this.messageArrayList = messageArrayList;
        this.senderId = senderUId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sendermessagelayout,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receivemessagelayout,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class){
                SenderViewHolder viewHolder = (SenderViewHolder) holder;
                viewHolder.message.setText(message.getContent());
                viewHolder.timeOfMessage.setText(message.getCurrentTime());
        }
        else{
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.message.setText(message.getContent());
            viewHolder.timeOfMessage.setText(message.getCurrentTime());

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageArrayList.get(position).getSenderId().equals(senderId)) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();

    }

    class  SenderViewHolder extends  RecyclerView.ViewHolder{
        TextView message;
        TextView timeOfMessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.sendmessage);
            timeOfMessage = itemView.findViewById(R.id.timeOfMessage);
        }
    }
    class  ReceiverViewHolder extends  RecyclerView.ViewHolder{
        TextView message;
        TextView timeOfMessage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.sendmessage2);
            timeOfMessage = itemView.findViewById(R.id.timeOfMessage);
        }
    }
}
