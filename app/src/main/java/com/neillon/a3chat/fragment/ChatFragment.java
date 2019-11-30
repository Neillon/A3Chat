package com.neillon.a3chat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.neillon.a3chat.R;

public class ChatFragment extends Fragment {

    private TextInputEditText txtMessage;
    private RecyclerView recyclerViewChat;
    private MaterialButton btnSendMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViewComponents();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "OI", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViewComponents() {
        txtMessage = getView().findViewById(R.id.txt_message);
        recyclerViewChat = getView().findViewById(R.id.recycler_view_chat);
        btnSendMessage = getView().findViewById(R.id.btn_send_message);

        btnSendMessage.bringToFront();
    }
}
