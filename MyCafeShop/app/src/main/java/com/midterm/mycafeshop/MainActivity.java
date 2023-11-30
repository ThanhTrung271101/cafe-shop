package com.example.finalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalproject.Fragment.History.HistoryFragment;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.databinding.ActivityMainBinding;
import com.example.finalproject.ui.History;
import com.example.finalproject.ui.Home;
import com.example.finalproject.ui.MyOrder;
import com.example.finalproject.ui.Profile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    FirebaseAuth mAuth;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseData data=new FirebaseData();
        createNotification();
        data.GetNotification(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.child("detail").getChildren())
                {
                    Map singleValue=(Map)ds.getValue();
                    String Title=(String)singleValue.get("title");
                    String Content=(String)singleValue.get("content");

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, "Accept")
                            .setSmallIcon(R.drawable.coffeebean)
                            .setContentTitle(Title)
                            .setContentText(Content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                    notificationManager.notify(100, mBuilder.build());
                    data.DeleteNotification(mAuth.getCurrentUser().getUid(),ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent=getIntent();
        String Menu=intent.getStringExtra("menu");
        if(Menu!=null)
        {
        if(Menu.equals("1"))
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout,new History())
                    .commit();
        }
        else if(Menu.equals("2"))
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout,new MyOrder())
                    .commit();
        }
        else if(Menu.equals("3"))
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout,new Profile())
                    .commit();
        }
        }
        DrawerLayout drawer = binding.drawerLayout;
        mDrawerLayout=binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_myorder,R.id.nav_rewards,R.id.nav_profile,R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void open(){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
        {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else{
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void Logout(MenuItem item) {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    public void OpenDetail(View view){
        int id=view.getId();
        if(id==R.id.item1){
            Intent intent=new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("id","34934230d43985fd511a4e678e31eda4");
            startActivity(intent);
        }
    }
    public void createNotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="android";
            String description="test";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("Accept",name,importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}