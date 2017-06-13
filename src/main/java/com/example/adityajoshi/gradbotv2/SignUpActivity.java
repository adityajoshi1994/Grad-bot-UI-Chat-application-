package com.example.adityajoshi.gradbotv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by adityajoshi on 4/29/17.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences.Editor editor;
    EditText displayName,email,password,confirmPassword,channels;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        editor = getSharedPreferences("Preferences",MODE_PRIVATE).edit();
        Button createAccount = (Button) findViewById(R.id.btn_singnup);
        displayName = (EditText) findViewById(R.id.signup_name);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        confirmPassword = (EditText) findViewById(R.id.signup_password);
        channels = (EditText) findViewById(R.id.signup_channels);
        createAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        editor.putString("Name",displayName.getText().toString());
        editor.putString("Email",email.getText().toString());
        editor.putString("Password",password.getText().toString());
        editor.putString("Channels",channels.getText().toString() + ",General");
        //String[] splt = channels.getText().toString().split(",");
//        for (String str: splt){
//            editor.putStringSet(str,null);
//        }
        editor.commit();
        Toast.makeText(this,"Account Created",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(intent);
    }
}
