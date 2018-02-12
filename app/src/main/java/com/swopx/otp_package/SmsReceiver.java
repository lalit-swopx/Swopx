package com.swopx.otp_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.swopx.utils.Constant;

/**
 * Created by Office Work on 27-11-2017.
 */

public class SmsReceiver extends BroadcastReceiver {
    Boolean b;
    String abcd,xyz;

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.
            String messageBody = smsMessage.getMessageBody();
            //Pass on the text to our listener.
//            mListener.messageReceived(messageBody);
            if(messageBody.contains("SWOPX. Your registration OTP is:"))
            {
                abcd=messageBody.replaceAll("[^0-9]","");   // here abcd contains otp
                String desiredString = abcd.substring(0,6);
                Constant.Log("SMS REceives======",""+desiredString);
                mListener.messageReceived(desiredString);
            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
