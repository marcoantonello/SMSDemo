package com.example.smsapp;

import android.telephony.SmsMessage;

public interface SMSListener {
    void onSMSReceived(SmsMessage message);
}
