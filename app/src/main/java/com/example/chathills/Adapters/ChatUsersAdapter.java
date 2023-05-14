package com.example.chathills.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chathills.ChattingActivity;
import com.example.chathills.R;
import com.example.chathills.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.ViewHolder>{

    ArrayList <Users> list;
    Context context;

    public ChatUsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = list.get(position);

        Picasso.get().load(user.getUserProfilePicture()).placeholder(R.drawable.user_avatar).into(holder.image);
        holder.name.setText(user.getUserName());

        //last message coding is under the development...
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChattingActivity.class);
                intent.putExtra("userID", user.getUserID("userID"));
                intent.putExtra("userPicture", user.getUserProfilePicture());
                intent.putExtra("userName", user.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name, lastmessage;
        public ViewHolder(@NonNull View view){
            super(view);

            image = view.findViewById(R.id.profile_image);
            name = view.findViewById(R.id.txtUserName);
            lastmessage = view.findViewById(R.id.txtLastMessage);

        }

    }

}
