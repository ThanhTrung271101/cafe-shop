package com.example.finalproject.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.Adapter.AcceptItemAdater;
import com.example.finalproject.Adapter.HistoryDetailAdater;
import com.example.finalproject.DetailHistoryActivity;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.MainadminActivity;
import com.example.finalproject.Models.Detail1;
import com.example.finalproject.Models.Notification;
import com.example.finalproject.Models.Order;
import com.example.finalproject.Models.Product;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DetailOrderAcceptActivity extends AppCompatActivity {

    ListView listView;
    FirebaseAuth mAuth;
    AcceptItemAdater adater;
    TextView total1;
    TextView subtotal1;
    TextView Accept, Cancel;
    TextView name,phone,address;
    Double Total;
    private ImageView BtnPre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_accept);
        List<Detail1> listDetail1=new ArrayList<>();
        FirebaseData data=new FirebaseData();
        listView=(ListView) findViewById(R.id.listview);
        mAuth= FirebaseAuth.getInstance();
        listView.setEnabled(false);
        if(mAuth.getCurrentUser().getUid()==null)
        {
            startActivity(new Intent(DetailOrderAcceptActivity.this, LoginActivity.class));
        }
        Intent intent=getIntent();
        String Idbill= intent.getStringExtra("id");
        BtnPre1=findViewById(R.id.BtnPre1);
        total1=findViewById(R.id.total);
        subtotal1=findViewById(R.id.subtotal);
        Accept=findViewById(R.id.accept);
        Cancel=findViewById(R.id.cancel);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);

        data.GetOneData(Idbill).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDetail1.clear();
                Total=0.0;
                    Order order=snapshot.getValue(Order.class);
                    for(DataSnapshot ds:snapshot.child("detail").getChildren())
                    {
                        Map singleValue=(Map)ds.getValue();
                        int Quantity=((Long) singleValue.get("quanlity")).intValue();
                        String OrderRequest=(String)singleValue.get("orderRequest");
                        Product product= ds.child("product").getValue(Product.class);
                        double total=((Long) singleValue.get("total")).doubleValue();
                        String CartId=(String)singleValue.get("idCart");
                        listDetail1.add(new Detail1(ds.getKey(),product,total,Quantity,OrderRequest,CartId));
                        Total=Total+total;
                    }
                listView.getLayoutParams().height=250*listDetail1.size();
                adater=new AcceptItemAdater(DetailOrderAcceptActivity.this,listDetail1);
                listView.setDivider(null);
                listView.setAdapter(adater);
                subtotal1.setText("$"+Total);
                total1.setText("$"+Total);
                if(order!=null)
                {
                    phone.setText(order.getPhone());
                    name.setText(order.getName());
                    address.setText(order.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               data.AcceptOrder(mAuth.getCurrentUser().getUid(),Idbill);
               String Id=getMd5("Accept Order"+"Your order has been delivered"+new Date().toString());
               data.AddNotification(mAuth.getCurrentUser().getUid(),new Notification(Id,"Your order has been delivered","Accept Order","",false));
                Intent intent=new Intent(DetailOrderAcceptActivity.this, MainadminActivity.class);
                intent.putExtra("tab","3");
                startActivity(intent);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.CancelOrder(mAuth.getCurrentUser().getUid(),Idbill);
                String Id=getMd5("Cancel Order"+"Your order has been cancelled"+new Date().toString());
                data.AddNotification(mAuth.getCurrentUser().getUid(),new Notification(Id,"Your order has been cancelled","Cancel Order","",false));
                Intent intent=new Intent(DetailOrderAcceptActivity.this, MainadminActivity.class);
                intent.putExtra("tab","3");
                startActivity(intent);
            }
        });
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailOrderAcceptActivity.this, MainadminActivity.class);
                intent.putExtra("tab","3");
                startActivity(intent);
            }
        });
    }
    static String getMd5(String input)
    {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}