package com.swopx.fragment.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 24-11-2017.
 */

public class Otp extends Fragment {
    private EditText mobile_no;
    private ImageButton forwrd_btn;
    private int My_Permission_request_SMS_Receive=10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_number_screen, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Login_Main) getActivity()).currentFragment = this;
        ((Login_Main) getActivity()).skip.setVisibility(View.GONE);
        int_view(view);
    }




    private void int_view(View view) {
        mobile_no=(EditText)view.findViewById(R.id.mobile_no);
        forwrd_btn=(ImageButton) view.findViewById(R.id.forwrd_btn);

        forwrd_btn.setEnabled(true);
        forwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    if(Constant.isNetConnected(getActivity()))
                    {
                        Otp();
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                    }
                }

            }
        });
        mobile_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(mobile_no.getText().toString().length()>=1)
                {
//                    forwrd_btn.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
                    }

                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_gray));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Constant.Log("=======OnTextAfter",""+s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Constant.Log("=======OnTextBefore",s+";"+count);
                // TODO Auto-generated method stub
            }


        });
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_CONTACTS},
                    My_Permission_request_SMS_Receive);
//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
//                My_Permission_request_Read);
//            return;
//        }

    }

    private boolean isValid() {
        if(TextUtils.isEmpty(mobile_no.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.mobile_validation));
            return false;
        }
        else if(mobile_no.getText().length()!=10)
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.mobile_10));
            return false;
        }
        return true;
    }


    private void Otp() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("mobile", mobile_no.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Otp_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {

                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("205"))
                    {
                        ((Login_Main) getActivity()).replaceFragment(new Name());
//                        Intent intent = new Intent(getActivity(), Verify_OTP.class);
//                        startActivity(intent);
//                        finish();
                    }
                    else if(response.getJSONObject("result").getString("status").equalsIgnoreCase("204"))
                    {
                        ((Login_Main) getActivity()).replaceFragment(new Verify_OTP());
//                        Constant.ToastShort(getActivity(),"Otp send on your mobile number");
//                        Intent intent = new Intent(Otp.this, Verify_OTP.class);
//                        startActivity(intent);
//                        finish();
                    }
                    else if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {

                        Password fragment = new Password();
                        Bundle bundle = new Bundle();
                        bundle.putString("logIn", "logIn");
                        fragment.setArguments(bundle);
                        ((Login_Main) getActivity()).replaceFragment(fragment);
//                        ((Login_Main) getActivity()).replaceFragment(new Password_Settings());
//                        Intent intent = new Intent(Otp.this, Login.class);
//                        startActivity(intent);
//                        finish();
                    }
                    CustomPreference.client_id=response.getJSONObject("result").getString("_id");
//                    CustomPreference.writeString(getActivity(),CustomPreference.Client_id,response.getJSONObject("result").getString("_id"));
                    CustomPreference.writeString(getActivity(),CustomPreference.Mobile_no,mobile_no.getText().toString());
//                    Constant.ToastShort(getActivity(),response.getJSONObject("result").getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(getActivity(), error.getMessage());
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==My_Permission_request_SMS_Receive)
        {

        }
    }
}
