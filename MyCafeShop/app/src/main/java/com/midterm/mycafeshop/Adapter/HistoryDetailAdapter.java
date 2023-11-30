package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.Models.Detail1;
import com.example.finalproject.R;

import java.util.List;

public class HistoryDetailAdater extends ArrayAdapter<Detail1> {
    Context context;
    List<Detail1> detailList;
    public HistoryDetailAdater(@NonNull Context context, List<Detail1>dataDetail) {
        super(context, R.layout.history_detail_item,dataDetail);
        this.context=context;
        this.detailList=dataDetail;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_detail_item,null,true);
        TextView name=view.findViewById(R.id.Name);
        TextView quantity=view.findViewById(R.id.Quantity);
        TextView price=view.findViewById(R.id.Price);
        TextView total=view.findViewById(R.id.Total);

        name.setText(detailList.get(position).getProduct().getName());
        quantity.setText(String.valueOf(detailList.get(position).getQuanlity())+"x");
        //price.setText("$"+detailList.get(position).getProduct().getPrice());
        total.setText("$"+detailList.get(position).getTotal());
        return view;
    }
}
