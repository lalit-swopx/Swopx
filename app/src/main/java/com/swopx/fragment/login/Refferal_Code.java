package com.swopx.fragment.login;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.registration.Coupon_Activity;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Invite_Ten_Activity;
import com.swopx.registration.Login_Main;
import com.swopx.registration.WalkThrough_Activity;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 03-01-2018.
 */

public class Refferal_Code extends Fragment {
    private TextView forget;
    private ImageButton frwrd_btn;
    private EditText referral_code;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.refferal_code_layout, container, false);
        Constant.Log("ONCREATE","ON CREATE====1");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Login_Main) getActivity()).currentFragment = this;
        ((Login_Main) getActivity()).skip.setVisibility(View.VISIBLE);
        intial_view(view);
//        alert_dialog();
    }

    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.email_sent_dailog, null);
        dialogBuilder.setView(dialogView);
        RadioGroup radio_grp=(RadioGroup)dialogView.findViewById(R.id.radio_grp);
        final RadioButton resend = (RadioButton) dialogView.findViewById(R.id.resend);
        final RadioButton ok_btn = (RadioButton) dialogView.findViewById(R.id.ok_btn);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.resend)
                {
//                  resend.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_black));
                    resend.setTextColor(getResources().getColor(R.color.colorWhite));
                    ok_btn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                else  if(checkedId==R.id.ok_btn)
                {
//                  resend.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_black));
                    ok_btn.setTextColor(getResources().getColor(R.color.colorWhite));
                    resend.setTextColor(getResources().getColor(R.color.colorBlack));
                    alertDialog.dismiss();
                }
            }
        });
//        editText.setText("test label");
        alertDialog.show();
    }

    private void intial_view(View view) {
        forget=(TextView)view.findViewById(R.id.forget);
        frwrd_btn=(ImageButton) view.findViewById(R.id.frwrd_btn);
        referral_code=(EditText) view.findViewById(R.id.referral_code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            frwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
        }
        frwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    {
                        if(!TextUtils.isEmpty(referral_code.getText().toString().trim()))
                        {
                            ((Login_Main) getActivity()).reffereal_code=referral_code.getText().toString();
                        }
                        else
                        {
                            ((Login_Main) getActivity()).reffereal_code="";
                        }
                        Password fragment = new Password();
                        Bundle bundle = new Bundle();
                        bundle.putString("signIn", "signIn");
                        fragment.setArguments(bundle);
                        ((Login_Main)getActivity()).replaceFragment(fragment);
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
        ss.setSpan(clickableSpan1, 59, 77, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 82, 96, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forget.setText(ss);
        forget.setMovementMethod(LinkMovementMethod.getInstance());
//        forget.setHighlightColor(Color.TRANSPARENT);

    }

    private boolean isValid()
    {
        if(TextUtils.isEmpty(referral_code.getText().toString()))
        {
            Constant.ToastShort(getActivity(),getString(R.string.enter_email));
            return  false;
        }



        return true;
    }



}
