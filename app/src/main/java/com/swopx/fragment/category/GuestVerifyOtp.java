package com.swopx.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.swopx.fragment.login.Name;
import com.swopx.otp_package.SmsListener;
import com.swopx.otp_package.SmsReceiver;
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

public class GuestVerifyOtp extends Fragment {

    private EditText otp;
    private TextView cont,phn_no;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_otp_second_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_views(view);
    }

    private void init_views(View view) {
//        ((MainActivity) getActivity()).currentFragment = this;

        otp=(EditText) view.findViewById(R.id.otp);
        cont=(TextView) view.findViewById(R.id.cont);
        phn_no=(TextView) view.findViewById(R.id.phn_no);
        phn_no.setText(getArguments().getString("phn_no"));

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                Constant.ToastShort(getActivity(),messageText);
//                otp_no.setText(messageText);
                if(Constant.isNetConnected(getActivity()))
                {
                    Otp_Verify(messageText);
                }
                else
                {
                    Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                }
            }
        });


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomPreference.readString(getActivity(),CustomPreference.User_Type,"").equalsIgnoreCase("2"))
                {
                }

            }
        });
    }

    private void Otp_Verify(String messageText) {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("otp", messageText);
            params.put("guest_id", CustomPreference.readString(getActivity(),CustomPreference.Guest_User_Id,""));
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Guest_Verify_Otp, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
//
                    if(response.getJSONObject("result").getString("verify").equalsIgnoreCase("yes"))
                    {
                        ((Dashboard_Main) getActivity()).replaceFragment(new RequestScreen());
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
