package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.Delivery;
import com.example.finalproject.Models.User;
import com.example.finalproject.ui.Profile;
import com.google.firebase.auth.FirebaseAuth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class DeliverActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView update;
    EditText name,address,email,phone,note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        update=findViewById(R.id.update);
        note=findViewById(R.id.note);
    }
    private void AddNewDelivery(){
        String Name=name.getText().toString();
        String Address=address.getText().toString();
        String Email=email.getText().toString();
        String Phone=phone.getText().toString();
        String Note=note.getText().toString();
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
        else if(TextUtils.isEmpty(Note)){
            note.setError("Note cannot be empty");
            note.requestFocus();
        }
        else{
            FirebaseData data=new FirebaseData();
            String Id=getMd5(Name+Address+Email+Phone+Note+new Date().toString());
            Delivery delivery=new Delivery(Id,Name,Address,Email,Phone,Note);
            data.AddDelivery(mAuth.getCurrentUser().getUid(),delivery);
            startActivity(new Intent(DeliverActivity.this, Profile.class));
        }
    }
    static String getMd5(String input)
    {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}