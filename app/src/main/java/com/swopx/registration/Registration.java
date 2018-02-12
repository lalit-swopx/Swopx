package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.utils.Constant;

/**
 * Created by Office Work on 24-11-2017.
 */

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private TextView login,cont;
    private ImageButton bck_btn;
    private EditText name,email,password,con_password,what_to_registr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        int_views();
    }

    private void int_views() {
        login=(TextView)findViewById(R.id.login);
        cont=(TextView)findViewById(R.id.cont);
        bck_btn=(ImageButton)findViewById(R.id.bck_btn);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        con_password=(EditText) findViewById(R.id.con_password);
        what_to_registr=(EditText) findViewById(R.id.what_to_registr);
        onClick();
    }

    private void onClick() {
        cont.setOnClickListener(this);
        bck_btn.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cont:
if(isValid())
                break;
            case R.id.bck_btn:
                finish();
                break;

                case R.id.login:
                Intent login=new Intent(Registration.this,Login.class);
                startActivity(login);
                finish();
                break;
            default:break;
        }
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(name.getText().toString()))
        {
            Constant.ToastShort(Registration.this,getString(R.string.enter_name));
            return  false;
        }
        else   if(TextUtils.isEmpty(email.getText().toString()))
        {
            Constant.ToastShort(Registration.this,getString(R.string.enter_email));
            return  false;
        }
        else   if(TextUtils.isEmpty(password.getText().toString()))
        {
            Constant.ToastShort(Registration.this,getString(R.string.enter_passwrd));
            return  false;
        }

        else   if(TextUtils.isEmpty(con_password.getText().toString()))
        {
            Constant.ToastShort(Registration.this,getString(R.string.enter_con_pass));
            return  false;
        }

        else   if(!con_password.getText().toString().equals(password.getText().toString()))
        {
            Constant.ToastShort(Registration.this,getString(R.string.psswrd_mathces));
            return  false;
        }
        return true;
    }
}
