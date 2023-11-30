package com.example.finalproject.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.finalproject.MainActivity;
import com.example.finalproject.MainadminActivity;
import com.example.finalproject.Models.Category;
import com.example.finalproject.Models.Product;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DetaiProActivity extends AppCompatActivity {
    private Spinner spnCategory;
    ImageView edit,image,chooseimage;
    EditText name,price,quanlity;
    TextView btn;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    int CateId;
    Category category1;
    String img1;
    private ImageView BtnPre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_pro);

        Intent intent=getIntent();
        String Id=intent.getStringExtra("Id");
        FirebaseData data=new FirebaseData();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        TextView title=findViewById(R.id.title);
        title.setText("Detail Product");
        if(Id!=null){
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
                    CateId=i;
                    category1=new Category(Integer.toString(i),spnCategory.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spnCategory.setEnabled(false);
            image=findViewById(R.id.image);
            chooseimage=findViewById(R.id.chooseimage);
            edit=findViewById(R.id.menubar);
            name=findViewById(R.id.name);
            price=findViewById(R.id.price);
            quanlity=findViewById(R.id.quantity);
            btn=findViewById(R.id.update);
            BtnPre1=findViewById(R.id.BtnPre1);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Name1=name.getText().toString();
                    String Price1=price.getText().toString();
                    String Quan=quanlity.getText().toString();
                    final String randomKey= UUID.randomUUID().toString();
                    img1="gs://androiproject-a386e.appspot.com/image/"+randomKey;
                    if(TextUtils.isEmpty(Name1)){
                        name.setError("Name cannot be empty");
                        name.requestFocus();
                    }else if(TextUtils.isEmpty(Price1)){
                        price.setError("Price cannot be empty");
                        price.requestFocus();
                    }else if(TextUtils.isEmpty(Quan)){
                        quanlity.setError("Quanlity cannot be empty");
                        quanlity.requestFocus();
                    }
                    else{
                        Product product=new Product(Id,Name1,Price1,"2000",Quan,img1,category1);
                        data.UpdateProduct(Id,product);
                        if(img1!=null)
                        {
                            uploadPicture(randomKey);
                            Intent intent=new Intent(DetaiProActivity.this, MainadminActivity.class);
                            intent.putExtra("tab","1");
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(DetaiProActivity.this,"you need to import pictures",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            BtnPre1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(DetaiProActivity.this, MainadminActivity.class);
                    intent.putExtra("tab","1");
                    startActivity(intent);
                }
            });
            data.GetOneProduct(Id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map singleValue=(Map)snapshot.getValue();
                    String Name=(String) singleValue.get("name");
                    Category Category=snapshot.child("category").getValue(Category.class);
                    String Price=(String)singleValue.get("price");
                    String Quantity=(String)singleValue.get("quantity");
                    String Image=(String)singleValue.get("image");
                    img1=Image;
                    CateId=Integer.parseInt(Category.getId());
                    name.setText(Name);
                    price.setText(Price);
                    quanlity.setText(Quantity);
                    spnCategory.setSelection(CateId);
                    loadimage(Image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name.setEnabled(true);
                    price.setEnabled(true);
                    quanlity.setEnabled(true);
                    spnCategory.setEnabled(true);
                    btn.setEnabled(true);
                    btn.setVisibility(View.VISIBLE);
                }
            });
            chooseimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseImage();
                }
            });
        }

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