package com.swopx.registration;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swopx.BuildConfig;
import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Category_Adapter;
import com.swopx.adapter.View_Pager_Adapter;
import com.swopx.chat.Chat;
import com.swopx.chat.UserDetails;
import com.swopx.fragment.browse_category.Category_Main;
import com.swopx.fragment.browse_category.Request_Detail;
import com.swopx.fragment.dashboard.Account;
import com.swopx.fragment.dashboard.Browse;
import com.swopx.fragment.dashboard.Dashboard;
import com.swopx.fragment.dashboard.Home_Class;
import com.swopx.fragment.dashboard.Messenger;
import com.swopx.fragment.dashboard.My_Projects;
import com.swopx.fragment.dashboard.Notification;
import com.swopx.fragment.login.Name;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Office Work on 02-12-2017.
 */

public class Dashboard_Main extends AppCompatActivity implements View.OnClickListener {
    public Fragment currentFragment;
    private ImageButton messages_button, browser, folder_button, notification_button, profile;
    public LinearLayout header, header2,header3;
    public ImageButton back, home, home1, share_button, back_button1;
    private int count=0;
    private TextView title1,back1;
    //    public ArrayList<Items> phon_no_array;
    private int SPLASH_TIME_OUT = 3000;
//    resquest values industry_id,industry_name,cat_id,cat_name,subcat_id,subcat_name,when,qty,contact

    public String industry_id = "", industry_name = "", cat_id = "", cat_name = "", subcat_id = "", subcat_name = "", when = "", qty = "", contact = "";
//    private TabLayout tabLayout;
//    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_main_layout);
        intialise_views();
    }

    private void intialise_views() {

        messages_button = (ImageButton) findViewById(R.id.messages_button);
        browser = (ImageButton) findViewById(R.id.browser);
        folder_button = (ImageButton) findViewById(R.id.folder_button);
        back_button1 = (ImageButton) findViewById(R.id.back_button1);
        share_button = (ImageButton) findViewById(R.id.share_button);
        home1 = (ImageButton) findViewById(R.id.home1);
        notification_button = (ImageButton) findViewById(R.id.notification_button);
        back = (ImageButton) findViewById(R.id.back);
        back1 = (TextView) findViewById(R.id.back1);
        home = (ImageButton) findViewById(R.id.home);
        header = (LinearLayout) findViewById(R.id.header);
        header2 = (LinearLayout) findViewById(R.id.header2);
        header3 = (LinearLayout) findViewById(R.id.header3);
        profile = (ImageButton) findViewById(R.id.profile);
        title1 = (TextView) findViewById(R.id.title1);
        onCLick();
        CustomPreference.phon_no_array.clear();
        new AsyncCaller().execute();
//        replaceFragment(new Home_Class());
        if (getIntent().hasExtra("type")) {
            if (getIntent().getStringExtra("type").equalsIgnoreCase("Project")) {
              /*  messages_button.setImageResource(R.drawable.footer_message_deselect);
                notification_button.setImageResource(R.drawable.footer_notification);
                profile.setImageResource(R.drawable.footer_profile);
                folder_button.setImageResource(R.drawable.footer_project);
                browser.setImageResource(R.drawable.footer_search_select);
                replaceFragment(new Browse());*/
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getStringExtra("project_id"));
                bundle.putString("type", "notification");
                Request_Detail fragment = new Request_Detail();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            } else {
                project_folder();
            }

        } else {
            project_folder();
        }
        if (Constant.isNetConnected(Dashboard_Main.this)) {
//            post_version_code();
            getversion();
        } else {
            Constant.ToastShort(Dashboard_Main.this, getResources().getString(R.string.internet_connection));
        }


//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
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
                        if (Constant.isNetConnected(Dashboard_Main.this)) {

                            final String appPackageName = getPackageName();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("market://details?id=" + appPackageName));
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

    private void project_folder() {
        messages_button.setImageResource(R.drawable.chat_icon_new);
        notification_button.setImageResource(R.drawable.update);
        profile.setImageResource(R.drawable.footer_profile);
        folder_button.setImageResource(R.drawable.footer_project_select);
        browser.setImageResource(R.drawable.leads_icon);
        replaceFragmentBack(new My_Projects());
    }

    private void onCLick() {
        messages_button.setOnClickListener(this);
        browser.setOnClickListener(this);
        folder_button.setOnClickListener(this);
        notification_button.setOnClickListener(this);
        profile.setOnClickListener(this);
        home.setOnClickListener(this);
        home1.setOnClickListener(this);
        back.setOnClickListener(this);
        back_button1.setOnClickListener(this);
        back1.setOnClickListener(this);

        Constant.Sop("Device Id=======" + FirebaseInstanceId.getInstance().getToken());
    }

    private void setupViewPager(ViewPager viewPager) {
        View_Pager_Adapter adapter = new View_Pager_Adapter(getSupportFragmentManager());
        adapter.addFragment(new Dashboard(), "Popular");
//        adapter.addFragment(new TwoFragment(), "TWO");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/
        ;
    }

    public void replaceFragmentBack(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/
        ;
    }

    @Override
    public void onBackPressed() {

        if (currentFragment instanceof My_Projects || currentFragment instanceof Account || currentFragment instanceof Browse ||
                currentFragment instanceof Messenger || currentFragment instanceof Notification) {
//            finish();
            count=count+1;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    count=0;
                }


            }, SPLASH_TIME_OUT);
            if(count>0)
            {
                if(count==1)
                {
                    Constant.ToastShort(Dashboard_Main.this,"Press again to close Swopx");
                }
                if(count==2)
                {
                    finish();
                }

            }

        }
        else if (currentFragment instanceof Request_Detail )
        {
            messages_button.setImageResource(R.drawable.chat_icon_new);
            notification_button.setImageResource(R.drawable.update);
            profile.setImageResource(R.drawable.footer_profile);
            folder_button.setImageResource(R.drawable.footer_project);
            browser.setImageResource(R.drawable.leads_fill);
            replaceFragment(new Browse());
        }
        else {
            super.onBackPressed();
        }

    }

    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard_Main.this, R.style.CustomDialog);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.coupon_layout, null);
        dialogBuilder.setView(dialogView);
