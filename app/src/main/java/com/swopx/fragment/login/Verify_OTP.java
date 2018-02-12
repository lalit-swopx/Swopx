package com.swopx.fragment.login;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.otp_package.SmsListener;
import com.swopx.otp_package.SmsReceiver;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Office Work on 24-11-2017.
 */

public class Verify_OTP extends Fragment implements View.OnClickListener {
    private ImageButton forwrd_btn;
    private TextView resend,mobile_no,timer;
    private EditText otp_no,otp_no2,otp_no6,otp_no3,otp_no4,otp_no5;
    private LinearLayout timer_layout;
    private int My_Permission_request_SMS_Receive=10;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_otp, container, false);
        Constant.Log("ONCREATE","ON CREATE====1");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Login_Main) getActivity()).currentFragment = this;
        ((Login_Main) getActivity()).skip.setVisibility(View.GONE);
        int_views(view);
    }


    private void int_views(View view)
         {
        resend=(TextView)view.findViewById(R.id.resend);
        mobile_no=(TextView)view.findViewById(R.id.mobile_no);
        timer=(TextView)view.findViewById(R.id.timer);
        otp_no=(EditText)view.findViewById(R.id.otp_no);
        otp_no2=(EditText)view.findViewById(R.id.otp_no2);
        otp_no3=(EditText)view.findViewById(R.id.otp_no3);
        otp_no4=(EditText)view.findViewById(R.id.otp_no4);
        otp_no5=(EditText)view.findViewById(R.id.otp_no5);
        otp_no6=(EditText)view.findViewById(R.id.otp_no6);
        timer_layout=(LinearLayout)view.findViewById(R.id.timer_layout);
        forwrd_btn=(ImageButton)view.findViewById(R.id.forwrd_btn);
//        forwrd_btn.setEnabled(false);
        timer_layout.setVisibility(View.VISIBLE);
        resend.setVisibility(View.GONE);
        onClick();
        count_down();
        TextWatcher();

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

//                Constant.ToastShort(getActivity(),messageText);
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
    }


      int counter;
    private void count_down() {
        counter=30;
        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                timer.setText(String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){
                timer_layout.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void TextWatcher() {
        otp_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no.getText().toString().length()==1)
                {
//                    otp_no2.setFocusable(true);
                    otp_no2.requestFocus(otp_no2.getText().length());
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
//        457743
        otp_no2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no2.getText().toString().length()==1)
                {
//                    otp_no3.setFocusable(true);
                    otp_no3.requestFocus(otp_no3.getText().length());
                }
                else  if(otp_no6.getText().toString().length()==0)
                {
                    otp_no.requestFocus(otp_no.getText().length());
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
        otp_no3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no3.getText().toString().length()==1)
                {
//                    otp_no4.setFocusable(true);
                    otp_no4.requestFocus(otp_no4.getText().length());
                }
                else  if(otp_no6.getText().toString().length()==0)
                {
                    otp_no2.requestFocus(otp_no2.getText().length());
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
        otp_no4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no4.getText().toString().length()==1)
                {
//                    otp_no5.setFocusable(true);
                    otp_no5.requestFocus(otp_no5.getText().length());
                }
                else  if(otp_no6.getText().toString().length()==0)
                {
                    otp_no3.requestFocus(otp_no3.getText().length());
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
        otp_no5.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no5.getText().toString().length()==1)
                {
//                    otp_no6.setFocusable(true);
                    otp_no6.requestFocus(otp_no6.getText().length());
                }
                else  if(otp_no6.getText().toString().length()==0)
                {
                    otp_no4.requestFocus(otp_no4.getText().length());
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
        otp_no6.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(otp_no6.getText().toString().length()==1)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        forwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
                    }

//                    otp_no2.requestFocus(otp_no2.getText().length());
                }
                else  if(otp_no6.getText().toString().length()==0)
                {
                    otp_no5.requestFocus(otp_no5.getText().length());
                }
//                forwrd_btn.setEnabled(true);
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
    private void onClick() {
        resend.setOnClickListener(this);
        forwrd_btn.setOnClickListener(this);
        mobile_no.setText(getResources().getString(R.string.enter_6_digits)+"\n"+CustomPreference.readString(getActivity(),CustomPreference.Mobile_no,""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {


            case R.id.forwrd_btn:
                if(isValid())
                {
                    if(Constant.isNetConnected(getActivity()))
                    {
                        String messageText="";
                        Otp_Verify(messageText);
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                    }
                }

                break;
            case R.id.resend:

                if(Constant.isNetConnected(getActivity()))
                {
                    Resend();
                }
                else
                {
                    Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                }
//                Intent login=new Intent(Verify_OTP.this,Login.class);
//                startActivity(login);
//                finish();
                break;
            default:break;
        }
    }

    private boolean isValid() {

        if(TextUtils.isEmpty(otp_no.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        else    if(TextUtils.isEmpty(otp_no2.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        else    if(TextUtils.isEmpty(otp_no3.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        else    if(TextUtils.isEmpty(otp_no4.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        else    if(TextUtils.isEmpty(otp_no5.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        else    if(TextUtils.isEmpty(otp_no6.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.valid_otp));
            return false;
        }
        return true;
    }


    private void Otp_Verify(String messageText) {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
             if(TextUtils.isEmpty(messageText))
             {

                 String msg=otp_no.getText().toString()+otp_no2.getText().toString()+otp_no3.getText().toString()+otp_no4.getText().toString()+
                        otp_no5.getText().toString()+otp_no6.getText().toString();
                 params.put("otp", msg);
             }
             else
             {
                 params.put("otp", messageText);
             }

            params.put("client_id", CustomPreference.client_id);
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Verify_Otp, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {
                        ((Login_Main) getActivity()).replaceFragment(new Name());
//                        Constant.ToastShort(getActivity(),"Otp verified successful");
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


    private void Resend() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("mobile", CustomPreference.readString(getActivity(),CustomPreference.Mobile_no,""));
            params.put("client_id", CustomPreference.client_id);
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Resent_Otp, false) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {

                        timer_layout.setVisibility(View.VISIBLE);
                        resend.setVisibility(View.GONE);
                        count_down();

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
