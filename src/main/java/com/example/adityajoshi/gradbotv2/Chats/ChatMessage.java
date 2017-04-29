package com.example.adityajoshi.gradbotv2.Chats;

import java.util.Random;

/**
 * Created by adityajoshi on 4/25/17.
 */

public class ChatMessage {
    private String body, sender, receiver;
    public String date,time,msgID;
    private boolean isMine;

    /**
     * Instantiate a chatmessage with the following primary details.
     * @param sender
     * @param receiver
     * @param messageString
     * @param isMINE
     */
    public ChatMessage(String sender, String receiver, String messageString, boolean isMINE){
        this.sender = sender;
        this.receiver = receiver;
        this.body =  messageString;
        this.msgID = setMsgID();
        this.isMine = isMINE;
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

    /**
     * Setter methods
     */
    public void setBody(String body) {
        this.body = body;
    }


    /**
     * Set msgID using random number generation for the msg
     */
    public static String setMsgID(){

        return String.valueOf(new Random().nextInt(1000));
    }

}
