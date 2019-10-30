package com.example.smsconnectivity;

import android.telephony.SmsMessage;

import java.io.Serializable;

public class PDU implements Serializable {
    String userData;
    String destinationAddress;//split
    String originatingAddress;

    public PDU(SmsMessage sms)
    {
        userData=new String(sms.getUserData());
        originatingAddress=sms.getOriginatingAddress();
    }
    public PDU(String destinationAddress, String userData)
    {
        this.userData=userData;
        this.destinationAddress=destinationAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getUserData() {
        return userData;
    }


}
