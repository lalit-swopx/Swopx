package com.swopx.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.fragment.edit_package.Edit;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 13-12-2017.
 */

public class Quantity extends Fragment {
    private RecyclerView recycler;
    private GridLayoutManager gmanager;
    private ArrayList<Items> items=new ArrayList<>();
    private EditText search_edt;
    private TextView cont;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quantity_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_views(view);
    }

    private void init_views(View view) {
        recycler=(RecyclerView)view.findViewById(R.id.recycler);
        search_edt=(EditText) view.findViewById(R.id.search_edt);
        cont=(TextView) view.findViewById(R.id.cont);
        gmanager = new GridLayoutManager(getActivity(), 3);
        recycler.setLayoutManager(gmanager);

        put_values("20 TONS");
        put_values("50 TONS");
        put_values("100 TONS");
        put_values("500 MM");
        put_values("1000 MM");
        put_values("2000 MM");

        Grid_Adapter recyclerView_Adapter = new Grid_Adapter(getActivity(),items, "quantity",search_edt);
        recycler.setAdapter(recyclerView_Adapter);
        if(Constant.isNetConnected(getActivity()))
        {
//            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dashboard_Main)getActivity()).qty=search_edt.getText().toString().trim();
                if(CustomPreference.readString(getActivity(),CustomPreference.User_Type,"").equalsIgnoreCase("2"))
                {
                    ((Dashboard_Main) getActivity()).replaceFragment(new GuestUser_Otp());
                }
                else
                {
                    if(Constant.isNetConnected(getActivity()))
                    {
                        Otp();
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                    }
                }

            }
        });
    }

    private void put_values(String drawable) {
        Items i=new Items();
        i.setTitle(drawable);
        items.add(i);
    }
    private void Otp() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("industry_id", ((Dashboard_Main)getActivity()).industry_id);
            params.put("industry_name", ((Dashboard_Main)getActivity()).industry_name);
            params.put("cat_id", ((Dashboard_Main)getActivity()).cat_id);
            params.put("cat_name", ((Dashboard_Main)getActivity()).cat_name);
            params.put("subcat_id", ((Dashboard_Main)getActivity()).subcat_id);
            params.put("subcat_name", ((Dashboard_Main)getActivity()).subcat_name);
            params.put("when", ((Dashboard_Main)getActivity()).when);
            params.put("qty", ((Dashboard_Main)getActivity()).qty);
            params.put("client_id", CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params, Url_Links.Buyer_Request_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {

                    if(response.getJSONObject("result").getString("status").equalsIgnoreCase("200"))
                    {

                        ((Dashboard_Main) getActivity()).replaceFragment(new RequestScreen());
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),response.getJSONObject("result").getString("msg"));
                    }



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
