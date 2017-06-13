package com.example.adityajoshi.gradbotv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    EditText email,password;
    public MessageReceiver messageReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AppCompatButton Loginbutton = (AppCompatButton) findViewById(R.id.btn_login);
        Loginbutton.setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.link_signup);
        textView.setOnClickListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.container);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        sharedPreferences = getSharedPreferences("Preferences",MODE_PRIVATE);


    }





    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.link_signup:
                intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                //if(email.getText().toString().equals(sharedPreferences.getString("Email",""))){
                  //  if(password.getText().toString().equals(sharedPreferences.getString("Password",""))){
                        intent = new Intent(getApplicationContext(),HomeScreen.class);
                        startActivity(intent);
                    //}
                    //else
                      //  Toast.makeText(this,"Invalid Password",Toast.LENGTH_LONG).show();

                //}else
                  //  Toast.makeText(this,"EmailID does not exist",Toast.LENGTH_LONG).show();
                break;

        }
    }
}
