package com.example.plantsdata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.SoilImage;

public class AddPlantSoil extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private GpsTracker gpsTracker;
    private TextView tvLatitude,tvLongitude;
    private double mLatitude;
    private double mLongitude;

    private DatabaseHandler mDatabaseHandler;
    long total;

    private ImageView mImageView;
    private Bitmap mCaptureImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_soil);

        tvLatitude = (TextView)findViewById(R.id.latitude);
        tvLongitude = (TextView)findViewById(R.id.longitude);

        mDatabaseHandler = new DatabaseHandler(this);
        total = mDatabaseHandler.getProfilesCount();

        mImageView = findViewById(R.id.treeImage);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getLocation(View view) {
        gpsTracker = new GpsTracker(AddPlantSoil.this);
        if(gpsTracker.canGetLocation()){
            mLatitude = gpsTracker.getLatitude();
            mLongitude = gpsTracker.getLongitude();
            tvLatitude.setText(String.valueOf(mLatitude));
            tvLongitude.setText(String.valueOf(mLongitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void next(View view) {
        storeImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            mCaptureImage = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(mCaptureImage);
        }
    }

    public void addSoil(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void storeImage() {
        try {
            if (mImageView.getDrawable() != null){

                // TODO submit data to database;
                mDatabaseHandler.soilImage(new SoilImage(mCaptureImage, mLatitude, mLongitude, (int) total));
                startActivity(new Intent(AddPlantSoil.this, AddPlantNeighbors.class));
            } else {
                Toast.makeText(this, "Please add image and Image name", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}