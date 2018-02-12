package com.swopx.fragment.dashboard;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Messengs_Adapter;
import com.swopx.fragment.browse_category.Bid_Main;
import com.swopx.fragment.edit_package.Basic_Info;
import com.swopx.fragment.edit_package.Edit;
import com.swopx.fragment.edit_package.Terms;
import com.swopx.fragment.my_project_pkg.Post_Project_Main;
import com.swopx.registration.Blank_Activity;
import com.swopx.registration.Connection_Activity;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Refer_Earn;
import com.swopx.registration.WalkThrough_Activity;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomDialog;
import com.swopx.utils.CustomPreference;
import com.swopx.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Account extends Fragment implements View.OnClickListener {
    private LinearLayout edit_info,invite_frndz,logout_info,walk_info,profile_layout,post_project,policy_info,terms_info;
    private TextView name,wallet,app_version;
    private ImageView profile_pic;
    final int Pick_FROM_GALLERY = 200;
    final int PICK_CAMERA_REQUEST = 100;
    private int cameraId = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }
    private void intialise_views(View view) {
        ((Dashboard_Main) getActivity()).currentFragment = this;
        ((Dashboard_Main) getActivity()).header.setVisibility(View.GONE);
        ((Dashboard_Main) getActivity()).header2.setVisibility(View.GONE);
        edit_info=(LinearLayout) view.findViewById(R.id.edit_info);
        terms_info=(LinearLayout) view.findViewById(R.id.terms_info);
        policy_info=(LinearLayout) view.findViewById(R.id.policy_info);
        invite_frndz=(LinearLayout) view.findViewById(R.id.invite_frndz);
        logout_info=(LinearLayout) view.findViewById(R.id.logout_info);
        post_project=(LinearLayout) view.findViewById(R.id.post_project);
        walk_info=(LinearLayout) view.findViewById(R.id.walk_info);
        profile_pic=(ImageView) view.findViewById(R.id.profile_pic);
        profile_layout=(LinearLayout) view.findViewById(R.id.profile_layout);
        name=(TextView) view.findViewById(R.id.name);
        wallet=(TextView) view.findViewById(R.id.wallet);
        app_version=(TextView) view.findViewById(R.id.app_version);

        try {
            PackageInfo packinfo= getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
//            Constant.ToastShort(getActivity(),""+packinfo.versionCode);
            app_version.setText(String.valueOf(packinfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        name.setText(CustomPreference.readString(getActivity(),CustomPreference.Client_name,""));
        wallet.setText("₹"+CustomPreference.readString(getActivity(),CustomPreference.Wallet_Amount,"")+" INR");
        onClick();
//        Lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        recycler.setLayoutManager(Lmanager);
//        Messengs_Adapter adapter=new Messengs_Adapter(getActivity());
//        recycler.setAdapter(adapter);

        if(Constant.isNetConnected(getActivity()))
        {
            get_Wallet();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
    }

    private void onClick() {
        edit_info.setOnClickListener(this);
        invite_frndz.setOnClickListener(this);
        logout_info.setOnClickListener(this);
        walk_info.setOnClickListener(this);
        profile_layout.setOnClickListener(this);
        post_project.setOnClickListener(this);
        terms_info.setOnClickListener(this);
        policy_info.setOnClickListener(this);
//        profile_pic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.edit_info:
                Intent basic=new Intent(getActivity(),Edit.class);
                startActivity(basic);
                break;


                case R.id.profile_pic:
                    getImage();
                break;

            case R.id.policy_info:
                Intent pol=new Intent(getActivity(),Terms.class);
                pol.putExtra("value","policy");
                startActivity(pol);
                break;

            case R.id.terms_info:
                Intent ter=new Intent(getActivity(),Terms.class);
                ter.putExtra("value","terms");
                startActivity(ter);
                break;


            case R.id.post_project:
                Intent i=new Intent(getActivity(), Post_Project_Main.class);
                startActivity(i);
                break;

                case R.id.profile_layout:
                Intent blank=new Intent(getActivity(),Blank_Activity.class);
                startActivity(blank);
                break;

                case R.id.invite_frndz:
//                Intent invite=new Intent(getActivity(),Connection_Activity.class);
                Intent invite=new Intent(getActivity(),Refer_Earn.class);
                startActivity(invite);
                break;

                case R.id.logout_info:
                    CustomDialog cdd = new CustomDialog(getActivity(), "logout");
                    cdd.show();
                break;


                case R.id.walk_info:
                    Intent intent = new Intent(getActivity(), WalkThrough_Activity.class);
                    startActivity(intent);
                    getActivity().finish();
                break;
            default:
                break;
        }
    }



    public void getImage() {
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(getActivity());
        exitDialog.setTitle("Choose");
        exitDialog.setMessage("Select from ?");
        exitDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                cameraId = Constant.findFrontFacingCamera();
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PICK_CAMERA_REQUEST);
                    return;
                }
                Util.openCamera(getActivity(), PICK_CAMERA_REQUEST, 0);

            }
        });

        exitDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            Pick_FROM_GALLERY);
                    return;
                }
                Util.openGallery(getActivity(), Pick_FROM_GALLERY);
                // imageBool_first = false;
            }
        });
        exitDialog.show();
    }


    private void get_Wallet() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.Get_Wallet, false) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        CustomPreference.writeString(getActivity(),CustomPreference.Wallet_Amount,obj.getString("wallet"));
                        wallet.setText("₹"+CustomPreference.readString(getActivity(),CustomPreference.Wallet_Amount,"")+" INR");

                    }

//                    Constant.ToastShort(getActivity(),obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(getActivity(), error.getMessage());
            }
        };

    }
}
