package com.example.smsapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SmsServer extends Service implements SMSListener{
    SmsUtility smsUtility;
    ArrayList<Intent> list;
    private final IBinder mBinder = new SmsBinder();

    ///////////////////////////////SmsListener///////////////////////////////
    @Override
    public void onSMSReceived(SmsMessage message) {
        Toast.makeText(getApplicationContext(),"messaggio da "+message.getOriginatingAddress(),Toast.LENGTH_LONG).show();
    }
    ////////////////////////////////Service///////////////////////////////////
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        smsUtility=new SmsUtility(getApplicationContext(),this );
        Toast.makeText(getApplicationContext(), "Service created!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service stopped", Toast.LENGTH_LONG).show();
        //save apps list&data on a file on disk
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(getApplicationContext(), "Service started by user.", Toast.LENGTH_LONG).show();
    }
    //////////////////////////////////////UTILITY/////////////////////////////////////////


    ///////////////////////////////////////////////////////METHODS//////////////////////////////////////
    public void sendDataSms(String destinationAddress, String scAddress, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        smsUtility.sendData(destinationAddress, scAddress, data, sentIntent, deliveryIntent);
    }


    //#########################################################SmsBinder#################################################
    public class SmsBinder extends Binder {
        SmsServer getService() {
            return SmsServer.this;
        }
    }
}
