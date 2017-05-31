package com.example.adityajoshi.gradbotv2.Chats;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.adityajoshi.gradbotv2.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by adityajoshi on 4/24/17.
 */

public class Chat extends AppCompatActivity implements View.OnClickListener, LocationListener {

    String channelName = "";
    ChatAdapter chatAdapter;
    String cityName = "";
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
        //this.setTitle(channelName.toString());
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
            ChatMessage chatMessage = new ChatMessage("user",channelName,message,true);
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
            editText.setText("");
            new MessageTransporter().execute("http://10.0.2.2:8080/publishServer/service/format",chatMessage);
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
