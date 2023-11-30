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

public class AcceptItemAdater extends ArrayAdapter<Detail1> {
    Context context;
    List<Detail1>orderList;
    public AcceptItemAdater(@NonNull Context context, List<Detail1>dataOrder) {
        super(context, R.layout.item_accept_detail,dataOrder);
        this.context=context;
        this.orderList=dataOrder;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept_detail,null,true);

        TextView name=view.findViewById(R.id.Name);
        TextView quantity=view.findViewById(R.id.Quantity);
        TextView total=view.findViewById(R.id.Total);
        TextView orderrequest=view.findViewById(R.id.orderrequest);

        name.setText(orderList.get(position).getProduct().getName().toString());
        quantity.setText(String.valueOf(orderList.get(position).getQuanlity())+"x");
        total.setText("$"+orderList.get(position).getTotal());
        orderrequest.setText(orderList.get(position).getOrderRequest());
        return view;
    }

}
