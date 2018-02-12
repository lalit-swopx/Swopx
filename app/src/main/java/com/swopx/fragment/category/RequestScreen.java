package com.swopx.fragment.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.fragment.dashboard.Dashboard;
import com.swopx.fragment.dashboard.Home_Class;
import com.swopx.registration.Craete_Account;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Splash;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import java.util.ArrayList;

/**
 * Created by Office Work on 13-12-2017.
 */

public class RequestScreen extends Fragment {
    private static int REQUEST_TIME_OUT=3000;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_submit_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

           ((Dashboard_Main)getActivity()).replaceFragment(new Home_Class());

            }


        },REQUEST_TIME_OUT);
    }





}
