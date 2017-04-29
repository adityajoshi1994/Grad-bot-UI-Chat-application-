package com.example.adityajoshi.gradbotv2.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adityajoshi.gradbotv2.R;

/**
 * Created by adityajoshi on 4/29/17.
 */

public class Profile extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup,container,false);
        return view;
    }
}