//        RadioGroup radio_grp=(RadioGroup)dialogView.findViewById(R.id.radio_grp);
//        final RadioButton resend = (RadioButton) dialogView.findViewById(R.id.resend);
//        final RadioButton ok_btn = (RadioButton) dialogView.findViewById(R.id.ok_btn);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setGravity(Gravity.CENTER);
//        editText.setText("test label");
        alertDialog.show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.messages_button:
                messages_button.setImageResource(R.drawable.chat_icon_new_fill);
                notification_button.setImageResource(R.drawable.update);
                profile.setImageResource(R.drawable.footer_profile);
                folder_button.setImageResource(R.drawable.footer_project);
                browser.setImageResource(R.drawable.leads_icon);
                replaceFragment(new Messenger());
                break;

            case R.id.notification_button:
                messages_button.setImageResource(R.drawable.chat_icon_new);
                notification_button.setImageResource(R.drawable.update_fill);
                profile.setImageResource(R.drawable.footer_profile);
                folder_button.setImageResource(R.drawable.footer_project);
                browser.setImageResource(R.drawable.leads_icon);
                replaceFragment(new Notification());
                break;

            case R.id.profile:
                messages_button.setImageResource(R.drawable.chat_icon_new);
                notification_button.setImageResource(R.drawable.update);
                profile.setImageResource(R.drawable.footer_profile_select);
                folder_button.setImageResource(R.drawable.footer_project);
                browser.setImageResource(R.drawable.leads_icon);
                replaceFragment(new Account());
                break;

            case R.id.folder_button:
                project_folder();
                break;

            case R.id.browser:
                messages_button.setImageResource(R.drawable.chat_icon_new);
                notification_button.setImageResource(R.drawable.update);
                profile.setImageResource(R.drawable.footer_profile);
                folder_button.setImageResource(R.drawable.footer_project);
                browser.setImageResource(R.drawable.leads_fill);
                replaceFragment(new Browse());
                break;

            case R.id.back_button1:
                onBackPressed();
                break;

                case R.id.back1:
                onBackPressed();
                break;

            case R.id.home1:
                replaceFragment(new Home_Class());
                break;

            case R.id.back:
                onBackPressed();
                break;

            case R.id.home:
                replaceFragment(new Home_Class());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//                Constant.ToastShort(Dashboard_Main.this,"onResume"+getFragmentManager().getBackStackEntryCount());
//        if(getFragmentManager().getBackStackEntryCount() > 0)
//            getFragmentManager().popBackStack();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
//        CustomLoader customLoader;

        public AsyncCaller() {

//            customLoader = new CustomLoader(Dashboard_Main.this, android.R.style.Theme_Translucent_NoTitleBar);
//            customLoader.show();
//            customLoader.setCanceledOnTouchOutside(false);
//            customLoader.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            phon_no_array=new ArrayList<>();


//            String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
//                    + ("1") + "'";
//            String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
//                    + " COLLATE LOCALIZED ASC";
//            cur = context.getContentResolver().query(
//                    ContactsContract.Contacts.CONTENT_URI, projection, selection
//                            + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER
//                            + "=1", null, sortOrder);// this query only return contacts which had phone number and not duplicated

            CustomPreference.phon_no_array.clear();
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    /*String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME +"COLLATE NOCASE ASC"));*/

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Items i = new Items();
                            i.setName(name);
                            i.setNumber(phoneNo);
                            i.setIs_checked(false);
                            i.setId(id);
                            CustomPreference.phon_no_array.add(i);
//                        Constant.Log("Name====", "Name: " + name);
//                        Constant.Log("Phone Number", "Phone Number: " + phoneNo);
                        }
                        pCur.close();
                    }
                }
            }

//            Constant.Log("Phone NumberArray=====", "Phone Number: " + phon_no_array.size()+"aRRAY"+phon_no_array);
            if (cur != null) {
                cur.close();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            loader(getActivity());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Collections.sort(CustomPreference.phon_no_array, Items.BY_ALPHA);
//            Constant.ToastShort(MainActivity.this, "DAta Inserted in Table");
//            customLoader.dismiss();
        }
    }

    private void post_version_code() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            {
                params.put("version_id", "5");
            }

            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(Dashboard_Main.this, params, Url_Links.Post_Version_Code, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);

            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Dashboard_Main.this, error.getMessage());
            }
        };
    }

    private void getversion() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();


        Constant.Log("reponse Login", "=================" + params);
        new LoginApi(Dashboard_Main.this, Url_Links.Get_Version_Code, false) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj = response.getJSONObject("result");

                    if (obj.getString("status").equalsIgnoreCase("200")) {
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
//                        Constant.ToastShort(Dashboard_Main.this, versionName+versionCode);
                        if (versionCode < latest_version) {
                            setUpdateDialog(Dashboard_Main.this);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Dashboard_Main.this, error.getMessage());
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
