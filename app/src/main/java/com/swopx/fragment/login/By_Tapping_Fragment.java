package com.swopx.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.registration.Login_Main;
import com.swopx.registration.WalkThrough_Activity;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 10-01-2018.
 */

public class By_Tapping_Fragment extends Fragment {
    private ImageButton frwrd_btn;
    private WebView webview;
    private TextView forget;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.by_tapping_layout, container, false);
        Constant.Log("ONCREATE", "ON CREATE====1");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Login_Main) getActivity()).currentFragment = this;
        ((Login_Main) getActivity()).skip.setVisibility(View.GONE);
        intial_view(view);
    }
    private void intial_view(View view) {
        frwrd_btn = (ImageButton) view.findViewById(R.id.forwrd_btn);
        forget = (TextView) view.findViewById(R.id.forget);
        frwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        SpannableString ss = new SpannableString(getResources().getString(R.string.terms_con));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
//                startActivity(new Intent(MyActivity.this, NextActivity.class));
                Terms_Privacy fragment = new Terms_Privacy();
                Bundle bundle = new Bundle();
                bundle.putString("values", "privacy");
                fragment.setArguments(bundle);
                ((Login_Main) getActivity()).replaceFragment(fragment);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.colorblue));
            }
        };

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
//                startActivity(new Intent(MyActivity.this, NextActivity.class));
                Terms_Privacy fragment = new Terms_Privacy();
                Bundle bundle = new Bundle();
                bundle.putString("values", "terms");
                fragment.setArguments(bundle);
                ((Login_Main) getActivity()).replaceFragment(fragment);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.colorblue));
            }
        };
        ss.setSpan(clickableSpan1, 59, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 82, 96, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forget.setText(ss);
        forget.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void login() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id", CustomPreference.client_id);
            params.put("password", ((Login_Main)getActivity()).passwrd);
            params.put("email", ((Login_Main)getActivity()).email_address);
            params.put("lastname", ((Login_Main)getActivity()).l_name);
            params.put("firstname", ((Login_Main)getActivity()).f_name);
            params.put("refer_by", ((Login_Main)getActivity()).reffereal_code);
            params.put("device_id", FirebaseInstanceId.getInstance().getToken());


            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Registration, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("yes"))
                    {
                        JSONObject obj=response.getJSONObject("result");
                        if(response.getJSONObject("result").getString("is_active").equalsIgnoreCase("active"))
                        {
                            CustomPreference.writeString(getActivity(),CustomPreference.Client_id,CustomPreference.client_id);
                            CustomPreference.writeString(getActivity(),CustomPreference.Client_name,obj.getString("name"));
                            CustomPreference.writeString(getActivity(),CustomPreference.Refferal_Code,obj.getString("referral"));
                            Intent intent = new Intent(getActivity(), WalkThrough_Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Constant.ToastShort(getActivity(), obj.getString("msg"));
//                          register_tofirebase(obj.getString("id"),obj.getString("name"),obj.getString("msg"));
                        }
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

    private void register_tofirebase(final String client_id, final String name,final String msg) {

        final CustomLoader customLoader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        customLoader.show();
        customLoader.setCanceledOnTouchOutside(false);
        customLoader.setCancelable(false);
//        String url = "https://realtimechat-658d5.firebaseio.com/users.json";
        String url = Url_Links.Users_Json;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
//                Firebase reference = new Firebase("https://realtimechat-658d5.firebaseio.com/users");
                Firebase reference = new Firebase(Url_Links.Users);
                Constant.Log("Response====","OnResponse:"+s);
                if(s.equals("null")) {
                    reference.child(client_id).child("name").setValue(name);
//                    Intent intent = new Intent(getActivity(), Dashboard_Main.class);
                    Intent intent = new Intent(getActivity(), WalkThrough_Activity.class);
                    startActivity(intent);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);
                        Constant.Log("Response====","OnResponse1:"+obj);
                        if (!obj.has(client_id)) {
                            reference.child(client_id).child("name").setValue(name);
                            Intent intent = new Intent(getActivity(), WalkThrough_Activity.class);
//                            Intent intent = new Intent(getActivity(), Invite_Ten_Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Constant.ToastShort(getActivity(),msg);
//                            Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getActivity(), "username already exists", Toast.LENGTH_LONG).show();
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
                System.out.println("" + volleyError );
                customLoader.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }
}
