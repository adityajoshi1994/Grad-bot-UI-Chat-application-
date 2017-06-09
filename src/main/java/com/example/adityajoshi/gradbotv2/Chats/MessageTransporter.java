package com.example.adityajoshi.gradbotv2.Chats;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by adityajoshi on 5/14/17.
 */

public class MessageTransporter extends AsyncTask<Object,Object,Object>{

    @Override
    protected Object doInBackground(Object... params) {
        try {
            Log.i("Message Transporter","Inside doInBack");
            URL url = new URL((String) params[0]);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type","application/json");
            client.setDoOutput(true);
            OutputStreamWriter outputStream  = new OutputStreamWriter(client.getOutputStream());
            ChatMessage cm = (ChatMessage) params[1];
            Gson gson = new Gson();
            String json = gson.toJson(cm);
            json = "{\"Type\":\"Android\",\"Payload\":" + json + "}";
            System.out.println(json);
            outputStream.write(json);
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            int i = 0;
            while((line = br.readLine()) != null)
            {
                // Append server response in string
                Log.i("Return val " + i, line);
                i++;
            }
            outputStream.close();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
