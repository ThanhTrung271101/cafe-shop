package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalproject.Admin.AcceptOrderFragment;
import com.example.finalproject.Admin.ProductFragment;
import com.example.finalproject.Admin.UserFragment;
import com.example.finalproject.databinding.ActivityMainadminBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainadminActivity extends AppCompatActivity {

    ActivityMainadminBinding binding;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        binding=ActivityMainadminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        relaceFragment(new AcceptOrderFragment());

        Intent intent=getIntent();
        String Tab=intent.getStringExtra("tab");


        binding.bottomNavigrationView.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.accept:
                    relaceFragment(new AcceptOrderFragment());
                    break;
                case R.id.product:
                    relaceFragment(new ProductFragment());
                    break;
                case R.id.user:
                    relaceFragment(new UserFragment());
                    break;
            }
            return true;
        });
        if(Tab!=null)
        {
            if(Tab.equals("1"))
            {
                relaceFragment(new ProductFragment());
            }
            else if(Tab.equals("2"))
            {
                relaceFragment(new UserFragment());
            }
            else if(Tab.equals("3"))
            {
                relaceFragment(new AcceptOrderFragment());
            }
        }
    }
    private void relaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    public void Logout(MenuItem item) {
        mAuth.signOut();
        startActivity(new Intent(MainadminActivity.this, LoginActivity.class));
    }
}