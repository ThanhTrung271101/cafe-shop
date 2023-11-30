package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Helper.FirebaseData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView Login;
    TextView ResetPass;
    TextView CreateAccount;
    EditText email,password;
    RadioButton customer,admin;
    FirebaseAuth mAuth;
    String Flagrole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login=(TextView) findViewById(R.id.Login);
        ResetPass=(TextView) findViewById(R.id.ResetPass);
        CreateAccount=(TextView) findViewById(R.id.CreateAccount);

        customer=findViewById(R.id.radiocus);
        admin=findViewById(R.id.radiocus);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        customer=findViewById(R.id.radiocus);
        customer.setChecked(true);
        admin=findViewById(R.id.radioadmin);
        admin.setChecked(false);
        mAuth=FirebaseAuth.getInstance();

        ResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(i);
            }
        });
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser(){
        FirebaseData data=new FirebaseData();
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        if(TextUtils.isEmpty(Email)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        }else if(TextUtils.isEmpty(Password)) {
            password.setError("Password cannot be empty");
            password.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map singleValue=(Map)snapshot.getValue();
                                String Role= (String) singleValue.get("role");
                                if(admin.isChecked())
                                {
                                    if(Role.equals("Admin"))
                                    {
                                        Toast.makeText(LoginActivity.this,"User logged in successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,MainadminActivity.class));
                                    }
                                    else{
                                        mAuth.signOut();
                                        Toast.makeText(LoginActivity.this,"You do not have permission to login",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if(customer.isChecked())
                                {
                                    Toast.makeText(LoginActivity.this,"User logged in successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Log in Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null)
        {

        }
    }
}