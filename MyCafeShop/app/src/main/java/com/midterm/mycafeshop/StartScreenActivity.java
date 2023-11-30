package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.Adapter.MpagerAdater;

public class StartScreenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    TextView[] dots;
    private TextView button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        button=(TextView) findViewById(R.id.button_started);
        viewPager=findViewById(R.id.view_pager);
        dotLayout=findViewById(R.id.layoutDots);



        MpagerAdater adater=new MpagerAdater(this);
        viewPager.setAdapter(adater);
        adddots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adddots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartScreenActivity.this, ChooseLoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void adddots(int i) {
        dots=new TextView[3];
        dotLayout.removeAllViews();
        for(int a=0;a<dots.length;a++){
            dots[a]=new TextView(this);
            dots[a].setText(Html.fromHtml("&#8226;"));
            dots[a].setTextSize(50);
            dots[a].setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotLayout.addView(dots[a]);

        }
        if(dots.length>0) {
            dots[i].setTextColor(Color.parseColor("#04764E"));
        }
    }
}