package com.swopx.fragment.dashboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.fragment.edit_package.Basic_Info;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 24-11-2017.
 */

public class Dashboard extends Fragment {
 private RecyclerView recycler;
 private GridLayoutManager gmanager;
    private EditText search_edt;

    // private ArrayList<Items> items=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_views(view);
    }

    private void init_views(View view) {
//        ((MainActivity) getActivity()).currentFragment = this;
        recycler=(RecyclerView)view.findViewById(R.id.recycler);
        gmanager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(gmanager);

        put_values(getActivity().getResources().getDrawable(R.drawable.stone_grit));
        put_values(getActivity().getResources().getDrawable(R.drawable.core_sand));
        put_values(getActivity().getResources().getDrawable(R.drawable.cement));
        put_values(getActivity().getResources().getDrawable(R.drawable.concreate));
        put_values(getActivity().getResources().getDrawable(R.drawable.contract));
//        put_values(getActivity().getResources().getDrawable(R.drawable.architect));
        put_values(getActivity().getResources().getDrawable(R.drawable.solar_roof));
        put_values(getActivity().getResources().getDrawable(R.drawable.steel_bar));
        put_values(getActivity().getResources().getDrawable(R.drawable.architect));
        put_values(getActivity().getResources().getDrawable(R.drawable.brick));
//        put_values(getActivity().getResources().getDrawable(R.drawable.remodelling));
//        put_values(getActivity().getResources().getDrawable(R.drawable.cement));
//        put_values(getActivity().getResources().getDrawable(R.drawable.other));

        if(Constant.isNetConnected(getActivity()))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
    }

    private void put_values(Drawable drawable) {
        Items i=new Items();
        i.setImage(drawable);
//        items.add(i);
    }
    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
//        Constant.Log("reponse Login", "=================" + Url_Links.Company_Entity_Url+CustomPreference.readString(Basic_Info.this,CustomPreference.Client_id,""));
        new LoginApi(getActivity(), Url_Links.SubIndustry_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("industry").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("industry").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("industry").getJSONObject(i).getJSONObject("_id").getString("$oid"));
                                data.setImage_url(obj.getJSONArray("industry").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("industry").getJSONObject(i).getString("title"));
                                items.add(data);
                            }
                            Grid_Adapter recyclerView_Adapter = new Grid_Adapter(getActivity(),items, "category", search_edt);
                            recycler.setAdapter(recyclerView_Adapter);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),obj.getString("msg"));
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
