package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.Models.Cart;
import com.example.finalproject.Models.Detail;
import com.example.finalproject.Models.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    String Size,Roas,Grind,Ice;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    double Total;
    int Quanlity;
    Product product;
    FirebaseAuth mAuth;
    FirebaseData data;
    TextView Quan1,Name,total,AddCard;
    ImageView SizeM,SizeL,SizeXL,SmallFire,MediumFire,BigFire,GrindSmall,GrindBig,NoneIce,HalfIce,FullIce,imageCoffee;
    String ImageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //region Declare
        total=(TextView)findViewById(R.id.total);
        Name=(TextView)findViewById(R.id.name);
        Quan1=(TextView)findViewById(R.id.Quantity1);
        SizeM=(ImageView)findViewById(R.id.sizeM);
        SizeL=(ImageView)findViewById(R.id.sizeL);
        SizeXL=(ImageView)findViewById(R.id.sizeXL);
        SmallFire=(ImageView)findViewById(R.id.smallfire);
        MediumFire=(ImageView)findViewById(R.id.mediumfire);
        BigFire=(ImageView)findViewById(R.id.bigfire);
        GrindSmall=(ImageView)findViewById(R.id.grindsmall);
        GrindBig=(ImageView)findViewById(R.id.grindbig);
        NoneIce=(ImageView)findViewById(R.id.noneice);
        HalfIce=(ImageView)findViewById(R.id.halfice);
        FullIce=(ImageView)findViewById(R.id.fullice);
        AddCard=(TextView)findViewById(R.id.addcard);
        imageCoffee=(ImageView)findViewById(R.id.imgcoffe);
        //endregion
        mAuth=FirebaseAuth.getInstance();
        Size="M";Roas="Small";Grind="Small";Ice="Half";Quanlity=1;
        data=new FirebaseData();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Intent intent = getIntent();
        String id1=intent.getStringExtra("id");
        TextView title=findViewById(R.id.title);
        title.setText("Product Detail");

        data.GetNotification(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Map singleValue=(Map)ds.getValue();
                    String Title=(String)singleValue.get("title");
                    String Content=(String)singleValue.get("content");

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ProductDetailActivity.this, "Accept")
                            .setSmallIcon(R.drawable.coffeebean)
                            .setContentTitle(Title)
                            .setContentText(Content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ProductDetailActivity.this);
                    notificationManager.notify(100, mBuilder.build());
                    data.DeleteNotification(mAuth.getCurrentUser().getUid(),ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data.GetDataProduct(id1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product data1=new Product();
                data1=snapshot.getValue(Product.class);
                product=data1;
                Total=Double.parseDouble(data1.getPrice());
                Name.setText(data1.getName());
                total.setText("$ "+data1.getPrice());
                ImageLink=data1.getImage();
                loadimage(ImageLink);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OrderRequest=Size+"|"+Roas+"|"+Grind+"|"+Ice;
                Date date = new Date();
                String Id=getMd5(String.valueOf(Quanlity)+product.toString()+OrderRequest+String.valueOf(Total));
                Cart detail=new Cart(Id,Quanlity,product,OrderRequest,Total);
                AddToCart(detail);
                Toast.makeText(ProductDetailActivity.this,"successfully added "+product.getName()+" to cart",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProductDetailActivity.this,AllProductActivity.class));
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
    public void OnClick(View view){
        int id=view.getId();
        switch (id){
            case R.id.buttonminus:
                Minus();
                break;
            case R.id.buttonplus:
                Plus();
                break;
            case R.id.imageminus:
                Minus();
                break;
            case R.id.imageplus:
                Plus();
                break;
            case R.id.sizeM:
                ChangeSize(R.id.sizeM);
                break;
            case R.id.sizeL:
                ChangeSize(R.id.sizeL);
                break;
            case R.id.sizeXL:
                ChangeSize(R.id.sizeXL);
                break;
            case R.id.smallfire:
                ChangeRoast(R.id.smallfire);
                break;
            case R.id.mediumfire:
                ChangeRoast(R.id.mediumfire);
                break;
            case R.id.bigfire:
                ChangeRoast(R.id.bigfire);
                break;
            case R.id.grindsmall:
                ChangeGrind(R.id.grindsmall);
                break;
            case R.id.grindbig:
                ChangeGrind(R.id.grindbig);
                break;
            case R.id.noneice:
                ChangeIce(R.id.noneice);
                break;
            case R.id.halfice:
                ChangeIce(R.id.halfice);
                break;
            case R.id.fullice:
                ChangeIce(R.id.fullice);
                break;

        }
    }
    public void Plus(){
        if(Quanlity<10){
            Total=Total+(Total/Quanlity);
            Quanlity=Quanlity+1;
            Quan1.setText(Integer.toString(Quanlity));

        }
        total.setText("$ "+Double.toString(Total));
    }
    public void Minus(){
        if(Quanlity>1){
            Total=Total-(Total/Quanlity);
            Quanlity=Quanlity-1;
            Quan1.setText(Integer.toString(Quanlity));

        }
        total.setText("$ "+Double.toString(Total));
    }
    public void ChangeSize(int Id){
        if(Id==R.id.sizeM){
            if(Size=="L")
            {
                Total=Total-(2*Quanlity);
            }else if(Size=="XL")
            {
                Total=Total-(4*Quanlity);
            }
            SizeM.setImageResource(R.drawable.coffee);
            SizeL.setImageResource(R.drawable.coffeecup);
            SizeXL.setImageResource(R.drawable.coffeecup);
            Size="M";
        }
        else if(Id==R.id.sizeL){
            if(Size=="M")
            {
                Total=Total+(2*Quanlity);
            }else if(Size=="XL")
            {
                Total=Total-(2*Quanlity);
            }
            SizeL.setImageResource(R.drawable.coffee);
            SizeM.setImageResource(R.drawable.coffeecup);
            SizeXL.setImageResource(R.drawable.coffeecup);
            Size="L";
        }
        else{
            if(Size=="L")
            {
                Total=Total+(2*Quanlity);
            }else if(Size=="M")
            {
                Total=Total+(4*Quanlity);
            }
            SizeXL.setImageResource(R.drawable.coffee);
            SizeM.setImageResource(R.drawable.coffeecup);
            SizeL.setImageResource(R.drawable.coffeecup);
            Size="XL";
        }
        total.setText("$ "+Double.toString(Total));
    }
    public void ChangeRoast(int Id){
        if(Id==R.id.smallfire)
        {
            SmallFire.setImageResource(R.drawable.fire);
            MediumFire.setImageResource(R.drawable.fire1);
            BigFire.setImageResource(R.drawable.fire1);
            Roas="Small";
        }
        else if(Id==R.id.mediumfire){
            SmallFire.setImageResource(R.drawable.fire1);
            MediumFire.setImageResource(R.drawable.fire);
            BigFire.setImageResource(R.drawable.fire1);
            Roas="Medium";
        }
        else{
            SmallFire.setImageResource(R.drawable.fire1);
            MediumFire.setImageResource(R.drawable.fire1);
            BigFire.setImageResource(R.drawable.fire);
            Roas="Big";
        }
    }
    public void ChangeGrind(int Id){
        if(Id==R.id.grindsmall){
            GrindSmall.setImageResource(R.drawable.coffeebean);
            GrindBig.setImageResource(R.drawable.coffeeb1);
            Grind="Small";
        }
        else{
            GrindSmall.setImageResource(R.drawable.coffeeb1);
            GrindBig.setImageResource(R.drawable.coffeebean);
            Grind="Big";
        }
    }
    public void ChangeIce(int Id){
        if(Id==R.id.noneice){
            NoneIce.setImageResource(R.drawable.ice1);
            HalfIce.setImageResource(R.drawable.icecubes);
            FullIce.setImageResource(R.drawable.icecubes);
            Ice="None";
        }
        else if(Id==R.id.halfice){
            NoneIce.setImageResource(R.drawable.ice);
            HalfIce.setImageResource(R.drawable.icecubes1);
            FullIce.setImageResource(R.drawable.icecubes);
            Ice="Half";
        }
        else{
            NoneIce.setImageResource(R.drawable.ice);
            HalfIce.setImageResource(R.drawable.icecubes);
            FullIce.setImageResource(R.drawable.icecubes1);
            Ice="Full";
        }
    }
    public void AddToCart(Cart card){
        data.AddCard(mAuth.getCurrentUser().getUid(),card);
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
                    imageCoffee.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}