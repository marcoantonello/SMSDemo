package com.example.smsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

   EditText txt_message;
   EditText txt_phone_number;
   FirstLevelSmsHandler flHandler;
   boolean canSend,canReceive,canRead;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       canReceive=false;
       canReceive=false;
       canSend=false;



       txt_message = (EditText) findViewById(R.id.txt_message);
       txt_phone_number = (EditText) findViewById(R.id.txt_phone_number);

       flHandler= new FirstLevelSmsHandler(this);
   }

    public void btn_send(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
            canSend=true;
            MyMessage();
        }

        else {
            canSend=false;
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},3);
        }
    }

    public void btn_receive(View view) {
        PDU pdu=null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)== PackageManager.PERMISSION_GRANTED)
        {
            canReceive=true;
        }
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS},0);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)== PackageManager.PERMISSION_GRANTED)
        {
            canRead=true;
        }
        else
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS},1);

        if(canReceive&&canRead)
        {
            pdu=flHandler.getPDU();
            if(pdu!=null)
            {
                EditText txtRecived=findViewById(R.id.txt_message2);
                txtRecived.setText("num "+pdu.getOriginatingAddress()+"\n testo "+pdu.getUserData());
            }
        }
    }

    private void MyMessage() {
        String phoneNumber = txt_phone_number.getText().toString().trim();
        String message = txt_message.getText().toString().trim();

        if((!phoneNumber.equals("") && !message.equals(""))) {

            if (phoneNumber.length()>16) {
                Toast.makeText(this, "Phone number has more than 16 digits", Toast.LENGTH_SHORT).show();
                return;
            }

            if (message.length()>160) {
                Toast.makeText(this, "Message too long", Toast.LENGTH_SHORT).show();
                return;
            }

            flHandler.passPDU(new PDU(phoneNumber,message));

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, "Either phone number or message body is missing", Toast.LENGTH_SHORT).show();
        }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;

            case 0:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canReceive=true;
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    canRead=true;
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }
                break;
        }
   }
}
