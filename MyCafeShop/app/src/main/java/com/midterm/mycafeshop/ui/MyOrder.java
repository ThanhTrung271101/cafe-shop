package com.example.finalproject.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Adapter.ListOrderAdater;
import com.example.finalproject.Adapter.ListProductAdater;
import com.example.finalproject.Admin.DetailUserActivity;
import com.example.finalproject.CheckoutActivity;
import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainActivity;
import com.example.finalproject.Models.Cart;
import com.example.finalproject.Models.Detail;
import com.example.finalproject.Models.Detail1;
import com.example.finalproject.Models.Order;
import com.example.finalproject.Models.Product;
import com.example.finalproject.Models.User;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyOrder extends Fragment {
    private View view;
    TextView placeorder;
    ListView listView;
    ListOrderAdater adater;
    private ImageView BtnPre1;
    double Total;
    ImageView menubar;
    FirebaseAuth mAuth;
    FirebaseData data;
    User user;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    public MyOrder() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_my_order, container, false);
        }
        List<Cart>listCart=new ArrayList<>();
        data=new FirebaseData();
        user=new User();
        listView=(ListView) view.findViewById(R.id.Lisview_Order);
        placeorder=(TextView)view.findViewById(R.id.placeorder);
        mAuth=FirebaseAuth.getInstance();
        //------------------------------------------------------------------------------------------
        TextView title=view.findViewById(R.id.title);
        title.setText("My Order");
        //------------------------------------------------------------------------------------------
        data.GetDataCart(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCart.clear();
                Total=0;
                Product product=new Product();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Map singleValue=(Map)ds.getValue();
                    int Quantity=((Long) singleValue.get("quantity")).intValue();
                    String OrderRequest=(String)singleValue.get("orderRequest");
                    product= ds.child("product").getValue(Product.class);
                    double total=((Long) singleValue.get("total")).doubleValue();
                    Total=Total+total;
                    listCart.add(new Cart(ds.getKey(),Quantity,product,OrderRequest,total));
                }
                if (getActivity()!=null) {
                    adater = new ListOrderAdater(getActivity(), listCart);
                }
                listView.setDivider(null);
                listView.setAdapter(adater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //------------------------------------------------------------------------------------------
        data.GetDataUser(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map singleValue=(Map)snapshot.getValue();
                String Id1=snapshot.getKey();
                String Name1= (String) singleValue.get("name");
                String Phone1= (String) singleValue.get("phone");
                String Address1= (String) singleValue.get("address");
                String Email1=(String) singleValue.get("email");
                user=new User(Id1,Name1,"",Address1,Email1,Phone1,0,0,"","");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //------------------------------------------------------------------------------------------
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCart.size()>0)
                {
                    AddOrder(user,listCart);
                }
                else{
                    Toast.makeText(getActivity(),"No oke",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------------------------------------------------------------------------------
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
        //------------------------------------------------------------------------------------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure you want to remove the product from the cart?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.deleteCart(mAuth.getCurrentUser().getUid(),listCart.get(i).getId());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog=builder.create();
                dialog.show();
                return true;
            }
        });
        return view;
    }
    public void AddOrder(User user,List<Cart>listCart){
        Date date=new Date();
        String Id=getMd5(user.getAddress()+user.getName()+user.getPhoneNumber()+"Confirmation"+date.toString());
        Order order=new Order(Id,user.getAddress(),user.getName(),user.getPhoneNumber(),"Confirmation",new SimpleDateFormat("MM/dd/yyyy").format(date),Total);
        data.AddOrderTemp(mAuth.getCurrentUser().getUid(),order);
        for(Cart cart:listCart){
            String Id1=getMd5(cart.getProduct().toString()+Double.toString(cart.getQuantity())+cart.getOrderRequest()+Integer.toString(cart.getQuantity())+new Date().toString());
            Detail1 detail1=new Detail1(Id1,cart.getProduct(), cart.getTotal(),cart.getQuantity(),cart.getOrderRequest(),cart.getId());
            data.AddOrderDetailTemp(mAuth.getCurrentUser().getUid(),Id,detail1);
            //data.deleteCart(mAuth.getCurrentUser().getUid(),cart.getId());
        }
        Intent I=new Intent(getActivity(), CheckoutActivity.class);
        I.putExtra("id",order.getId());
        startActivity(I);
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