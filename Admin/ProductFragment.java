package com.example.finalproject.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.Category;
import com.example.finalproject.Models.Product;
import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ProductFragment extends Fragment {

    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;
    ImageView add;
    private ImageView BtnPre1;
    ImageView menubar;
    public ProductFragment() {
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
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_product, container, false);
        }
        TableView table=view.findViewById(R.id.table_data_view);

        menubar=view.findViewById(R.id.menubar);
        BtnPre1=view.findViewById(R.id.BtnPre1);
        menubar.setVisibility(View.INVISIBLE);
        BtnPre1.setVisibility(View.INVISIBLE);
        TextView title=view.findViewById(R.id.title);
        title.setText("Products");
        add=view.findViewById(R.id.add);
        String[] Headers={"Name","Price","Category","Quality"};
        FirebaseData data=new FirebaseData();
        List<Product> productList=new ArrayList<>();
        data.GetData().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Map singleValue=(Map)ds.getValue();
                    String Id=ds.getKey();
                    String Name=(String) singleValue.get("name");
                    Category Category=ds.child("category").getValue(Category.class);
                    String Price=(String)singleValue.get("price");
                    String Quantity=(String)singleValue.get("quantity");
                    productList.add(new Product(Id,Name,Price,"",Quantity,"",Category));
                }
                if(productList.size()>0)
                {
                    String[][] datatable=new String[productList.size()][4];
                    for(int i=0;i<productList.size();i++){
                        Product p=productList.get(i);
                        datatable[i][0]=p.getName();
                        datatable[i][1]=p.getPrice();
                        datatable[i][2]=p.getQuantity();
                        datatable[i][3]=p.getCategory().getName();
                    }
                    if(getActivity()!=null) {
                        table.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), Headers));
                        table.setDataAdapter(new SimpleTableDataAdapter(getActivity(), datatable));
                    }
                }
                else {
                    String[][] datatable={{"","","",""}};
                    if(getActivity()!=null) {
                    table.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(),Headers));
                    table.setDataAdapter(new SimpleTableDataAdapter(getActivity(),datatable));
                }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddProductActivity.class));
            }
        });

        table.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Intent i=new Intent(getActivity(),DetaiProActivity.class);
                i.putExtra("Id",productList.get(rowIndex).getId());
                startActivity(i);
            }
        });

        table.addDataLongClickListener(new TableDataLongClickListener() {
            @Override
            public boolean onDataLongClicked(int rowIndex, Object clickedData) {
                builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure you want to remove");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.DeleteProduct(productList.get(rowIndex).getId());
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

}