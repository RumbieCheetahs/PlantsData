package com.example.plantsdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.PersonImage;

public class AddPersonDetails extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mImageView;
    Uri imageUri;
    private Bitmap imageToStore;
    EditText personImage;
    private DatabaseHandler mDatabaseHandler;
    long total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_details);

        mImageView = findViewById(R.id.treeImage);
        personImage = findViewById(R.id.neighbour);
        mDatabaseHandler = new DatabaseHandler(this);

        total = mDatabaseHandler.getProfilesCount() - 1;
    }

    public void next(View view){
        storeImage();
    }

    private void openCamera() {
        Intent objectIntent = new Intent();
        objectIntent.setType("image/*");
        objectIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(objectIntent, IMAGE_CAPTURE_CODE);
    }
    private void openCamera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                mImageView.setImageBitmap(imageToStore);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addPerson(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                // permission granted
                openCamera();
            }
        } else {
            // system OS less than marshmallow
            openCamera();
        }
    }

    public void storeImage() {
        try {
            if (mImageView.getDrawable() != null && !personImage.getText().toString().isEmpty() && imageToStore != null){

                // TODO submit data to database;
                mDatabaseHandler.personImage(new PersonImage(imageToStore, personImage.getText().toString(), (int) total));
                startActivity(new Intent(AddPersonDetails.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Please add image and Image name", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addPersonCamera(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                // permission granted
                openCamera2();
            }
        } else {
            // system OS less than marshmallow
            openCamera2();
        }
    }
}
