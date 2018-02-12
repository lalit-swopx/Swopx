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
import com.swopx.adapter.RequestAdapter;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Office Work on 19-12-2017.
 */

public class Request extends Fragment {
    private RecyclerView recycler_categories;
    private LinearLayoutManager Lmanagercat;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_layout, container, false);
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
        ((Category_Main)getActivity()).cate_name=(getArguments().getString("title"));
        ((Category_Main)getActivity()).royalty=(getArguments().getString("royalty"));
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
            params.put("category_id" ,getArguments().getString("id"));
            params.put("client_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.Get_Buyer_Request, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        recycler_categories.setVisibility(View.VISIBLE);
                        if(obj.getJSONArray("itam").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("itam").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("itam").getJSONObject(i).getJSONObject("id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("itam").getJSONObject(i).getString("title"));
                                data.setSub_category(obj.getJSONArray("itam").getJSONObject(i).getString("sub_title"));
                                data.setBid(obj.getJSONArray("itam").getJSONObject(i).getString("bids"));
                                data.setTime(obj.getJSONArray("itam").getJSONObject(i).getString("time"));
                                items.add(data);
                            }
                            RequestAdapter adapter=new RequestAdapter(getActivity(),items, "category");
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
