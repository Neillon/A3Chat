package com.neillon.a3chat.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.textview.MaterialTextView;
import com.neillon.a3chat.R;
import com.neillon.a3chat.model.Chat;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyHolder> {

    private List<Chat> listChat;
    private SharedPreferences sharedPreferences;
    private Context context;

    public RecycleViewAdapter (List<Chat> list, Context context) {
        this.listChat = list;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_key), Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_layout, parent, false);

        return new MyHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Chat chat = listChat.get(position);
        holder.message.setText(chat.getMessage());

        if (chat.getName().trim().equals(getUserNickname())) {
            holder.name.setVisibility(View.GONE);
            holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.name.setTextColor(context.getColor(R.color.colorPrimaryDark));
            holder.name.setPadding(12,6,36,6);
            holder.message.setPadding(12,1,36,6);
            holder.relativeBackground.setBackground(context.getDrawable(R.drawable.bubble_out));
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            holder.name.setTextColor(context.getColor(R.color.colorAccent));
            holder.name.setPadding(36,6,12,6);
            holder.message.setPadding(36,1,12,6);
            holder.relativeBackground.setBackground(context.getDrawable(R.drawable.bubble_in));
            
            if(position >= 2 && listChat.get(position-1).getName().equals(chat.getName())) {
                holder.name.setVisibility(View.GONE);

            }else {
                holder.name.setText(chat.getName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeBackground;
        MaterialTextView name, message;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            relativeBackground = itemView.findViewById(R.id.relativeBackground);
            name = itemView.findViewById(R.id.txtChatName);
            message = itemView.findViewById(R.id.txtChatMessage);
        }
    }

    public String getUserNickname () {
        String nickname = sharedPreferences.getString(context.getString(R.string.nickname_key), "");
        return nickname;
    }
}
