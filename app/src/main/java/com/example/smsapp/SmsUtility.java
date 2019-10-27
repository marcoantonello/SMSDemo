package com.example.smsapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class SmsUtility {
    SmsManager smsMan;
    private BroadcastReceiver brdcReciver;
    SMSListener listener;


    public SmsUtility(Context context, SMSListener listener)
    {
        smsMan=SmsManager.getDefault();
        this.listener=listener;

        brdcReciver= new SMSReceiver(listener);

        context.registerReceiver(brdcReciver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }

    public void sendData(String destinationAddress, String scAddress, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent)
    {

        smsMan.sendTextMessage(destinationAddress,scAddress,textiseData(data),sentIntent,deliveryIntent);
    }

    private String textiseData(byte[] data)
    {
        //forse dovrebbe fare altre cose
        return new String(data);
    }



    public ArrayList<String> divideMessage (String text)//da cambiare
    {
        return smsMan.divideMessage(text);
    }}
