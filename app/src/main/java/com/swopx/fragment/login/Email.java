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
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.swopx.MainActivity;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Login_Main;
import com.swopx.registration.Registration;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 30-11-2017.
 */

public class Email extends Fragment {
    private TextView forget;
    private ImageButton frwrd_btn;
    private EditText email;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.email_layout, container, false);
        Constant.Log("ONCREATE","ON CREATE====1");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Login_Main) getActivity()).currentFragment = this;
        ((Login_Main) getActivity()).skip.setVisibility(View.GONE);
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
//                    resend.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_black));
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
        email=(EditText) view.findViewById(R.id.email);
        frwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    ((Login_Main) getActivity()).email_address=email.getText().toString();
                    ((Login_Main) getActivity()).replaceFragment(new Refferal_Code());
                   /* Password fragment = new Password();
                    Bundle bundle = new Bundle();
                    bundle.putString("signIn", "signIn");
                    fragment.setArguments(bundle);
                    ((Login_Main)getActivity()).replaceFragment(fragment);
*/
//                    if(getArguments().containsKey("signIn"))
//                    {
//                        if(Constant.isNetConnected(getActivity()))
//                        {
//                            login();
//                        }
//                        else
//                        {
//                            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
//                        }
//
//                    }
                }

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(email.getText().toString().length()>=1)
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
           if(TextUtils.isEmpty(email.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getString(R.string.enter_email));
            return  false;
        }
        else   if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            Constant.ToastShort(getActivity(),getString(R.string.enter_valid_email));
            return  false;
        }


        return true;
    }


    private void login() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
//          params.put("client_id", CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
            params.put("client_id", CustomPreference.client_id);
            params.put("password", ((Login_Main)getActivity()).passwrd);
            params.put("email", email.getText().toString());
            params.put("lastname", ((Login_Main)getActivity()).l_name);
            params.put("firstname", ((Login_Main)getActivity()).f_name);
//            if(!TextUtils.isEmpty(referral_code.getText().toString()))
//            {
//                params.put("refer_by", referral_code.getText().toString().trim());
//            }
//            else
//            {
//                params.put("refer_by", "");
//            }

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
                            register_tofirebase(obj.getString("id"),obj.getString("name"));
                        }


//                        Constant.ToastShort(getActivity(),"You registered successfully");
                        Constant.ToastShort(getActivity(),response.getJSONObject("result").getString("msg"));
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

    private void register_tofirebase(final String client_id, final String name) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();

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
                    Intent intent = new Intent(getActivity(), Dashboard_Main.class);
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
                            Intent intent = new Intent(getActivity(), Dashboard_Main.class);
                            startActivity(intent);
                            getActivity().finish();
                            Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "username already exists", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                pd.dismiss();
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
                pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
    }
}
