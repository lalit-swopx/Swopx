package com.swopx.otp_package;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.login.Name;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 04-12-2017.
 */

public class HttpService extends IntentService {

    private static String TAG = HttpService.class.getSimpleName();

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
        }
    }

    /**
     * Posting the OTP to server and activating the user
     *
     * @param otp otp received in the SMS
     */
    private void verifyOtp(final String otp) {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("otp", otp);
//            params.put("client_id", CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
            params.put("client_id", CustomPreference.client_id);
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getApplicationContext(), params, Url_Links.Verify_Otp, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
//                    Intent intent = new Intent(getActivity(), Login.class);
//                    startActivity(intent);
//                    getActivity().finish();
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {
                        ((Login_Main) getApplicationContext()).replaceFragment(new Name());
//                        Constant.ToastShort(getApplicationContext(),"Otp verified successful");
//                        Intent intent = new Intent(Verify_OTP.this, Login.class);
//                        startActivity(intent);
//                        finish();
                    }

                    Constant.ToastShort(getApplicationContext(),response.getJSONObject("result").getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(getApplicationContext(), error.getMessage());
            }
        };




    }

}
