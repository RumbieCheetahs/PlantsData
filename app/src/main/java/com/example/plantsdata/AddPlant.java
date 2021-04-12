package com.example.plantsdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.TreeImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPlant extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mImageView;
    TextView dateTime;
    EditText treeName;
    String date_n;

    DatabaseHandler mDatabaseHandler;
    private Bitmap mCaptureImage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

        dateTime = findViewById(R.id.dateTime);
        dateTime.setText(date_n);


        mImageView = findViewById(R.id.treeImage);

        treeName = findViewById(R.id.treeName);
        mDatabaseHandler = new DatabaseHandler(this);
    }

    public void next(View view){
        storeImage();
    }

    public void newTree(View view) {
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

    public void storeImage() {
        try {
            if (mImageView.getDrawable() != null && !treeName.getText().toString().isEmpty() ){

                // TODO submit data to database;
                mDatabaseHandler.storePlant(new TreeImage(mCaptureImage ,treeName.getText().toString(), date_n));
                startActivity(new Intent(AddPlant.this, AddPlantStatus.class));
            } else {
                Toast.makeText(this, "Please add image and Image name", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
