package com.swopx.fragment.browse_category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 19-12-2017.
 */

public class Category extends Fragment {
    private RecyclerView recycler_categories;
    private LinearLayoutManager Lmanagercat;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_recycler_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }
    private void intialise_views(View view) {
        ((Category_Main) getActivity()).currentFragment = this;
        recycler_categories=(RecyclerView)view.findViewById(R.id.recycler_categories);
        Lmanagercat = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycler_categories.setLayoutManager(Lmanagercat);
        ((Category_Main)getActivity()).title.setText(getArguments().getString("title"));
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
        try {
            params.put("industry_id" ,getArguments().getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.Get_Category, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        recycler_categories.setVisibility(View.VISIBLE);
                        if(obj.getJSONArray("data").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("data").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("data").getJSONObject(i).getJSONObject("id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("category"));
                                data.setRoyalty(obj.getJSONArray("data").getJSONObject(i).getString("royalty"));
                                data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("sub-category"));
                                items.add(data);
                            }
                            Category_Adapter adapter=new Category_Adapter(getActivity(),items, "category");
                            recycler_categories.setAdapter(adapter);
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
