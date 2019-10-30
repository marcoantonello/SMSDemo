package com.example.smsconnectivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import androidx.cursoradapter.widget.CursorAdapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FirstLevelSmsHandler implements SmsListener{

    SmsManager smsMan;
    static final int USER_DATA_LENGHT=160;
    Context appContext;
    private DBManager db=null;



    public FirstLevelSmsHandler(Context context)
    {
        appContext=context;
        smsMan=SmsManager.getDefault();

        db=new DBManager(context);
    }

    /*maybe i need those
    public boolean Register(Boolean wakeOnSms, Context context,Class<?> activity) //register the activity
    {
        return true;
    }

    public boolean Unregister(Context context,Class<?> activity)
    {
        return true;
    }



    public void handleSmsRecived(SmsMessage sms)
    {
        boolean a =true;

        //controll sms
        //if it s special register it
    }
    public void sendData(String destinationAddress, String scAddress, String data, PendingIntent sentIntent, PendingIntent deliveryIntent)
    {
        smsMan.sendTextMessage(destinationAddress,scAddress,data,sentIntent,deliveryIntent);//no ack to this class
    }



    /*
    not yet implemented cause difficulty to convert to the 7-bit default alphabet of gms
    public void sendData(String destinationAddress, String scAddress, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent)
    {
        smsMan.sendTextMessage(destinationAddress,scAddress,makeUserData(data),sentIntent,deliveryIntent);//no ack to this class
    }




    *pre receive byte[] data of lenght<USER_DATA_LENGHT
    * post return the text formed by using 7-bit symbols


    private String makeUserData(byte[] data)
    {
        //it might do thing as form header in payload
        return null;
    }


    public static ArrayList<byte[]> divideMessage (byte[] data)
    {
        ArrayList<byte[]> divided=new ArrayList<byte[]>();
        int start=0;
        while(start<=data.length)
        {
            divided.add(copyOfRange(data,start,USER_DATA_LENGHT));
        }
        return divided;
    }

    public static ArrayList<String> divideMessage (String data)
    {
        ArrayList<String> divided=new ArrayList<>();
        int start=0;
        while(start<data.length())
        {
            if(start+USER_DATA_LENGHT<data.length())
                divided.add(data.substring(start,USER_DATA_LENGHT));
            else
                divided.add(data.substring(start,data.length()-1-start));
            start+=USER_DATA_LENGHT;

        }
        return divided;
    }
*/

////////////////NEW

    public ArrayList<PDU> getPDUS()//int key)
    {
        ArrayList<PDU> pdus=new ArrayList<>();
        Cursor cursor=db.query();

        while(cursor.moveToNext())
        {
            int index;

            index = cursor.getColumnIndexOrThrow(DBHelper.FIELD_NUMBER);
            String PhoneNumber = cursor.getString(index);

            index = cursor.getColumnIndexOrThrow(DBHelper.FIELD_TEXT);
            String text = cursor.getString(index);

            pdus.add(new PDU(null,PhoneNumber,text));
        }
        db.deleteAll();//delete all messages
        if(pdus.isEmpty())
            return null;
        return pdus;
    }

    public void passPDU(PDU pdu)
    {
        sendSms(pdu.getDestinationAddress(),null, pdu.getUserData());
    }

    private void sendSms(String destinationAddress,String scAddress,String text)
    {
        smsMan.sendTextMessage(destinationAddress,scAddress,text,null,null);
    }

    @Override
    public void handleSmS(SmsMessage sms) {
        db.save(sms.getDisplayOriginatingAddress(),sms.getMessageBody());//insert sms in database
    }
}
