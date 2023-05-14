package com.example.chathills.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chathills.R;
import com.example.chathills.models.MessagesModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessagesModel> messagesModel;
    Context context;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessagesModel> messagesModel, Context context) {
        this.messagesModel = messagesModel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new RecyclerViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesModel.get(position).getuID().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    // Get the current time


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String format = "hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        String time = simpleDateFormat.format(new Date());
        MessagesModel messagesModel1 = messagesModel.get(position);

        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText(messagesModel1.getMessage());
            ((SenderViewHolder)holder).senderTime.setText(time);
        }else{
            ((RecyclerViewHolder)holder).receiverMessage.setText(messagesModel1.getMessage());
            ((RecyclerViewHolder)holder).receiverTime.setText(time);

        }
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMessage, receiverTime;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receiver_text);
            receiverTime = itemView.findViewById(R.id.receiver_time);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.send_text);
            senderTime = itemView.findViewById(R.id.send_time);
        }
    }


}
