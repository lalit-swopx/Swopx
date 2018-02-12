package com.swopx.fragment.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.registration.Login_Main;
import com.swopx.utils.Constant;

/**
 * Created by Office Work on 01-12-2017.
 */

public class Name extends Fragment {
    private EditText first,last;
    private TextView forget;
    private ImageButton frwrd_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.what_your_name_layout, container, false);
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

    private void int_view(View view) {
        first=(EditText)view.findViewById(R.id.first);
        last=(EditText)view.findViewById(R.id.last);
        forget=(TextView) view.findViewById(R.id.forget);
        frwrd_btn=(ImageButton) view.findViewById(R.id.frwrd_btn);
        frwrd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {

                      ((Login_Main) getActivity()).f_name=first.getText().toString();
                      ((Login_Main) getActivity()).l_name=last.getText().toString();

                    Email fragment = new Email();
                    Bundle bundle = new Bundle();
                    bundle.putString("signIn", "signIn");
                    fragment.setArguments(bundle);
                    ((Login_Main) getActivity()).replaceFragment(fragment);


                }
            }
        });


        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(first.getText().toString().length()>=1)
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

        last.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Constant.Log("=======OnTextCHanged",s+";"+count);
                if(last.getText().toString().length()>=1)
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
    }

    private boolean isValid() {

        if(TextUtils.isEmpty(first.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),"Please enter first name.");
            return false;
        }
        else
        if(TextUtils.isEmpty(last.getText().toString().trim()))
        {
            Constant.ToastShort(getActivity(),"Please enter last name.");
            return false;
        }

        return true;
    }
}
