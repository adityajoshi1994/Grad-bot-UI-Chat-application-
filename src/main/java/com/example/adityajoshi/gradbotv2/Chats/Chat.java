package com.example.adityajoshi.gradbotv2.Chats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adityajoshi.gradbotv2.R;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adityajoshi on 4/24/17.
 */

public class Chat extends AppCompatActivity implements View.OnClickListener, LocationListener {

    String channelName = "";
    ChatAdapter chatAdapter;
    SharedPreferences sharedPreferences;
    SQLiteDatabase sqLiteDatabase;
    Gson gson = new Gson();
    private Timer autoUpdate;
    JsonParser parser;
    Set<String> displayedMessages = new HashSet<String>();


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


    @Override
    protected void onResume(){
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateChat();
                    }
                });
            }
        }, 0, 1000); // updates each 10 secs
    }


    public void updateChat(){
        Log.i("Came in",String.valueOf(chatAdapter.messages.size()));
        Cursor resultSet = sqLiteDatabase.rawQuery("SELECT * FROM ChatMessages WHERE ChannelName=" + "'" + channelName + "'",null);
        resultSet.moveToFirst();
        Log.i("Result",String.valueOf(resultSet.getCount()));
        if(resultSet.getCount() == 0|| chatAdapter.messages.size() >= resultSet.getCount()){
            return;
        }

        //parser = new JsonParser();
        //JsonObject jsonObject = parser.parse(resultSet.getString(1)).getAsJsonObject();

        Set<String> hashtags = null;
        try {
            hashtags = getHashTagsFromJson(resultSet.getString(resultSet.getColumnIndex("Hashtags")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ChatMessage cm = new ChatMessage(resultSet.getString(resultSet.getColumnIndex("Sender")),
//                resultSet.getString(resultSet.getColumnIndex("Location")),
//                sharedPreferences.getString("Name",""),
//                resultSet.getString(resultSet.getColumnIndex("Message")),
//                resultSet.getInt(resultSet.getColumnIndex("Mine")) == 1,
//                hashtags);
//        //cm.setMine(false);
//        chatAdapter.add(cm);
        int size = chatAdapter.messages.size();
        int i = 0;
        ChatMessage cm;
        boolean done = true;
        while (done){
            //jsonObject = parser.parse(resultSet.getString(1)).getAsJsonObject();

            if(displayedMessages.contains(resultSet.getString(resultSet.getColumnIndex("MSGID")))){
                done = resultSet.moveToNext();
                continue;
            }
            try {
                hashtags = getHashTagsFromJson(resultSet.getString(resultSet.getColumnIndex("Hashtags")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cm = new ChatMessage(resultSet.getString(resultSet.getColumnIndex("Sender")),
                    resultSet.getString(resultSet.getColumnIndex("Location")),
                    sharedPreferences.getString("Name",""),
                    resultSet.getString(resultSet.getColumnIndex("Message")),
                    resultSet.getInt(resultSet.getColumnIndex("Mine")) == 1,
                    hashtags);
            chatAdapter.add(cm);
            chatAdapter.notifyDataSetChanged();
            displayedMessages.add(resultSet.getString(resultSet.getColumnIndex("MSGID")));
            i++;
            done = resultSet.moveToNext();
        }
        resultSet.close();

    }

    private Set<String> getHashTagsFromJson(String hashtags) throws JSONException {
        JSONArray jsonArray = new JSONArray(hashtags);
        Set<String> hashtagSet = new HashSet<String>();
        for (int i = 0;i < jsonArray.length();i++){
            hashtagSet.add(jsonArray.get(i).toString());
        }
        return hashtagSet;
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
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
        sqLiteDatabase = openOrCreateDatabase("UserData",MODE_PRIVATE,null);
        //updateChat();

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
            displayedMessages.add(chatMessage.getMsgID());
            editText.setText("");
            insertInDatabase(chatMessage);
            new MessageTransporter().execute("http://192.168.0.5:8080/procons/rest/format",chatMessage);
        }
    }

    private void insertInDatabase(ChatMessage chatMessage) {
        int mine = 1;
        String jsonArray = gson.toJson(chatMessage.getHashtags());
        sqLiteDatabase.execSQL("INSERT INTO ChatMessages VALUES("+chatMessage.getMsgID().toString()+","
                +"'" + channelName + "'"+ ","
                +"'" +chatMessage.getSender()+ "'"+ ","
                +"'" +chatMessage.getDate()+ "'" + ","
                +"'" + sharedPreferences.getString("City","") + "'" + ","
                +"'" +chatMessage.getBody()+ "'" + ","
                +mine+ ","
                +"'" + jsonArray + "'" + ");");
        Log.i("Database","Data inserted");
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
