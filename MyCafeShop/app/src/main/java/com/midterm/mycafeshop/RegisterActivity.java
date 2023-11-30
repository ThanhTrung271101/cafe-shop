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

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,password,repassword;
    TextView signup;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        repassword=(EditText) findViewById(R.id.repassword);
        signup=(TextView) findViewById(R.id.SignUp);

        mAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateUser();
            }
        });
    }
    private void CreateUser(){
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        String RePassword=repassword.getText().toString();
        String Name=name.getText().toString();
        if(TextUtils.isEmpty(Email)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }else if(TextUtils.isEmpty(Password)){
            password.setError("Password cannot be empty");
            password.requestFocus();
        }else if(TextUtils.isEmpty(RePassword)){
            repassword.setError("RePassword cannot be empty");
            repassword.requestFocus();
        }else if(TextUtils.isEmpty(Name)){
            name.setError("Name cannot be empty");
            name.requestFocus();
        }else if(!Password.equals(RePassword)){
            repassword.setError("RePassword is incorrect");
            repassword.requestFocus();
        }
        else{
            FirebaseData data=new FirebaseData();
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user=new User(mAuth.getCurrentUser().getUid(),Name,"","",Email,"",0,0,"","User");
                        data.CreateNewUser(user);
                        Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registtation Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}