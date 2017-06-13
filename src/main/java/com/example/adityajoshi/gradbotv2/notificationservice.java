package com.example.adityajoshi.gradbotv2;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class notificationservice extends IntentService {
    public static final int NOTIFICATION_ID = 234;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.adityajoshi.gradbotv2.action.FOO";
    private static final String ACTION_BAZ = "com.example.adityajoshi.gradbotv2.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.adityajoshi.gradbotv2.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.adityajoshi.gradbotv2.extra.PARAM2";
   // SharedPreferences sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);
    public notificationservice() {
        super("notificationservice");
    }
    SQLiteDatabase sqLiteDatabase;


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, notificationservice.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        /*if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);

            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }*/
        //Consumer consumer = new Consumer();
        //consumer.consume_data();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);
        sqLiteDatabase = openOrCreateDatabase("UserData",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ChatMessages");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ChatMessages(MSGID VARCHAR," + "ChannelName VARCHAR,Sender VARCHAR," +
                "Date VARCHAR,Location VARCHAR,Message VARCHAR,Mine INTEGER,Hashtags VARCHAR);");
        Log.i("Service","Service started");
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        Gson gson = new Gson();

       while (true){
           String val = makeRequest(sharedPreferences.getString("Name",""),sharedPreferences.getString("Channels","").split(","));
            if(!val.equals("")){
                //bundle.putString("retVal",val);
                //resultReceiver.send(Activity.RESULT_OK, bundle);
                //insertIntoDatabase(val);
                getChannelMessages(val);

            }
           try {
               Thread.sleep(10000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    }

    private void getChannelMessages(String val) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(val);
        Log.i("Channel Val",val);
        //JsonParser parser = new JsonParser();
        //JsonObject jsonobject = parser.parse(val).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry: entries) {
            JsonArray jsonArray = jsonObject.get(entry.getKey()).getAsJsonArray();
            for(int i = 0;i < jsonArray.size();i++){
                insertIntoDatabase(jsonArray.get(i).toString(),entry.getKey());
            }
        }
    }

    private void insertIntoDatabase(String val,String channel) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(val).getAsJsonObject();
        Log.i("hashtags",jsonObject.get("hashtags").toString());
        int mine  = 0;
        sqLiteDatabase.execSQL("INSERT INTO ChatMessages VALUES("+jsonObject.get("msgID").toString()+","
                +"'" + channel.toString() + "'" + ","
                +jsonObject.get("user").toString()+ ","
                +jsonObject.get("date").toString() + ","
                +jsonObject.get("location").toString() + ","
                +jsonObject.get("body") + ","
                +mine+ ","
                +"'" + jsonObject.get("hashtags").toString() + "'" + ");");
    }

    public void createNotification(){

        /*Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Sample Notification")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
        */

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    /*private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    /*private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    public String makeRequest(String userName,String[] channels){
        String retVal = "";
        String json = "";

        try {
            Log.i("Make request","Inside make request");
            URL url = new URL("http://192.168.0.5:8080/procons/rest/getdata");
            //URL url = new URL("http://192.168.0.9:8080/publishServer/service/cons");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type","application/json");
            client.setDoOutput(true);
            OutputStreamWriter outputStream  = new OutputStreamWriter(client.getOutputStream());
            //ChatMessage cm = (ChatMessage) params[1];
            Gson gson = new Gson();
            //String json = gson.toJson(cm);
            String jsonArray = gson.toJson(channels);
            Log.i("Json Array",jsonArray);
            json = "{\"User\":"+ userName + ",\"Channels\":" + jsonArray + "}";
            System.out.println(json);
            outputStream.write(json);
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
//            int i = 0;
//            while((line = br.readLine()) != null)
//            {
//                // Append server response in string
//                Log.i("Return message " + i, line);
//                i++;
//                line = line + " ";
//            }
            retVal = br.readLine();

            if(retVal == null)
                return "";
            Log.i("Retval",retVal);
            outputStream.close();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }


}
