package com.swopx.registration;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.swopx.BuildConfig;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.app.Config;
import com.swopx.fragment.login.Confirm_password;
import com.swopx.fragment.login.Email;
import com.swopx.fragment.login.Forget_Password;
import com.swopx.fragment.login.Name;
import com.swopx.fragment.login.Otp;
import com.swopx.fragment.login.Password;
import com.swopx.fragment.login.Verify_OTP;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Office Work on 01-12-2017.
 */

public class Login_Main extends AppCompatActivity {
    public Fragment currentFragment;
    private ImageButton bck_btn;
    public TextView skip;
    public String f_name="",l_name="", passwrd="",con_passwrd="",email_address="",reffereal_code="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_layout);
        Constant.Log("Current Fragment=====","=================:"+FirebaseInstanceId.getInstance().getToken());
        broadcastReciever();
        Constant.displayFirebaseRegId(this);
        intialize();
    }

    private void intialize() {
        bck_btn=(ImageButton)findViewById(R.id.bck_btn);
        skip=(TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password fragment = new Password();
                Bundle bundle = new Bundle();
                bundle.putString("signIn", "signIn");
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            }
        });
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        replaceFragment(new Otp());
        if(Constant.isNetConnected(Login_Main.this))
        {
            getversion();
        }
        else
        {
            Constant.ToastShort(Login_Main.this,getResources().getString(R.string.internet_connection));
        }
    }
    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }
    public void replaceFragmentBack(Fragment fragment)
    {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }

    @Override
    public void onBackPressed() {

        if(currentFragment instanceof Otp)
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        System.gc();
    }
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private void broadcastReciever() {

        Constant.Log("Current Fragment=====","======:"+currentFragment);
        if (Constant.isNetConnected(Login_Main.this)) {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // checking for type intent filter
                    if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                        Constant.Log("Current Fragment=====","======1:"+currentFragment);
                        Constant.displayFirebaseRegId(context);

                    } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                        // new push notification is received
                        Constant.Log("Current Fragment=====","======2:"+currentFragment);
                        String message = intent.getStringExtra("message");
                        Constant.ToastShort(context, "Push notification: ");
                    }
                }
            };
        } else {
            Constant.ToastShort(Login_Main.this, Login_Main.this.getResources().getString(R.string.internet_connection));

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        //register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

    }


    private void getversion() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();


        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Login_Main.this, Url_Links.Get_Version_Code, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
//                        CustomPreference.writeInteger(Dashboard_Main.this,
//                                CustomPreference.New_Version,Integer.parseInt(obj.getString("version")));
                        int versionCode=0;
                        try {
                            PackageInfo packinfo= getPackageManager().getPackageInfo(getPackageName(),0);
                            versionCode=packinfo.versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
//                        int versionCode = BuildConfig.VERSION_CODE;
                        int latest_version = Integer.parseInt(obj.getString("version"));
                        String versionName = BuildConfig.VERSION_NAME;
//                        CustomPreference.writeInteger(Splash.this,CustomPreference.Old_version,versionCode);
//                        Constant.ToastShort(Login_Main.this, versionName+versionCode);
                        if (versionCode<latest_version)
                        {
                            setUpdateDialog(Login_Main.this);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Login_Main.this, error.getMessage());
            }
        };
    }

    /**
     * Dialog open when new version of app updated on playstore.
     */
    public void setUpdateDialog(final Activity activity) {
        final android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(activity);
        alertbox.setTitle("Update App");
        alertbox.setMessage("New version found. Please update your App");
        alertbox.setIcon(R.drawable.black_asterick);
        alertbox.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // finish used for destroyed activity
                        if (Constant.isNetConnected(Login_Main.this)) {

                            final String appPackageName = getPackageName();
                            Intent i=new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("market://details?id="+appPackageName));
                            startActivity(i);
                        }
                    }
                });

        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //GlobelMethod.setValuesUpdate(activity, values);
            }
        });

        alertbox.show();
    }

}
