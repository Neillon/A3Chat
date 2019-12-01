package com.neillon.a3chat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.neillon.a3chat.R;
import com.neillon.a3chat.adapter.RecycleViewAdapter;
import com.neillon.a3chat.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private TextInputEditText txtMessage;
    private RecyclerView recyclerViewChat;
    private MaterialButton btnSendMessage;
    private List<Chat> list = new ArrayList<>();
    RecycleViewAdapter adapter;

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
                Toast.makeText(getContext(), "OI", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViewComponents() {
        txtMessage = getView().findViewById(R.id.txt_message);
        recyclerViewChat = getView().findViewById(R.id.recycler_view_chat);
        adapter = new RecycleViewAdapter(list, getContext());
        btnSendMessage = getView().findViewById(R.id.btn_send_message);
        btnSendMessage.bringToFront();
    }

    private void setRecyclerViewChat () {
        recyclerViewChat.setAdapter(adapter);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChat.smoothScrollToPosition(list.size());
    }

    private void listenerChat () {

        Chat chat = new Chat();
        chat.setName("Emery");
        chat.setMessage("Sua mãe me traiu");
        list.add(chat);

        chat = new Chat();
        chat.setName("Thompson");
        chat.setMessage("Acreditei que era pra vale");
        list.add(chat);

        chat = new Chat();
        chat.setName("Emery");
        chat.setMessage("Depois ela fugiu");
        list.add(chat);

        chat = new Chat();
        chat.setName("Thompson");
        chat.setMessage("E me deixou com os braços a abanar");
        list.add(chat);

        chat = new Chat();
        chat.setName("Thompson");
        chat.setMessage("Aqui estou, mais um dia\n" +
                "Sob o olhar sanguinário do vigia\n" +
                "Você não sabe como é caminhar\n" +
                "Com a cabeça na mira de uma HK\n" +
                "Metralhadora alemã ou de Israel\n" +
                "Estraçalha ladrão que nem papel\n" +
                "Na muralha, em pé\n" +
                "Mais um cidadão José\n" +
                "Servindo o Estado, um PM bom\n" +
                "Passa fome, metido a Charles Bronson\n" +
                "Ele sabe o que eu desejo, sabe o que eu penso\n" +
                "O dia tá chuvoso, o clima tá tenso\n" +
                "Vários tentaram fugir, eu também quero\n" +
                "Mas de um a cem, a minha chance é zero");
        list.add(chat);

        chat = new Chat();
        chat.setName("Thompson");
        chat.setMessage("Aqui estou, mais um dia\n" +
                "Sob o olhar sanguinário do vigia\n" +
                "Você não sabe como é caminhar\n" +
                "Com a cabeça na mira de uma HK\n" +
                "Metralhadora alemã ou de Israel\n" +
                "Estraçalha ladrão que nem papel\n" +
                "Na muralha, em pé\n" +
                "Mais um cidadão José\n" +
                "Servindo o Estado, um PM bom\n" +
                "Passa fome, metido a Charles Bronson\n" +
                "Ele sabe o que eu desejo, sabe o que eu penso\n" +
                "O dia tá chuvoso, o clima tá tenso\n" +
                "Vários tentaram fugir, eu também quero\n" +
                "Mas de um a cem, a minha chance é zero");
        list.add(chat);

        chat = new Chat();
        chat.setName("Emery");
        chat.setMessage("Aqui estou, mais um dia\n" +
                "Sob o olhar sanguinário do vigia\n" +
                "Você não sabe como é caminhar\n" +
                "Com a cabeça na mira de uma HK\n" +
                "Metralhadora alemã ou de Israel\n" +
                "Estraçalha ladrão que nem papel\n" +
                "Na muralha, em pé\n" +
                "Mais um cidadão José\n" +
                "Servindo o Estado, um PM bom\n" +
                "Passa fome, metido a Charles Bronson\n" +
                "Ele sabe o que eu desejo, sabe o que eu penso\n" +
                "O dia tá chuvoso, o clima tá tenso\n" +
                "Vários tentaram fugir, eu também quero\n" +
                "Mas de um a cem, a minha chance é zero");
        list.add(chat);
    }
}
