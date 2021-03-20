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
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.LeafImage;

import java.io.IOException;

public class AddPlantHealth extends AppCompatActivity {

    int number = 0;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mImageView;
    Uri imageUri;
    private DatabaseHandler mDatabaseHandler;
    private Bitmap mCaptureImage;
    long total;

    EditText plantHeight, plantCircumference, insectTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_health);

        mImageView = findViewById(R.id.treeImage);
        plantHeight = findViewById(R.id.plantHeight);
        plantCircumference = findViewById(R.id.plantCircumference);
        insectTypes = findViewById(R.id.insectTypes);

        mDatabaseHandler = new DatabaseHandler(this);
        total = mDatabaseHandler.getProfilesCount();
    }

    public void next(View view) {
        storeImage();
    }

    public void increaseInteger(View view) {
        number += 1;
        display(number);

    }

    public void decreaseInteger(View view) {
        if (number > 0) {
            number -= 1;
            display(number);
        }
    }

    private void display(int number) {
        TextView displayInteger = findViewById(
                R.id.display);
        displayInteger.setText("" + number);
    }

    private void openCamera() {
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
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            mCaptureImage = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(mCaptureImage);
        }
    }

    public void addLeaf(View view) {
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
            if (mImageView.getDrawable() != null && !plantHeight.getText().toString().isEmpty() &&
                    !plantCircumference.getText().toString().isEmpty() && number != 0){

                double height = Double.parseDouble(plantHeight.getText().toString());
                double circumference = Double.parseDouble(plantCircumference.getText().toString());

                // TODO submit data to database;
                mDatabaseHandler.leafImage(new LeafImage(mCaptureImage ,height, circumference, number, insectTypes.getText().toString(), (int) total));
                startActivity(new Intent(AddPlantHealth.this, AddPlantStatus.class));
            } else {
                Toast.makeText(this, "Please add leaf image and other requirements.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
