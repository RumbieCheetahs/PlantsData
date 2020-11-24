package com.example.plantsdata.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "plant.db";
    private static final int DB_VERSION = 1;

    // Database tables
    private static final String TABLE_ONE = "plant";
    private static final String TABLE_TWO = "leafTable";
    private static final String TABLE_THREE = "soilTable";
    private static final String TABLE_FOUR = "neighbour";
    private static final String TABLE_FIVE = "personDetails";

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    Context context;
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Declare queries
            String query1 = "CREATE TABLE IF NOT EXISTS "+ TABLE_ONE +" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "treeImage BLOB, "+
                    "treeName TEXT, "+
                    "date TEXT);";
            String query2 = "CREATE TABLE IF NOT EXISTS "+ TABLE_TWO +" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "leafImage BLOB, "+
                    "treeHeight DOUBLE, "+
                    "treeCircumference DOUBLE, "+
                    "treeStems INTEGER, "+
                    "insectTypes TEXT);";
            String query3 = "CREATE TABLE IF NOT EXISTS "+ TABLE_THREE +" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "soilImage BLOB, "+
                    "plantStatus TEXT, "+
                    "fruit TEXT, "+
                    "flower TEXT, "+
                    "leaves TEXT, "+
                    "latitude DOUBLE, "+
                    "longtitude DOUBLE);";
            String query4 = "CREATE TABLE IF NOT EXISTS "+ TABLE_FOUR +" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "plant_id INTEGER, "+
                    "neighborImage BLOB, "+
                    "nameOfNeighbour TEXT, "+
                    "occurrences INTEGER);";
            String query5 = "CREATE TABLE IF NOT EXISTS "+ TABLE_FIVE +" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "personImage BLOB, "+
                    "name TEXT);";

            // Run queries
            db.execSQL(query1);
            db.execSQL(query2);
            db.execSQL(query3);
            db.execSQL(query4);
            db.execSQL(query5);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_ONE);
        db.close();
        return count;
    }

    public void storePlant(TreeImage treeImage) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = treeImage.getTreeImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("treeImage", imageInBytes);
            objectContentValues.put("treeName", treeImage.getTreeName());
            objectContentValues.put("date", treeImage.getDate());

            long checkIfQueryRuns = db.insert("plant", null, objectContentValues);
            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
                db.close();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void leafImage(LeafImage leafImage) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = leafImage.getLeafImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("leafImage", imageInBytes);
            objectContentValues.put("treeHeight", leafImage.getTreeHeight());
            objectContentValues.put("treeCircumference", leafImage.getTreeCircumference());
            objectContentValues.put("treeStems", leafImage.getTreeStems());
            objectContentValues.put("insectTypes", leafImage.getInsectTypes());

            long checkIfQueryRuns = db.insert("leafTable", null, objectContentValues);
            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
                db.close();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void soilImage(SoilImage soilImage) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = soilImage.getSoil();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("soilImage", imageInBytes);
            objectContentValues.put("plantStatus", soilImage.getPlantHealth());
            objectContentValues.put("fruit", soilImage.getFruit());
            objectContentValues.put("flower", soilImage.getFlower());
            objectContentValues.put("leaves", soilImage.getLeaves());
            objectContentValues.put("latitude", soilImage.getLatitude());
            objectContentValues.put("longtitude", soilImage.getLongtitude());

            long checkIfQueryRuns = db.insert("soilTable", null, objectContentValues);
            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
                db.close();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void personImage(PersonImage personImage) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = personImage.getPersonImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("personImage", imageInBytes);
            objectContentValues.put("name", personImage.getName());

            long checkIfQueryRuns = db.insert("personDetails", null, objectContentValues);
            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
                db.close();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void neighborImage(Neighbour neighbour) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = neighbour.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("plant_id", neighbour.getPlant_id());
            objectContentValues.put("neighborImage", imageInBytes);
            objectContentValues.put("nameOfNeighbour", neighbour.getName());
            objectContentValues.put("occurrences", neighbour.getOccurrences());

            long checkIfQueryRuns = db.insert("neighbour", null, objectContentValues);
            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added into our table", Toast.LENGTH_SHORT).show();
                db.close();
            } else {
                Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
