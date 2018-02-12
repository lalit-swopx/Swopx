package com.swopx.fragment.edit_package;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
 * Created by Office Work on 06-12-2017.
 */

public class Contact extends AppCompatActivity implements View.OnClickListener {
    private TextView type,info,done;
    private ImageButton back,fab;
    private LinearLayout linearLayoutForm;
    private ScrollView scroll;
    private EditText website,mobile_no,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layour);
        intialize();
    }

    private void intialize() {
        type=(TextView)findViewById(R.id.type);
        info=(TextView)findViewById(R.id.info);
        done=(TextView)findViewById(R.id.done);
        fab=(ImageButton)findViewById(R.id.fab);
        back=(ImageButton) findViewById(R.id.back);
        linearLayoutForm=(LinearLayout) findViewById(R.id.linearLayoutForm);
        scroll=(ScrollView) findViewById(R.id.scroll);
        website=(EditText) findViewById(R.id.website);
        mobile_no=(EditText) findViewById(R.id.mobile_no);
        email=(EditText) findViewById(R.id.email);
        setValues();
        onClick();
//        add_view();
    }

    private void onClick() {
        done.setOnClickListener(this);
        back.setOnClickListener(this);
        fab.setOnClickListener(this);
//        scroll.fullScroll(ScrollView.FOCUS_DOWN);
//        scroll.smoothScrollTo(0,0);
       /* scroll.post(new Runnable() {
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });*/

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
                    if(Constant.isNetConnected(Contact.this))
                    {
                        Contact_info();
                    }
                    else
                    {
                        Constant.ToastShort(Contact.this,getResources().getString(R.string.internet_connection));
                    }
                break;

            case R.id.back:
                finish();
                break;
                
            case R.id.fab:
//                add_view();
                break;

            default:
                break;
        }
    }
    private boolean isValid() {
        if(TextUtils.isEmpty(email.getText().toString().trim()))
        {
            Constant.ToastShort(Contact.this,getResources().getString(R.string.enter_email));
            return false;
        }
        else  if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            Constant.ToastShort(Contact.this,getResources().getString(R.string.enter_valid_email));
            return false;
        }
        else   if(TextUtils.isEmpty(mobile_no.getText().toString().trim()))
        {
            Constant.ToastShort(Contact.this,getResources().getString(R.string.mobile_validation));
            return false;
        }
        else if(mobile_no.getText().length()!=10)
        {
            Constant.ToastShort(Contact.this,getResources().getString(R.string.mobile_10));
            return false;
        }
        else  if(TextUtils.isEmpty(website.getText().toString().trim()))
        {
            Constant.ToastShort(Contact.this,getResources().getString(R.string.enter_website));
            return false;
        }
//        else  if(Patterns.WEB_URL.matcher(website.getText().toString().trim()).matches())
//        {
//            Constant.ToastShort(Contact.this,getResources().getString(R.string.valid_website));
//            return false;
//        }
        return true;
    }
    private void Contact_info() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id", CustomPreference.readString(Contact.this,CustomPreference.Client_id,""));
            params.put("website", website.getText().toString());
            params.put("email", email.getText().toString());
            params.put("mobile", mobile_no.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new LoginApi(Contact.this, params, Url_Links.Contact_Info_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getString("status").equalsIgnoreCase("200"))
                    {


//                            CustomPreference.writeString(Basic_Info.this,CustomPreference.Company_Id,CustomPreference.client_id);

//                        Constant.ToastShort(Contact.this,response.getString("msg"));
                        finish();
                    }
                    Constant.ToastShort(Contact.this,response.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Contact.this, error.getMessage());
            }
        };
    }

    private void add_view() {
        final LinearLayout newView = (LinearLayout)this.getLayoutInflater().inflate(R.layout.contact_row, null);
        newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
        linearLayoutForm.addView(newView);
    }
}
