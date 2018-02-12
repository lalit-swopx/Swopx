package com.swopx.fragment.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class Terms_Privacy extends Fragment {
    private ImageButton frwrd_btn;
    private WebView webview;
    private LinearLayout footer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.terms_privacy_layout, container, false);
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
        footer = (LinearLayout) view.findViewById(R.id.footer);
        footer.setVisibility(View.VISIBLE);
        webview = (WebView) view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        if(getArguments().getString("value").equalsIgnoreCase("privacy"))
        {
            webview.loadUrl(Url_Links.Privacy_Url);
        }
        else
        {
            webview.loadUrl(Url_Links.Terms_Url);
        }

        webview.setHorizontalScrollBarEnabled(false);
        frwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });



//        forget.setHighlightColor(Color.TRANSPARENT);

    }




    private void login() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
//          params.put("client_id", CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
            params.put("client_id", CustomPreference.client_id);
            params.put("password", ((Login_Main) getActivity()).passwrd);
            params.put("lastname", ((Login_Main) getActivity()).l_name);
            params.put("firstname", ((Login_Main) getActivity()).f_name);
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Registration, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {

                    if (response.getJSONObject("result").getString("status").equalsIgnoreCase("yes")) {

                        if (response.getJSONObject("result").getString("is_active").equalsIgnoreCase("active")) {
                            CustomPreference.writeString(getActivity(), CustomPreference.Client_id, CustomPreference.client_id);
                        }
                        Constant.ToastShort(getActivity(),response.getJSONObject("result").getString("msg"));
                        getActivity().finish();
//                        ((Login_Main) getActivity()).replaceFragment(new Verify_OTP());
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
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
}
