package com.example.adityajoshi.gradbotv2.Chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.adityajoshi.gradbotv2.R;

import java.util.ArrayList;

/**
 * Created by adityajoshi on 4/24/17.
 */

public class Chat extends ActionBarActivity implements View.OnClickListener {

    String channelName = "";
    ChatAdapter chatAdapter;
    /**
     * Renders Channel UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        chatAdapter = new ChatAdapter(this,new ArrayList<ChatMessage>());
        prepareChatWindow();


    }

    /**
     * Set title, set up chat area, set an onClickListener for the send button
     */
    private void prepareChatWindow(){
        Intent intent = getIntent();
        View view = findViewById(android.R.id.content);
        //Set Title
        channelName = intent.getStringExtra("name");
        this.getSupportActionBar().setTitle(channelName);
        this.setTitle(channelName.toString());
        //Set chat area
        ListView chatListView = (ListView) view.findViewById(R.id.msgListView);
        chatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatListView.setStackFromBottom(true);
        chatListView.setAdapter(chatAdapter);
        //Set send button
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);
    }

    /**
     * Called on OnClick Event by View.OnClickListener
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendMessageButton:
                sendTextMessage(v);
        }
    }

    /**
     * Sends message by calling the ChatAdapter with appropriate parameters
     * @param v
     */
    public void sendTextMessage(View v){
        EditText editText = (EditText) findViewById(R.id.messageEditText);
        String message = editText.getEditableText().toString();
        if(!message.equals("")){
            ChatMessage chatMessage = new ChatMessage(channelName,"user",message,true);
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
            editText.setText("");
        }

    }
}
