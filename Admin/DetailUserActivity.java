package com.example.finalproject.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainadminActivity;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DetailUserActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText name,address,email,phone;
    TextView update;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    private ImageView BtnPre1;
    ImageView menubar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menubar=findViewById(R.id.menubar);

        setContentView(R.layout.activity_detail_user);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        update=findViewById(R.id.changerole);
        mAuth=FirebaseAuth.getInstance();
        TextView title=findViewById(R.id.title);
        BtnPre1=findViewById(R.id.BtnPre1);
        title.setText("Detail User");
        FirebaseData data=new FirebaseData();
        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map singleValue=(Map)snapshot.getValue();
                String Id1=snapshot.getKey();
                String Name1= (String) singleValue.get("name");
                String Phone1= (String) singleValue.get("phone");
                String Address1= (String) singleValue.get("address");
                String Email1=(String) singleValue.get("email");
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
                builder=new AlertDialog.Builder(DetailUserActivity.this);
                builder.setTitle("Are you sure you want to set admin rights for this user");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.ChangeRole(mAuth.getCurrentUser().getUid());
                        Intent intent=new Intent(DetailUserActivity.this, MainadminActivity.class);
                        intent.putExtra("tab","2");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog=builder.create();
                dialog.show();
            }
        });
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailUserActivity.this, MainadminActivity.class);
                intent.putExtra("tab","2");
                startActivity(intent);
            }
        });
    }
}