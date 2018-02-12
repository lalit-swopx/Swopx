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
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 13-12-2017.
 */

public class Subcategory extends Fragment implements View.OnClickListener {
    private RecyclerView recycler;
    private GridLayoutManager gmanager;
    private TextView cont;
    private EditText search_edt;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_category_layout, container, false);
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
        ((Dashboard_Main) getActivity()).header2.setVisibility(View.VISIBLE);
        recycler=(RecyclerView)view.findViewById(R.id.recycler);
        cont=(TextView) view.findViewById(R.id.cont);
        gmanager = new GridLayoutManager(getActivity(), 3);
        recycler.setLayoutManager(gmanager);
        onClick();
        if(Constant.isNetConnected(getActivity()))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }

//        Lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        recycler.setLayoutManager(Lmanager);
//        Messengs_Adapter adapter=new Messengs_Adapter(getActivity());
//        recycler.setAdapter(adapter);

    }

    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();

        try {
            ((Dashboard_Main)getActivity()).industry_id=getArguments().getString("id");
            ((Dashboard_Main)getActivity()).industry_name=getArguments().getString("name");
            params.put("industry_id", getArguments().getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(), params,Url_Links.GetCategory_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("category").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("category").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("category").getJSONObject(i).getJSONObject("_id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("category").getJSONObject(i).getString("title"));
                                items.add(data);
                            }
                            Grid_Adapter recyclerView_Adapter = new Grid_Adapter(getActivity(),items,"subcategory", search_edt);
                            recycler.setAdapter(recyclerView_Adapter);
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
    private void onClick() {
        cont.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.cont:
//                Intent basic=new Intent(getActivity(),Edit.class);
//                startActivity(basic);
                break;
            default:
                break;
        }
    }
}
