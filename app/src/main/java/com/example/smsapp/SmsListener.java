package com.example.smsapp;

import android.telephony.SmsMessage;

public interface SmsListener {
    public void handleSmS(SmsMessage sms);
}
