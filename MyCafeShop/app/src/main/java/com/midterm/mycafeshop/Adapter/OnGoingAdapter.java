package com.example.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.DetailHistoryActivity;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainActivity;
import com.example.finalproject.Models.Order;
import com.example.finalproject.ProductDetailActivity;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class OnGoingAdater extends ArrayAdapter<Order> {
    Context context;
    List<Order> orderList;
    TextView Btn;
    FirebaseAuth mAuth;
    FirebaseData data;
    public OnGoingAdater(@NonNull Context context, List<Order>dataOrder) {
        super(context, R.layout.ongoing_item,dataOrder);
        this.context=context;
        this.orderList=dataOrder;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_item,null,true);
        TextView DateOrder=view.findViewById(R.id.dateorder);
        TextView Name=view.findViewById(R.id.name);
        TextView Address=view.findViewById(R.id.address);
        TextView Total=view.findViewById(R.id.total);
        DateOrder.setText(orderList.get(position).getTimeOrder());
        Name.setText(orderList.get(position).getName());
        Address.setText(orderList.get(position).getAddress());
        Total.setText(Double.toString(orderList.get(position).getTotal()));
        //-----------------------------------------------------------------
        mAuth= FirebaseAuth.getInstance();
        data=new FirebaseData();
        Btn=(TextView)view.findViewById(R.id.BtnCancel);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.DeleteOrder(mAuth.getCurrentUser().getUid(),orderList.get(position).getId());
                Intent intent=new Intent(parent.getContext(), MainActivity.class);
                intent.putExtra("menu","1");
                context.startActivity(intent);
            }
        });
        //-----------------------------------------------------------------
        return view;
    }

}