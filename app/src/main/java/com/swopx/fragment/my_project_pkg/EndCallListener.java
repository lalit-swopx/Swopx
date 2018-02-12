package com.swopx.fragment.my_project_pkg;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.swopx.utils.Constant;

/**
 * Created by Office Work on 11-01-2018.
 */

public class EndCallListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        if(TelephonyManager.CALL_STATE_RINGING == state) {
            Constant.Log("CALL_STATE_RINGING", "=======RINGING, number: " + incomingNumber);
        }
        if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
            //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
            Constant.Log("CALL_STATE_OFFHOOK", "=======OFFHOOK");
        }
        if(TelephonyManager.CALL_STATE_IDLE == state) {
            //when this state occurs, and your flag is set, restart your app
            Constant.Log("CALL_STATE_IDLE",  "=======IDLE");
        }
    }
}
