package com.example.finalproject.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Helper.FirebaseData;
import com.example.finalproject.MainadminActivity;
import com.example.finalproject.Models.Category;
import com.example.finalproject.Models.Product;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    ImageView chooseImage,image;
    TextView add;
    EditText Name,Price,Quanlity;
    private Spinner spnCategory;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public Uri imageUri;
    Category cate;
    private ImageView BtnPre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        BtnPre1=findViewById(R.id.BtnPre1);
        add=findViewById(R.id.add);
        chooseImage=findViewById(R.id.chooseimage);
        Name=findViewById(R.id.name);
        Price=findViewById(R.id.price);
        Quanlity=findViewById(R.id.quantity);
        image=findViewById(R.id.image);
        TextView title=findViewById(R.id.title);
        title.setText("Add Product");

        FirebaseData data=new FirebaseData();
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
            spnCategory = (Spinner) findViewById(R.id.category);
            List<String> category=new ArrayList<>();
        category.add("Coffee");
        category.add("Tea");
        category.add("Fruit Juice");
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,category);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnCategory.setAdapter(adapter);
            spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cate=new Category(Integer.toString(i),spnCategory.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Name1=Name.getText().toString();
                    String Price1=Price.getText().toString();
                    String Quan=Quanlity.getText().toString();
                    String Id=getMd5(Name1+Price1+Quan+new Date().toString());
                    final String randomKey= UUID.randomUUID().toString();
                    if(TextUtils.isEmpty(Name1)){
                        Name.setError("Name cannot be empty");
                        Name.requestFocus();
                    }else if(TextUtils.isEmpty(Price1)){
                        Price.setError("Price cannot be empty");
                        Price.requestFocus();
                    }else if(TextUtils.isEmpty(Quan)){
                        Quanlity.setError("Quanlity cannot be empty");
                        Quanlity.requestFocus();
                    }
                    else{
                        Product product=new Product(Id,Name1,Price1,"2000",Quan,"gs://androiproject-a386e.appspot.com/image/"+randomKey,cate);
                        data.AddProduct(product);
                        if(imageUri!=null)
                        {
                            uploadPicture(randomKey);
                            Intent intent=new Intent(AddProductActivity.this, MainadminActivity.class);
                            intent.putExtra("tab","1");
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AddProductActivity.this,"you need to import pictures",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        BtnPre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddProductActivity.this, MainadminActivity.class);
                intent.putExtra("tab","1");
                startActivity(intent);
            }
        });
    }
    private void chooseImage(){
        Intent intentImage=new Intent();
        intentImage.setType("image/*");
        intentImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentImage,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& resultCode==RESULT_OK && data!=null&& data.getData()!=null)
        {
            imageUri=data.getData();
            image.setImageURI(imageUri);
            //uploadPicture();
        }
    }

    private void uploadPicture(String randomKey) {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Update Image...");
        StorageReference riverRef=storageReference.child("image/"+randomKey);

        riverRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                //Snackbar.make(findViewById(R.id.content),"ImageUploaded",Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplication(),"Failed To Upload",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progessPercent=(100.00*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Percentage: "+(int)progessPercent+"%");
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