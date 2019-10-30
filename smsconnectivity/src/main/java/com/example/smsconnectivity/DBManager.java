package com.example.smsconnectivity;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBManager
{
    private DBHelper dbhelper;
    public DBManager(Context ctx)
    {
        dbhelper=new DBHelper(ctx);
    }
    public void save(String phoneNumber, String text)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DBHelper.FIELD_NUMBER, phoneNumber);
        cv.put(DBHelper.FIELD_TEXT, text);
        try
        {
            db.insert(DBHelper.TABLE_NAME, null,cv);
        }
        catch (SQLiteException sqle)
        {
// Gestione delle eccezioni
        }
    }
    public boolean delete(long id)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            if (db.delete(DBHelper.TABLE_NAME, DBHelper.ID+" = "+id, new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }
    }
    public Cursor query()
    {
        Cursor crs=null;
        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            crs=db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null, null);
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return crs;
    }
    public boolean deleteAll()
    {

        try
        {
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            db.execSQL("delete from "+DBHelper.TABLE_NAME);//sarebbe da mettere dei segnapostiper i nomi dei campi e delle tabelle
        }
        catch(SQLiteException sqle)
        {
            return false;
        }
        return true;
    }
}
