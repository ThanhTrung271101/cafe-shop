package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {
    TextView Verify;
    TextView login;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        Verify=(TextView) findViewById(R.id.Verify);
        login=(TextView) findViewById(R.id.login);
        email=(EditText)findViewById(R.id.emailreset);

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailAddress=email.getText().toString();
                if(TextUtils.isEmpty(EmailAddress)){
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                }
                else
                {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(EmailAddress)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassActivity.this,"Successful, please check your email",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetPassActivity.this,ChooseLoginActivity.class));
                                } else {
                                    Toast.makeText(ResetPassActivity.this,"Failed, please try again",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }}
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResetPassActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}