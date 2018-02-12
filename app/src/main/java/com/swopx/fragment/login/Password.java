package com.swopx.fragment.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swopx.MainActivity;
import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.chat.UserDetails;
import com.swopx.chat.Users;
import com.swopx.otp_package.SmsListener;
import com.swopx.otp_package.SmsReceiver;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Invite_Ten_Activity;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 30-11-2017.
 */

public class Password extends Fragment implements View.OnClickListener {

    private TextView forget,passwrd_content;
    private EditText passwrd;
    private ImageButton frwrd_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.passwrd_layout, container, false);
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
        passwrd=(EditText)view.findViewById(R.id.passwrd);
        forget=(TextView) view.findViewById(R.id.forget);
        passwrd_content=(TextView) view.findViewById(R.id.passwrd_content);
        frwrd_btn=(ImageButton) view.findViewById(R.id.frwrd_btn);
        forget.setOnClickListener(this);
        frwrd_btn.setOnClickListener(this);
        if(getArguments().containsKey("signIn"))
        {
            forget.setVisibility(View.INVISIBLE);
            passwrd_content.setText("Create Password");
        }
        else
        {
            passwrd_content.setText("Welcome back, sign in to continue");
            SmsReceiver.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String messageText) {
                    {
                        Constant.ToastShort(getActivity(),"Soon you will receive your password");
                    }
                }
            });
            forget.setVisibility(View.VISIBLE);
        }


        passwrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(passwrd.getText().toString().length()>=1)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        frwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        frwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_gray));
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

    private boolean isValid() {

        if(TextUtils.isEmpty(passwrd.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.enter_passwrd));
            return false;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void login() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            Constant.Log("VAlues Login", "Device ID" + Constant.displayFirebaseRegId(getActivity()));
//            params.put("device_id", Constant.displayFirebaseRegId(getActivity()));
            params.put("device_id", FirebaseInstanceId.getInstance().getToken());
            params.put("mobile", CustomPreference.readString(getActivity(),CustomPreference.Mobile_no,""));
            params.put("password", passwrd.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Login, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    JSONObject obj=response.getJSONObject("result");
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("yes"))
                    {
                        CustomPreference.writeString(getActivity(),CustomPreference.Client_id,CustomPreference.client_id);
                        CustomPreference.user_type="1";
                        CustomPreference.writeString(getActivity(),CustomPreference.User_Type,CustomPreference.user_type);
                        CustomPreference.writeString(getActivity(),CustomPreference.Client_name,obj.getString("name"));
                        CustomPreference.writeString(getActivity(),CustomPreference.Refferal_Code,obj.getString("referral"));
                        CustomPreference.writeString(getActivity(),CustomPreference.Wallet_Amount,obj.getString("wallet"));
//                        login_with_base(obj.getString("id"),obj.getString("name"),obj.getString("msg"));
                        Intent intent = new Intent(getActivity(), Dashboard_Main.class);
                        startActivity(intent);
                        getActivity().finish();
                        Constant.ToastLong(getActivity(),obj.getString("msg"));
//                        ((Login_Main) getActivity()).replaceFragment(new Verify_OTP());
//                        Constant.ToastShort(getActivity(),"Login successfully");

                    }


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

    private void login_with_base(final String id, final String name,final String msg) {
//        String url = "https://realtimechat-658d5.firebaseio.com/users.json";
        String url = Url_Links.Users_Json;
        final CustomLoader customLoader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        customLoader.setCanceledOnTouchOutside(false);
        customLoader.setCancelable(false);
//        final ProgressDialog pd = new ProgressDialog(getActivity());
//        pd.setMessage("Loading...");
//        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                if(s.equals("null")){
                    Toast.makeText(getActivity(), "user not found", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        JSONObject obj = new JSONObject(s);

                        if(!obj.has(id)){
                            Toast.makeText(getActivity(), "user not found", Toast.LENGTH_LONG).show();
                        }
                        else if(obj.getJSONObject(id).getString("name").equals(name)){


                            Intent intent = new Intent(getActivity(), Dashboard_Main.class);
                            startActivity(intent);
                            getActivity().finish();
                            Constant.ToastLong(getActivity(),msg);
                        }
                        else {
                            Toast.makeText(getActivity(), "incorrect password", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                customLoader.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                customLoader.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.forget:

                ((Login_Main)getActivity()).replaceFragment(new Forget_Password());
                break;
            case R.id.frwrd_btn:
                if(isValid())
                {

                    if(getArguments().containsKey("signIn"))
                    {

                        ((Login_Main) getActivity()).passwrd=passwrd.getText().toString();
                        ((Login_Main) getActivity()).replaceFragment(new Confirm_password());
                    }
                    else
                    {
                        if(Constant.isNetConnected(getActivity()))
                        {
                            login();
                        }
                        else
                        {
                            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                        }
                    }


            //  ((Login_Main) getActivity()).currentFragment = this;

                }
                break;
                default:
                    break;
        }
    }
}
