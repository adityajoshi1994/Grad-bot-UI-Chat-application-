package com.example.adityajoshi.gradbotv2.Chats;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Set;

/**
 * Created by adityajoshi on 4/25/17.
 */

public class ChatMessage {
    private String body, sender, receiver,cityName;
    public String date, time, msgID;
    Set<String> hashtags;
    private boolean isMine;
    transient DateFormat  df;




    /**
     * Instantiate a chatmessage with the following primary details.
     * @param sender
     * @param receiver
     * @param messageString
     * @param isMINE
     */
    public ChatMessage(String sender, String senderLocation, String receiver, String messageString, boolean isMINE, Set<String>hashtags) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = messageString;
        this.msgID = setMsgID();
        this.isMine = isMINE;
        df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        this.date = df.format(today);
        this.cityName = senderLocation;
        this.hashtags = hashtags;
    }


    /**
     * Getter methods
     */
    public boolean getisMine() {
        return isMine;
    }

    public String getDate() {
        return date;
    }

    public String getMsgID() {
        return msgID;
    }

    public String getBody() {
        return body;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getCityName(){return cityName;}

    public Set<String> getHashtags() {
        return hashtags;
    }

    /**
     * Setter methods
     */

    /**
     * Set msgID using random number generation for the msg
     */
    public static String setMsgID(){
        return String.valueOf(new Random().nextInt(1000));
    }

    public void serDate(Date date){
        this.date = df.format(date);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public void setMine(boolean isMine){
        this.isMine = isMine;
    }




}
