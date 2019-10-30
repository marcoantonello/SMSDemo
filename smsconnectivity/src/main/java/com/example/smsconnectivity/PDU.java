package com.example.smsconnectivity;

import android.telephony.SmsMessage;

import java.io.Serializable;

public class PDU implements Serializable {
    String userData;
    String destinationAddress;//split
    String originatingAddress;

    public PDU(SmsMessage sms)
    {
        userData=new String(sms.getMessageBody());
        originatingAddress=sms.getOriginatingAddress();
    }
    public PDU(String destinationAddress,String originatingAddress, String userData)
    {
        this.userData=userData;
        this.destinationAddress=destinationAddress;
        this.originatingAddress=originatingAddress;
    }

    public String getOriginatingAddress() {
        return originatingAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getUserData() {
        return userData;
    }


}
