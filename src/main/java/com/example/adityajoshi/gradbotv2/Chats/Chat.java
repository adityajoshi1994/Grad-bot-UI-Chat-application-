package com.example.adityajoshi.gradbotv2.Chats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adityajoshi.gradbotv2.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by adityajoshi on 4/24/17.
 */

public class Chat extends AppCompatActivity implements View.OnClickListener, LocationListener {

    String channelName = "";
    ChatAdapter chatAdapter;
    SharedPreferences sharedPreferences;

    /**
     * Renders Channel UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        chatAdapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
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
        //this.setTitle(channelName.toString());
        //Set chat area
        ListView chatListView = (ListView) view.findViewById(R.id.msgListView);
        chatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatListView.setStackFromBottom(true);
        chatListView.setAdapter(chatAdapter);
        //Set send button
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
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
        Set<String> hashTags =  getHashTags(message);
        if(!message.equals("")){
            ChatMessage chatMessage = new ChatMessage(sharedPreferences.getString("Name",""),sharedPreferences.getString("City",""),channelName,message,true,hashTags);
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
            editText.setText("");
            new MessageTransporter().execute("http://192.168.0.9:8080/publishServer/service/format",chatMessage);
        }
    }

    private Set<String> getHashTags(String message) {
        String[] arr = message.split(" ");
        HashSet<String> set = new HashSet<>();
        for(String str: arr){
            if(str.startsWith("#"))
                set.add(str);
        }
        return set;
    }


    @Override
    public void onLocationChanged(Location location) {
        //getLcoation(location);
        Toast.makeText(getApplicationContext(),location.getLatitude()+"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
