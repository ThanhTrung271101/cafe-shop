package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChooseLoginActivity extends AppCompatActivity {

    TextView LoginEmail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        mAuth=FirebaseAuth.getInstance();
        LoginEmail=(TextView) findViewById(R.id.LoginEmail);


    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent i = new Intent(ChooseLoginActivity.this, MainActivity.class);
            startActivity(i);
        }
        else{
            LoginEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ChooseLoginActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}