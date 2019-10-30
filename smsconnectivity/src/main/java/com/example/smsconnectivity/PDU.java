package com.example.smsconnectivity;

import android.telephony.SmsMessage;

import java.io.Serializable;

public class PDU implements Serializable {
    String userData;
    String destinationAddress;
    String originatingAddress;


    /*
    *@param sms not null
    *@returns build a PDU from sms
     */
    public PDU(SmsMessage sms)
    {
        userData=sms.getMessageBody();
        originatingAddress=sms.getOriginatingAddress();
    }
    /*
    *@param destinationaddress, originating address, userdata not null
    *@returns a PDU build from info supplied
     */
    public PDU(String destinationAddress,String originatingAddress, String userData)
    {
        this.userData=userData;
        this.destinationAddress=destinationAddress;
        this.originatingAddress=originatingAddress;
    }
    /*
     *@returns originatingAddress
     */
    public String getOriginatingAddress() {
        return originatingAddress;
    }

    /*
     *@returns destinationAddress
     */
    public String getDestinationAddress() {
        return destinationAddress;
    }

    /*
     *@returns userData
     */
    public String getUserData() {
        return userData;
    }


}
