package com.swopx.fragment.edit_package;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.login.Password;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Password_Settings extends AppCompatActivity implements View.OnClickListener {
    private TextView type,info,done;
    private ImageButton back;
    private EditText old_password,new_password,conf_password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_layout);
        intialize();
    }

    private void intialize() {
        type=(TextView)findViewById(R.id.type);
        info=(TextView)findViewById(R.id.info);
        done=(TextView)findViewById(R.id.done);
        old_password=(EditText) findViewById(R.id.old_password);
        new_password=(EditText) findViewById(R.id.new_password);
        conf_password=(EditText) findViewById(R.id.conf_password);
        back=(ImageButton) findViewById(R.id.back);
        setValues();
        onClick();
    }

    private void onClick() {
        done.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void setValues() {
        type.setText(getIntent().getStringExtra("type"));
        info.setText(getIntent().getStringExtra("info"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.done:
                if(isValid())
                if(Constant.isNetConnected(Password_Settings.this))
                {
                    Change_Password();
                }
                else
                {
                    Constant.ToastShort(Password_Settings.this,getResources().getString(R.string.internet_connection));
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private boolean isValid() {

        if(TextUtils.isEmpty(old_password.getText().toString().trim()))
        {
            Constant.ToastShort(Password_Settings.this,getResources().getString(R.string.enter_passwrd));
            return false;
        }
        else  if(TextUtils.isEmpty(new_password.getText().toString().trim()))
        {
            Constant.ToastShort(Password_Settings.this,getResources().getString(R.string.new_passwrd));
            return false;
        }
        else  if(TextUtils.isEmpty(conf_password.getText().toString().trim()))
        {
            Constant.ToastShort(Password_Settings.this,getResources().getString(R.string.retype_new));
            return false;
        }
        else  if(!conf_password.getText().toString().trim().equals(new_password.getText().toString().trim()))
        {
            Constant.ToastShort(Password_Settings.this,getResources().getString(R.string.psswrd_mathces));
            return false;
        }
        return true;
    }
    private void Change_Password() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
          params.put("client_id", CustomPreference.readString(Password_Settings.this,CustomPreference.Client_id,""));
            params.put("new_password", new_password.getText().toString());
            params.put("confirm_password", conf_password.getText().toString());
            params.put("old_password", old_password.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(Password_Settings.this, params, Url_Links.Change_Passwrd_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getString("status").equalsIgnoreCase("200"))
                    {
                       finish();
                    }
                    Constant.ToastShort(Password_Settings.this,response.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Password_Settings.this, error.getMessage());
            }
        };
    }
}
