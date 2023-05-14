package com.example.chathills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chathills.Adapters.ChatAdapter;
import com.example.chathills.databinding.ActivityChattingBinding;
import com.example.chathills.models.MessagesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChattingActivity extends AppCompatActivity {

    ActivityChattingBinding binding;

    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChattingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderID = auth.getUid();
        String receiverID = getIntent().getStringExtra("userID");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("userPicture");

        binding.chatUserName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.user_avatar).into(binding.chatUserPhoto);

        binding.leftBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChattingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //next work
        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messagesModels, this);

        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.sendMessageEditText.getText().toString();

                final MessagesModel model = new MessagesModel(senderID, message);
                model.setTimestamp(new Date().getTime());
                binding.sendMessageEditText.setText("");

                final String senderRoom = senderID + receiverID;
                final String receverRoom = receiverID +senderID;

                database.getReference().child("Chats")
                        .child(senderRoom)
                        .addValueEventListener(new ValueEventListener() {


                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messagesModels.clear();
                                for (DataSnapshot snapshot5 : snapshot.getChildren()) {
                                    MessagesModel model = snapshot5.getValue(MessagesModel.class);
                                    messagesModels.add(model);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                database.getReference().child("Chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("Chats")
                                        .child(receverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        });

            }
        });


    }
}

