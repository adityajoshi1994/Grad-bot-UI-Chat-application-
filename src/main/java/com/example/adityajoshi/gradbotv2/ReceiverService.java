package com.example.adityajoshi.gradbotv2;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by adityajoshi on 6/10/17.
 */

public class ReceiverService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReceiverService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("Service","Service started");
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        while (true){
            String val = "";
            //= makeRequest(sharedPreferences.getString("Name",""),sharedPreferences.getString("Channels","").split(","));
            if(!val.equals("")){
                bundle.putString("retVal",val);
                resultReceiver.send(Activity.RESULT_OK, bundle);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
