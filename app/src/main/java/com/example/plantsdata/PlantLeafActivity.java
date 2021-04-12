package com.example.plantsdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.LeafImage;

public class PlantLeafActivity extends AppCompatActivity {

    private ImageView mImageView;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private Bitmap mCaptureImage;
    private DatabaseHandler mDatabaseHandler;

    long total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_leaf);


        mImageView = findViewById(R.id.treeImage);

        mDatabaseHandler = new DatabaseHandler(this);

        total = mDatabaseHandler.getProfilesCount();
    }

    public void next(View view) {
        storeImage();
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

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
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
            if (mImageView.getDrawable() != null){

//                double height = Double.parseDouble(plantHeight.getText().toString());
//                double circumference = Double.parseDouble(plantCircumference.getText().toString());

                // TODO submit data to database;
                mDatabaseHandler.leafImage(new LeafImage(mCaptureImage, (int) total));
                startActivity(new Intent(PlantLeafActivity.this, AddPlantSoil.class));
            } else {
                Toast.makeText(this, "Please add leaf image and other requirements.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}