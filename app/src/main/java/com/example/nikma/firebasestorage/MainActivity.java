package com.example.nikma.firebasestorage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    Button choose,upload;
    EditText editText;
    ImageView imageView;
    TextView textView;
    Uri imageuri;
    private StorageReference mref;
    private DatabaseReference databaseReference;
    public static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose = findViewById(R.id.choose);
        upload = findViewById(R.id.upload);
        editText = findViewById(R.id.name);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.show);
        mref = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadfile();
            }
        });

    }
        private String getfileextension(Uri uri){
            ContentResolver cr = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
        }

    private void uploadfile() {
        if(imageuri!=null){
                StorageReference filereference = mref.child(System.currentTimeMillis()+"."+getfileextension(imageuri));
                filereference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Upload successful",Toast.LENGTH_LONG).show();
                        Upload upload = new Upload(editText.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                        String uploadID = databaseReference.push().getKey();
                        databaseReference.child(uploadID).setValue(upload);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Upload failed",Toast.LENGTH_LONG).show();
                    }
                });

        }else Toast.makeText(getApplicationContext(),"no file selected",Toast.LENGTH_LONG).show();

    }

    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST  &&resultCode ==RESULT_OK && data !=null &&data.getData()!=null){
            imageuri = data.getData();
            Glide.with(MainActivity.this)
                    .load(imageuri)
                    .into(imageView);


        }
    }
}
