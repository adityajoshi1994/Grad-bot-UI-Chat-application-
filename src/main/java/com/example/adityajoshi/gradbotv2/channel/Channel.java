package com.example.adityajoshi.gradbotv2.channel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adityajoshi.gradbotv2.Chats.Chat;
import com.example.adityajoshi.gradbotv2.R;

import java.util.ArrayList;

/**
 * Created by adityajoshi on 4/24/17.
 */

public class Channel extends Fragment implements AdapterView.OnItemClickListener {

    ArrayAdapter<String> adapter;
    ArrayList<String> subscribedChannels = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    /**
     * Get subscribed channels and displays Channels layout which is a list of subscribed channels
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channels_layout,container,false);
        adapter=new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                subscribedChannels);
        sharedPreferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        getSubscribedChannels();
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        if(listView == null)
            Log.i("ListView","listview is null");
        else
            listView.setOnItemClickListener(this);

        return view;
    }

    /**
     * Get a list of subscribed channels
     */
    private void getSubscribedChannels(){
        String[] arr = sharedPreferences.getString("Channels","").split(",");
        for(int i = 0;i < arr.length;i++){
            subscribedChannels.add(arr[i]);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Click on the channel to select the channel
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        Intent intent = new Intent(this.getContext(), Chat.class);
        intent.putExtra("position", position);
        intent.putExtra("name",subscribedChannels.get(position));
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
