package com.example.finalproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Adapter.VPAdater;
import com.example.finalproject.AllProductActivity;
import com.example.finalproject.Fragment.History.HistoryFragment;
import com.example.finalproject.Fragment.History.OngoingFragment;
import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.google.android.material.tabs.TabLayout;

public class History extends Fragment {


    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView BtnPre1;
    ImageView menubar;
    public History() {
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
            view=inflater.inflate(R.layout.fragment_history, container, false);
        }
        TextView title=view.findViewById(R.id.title);
        title.setText("My History");
        BtnPre1=view.findViewById(R.id.BtnPre1);
        menubar=view.findViewById(R.id.menubar);
        tabLayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.viewpager);

        if(getActivity()!=null){
            HistoryFragment historyFragment=new HistoryFragment();
            OngoingFragment ongoingFragment=new OngoingFragment();
            VPAdater vpAdater=new VPAdater(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            vpAdater.addFragment(historyFragment,"History");
            vpAdater.addFragment(ongoingFragment,"Ongoing");
            viewPager.setAdapter(vpAdater);
            tabLayout.setupWithViewPager(viewPager);
        }
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
                ((MainActivity)getActivity()).open();
            }
        });
        return view;
    }
}