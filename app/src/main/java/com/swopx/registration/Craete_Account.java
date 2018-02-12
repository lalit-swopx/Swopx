package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.swopx.MainActivity;
import com.swopx.R;
import com.swopx.fragment.dashboard.Dashboard;
import com.swopx.utils.CustomPreference;

/**
 * Created by Office Work on 18-11-2017.
 */

public class Craete_Account extends AppCompatActivity implements View.OnClickListener {
    private TextView create_account,login,guest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_aacount_layout);
        init_view();
    }

    private void init_view() {
        create_account=(TextView)findViewById(R.id.create_account);
        login=(TextView)findViewById(R.id.login);
        guest=(TextView)findViewById(R.id.guest);
        onClick();
    }

    private void onClick() {
        create_account.setOnClickListener(this);
        login.setOnClickListener(this);
        guest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.create_account:
                Intent regis=new Intent(Craete_Account.this,GetStarted_Screen.class);
                startActivity(regis);
                finish();
                break;
            case R.id.guest:
                CustomPreference.user_type="2";
                CustomPreference.writeString(Craete_Account.this,CustomPreference.User_Type,CustomPreference.user_type);
                Intent guest=new Intent(Craete_Account.this,Dashboard_Main.class);
                startActivity(guest);
                finish();
                break;
            case R.id.login:
                Intent login=new Intent(Craete_Account.this,GetStarted_Screen.class);
                startActivity(login);
                finish();
                break;

                default:
                    break;
        }
    }
}
