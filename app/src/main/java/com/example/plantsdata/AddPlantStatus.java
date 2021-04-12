package com.example.plantsdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantsdata.model.DatabaseHandler;
import com.example.plantsdata.model.PlantStatus;
import com.example.plantsdata.model.SoilImage;

public class AddPlantStatus extends AppCompatActivity {

    int number = 0;

    private DatabaseHandler mDatabaseHandler;
    long total;

    private EditText plantHeight, plantCircumference, insectTypes;


    private String status, hasFruit, hasFlower, hasLeaves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_status);


        plantHeight = findViewById(R.id.plantHeight);
        plantCircumference = findViewById(R.id.plantCircumference);
        insectTypes = findViewById(R.id.insectTypes);

        mDatabaseHandler = new DatabaseHandler(this);
        total = mDatabaseHandler.getProfilesCount();

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

    public void next(View view) {
        getCheckedCheckbox();
        storeData();
    }

    private void storeData() {
        try {
            if (!plantHeight.getText().toString().isEmpty() &&
                    !plantCircumference.getText().toString().isEmpty() && number != 0){

                double height = Double.parseDouble(plantHeight.getText().toString());
                double circumference = Double.parseDouble(plantCircumference.getText().toString());
                String insects = insectTypes.getText().toString();
                // TODO submit data to database;
                mDatabaseHandler.plantStatus(new PlantStatus((int) total, height, circumference, number, insects, status, hasFruit, hasFlower, hasLeaves));
                startActivity(new Intent(AddPlantStatus.this, PlantLeafActivity.class));
            } else {
                Toast.makeText(this, "Please add information.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
