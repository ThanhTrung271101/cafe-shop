package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.User;
import com.example.finalproject.ui.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView update;
    EditText name,address,email,phone;
    int Point_1,Point_2;
    private ImageView BtnPre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        BtnPre1=findViewById(R.id.BtnPre1);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        update=findViewById(R.id.update);
        mAuth=FirebaseAuth.getInstance();
        FirebaseData data=new FirebaseData();
        TextView title=findViewById(R.id.title);
        title.setText("Edit Profile");
        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map singleValue=(Map)snapshot.getValue();
                String Id1=snapshot.getKey();
                String Name1= (String) singleValue.get("name");
                String Phone1= (String) singleValue.get("phone");
                String Address1= (String) singleValue.get("address");
                String Email1=(String) singleValue.get("email");
                int GiftPoint= ((Long) singleValue.get("giftPoint")).intValue();
                int Point1=((Long) singleValue.get("accumulatedPoint")).intValue();
                Point_1=GiftPoint;
                Point_2=Point1;
                name.setText(Name1);
                address.setText(Address1);
                email.setText(Email1);
                phone.setText(Phone1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }
    private void updateProfile(){
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
            User user=new User(mAuth.getCurrentUser().getUid(),Name,"",Address,Email,Phone,Point_1,Point_2,"","");
            data.updataProfile(mAuth.getCurrentUser().getUid(),user);
            Intent intent=new Intent(EditUserActivity.this,MainActivity.class);
            intent.putExtra("menu","3");
            startActivity(intent);
            //startActivity(new Intent(EditUserActivity.this, Profile.class));
        }
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditUserActivity.this,MainActivity.class);
                intent.putExtra("menu","3");
                startActivity(intent);
            }
        });
    }
}