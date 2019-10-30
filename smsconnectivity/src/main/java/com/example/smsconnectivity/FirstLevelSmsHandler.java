package com.example.smsconnectivity;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FirstLevelSmsHandler implements SmsListener{

    SmsManager smsMan;
    static final int USER_DATA_LENGHT=160;
    private static SmsListener defaultListener=null;
    Context appContext;


    public FirstLevelSmsHandler(Context context)
    {
        appContext=context;
        smsMan=SmsManager.getDefault();
        if(defaultListener==null)//non so se funzia
            defaultListener=this;
    }

    public static SmsListener getDefaultLitener()
    {
        return  defaultListener;
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

    public PDU getPDU(int key)
    {
        FileInputStream fileInputStream=null;
        PDU sms=null;
        try{fileInputStream = appContext.openFileInput("data.dat");}
        catch(FileNotFoundException e){ Toast.makeText(appContext,"file not found",Toast.LENGTH_SHORT); }

        if(fileInputStream!=null)
        {
            ObjectInputStream is=null;
            try{ is = new ObjectInputStream(fileInputStream);}
            catch(IOException e){  }

            if(is!=null)
            {
                try {
                    sms = (PDU) is.readObject();
                } catch (IOException e) {
                } catch (ClassNotFoundException e) {
                }

                try {
                    is.close();
                } catch (IOException e) {
                }
            }

            try{ fileInputStream.close();}
            catch(IOException e){  }
        }
        return sms;
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
        FileOutputStream fos=null;

        try{ fos= appContext.openFileOutput("data.dat", Context.MODE_PRIVATE);}
        catch(IOException e){  }

        if(fos!=null) {
            ObjectOutputStream os=null;
            try{ os = new ObjectOutputStream(fos);}
            catch(IOException e){  }

            if(os!=null) {
                try {
                    os.writeObject(this);
                } catch (IOException e) {
                }

                try {
                    os.close();
                } catch (IOException e) {
                }
            }

            try{fos.close();}
            catch(IOException e){  }
        }
    }
}
