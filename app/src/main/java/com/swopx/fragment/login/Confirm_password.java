package com.swopx.fragment.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
 * Created by Office Work on 01-12-2017.
 */

public class Confirm_password extends Fragment implements View.OnClickListener {

    private TextView forget,passwrd_content;
    private EditText passwrd,con_passwrd;
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

    private void int_view(View view)
    {

        passwrd=(EditText)view.findViewById(R.id.passwrd);
        con_passwrd=(EditText)view.findViewById(R.id.con_passwrd);
        forget=(TextView) view.findViewById(R.id.forget);
        passwrd_content=(TextView) view.findViewById(R.id.passwrd_content);
        frwrd_btn=(ImageButton) view.findViewById(R.id.frwrd_btn);
        passwrd.setVisibility(View.GONE);
        forget.setVisibility(View.INVISIBLE);
        con_passwrd.setVisibility(View.VISIBLE);
        forget.setOnClickListener(this);
        frwrd_btn.setOnClickListener(this);
        passwrd_content.setText("Confirm Password");
        con_passwrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(con_passwrd.getText().toString().length()>=1)
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

        if(TextUtils.isEmpty(con_passwrd.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.enter_passwrd));
            return false;
        }

        else if(!con_passwrd.getText().toString().equalsIgnoreCase(((Login_Main)getActivity()).passwrd))
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.psswrd_mathces));
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            frwrd_btn.setBackground(getResources().getDrawable(R.drawable.circle_black));
        }
        frwrd_btn.setEnabled(true);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.forget:
                break;
            case R.id.frwrd_btn:
                if(isValid())
                    {
                        Constant.hideSoftKeyboard(getActivity());
                        ((Login_Main) getActivity()).replaceFragment(new By_Tapping_Fragment());
                    }
                break;
            default:
                break;
        }
    }



}
