package com.example.smsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements OnSmsReceivedListener {

   EditText txt_message;
   EditText txt_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1);
        RicezioneSMS.listener=this;
        txt_message = (EditText) findViewById(R.id.txt_message);
        txt_phone_number = (EditText) findViewById(R.id.txt_phone_number);

    }

    public void btn_send(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (permissionCheck== PackageManager.PERMISSION_GRANTED) {
            MyMessage();
        }

        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},0);
        }
    }

    private void MyMessage() {
        String phoneNumber = txt_phone_number.getText().toString().trim();
        String Message = txt_message.getText().toString().trim();

        if((!phoneNumber.equals("") && !Message.equals(""))) {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, Message, null, null);

            Toast.makeText(this, "Messagio inviato", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this, "Svejate fora, manca il numero o il messaggio", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else{
                    Toast.makeText(this, "Non hai i permessi", Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void onSmsReceived(String nTelefono, String testo) {
        Log.d("Main Activity", "Qui");
        Toast.makeText(getApplicationContext(), nTelefono+" ti ha mandato: "+ testo, Toast.LENGTH_LONG).show();
    }
}
