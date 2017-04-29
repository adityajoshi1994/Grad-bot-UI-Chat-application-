package com.example.adityajoshi.gradbotv2.Chats;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adityajoshi.gradbotv2.R;

import java.util.ArrayList;

/**
 * Created by adityajoshi on 4/25/17.
 */

public class ChatAdapter extends BaseAdapter {
    private static LayoutInflater layoutInflater = null;
    ArrayList<ChatMessage> messages;

    /**
     * Initialize ChatAdapter with following params and render the chat are bubble UI
     * @param activity
     * @param list
     */
    public ChatAdapter(Activity activity,ArrayList<ChatMessage> list){
        messages = list;
        layoutInflater =  (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Renders the message that is recently added to the messages list onto the chat area
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = (ChatMessage) messages.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = layoutInflater.inflate(R.layout.chatbubble, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.getBody());
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.getisMine()) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }

    /**
     * Add new message to the messages list
     * @param chatMessage
     */
    public void add(ChatMessage chatMessage){
        messages.add(chatMessage);
    }


}
