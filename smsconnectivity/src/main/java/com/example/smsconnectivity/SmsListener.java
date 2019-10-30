package com.example.smsconnectivity;

import android.telephony.SmsMessage;

public interface SmsListener {
    public void handleSmS(SmsMessage sms);
}
