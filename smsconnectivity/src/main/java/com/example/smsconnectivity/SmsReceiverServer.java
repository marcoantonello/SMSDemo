package com.example.smsconnectivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import androidx.annotation.Nullable;

public class SmsReceiverServer extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        return START_REDELIVER_INTENT;//is the os kill it he retry handling the command
    }
}
