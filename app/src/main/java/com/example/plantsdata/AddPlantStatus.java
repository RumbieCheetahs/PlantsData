package com.example.plantsdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.SoilImage;

import java.io.IOException;
import java.util.UUID;

public class AddPlantStatus extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    final int REQUEST_PERMISSION_CODE = 10002;
    ImageView mImageView;
    Uri imageUri;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private DatabaseHandler mDatabaseHandler;
    String status, hasFruit, hasFlower, hasLeaves;
    private Bitmap mCaptureImage;

    ImageButton start, stop, play, stopPlaying;
    String pathSave = "";
    MediaRecorder mMediaRecorder;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_status);

        ///////////////////////////////////////////////////////// audio

        if (!checkPermissionFromDevice())
            requestPermission();

        start = findViewById(R.id.mic);
        stop = findViewById(R.id.play);
        play = findViewById(R.id.stop);
        stopPlaying = findViewById(R.id.stopPlaying);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissionFromDevice()){
                    pathSave = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/" + UUID.randomUUID().toString() + "_audio_record.3gp";
                    setupMediaRecorder();
                    try {
                        mMediaRecorder.prepare();
                        mMediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    play.setEnabled(false);
                    stopPlaying.setEnabled(false);
                    startChronometer();
                    Toast.makeText(AddPlantStatus.this, "Recording...", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermission();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaRecorder.stop();
                stopPlaying.setEnabled(false);
                stop.setEnabled(false);
                start.setEnabled(true);
                play.setEnabled(true);
                pauseChronometer();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                stopPlaying.setEnabled(true);
                play.setEnabled(false);
                stop.setEnabled(false);

                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.setDataSource(pathSave);
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mMediaPlayer.start();
            }
        });

        stopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(true);
                stop.setEnabled(false);
                stopPlaying.setEnabled(false);
                play.setEnabled(true);

                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    setupMediaRecorder();
                }
            }
        });

        //////////////////////////////////////////////////////////

        mImageView = findViewById(R.id.treeImage);

        Spinner spinner = findViewById(R.id.plantStatusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.plantStatus, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // chronometer
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        mDatabaseHandler = new DatabaseHandler(this);

    }

    public void next(View view){
        getCheckedCheckbox();
        resetChronometer();
        storeImage();
        startActivity(new Intent(AddPlantStatus.this, AddPlantNeighbors.class));
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
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

    public void addSoil(View view) {
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

    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mic:
                startChronometer();
                break;
            case R.id.play:
                break;
            case R.id.stop:
                pauseChronometer();
                break;
        }
    }

    public void storeImage() {
        try {
            if (mImageView.getDrawable() != null){

                // TODO submit data to database;
                mDatabaseHandler.soilImage(new SoilImage(mCaptureImage, status, hasFruit, hasFlower, hasLeaves));
            } else {
                Toast.makeText(this, "Please add image and Image name", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getCheckedCheckbox() {
        CheckBox fruit = findViewById(R.id.fruit);
        CheckBox leaves = findViewById(R.id.leaves);
        CheckBox flowers = findViewById(R.id.flower);

        if (fruit.isChecked()) {
            hasFruit = "has fruits";
        } else {
            hasFruit = " has no fruit";
        }

        if (leaves.isChecked()) {
            hasLeaves = "has leaves";
        } else {
            hasLeaves = "has no leaves";
        }

        if (flowers.isChecked()) {
            hasFlower = "has flowers";
        } else {
            hasFlower = "has no flower";
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    private void setupMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setOutputFile(pathSave);
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
