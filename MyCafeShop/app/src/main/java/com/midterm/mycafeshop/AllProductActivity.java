package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Adapter.VPAdater;
import com.example.finalproject.Fragment.AllProduct.CategoryFragment1;
import com.example.finalproject.Fragment.AllProduct.CategoryFragment2;
import com.example.finalproject.Fragment.AllProduct.CategoryFragment3;
import com.example.finalproject.Helper.FirebaseData;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AllProductActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView BtnPre;
    private ImageView BtnPre1;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        mAuth = FirebaseAuth.getInstance();

        FirebaseData data = new FirebaseData();

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        BtnPre = findViewById(R.id.BtnPre);
        BtnPre1 = findViewById(R.id.BtnPre1);

        Intent intent = getIntent();
        String Tab = intent.getStringExtra("tab");


        CategoryFragment1 fragment1 = new CategoryFragment1();
        CategoryFragment2 fragment2 = new CategoryFragment2();
        CategoryFragment3 fragment3 = new CategoryFragment3();
        VPAdater vpAdater = new VPAdater(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdater.addFragment(fragment1, "Coffee");
        vpAdater.addFragment(fragment2, "Tea");
        vpAdater.addFragment(fragment3, "Fruit Juice");

        viewPager.setAdapter(vpAdater);

        tabLayout.setupWithViewPager(viewPager);

        TextView title = findViewById(R.id.title);
        title.setText("All Product");
        data.GetNotification(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map singleValue = (Map) ds.getValue();
                    String Title = (String) singleValue.get("title");
                    String Content = (String) singleValue.get("content");

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AllProductActivity.this, "Accept")
                            .setSmallIcon(R.drawable.coffeebean)
                            .setContentTitle(Title)
                            .setContentText(Content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AllProductActivity.this);
                    notificationManager.notify(100, mBuilder.build());
                    data.DeleteNotification(mAuth.getCurrentUser().getUid(), ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        BtnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllProductActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllProductActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        if (Tab != null) {
            if (Tab.equals("1")) {
                tabLayout.getTabAt(0).select();
            } else if (Tab.equals("2")) {
                tabLayout.getTabAt(1).select();
            } else if (Tab.equals("3")) {
                tabLayout.getTabAt(2).select();
            }
        }
    }
}
