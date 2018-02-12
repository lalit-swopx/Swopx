package com.swopx.fragment.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 02-12-2017.
 */

public class Forget_Password extends Fragment {
    private EditText mobile_no;
    private ImageButton forwrd_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_number_screen, container, false);
        Constant.Log("ONCREATE","ON CREATE====1");
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
        forwrd_btn.setEnabled(false);
        forwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constant.isNetConnected(getActivity()))
                {
                    forgot();
                }
                else
                {
                    Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                }
            }
        });
        mobile_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(mobile_no.getText().toString().length()==10)
                {
                    forwrd_btn.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
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

    }


    private void forgot() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("mobile", mobile_no.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Forgot_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {

                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {
                        Password fragment = new Password();
                        Bundle bundle = new Bundle();
                        bundle.putString("logIn", "logIn");
                        fragment.setArguments(bundle);
                        ((Login_Main) getActivity()).replaceFragment(fragment);
//                        ((Login_Main) getActivity()).replaceFragment(new Password());
//                        Intent intent = new Intent(getActivity(), Verify_OTP.class);
//                        startActivity(intent);
//                        finish();
                    }
                    Constant.ToastShort(getActivity(), response.getJSONObject("result").getString("msg"));


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
