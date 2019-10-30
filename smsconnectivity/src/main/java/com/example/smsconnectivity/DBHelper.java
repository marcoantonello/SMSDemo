package com.example.smsconnectivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DBNAME="SMSBD";
    public static final String TABLE_NAME="smstable";
    public static final String ID="_id";
    public static final String FIELD_NUMBER="number";
    public static final String FIELD_TEXT="text";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String q="CREATE TABLE "+TABLE_NAME+
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_NUMBER+" TEXT," +
                FIELD_TEXT+" TEXT)";
        db.execSQL(q);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { }
}

