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
    /*
    *@param phoneNumber not null, text not null
    * @return true if correctly saved else false
    *@modify save a [id_autoincrement,phoneNumber,text] tuple on database
     */
    public boolean save(String phoneNumber, String text)
    {
        //retrieve the db in write mode
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        //prepare the tuple's values
        ContentValues cv=new ContentValues();
        cv.put(DBHelper.FIELD_NUMBER, phoneNumber);
        cv.put(DBHelper.FIELD_TEXT, text);
        try
        {
            //try insert in db
            db.insert(DBHelper.TABLE_NAME, null,cv);
        }
        catch (SQLiteException sqle)
        {
           return false;
        }
        return true;
    }
    /*
    *@param id of the tuple non negative
    * @return true if is deleted correctly else false
    * @modify delete the tuple with specified id if present
     */
    public boolean delete(long id)
    {
        //retrieve db in write mode
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try
        {
            //delte the element that matches with ID=param id
            if (db.delete(DBHelper.TABLE_NAME, DBHelper.ID+" = "+id, new String[]{Long.toString(id)})>0)
                return true;
            return false;
        }
        catch (SQLiteException sqle)
        {
            return false;
        }
    }
    /*
    *@return a cursor to navigate the whole db content maybe null if query fails
     */
    public Cursor query()
    {
        Cursor crs=null;
        try
        {
            //retrieve db in read mode
            SQLiteDatabase db=dbhelper.getReadableDatabase();
            //retrieve the cursor
            crs=db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null, null);
        }
        catch(SQLiteException sqle)
        {
            return null;
        }
        return crs;
    }
    /*
    *@return true if it's all deleted successfully else false
    *@modify delete all the tuples from sms storage table
     */
    public boolean deleteAll()
    {

        try
        {
            //retrieve db in write mode
            SQLiteDatabase db=dbhelper.getWritableDatabase();
            //execute delete all
            db.execSQL("delete from "+DBHelper.TABLE_NAME);
        }
        catch(SQLiteException sqle)
        {
            return false;
        }
        return true;
    }
}
