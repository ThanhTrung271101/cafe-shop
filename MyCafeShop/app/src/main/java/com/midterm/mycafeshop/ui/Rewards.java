package com.example.finalproject.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.Adapter.ListOrderAdater;
import com.example.finalproject.Adapter.ListRewardAdater;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainActivity;
import com.example.finalproject.Models.Detail;
import com.example.finalproject.Models.Product;
import com.example.finalproject.Models.User;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Rewards extends Fragment {

    private View view;
    ListView listView;
    ListRewardAdater adater;
    private ImageView BtnPre1;
    ImageView menubar,coffee1,coffee2,coffee3,coffee4,coffee5;
    TextView Point,Point2;
    FirebaseAuth mAuth;
    public Rewards() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_rewards, container, false);
        }
        TextView title=view.findViewById(R.id.title);
        title.setText("My Reward");
        FirebaseData data=new FirebaseData();
        List<Detail> listDetail=new ArrayList<>();
        //region Declare
        listView=(ListView) view.findViewById(R.id.listview_Reward);
        Point=(TextView)view.findViewById(R.id.points);
        Point2=(TextView)view.findViewById(R.id.accumulatedpoint);
        coffee1=(ImageView)view.findViewById(R.id.coffee_1);
        coffee2=(ImageView)view.findViewById(R.id.coffee_2);
        coffee3=(ImageView)view.findViewById(R.id.coffee_3);
        coffee4=(ImageView)view.findViewById(R.id.coffee_4);
        coffee5=(ImageView)view.findViewById(R.id.coffee_5);
        mAuth=FirebaseAuth.getInstance();
        //endregion
        listView.setEnabled(false);
        data.GetDetail(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDetail.clear();
                //Product product=new Product();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Map singleValue=(Map)ds.getValue();
                    String Id=ds.getKey();
                    Product product= (Product) ds.child("product").getValue(Product.class);
                    String date= (String) singleValue.get("date");
                    listDetail.add(new Detail(Id,product,date));
                }
                if (getActivity()!=null){
                listView.getLayoutParams().height=350*listDetail.size();
                adater=new ListRewardAdater(getActivity(),listDetail);
                listView.setDivider(null);
                listView.setAdapter(adater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map singleValue=(Map)snapshot.getValue();
                int GiftPoint= ((Long) singleValue.get("giftPoint")).intValue();
                int Point1=((Long) singleValue.get("accumulatedPoint")).intValue();
                if(Point1>5){
                    Point1=5;
                }
                Loyalty(Point1);
                Point2.setText(Integer.toString(Point1)+" / 5");
                Point.setText(Integer.toString(GiftPoint));
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
                ((MainActivity)getActivity()).open();
            }
        });
        return view;
    }
    public void Loyalty(int point){

        coffee1.setImageResource(R.drawable.coffeecup);
        coffee2.setImageResource(R.drawable.coffeecup);
        coffee3.setImageResource(R.drawable.coffeecup);
        coffee4.setImageResource(R.drawable.coffeecup);
        coffee5.setImageResource(R.drawable.coffeecup);
        if(point==0){
            coffee1.setImageResource(R.drawable.coffeecup);
            coffee2.setImageResource(R.drawable.coffeecup);
            coffee3.setImageResource(R.drawable.coffeecup);
            coffee4.setImageResource(R.drawable.coffeecup);
            coffee5.setImageResource(R.drawable.coffeecup);
        }if(point>=1){
            coffee1.setImageResource(R.drawable.coffee);
        }if(point>=2){
            coffee2.setImageResource(R.drawable.coffee);
        }if(point>=3){
            coffee3.setImageResource(R.drawable.coffee);
        }if(point>=4){
            coffee4.setImageResource(R.drawable.coffee);
        }if(point==5){
            coffee5.setImageResource(R.drawable.coffee);
        }
    }
}