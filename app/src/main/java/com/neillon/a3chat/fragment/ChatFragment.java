package com.neillon.a3chat.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.neillon.a3chat.R;
import com.neillon.a3chat.adapter.RecycleViewAdapter;
import com.neillon.a3chat.configuration.FirebaseDatabaseConfiguration;
import com.neillon.a3chat.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private TextInputEditText txtMessage;
    private RecyclerView recyclerViewChat;
    private MaterialButton btnSendMessage;
    private RecycleViewAdapter adapter;
    private List<Chat> list = new ArrayList<>();

    private String nickname;

    public ChatFragment(String nickname) {
        this.nickname = nickname;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewComponents();
        listenerChat();
        setRecyclerViewChat();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtMessage.getText().toString();

                if (message.trim().isEmpty())
                    return;

                sendMessage(nickname, message);
            }
        });
    }

    private void initializeViewComponents() {
        txtMessage = getView().findViewById(R.id.txt_message);
        recyclerViewChat = getView().findViewById(R.id.recycler_view_chat);
        btnSendMessage = getView().findViewById(R.id.btn_send_message);
        btnSendMessage.bringToFront();
    }

    private void setRecyclerViewChat () {
        adapter = new RecycleViewAdapter(list, getContext());
        recyclerViewChat.setAdapter(adapter);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewChat.smoothScrollToPosition(list.size());
    }

    private void listenerChat () {
        FirebaseDatabaseConfiguration.getReference()
                .child("chat")
                .child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                Chat message = childSnapshot.getValue(Chat.class);
                                list.add(message);
                            }

                            adapter.notifyDataSetChanged();
                            recyclerViewChat.scrollToPosition(adapter.getItemCount() - 1);

                        } catch (Exception e) {
                            Log.i("FIREBASE", "Erro ao buscar dados ");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("FIREBASE ERROR", "Failed to read value.", databaseError.toException());
                    }
                });
    }

    private void sendMessage(String nickname, String message) {
        final Chat sendedMessage = new Chat(nickname, message);
        FirebaseDatabaseConfiguration.getReference()
                .child("chat")
                .child("messages")
                .push()
                .setValue(sendedMessage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                txtMessage.setText("");
            }
        });
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
