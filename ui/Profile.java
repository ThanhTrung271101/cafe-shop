package com.example.finalproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.EditUserActivity;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainActivity;
import com.example.finalproject.Models.User;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Profile extends Fragment {

    private View view;
    TextView Name,Location,PhoneNum,Email,Address;
    private ImageView BtnPre1;
    FirebaseAuth mAuth;
    ImageView menubar;
    public Profile() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){
            view=inflater.inflate(R.layout.fragment_profile, container, false);
        }
        TextView title=view.findViewById(R.id.title);
        title.setText("My Profile");
        FirebaseData data=new FirebaseData();
        mAuth=FirebaseAuth.getInstance();
        Name=(TextView) view.findViewById(R.id.name);
        Location=(TextView) view.findViewById(R.id.location);
        PhoneNum=(TextView) view.findViewById(R.id.phone);
        Email=(TextView) view.findViewById(R.id.email);
        Address=(TextView) view.findViewById(R.id.address);
        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = null;
                Map singleValue=(Map)snapshot.getValue();
                String Id1=snapshot.getKey();
                String Name1= (String) singleValue.get("name");
                String Phone1= (String) singleValue.get("phone");
                String Address1= (String) singleValue.get("address");
                String Email1=(String) singleValue.get("email");
                user=new User(Id1,Name1,"",Address1,Email1,Phone1,0,0,"","");


                Name.setText(user.getName());
                Location.setText(Address1);
                PhoneNum.setText(user.getPhoneNumber());
                Email.setText(user.getEmail());
                Address.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BtnPre1=view.findViewById(R.id.BtnPre1);
        menubar=view.findViewById(R.id.menubar);
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditUserActivity.class));
            }
        });
        return view;
    }
}