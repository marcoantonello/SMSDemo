package com.example.smsapp;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity{

   EditText txt_message;
   EditText txt_phone_number;
   boolean mIsBound;
   private ServiceConnection mConnection;
   private SmsServer mBoundService;
   static final int PERMISSION_READ_SMS=0;
   static final int PERMISSION_SEND_SMS=1;
   static final int PERMISSION_RECEIVE_SMS=2;
   boolean havePermission;
   Context context;


    //////////////////////////////LIFE CYCLE///////////////////////////////////
    ///@Override OCCHIO
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_SMS:
            case PERMISSION_RECEIVE_SMS:
            case PERMISSION_SEND_SMS: {
                if (grantResults.length >0 || grantResults[0] == PackageManager.PERMISSION_GRANTED)
                havePermission=true;

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try{ Toast.makeText(this, (String)savedInstanceState.get("come"), Toast.LENGTH_LONG).show(); }
       catch (Exception e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        havePermission=true;
        context=getApplicationContext();

        //if the service isn't running make it run
        if(!isMyServiceRunning(SmsServer.class)) {
            checkPermission();
            if (havePermission)
                startService(new Intent(context, SmsServer.class));
        }

        txt_message = (EditText) findViewById(R.id.txt_message);
        txt_phone_number = (EditText) findViewById(R.id.txt_phone_number);


        mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has
                // been established, giving us the service object we can use
                // to interact with the service.  Because we have bound to a
                // explicit service that we know is running in our own
                // process, we can cast its IBinder to a concrete class and
                // directly access it.
                mBoundService = ((SmsServer.SmsBinder)service).getService();

                // Tell the user about this for our demo.
                Toast.makeText(MainActivity.this, "connected to service", Toast.LENGTH_SHORT).show();
            }

            public void onServiceDisconnected(ComponentName className) {
                // This is called when the connection with the service has
                // been unexpectedly disconnected -- that is, its process
                // crashed. Because it is running in our same process, we
                // should never see this happen.
                mBoundService = null;
                Toast.makeText(MainActivity.this, "service disconnected", Toast.LENGTH_SHORT).show();
            }
        };

        //make a connection
        doBindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
    ///////////////////////////////BIND SERVICE//////////////////////////////////////////////////
    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation
        // that we know will be running in our own process (and thus
        // won't be supporting component replacement by other
        // applications).
        bindService(new Intent(this, SmsServer.class),
                mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }
    //////////////////////////////////////////////ACTIONS///////////////////////////////////////////////////////////////


    public void btn_send(View view) {
        MyMessage();
    }

    ////////////////////////////////////////////////////UTILITY////////////////////////////////////////////////////////////////////

    private void MyMessage() {
        String phoneNumber = txt_phone_number.getText().toString().trim();
        String Message = txt_message.getText().toString().trim();

        if((!phoneNumber.equals("") && !Message.equals(""))) {

            mBoundService.sendDataSms(phoneNumber, null, Message.getBytes(), null, null);

            Toast.makeText(this, "Messagio inviato", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, "Svejate fora, manca il numero o il messaggio", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    private boolean checkPermission()
    {
        //devo farmi ritornare qualcosa per capire
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            havePermission=false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},PERMISSION_SEND_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            havePermission=false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},PERMISSION_READ_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            havePermission=false;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},PERMISSION_RECEIVE_SMS);
        }

        return true;
    }


}
