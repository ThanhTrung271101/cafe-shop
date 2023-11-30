package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.Models.Order;
import com.example.finalproject.R;

import java.util.List;

public class HistoryAdater extends ArrayAdapter<Order> {
    Context context;
    List<Order>orderList;
    public HistoryAdater(@NonNull Context context, List<Order>dataOrder) {
        super(context, R.layout.history_item,dataOrder);
        this.context=context;
        this.orderList=dataOrder;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,null,true);
        TextView DateOrder=view.findViewById(R.id.dateorder);
        TextView Name=view.findViewById(R.id.name);
        TextView Address=view.findViewById(R.id.address);
        TextView Total=view.findViewById(R.id.total);
        DateOrder.setText(orderList.get(position).getTimeOrder());
        Name.setText(orderList.get(position).getName());
        Address.setText(orderList.get(position).getAddress());
        Total.setText(Double.toString(orderList.get(position).getTotal()));
        return view;
    }

}