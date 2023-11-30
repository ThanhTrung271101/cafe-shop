package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class ChangeAddressActivity extends AppCompatActivity {

    TextView name,email,phone,address,update;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        Intent intent=getIntent();
        mAuth=FirebaseAuth.getInstance();
        String Id=intent.getStringExtra("OrderId");
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        update=findViewById(R.id.update);



        TextView title=findViewById(R.id.title);
        title.setText("Change Address");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String Address=address.getText().toString();
                String Email=email.getText().toString();
                String Phone=phone.getText().toString();
                if(TextUtils.isEmpty(Name)){
                    name.setError("Name cannot be empty");
                    name.requestFocus();
                }
                else if(TextUtils.isEmpty(Address)){
                    address.setError("Address cannot be empty");
                    address.requestFocus();
                }
                else if(TextUtils.isEmpty(Email)){
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                }
                else if(TextUtils.isEmpty(Phone)){
                    phone.setError("Phone cannot be empty");
                    phone.requestFocus();
                }
                else{
                    FirebaseData data=new FirebaseData();
                    data.EditTemp(mAuth.getCurrentUser().getUid(),Id,Name,Phone,Address);
                    Intent i=new Intent(ChangeAddressActivity.this,CheckoutActivity.class);
                    i.putExtra("id",Id);
                    startActivity(i);
                }
            }
        });

    }
}