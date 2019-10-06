package com.mycity.mycityadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class changeslides extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6;
    ImageView slideimg1,slideimg2,slideimg3,slideimg4,slideimg5,slideimg6;
    public static final int REQUEST_CODE = 1234;
    public static final String ST_PATH = "image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeslides);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);

        slideimg1=findViewById(R.id.slide_img1);
        slideimg2=findViewById(R.id.slide_img2);
        slideimg3=findViewById(R.id.slide_img3);
        slideimg4=findViewById(R.id.slide_img4);
        slideimg5=findViewById(R.id.slide_img5);
        slideimg6=findViewById(R.id.slide_img6);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Lost");
        ClickMethod();
    }

    public void ClickMethod() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && imageReturnedIntent != null && imageReturnedIntent.getData() != null) {
           Uri imgUri = imageReturnedIntent.getData();

            try {

                UploadImage();
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                //object_image.setImageBitmap(bm);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public  void  UploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(you_lost.this);
        progressDialog.setTitle("Uploading and saving details");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ref = mStorageRef.child(ST_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
        uploadTask = ref.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String objectname = object_name.getText().toString();
                String objectdesc = object_desc.getText().toString();
                String fcity = city.getText().toString();
                String fcountry = country.getText().toString();
                String flandmark = landmark.getText().toString();
                String imageurl = taskSnapshot.getDownloadUrl().toString();
                String id = mDatabase.push().getKey();
                String contact_details = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                found_objects fo = new found_objects(objectname, objectdesc, fcity, flandmark, fcountry, imageurl, contact_details);
                mDatabase.child(id).setValue(fo);
                progressDialog.dismiss();
                SharedPreferences.Editor editor;  editor = you_lost.sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(you_lost.this, got.class));
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(you_lost.this, "Not Saved successfully!" + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        if(taskSnapshot.getTotalByteCount()==0) {
                            Toast.makeText(you_lost.this, "Select photo again", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    }
                });
    }
    }
}
