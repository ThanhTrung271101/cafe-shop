package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.Models.Cart;
import com.example.finalproject.Models.Product;
import com.example.finalproject.R;

import java.util.List;

public class ListOrderAdater extends ArrayAdapter<Cart> {
    Context context;
    List<Cart> ListCart;
    public ListOrderAdater(@NonNull Context context, List<Cart>datacart){
        super(context, R.layout.order_item,datacart);
        this.context=context;
        this.ListCart=datacart;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,null,true);
        TextView name=view.findViewById(R.id.Name);
        TextView quantity=view.findViewById(R.id.Quantity);
        TextView price=view.findViewById(R.id.Price);
        TextView total=view.findViewById(R.id.Total);

        name.setText(ListCart.get(position).getProduct().getName());
        quantity.setText(String.valueOf(ListCart.get(position).getQuantity())+"x");
        price.setText("$"+ListCart.get(position).getProduct().getPrice());
        total.setText("$"+Double.toString(ListCart.get(position).getTotal()));
        return view;
    }
}
