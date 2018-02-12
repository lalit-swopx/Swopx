package com.swopx.fragment.browse_category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.fragment.login.Otp;
import com.swopx.utils.Constant;

/**
 * Created by Office Work on 19-12-2017.
 */

public class Category_Main extends AppCompatActivity {
    public Fragment currentFragment;
    public TextView back,title;
    public String sub_Indus,cate_name,sub_cat,request_id,royalty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_main_layout);
        back=(TextView)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        sub_Indus= getIntent().getStringExtra("title");
        if(getIntent().hasExtra("second"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            Category fragment = new Category();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        }

    }

    @Override
    public void onBackPressed() {
        Constant.Log("Current Fragment=====",":"+currentFragment);
        if(currentFragment instanceof Category)
        {
            finish();
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        currentFragment.onActivityResult(requestCode, resultCode, data);

    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }
}
