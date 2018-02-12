package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.swopx.BuildConfig;
import com.swopx.MainActivity;
import com.swopx.R;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Office Work on 18-11-2017.
 */

public class Splash extends AppCompatActivity {
    private int SPLASH_TIME_OUT=3000;
    private com.wang.avi.AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi_dots);
        avi.show();

        if(!TextUtils.isEmpty(CustomPreference.readString(Splash.this,CustomPreference.Client_id,"")))
        {
            SPLASH_TIME_OUT=500;
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               if(!TextUtils.isEmpty(CustomPreference.readString(Splash.this,CustomPreference.Client_id,"")))
               {
                   Intent intent = new Intent(Splash.this, Dashboard_Main.class);
                   intent.putExtra("dashboard","dashboard");
                   startActivity(intent);
                   finish();
                   avi.hide();
               }
               else
                   {
                   Intent intent = new Intent(Splash.this, GetStarted_Screen.class);
                   intent.putExtra("dashboard","dashboard");
                   startActivity(intent);
                   finish();
                       avi.hide();
                  }


            }


        },SPLASH_TIME_OUT);
    }
    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }

}
