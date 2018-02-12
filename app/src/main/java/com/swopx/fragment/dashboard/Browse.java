package com.swopx.fragment.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Category_Adapter;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.adapter.Messengs_Adapter;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Browse  extends Fragment {
    private RecyclerView recycler_categories,recycler_recommended;
    private LinearLayoutManager Lmanager,Lmanagercat;
    private CardView cat;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse_layout, container, false);
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
        recycler_recommended=(RecyclerView)view.findViewById(R.id.recycler_recommended);
        cat=(CardView) view.findViewById(R.id.cat);
        recycler_categories=(RecyclerView)view.findViewById(R.id.recycler_categories);
        Lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        Lmanagercat = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycler_recommended.setLayoutManager(Lmanager);
        recycler_categories.setLayoutManager(Lmanagercat);
        if(Constant.isNetConnected(getActivity()))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
    }
    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();


        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),Url_Links.Get_Sub_Indus_Browse, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        recycler_categories.setVisibility(View.VISIBLE);
                        cat.setVisibility(View.GONE);
                        if(obj.getJSONArray("industry").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("industry").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("industry").getJSONObject(i).getJSONObject("_id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("industry").getJSONObject(i).getString("title"));
                                items.add(data);
                            }
                            Category_Adapter adapter=new Category_Adapter(getActivity(),items,"subinstry");
                            recycler_categories.setAdapter(adapter);

                        }
                        else
                        {
                            recycler_categories.setVisibility(View.GONE);
                            cat.setVisibility(View.VISIBLE);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),obj.getString("msg"));
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
