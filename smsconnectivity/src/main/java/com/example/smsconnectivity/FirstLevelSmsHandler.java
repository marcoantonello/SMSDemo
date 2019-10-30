package com.example.smsconnectivity;

import android.content.Context;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import java.util.ArrayList;


public class FirstLevelSmsHandler implements SmsListener{

    SmsManager smsMan;
    static final int USER_DATA_LENGHT=160;
    Context appContext;
    private DBManager db;



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
    /*
    *@return all the unretrieved messages null if there aren't
    *@modify delete all the messages
     */
    public ArrayList<PDU> getPDUS()//int key)
    {
        ArrayList<PDU> pdus=new ArrayList<>();
        Cursor cursor=db.query();

        while(cursor.moveToNext())//cursor seek the frst element on the first call//return true if move action succeeded
        {
            //column index in the tuple
            int colIndex;
            //retrieve column index from the field name
            colIndex = cursor.getColumnIndexOrThrow(DBHelper.FIELD_NUMBER);
            //retrieve the value
            String PhoneNumber = cursor.getString(colIndex);

            colIndex = cursor.getColumnIndexOrThrow(DBHelper.FIELD_TEXT);
            String text = cursor.getString(colIndex);

            pdus.add(new PDU(null,PhoneNumber,text));
        }
        db.deleteAll();//delete all messages
        if(pdus.isEmpty())
            return null;
        return pdus;
    }
    /*
    *@param message to send not null
    * @return send the sms,false if there was some problems
     */
    public boolean passPDU(PDU pdu)
    {
        //control if address is numeric only and not null or empty
        //control if text is not null
        if(pdu==null||pdu.getDestinationAddress()==null||pdu.getDestinationAddress()==""||pdu.getDestinationAddress().matches("[0-9]+")||pdu.getUserData()==null)
            return false;
        sendSms(pdu.getDestinationAddress(),null, pdu.getUserData());
        return true;
    }
    /*
     *wrapper of API same specifications
     */
    private void sendSms(String destinationAddress,String scAddress,String text)
    {
        smsMan.sendTextMessage(destinationAddress,scAddress,text,null,null);
    }

    /*
    *@param sms not null
    * @modify save the sms in db
     */
    @Override
    public void handleSmS(SmsMessage sms) {
        db.save(sms.getDisplayOriginatingAddress(),sms.getMessageBody());//insert sms in database
    }
}
