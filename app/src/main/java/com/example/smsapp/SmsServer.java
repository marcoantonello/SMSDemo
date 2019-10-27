package com.example.smsapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
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
    static final int PERMISSION_READ_SMS=0;
    static final int PERMISSION_SEND_SMS=1;
    static final int PERMISSION_RECEIVE_SMS=2;
    SmsUtility smsUtility;
    ArrayList<Intent> list;
    private final IBinder mBinder = new SmsBinder();

    ///////////////////////////////SmsListener///////////////////////////////
    @Override
    public void onSMSReceived(SmsMessage message) {
        //do things
        startActivity(list.get(0).putExtra("come","bene"));
    }
    ////////////////////////////////Service///////////////////////////////////
    @Override
    public IBinder onBind(Intent intent) {
        //register the app
        list.add(intent);
        return mBinder;
    }

    ///@Override OCCHIO
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_SMS:
            case PERMISSION_RECEIVE_SMS:
            case PERMISSION_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
              //  if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED)
                    int a =5;

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onCreate() {
        //check for permission sending/reciving sms,permission request
        if(checkPermission()) {


            //make a SmsUtility
            smsUtility=new SmsUtility(this,this );

                    //save the sms
                    //if the app is wakeonsms //check awakeness // awake pass sms data
                    //else save data for future request
        }


            Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
        //save apps list&data on a file on disk
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }
    //////////////////////////////////////UTILITY/////////////////////////////////////////
    private boolean checkPermission()
    {
        //devo farmi ritornare qualcosa per capire
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(new Activity() ,new String[]{Manifest.permission.SEND_SMS},PERMISSION_SEND_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(new Activity() ,new String[]{Manifest.permission.READ_SMS},PERMISSION_READ_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(new Activity() ,new String[]{Manifest.permission.READ_SMS},PERMISSION_RECEIVE_SMS);
        }

        return true;
    }

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
