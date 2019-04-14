package com.example.zero.scratchiot.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "dataSensor";
    private static final String COL1 = "ID";
    private static final String COL2 = "server";
    private static final String COL3 = "sensor1";
    private static final String COL4 = "sensor2";
    private static final String COL5 = "sensor3";
    private static final String COL6 = "sensor4";
    private static final String COL7 = "tanggal";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " TEXT, " +
                    COL3 + " TEXT, " +
                    COL4 + " TEXT, " +
                    COL5 + " TEXT, " +
                    COL6 + " TEXT, " +
                    COL7 + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ID_server, String sensor1, String sensor2, String sensor3, String sensor4, String tanggal) {

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, ID_server); //add idServer
        contentValues.put(COL3, sensor1); //add sensor1
        contentValues.put(COL4, sensor2); //add sensor2
        contentValues.put(COL5, sensor3); //add sensor3
        contentValues.put(COL6, sensor4); //add sensor4
        contentValues.put(COL7, tanggal); //add tanggalCreateDataServer

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getDataAt(String dateAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+
                        " where tanggal = ?",
                new String[]{dateAt});
        return res;
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }

    public String latest( String column_name) {// use the data type of the column
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"MAX(" +column_name + ") AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst(); // to move the cursor to first record
        int index = cursor.getColumnIndex("MAX");
        String data = cursor.getString(index);// use the data type of the column or use String itself you can parse it

        return data;
    }

    public Integer latestInt( String column_name) {// use the data type of the column
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"MAX(" +column_name + ") AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst(); // to move the cursor to first record
        int index = cursor.getColumnIndex("MAX");
        int data = cursor.getInt(index);// use the data type of the column or use String itself you can parse it

        return data;
    }


}
