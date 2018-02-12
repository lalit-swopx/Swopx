package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Office Work on 22-11-2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView signUp,login,forget_passwrd;
    private ImageButton seen_password;
    private EditText email,password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init_view();
    }
    private void init_view() {
        signUp=(TextView)findViewById(R.id.signUp);
        login=(TextView)findViewById(R.id.login);
        forget_passwrd=(TextView)findViewById(R.id.forget_passwrd);
        password=(EditText) findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        seen_password=(ImageButton) findViewById(R.id.seen_password);
        onClick();
    }
    private void onClick() {
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        forget_passwrd.setOnClickListener(this);
        seen_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUp:
                break;

                case R.id.forget_passwrd:
                break;
                case R.id.seen_password:
                break;
            case R.id.login:
                if(isValid())
                {
                    if(Constant.isNetConnected(Login.this))
                    {
//                    Login();
                    }
                    else
                    {
                        Constant.ToastShort(Login.this,getResources().getString(R.string.internet_connection));
                    }
                }

//                Intent login=new Intent(Login.this,Login.class);
//                startActivity(login);
                break;
            default:break;
        }
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(email.getText().toString().trim()))
        {
            Constant.ToastShort(Login.this,getResources().getString(R.string.enter_email));
            return false;
        }
       else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            Constant.ToastShort(Login.this,getResources().getString(R.string.enter_valid_email));
            return false;
        }
        if(TextUtils.isEmpty(password.getText().toString().trim()))
        {
            Constant.ToastShort(Login.this,getResources().getString(R.string.enter_passwrd));
            return false;
        }
        return true;
    }

    private void Login() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("otp", email.getText().toString());
            params.put("otp", password.getText().toString());
            params.put("client_id", CustomPreference.readString(Login.this,CustomPreference.Client_id,""));
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(Login.this, params, Url_Links.Login, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getString("status").equalsIgnoreCase("200"))
                    {

                        Intent intent = new Intent(Login.this, Login.class);
                        startActivity(intent);
                        finish();
                    }

//                        CustomPreference.writeString(Otp.this,CustomPreference.Vehicle_Id,response.getString("vehicle_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Login.this, error.getMessage());
            }
        };
    }

}
