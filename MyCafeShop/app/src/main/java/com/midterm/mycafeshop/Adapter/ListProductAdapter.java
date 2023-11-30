package com.example.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.finalproject.Models.Product;
import com.example.finalproject.ProductDetailActivity;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListProductAdater extends ArrayAdapter<Product> {
    Context context;
    List<Product>productList;
    TextView Btn;
    ImageView image;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public ListProductAdater(@NonNull Context context,List<Product>dataproduct){
        super(context, R.layout.product_item,dataproduct);
        this.context=context;
        this.productList=dataproduct;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,null,true);
        TextView name=view.findViewById(R.id.Name);
        TextView category=view.findViewById(R.id.Category);
        TextView price=view.findViewById(R.id.Price);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        name.setText(productList.get(position).getName());
        category.setText(productList.get(position).getCategory().getName());
        price.setText(productList.get(position).getPrice());
        image=view.findViewById(R.id.image1);
        loadimage(productList.get(position).getImage());
        Btn=(TextView)view.findViewById(R.id.BtnBuy);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ProductDetailActivity.class);
                intent.putExtra("id",productList.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
    private void loadimage(String uri)
    {
        StorageReference httpsReference = storage.getReferenceFromUrl(uri);
        try {
            File localFile= File.createTempFile("tempfile","png");
            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
