package com.swopx.fragment.category;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.fragment.dashboard.Dashboard;
import com.swopx.fragment.login.Name;
import com.swopx.fragment.login.Password;
import com.swopx.fragment.login.Verify_OTP;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Login_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 13-12-2017.
 */

public class GuestUser_Otp extends Fragment {

    private EditText phn_no;
    private TextView cont;
    private int My_Permission_request_SMS_Receive=10;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_number_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_views(view);
    }

    private void init_views(View view) {
        phn_no=(EditText) view.findViewById(R.id.phn_no);
        cont=(TextView) view.findViewById(R.id.cont);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.RECEIVE_SMS},
                My_Permission_request_SMS_Receive);

        cont.setOnClickListener(new View.OnClickListener() {
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
    }
    private boolean isValid() {
        if(TextUtils.isEmpty(phn_no.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.mobile_validation));
            return false;
        }
        else if(phn_no.getText().length()!=10)
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
            params.put("industry_id", ((Dashboard_Main)getActivity()).industry_id);
            params.put("industry_name", ((Dashboard_Main)getActivity()).industry_name);
            params.put("cat_id", ((Dashboard_Main)getActivity()).cat_id);
            params.put("cat_name", ((Dashboard_Main)getActivity()).cat_name);
            params.put("subcat_id", ((Dashboard_Main)getActivity()).subcat_id);
            params.put("subcat_name", ((Dashboard_Main)getActivity()).subcat_name);
            params.put("when", ((Dashboard_Main)getActivity()).when);
            params.put("qty", ((Dashboard_Main)getActivity()).qty);
            params.put("contact", phn_no.getText().toString().trim());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Guest_Otp_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {

                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {

                        CustomPreference.writeString(getActivity(),CustomPreference.Guest_User_Id,response.getJSONObject("result").getString("id"));
                        Bundle bundle = new Bundle();
                        bundle.putString("phn_no",phn_no.getText().toString().trim());
                        GuestVerifyOtp fragment = new GuestVerifyOtp();
                        fragment.setArguments(bundle);
                        ((Dashboard_Main) getActivity()).replaceFragment(fragment);
                    }

                    Constant.ToastShort(getActivity(),response.getJSONObject("result").getString("msg"));

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

}
