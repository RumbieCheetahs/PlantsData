package com.example.plantsdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.Neighbour;

public class AddPlantNeighbors extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mImageView;
    EditText name, occurrences;
    private DatabaseHandler mDatabaseHandler;
    private Bitmap mCaptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_neighbors);

        mImageView = findViewById(R.id.treeImage);
        name = findViewById(R.id.neighbour);
        occurrences = findViewById(R.id.occurrence);
        mDatabaseHandler = new DatabaseHandler(this);
    }

    public void next(View view){
        AlertDialog.Builder adb = new AlertDialog.Builder(AddPlantNeighbors.this);
        adb.setTitle("Confirm if last Plant");
        adb.setMessage("Imbwi omuti wombuniko ndji mburi mene yomuti mbwi omunene, tjeri owatjiri tuna owatjiri, nu tjiheri owatjiri tuna okuheri owatjiri");
        adb.setCancelable(false);
        adb.setPositiveButton("li", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeImage();
                startActivity(new Intent(AddPlantNeighbors.this, AddPersonDetails.class));
            }
        });
        adb.setNegativeButton("pokako", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeImage();
                startActivity(new Intent(AddPlantNeighbors.this, AddPlantNeighbors.class));
            }
        });
        adb.show();
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

    public void addNeighbour(View view) {
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
            if (mImageView.getDrawable() != null && !name.getText().toString().isEmpty() ){
                int occur = Integer.parseInt(occurrences.getText().toString().trim());

                // TODO submit data to database;
                mDatabaseHandler.neighborImage(new Neighbour(mCaptureImage, name.getText().toString(),occur));
            } else {
                Toast.makeText(this, "Please add image and Image name", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
