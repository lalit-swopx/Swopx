package com.swopx.fragment.edit_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Edit extends AppCompatActivity implements View.OnClickListener {
    private TextView cancel;
    private LinearLayout basic_info,project_prefrence,contact_info,location,notification,account_type,passwrd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        init_views();
    }

    private void init_views() {
        basic_info=(LinearLayout)findViewById(R.id.basic_info);
        project_prefrence=(LinearLayout)findViewById(R.id.project_prefrence);
        contact_info=(LinearLayout)findViewById(R.id.contact_info);
        location=(LinearLayout)findViewById(R.id.location);
        notification=(LinearLayout)findViewById(R.id.notification);
        account_type=(LinearLayout)findViewById(R.id.account_type);
        passwrd=(LinearLayout)findViewById(R.id.passwrd);
        cancel=(TextView) findViewById(R.id.cancel);
        onClick();
    }

    private void onClick() {
        basic_info.setOnClickListener(this);
//        project_prefrence.setOnClickListener(this);
        contact_info.setOnClickListener(this);
      /*  location.setOnClickListener(this);
        notification.setOnClickListener(this);
        account_type.setOnClickListener(this);*/
        cancel.setOnClickListener(this);
        passwrd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.basic_info:
                Intent basic=new Intent(Edit.this,Basic_Info.class);
                basic.putExtra("type",getResources().getString(R.string.basic_info));
                basic.putExtra("info",getResources().getString(R.string.basic_company_information));
                startActivity(basic);
                break;
            case R.id.project_prefrence:
                Intent project_prefrence=new Intent(Edit.this,Project_preferences.class);
                project_prefrence.putExtra("type",getResources().getString(R.string.project_prefrences));
                project_prefrence.putExtra("info",getResources().getString(R.string.your_dealing_category_and_other_prefrences));
                startActivity(project_prefrence);
                break;
            case R.id.contact_info:
                Intent contact=new Intent(Edit.this,Contact.class);
                contact.putExtra("type",getResources().getString(R.string.contact_info));
                contact.putExtra("info",getResources().getString(R.string.detail_to_better_reach_your));
                startActivity(contact);
                break;
            case R.id.location:
                Intent location=new Intent(Edit.this,Location.class);
                location.putExtra("type",getResources().getString(R.string.location));
                location.putExtra("info",getResources().getString(R.string.detail_regarding_your_loaction));
                startActivity(location);

                break;
            case R.id.notification:
                Intent notification=new Intent(Edit.this,Notification_Profile.class);
                notification.putExtra("type",getResources().getString(R.string.notifications));
                notification.putExtra("info",getResources().getString(R.string.basic_notification_settings));
                startActivity(notification);
                break;
            case R.id.account_type:
                Intent account_type=new Intent(Edit.this,AccountType.class);
                account_type.putExtra("type",getResources().getString(R.string.account));
                account_type.putExtra("info",getResources().getString(R.string.detail_of_account_type));
                startActivity(account_type);
                break;
            case R.id.passwrd:
                Intent passwrd=new Intent(Edit.this,Password_Settings.class);
                passwrd.putExtra("type",getResources().getString(R.string.password));
                passwrd.putExtra("info",getResources().getString(R.string.your_password_settings));
                startActivity(passwrd);
                break;
            case R.id.cancel:
                finish();
                break;
                default:
                    break;
        }
    }
}
