package com.example.adityajoshi.gradbotv2.User;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.adityajoshi.gradbotv2.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by adityajoshi on 4/29/17.
 */

public class Profile extends Fragment implements LocationListener, View.OnClickListener {
    private PopupWindow pw;
    private String cityName = "";
    View view;
    EditText city,name,email;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        //Log.i("City Name", "Inside");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, this);
        city = (EditText) view.findViewById(R.id.input_city);
        email = (EditText) view.findViewById(R.id.input_email);
        name = (EditText) view.findViewById(R.id.input_name);
        city.setText("");
        sharedPreferences = getActivity().getSharedPreferences("Preferences",getActivity().MODE_PRIVATE);
        editor =  sharedPreferences.edit();
        name.setText(sharedPreferences.getString("Name",""));
        email.setText(sharedPreferences.getString("Email",""));
        //editor.clear();
        Button done = (Button) view.findViewById(R.id.btn_done);
        done.setOnClickListener(this);
        return view;
    }


    public void getLcoation(Location location){
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;
        //Log.i("City Name1",location.getLatitude() + " " + location.getLongitude());
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses != null) {
                //System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
                //Log.i("City Name",cityName);
                if(city.getText().toString().equals(""))
                    city.setText(cityName);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeInSharedPreferences(){
        editor.putString("Name", name.getText().toString());
        editor.putString("Email", email.getText().toString());
        editor.putString("City", city.getText().toString());
        editor.commit();
        Log.i("Shared Preferences", "Written in shared preferences");
    }

    @Override
    public void onLocationChanged(Location location) {
        getLcoation(location);
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

    @Override
    public void onClick(View v) {
        if(!checkIfFormFilled()){
            Toast.makeText(getContext(),"All fields not filled",Toast.LENGTH_LONG).show();
            return;
        }
        writeInSharedPreferences();
    }

    private boolean checkIfFormFilled() {
        if(name.getText().toString().equals("") || email.getText().toString().equals("") || !email.getText().toString().contains("@") ||
                !email.getText().toString().contains(".") || city.getText().toString().equals(""))
            return false;
        return true;
    }
}
