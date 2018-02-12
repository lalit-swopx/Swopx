package com.swopx.fragment.my_project_pkg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.swopx.adapter.Category_Adapter;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 23-12-2017.
 */

public class Size_My_Project extends Fragment implements View.OnClickListener {
    private RecyclerView recycler;
    private LinearLayoutManager gmanager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.size_my_project_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }
    private void intialise_views(View view) {
        ((Post_Project_Main) getActivity()).currentFragment = this;
        ((Post_Project_Main) getActivity()).cancel_layout.setVisibility(View.GONE);
        ((Post_Project_Main) getActivity()).back_layout.setVisibility(View.VISIBLE);
        ((Post_Project_Main) getActivity()).next.setVisibility(View.GONE);
        ((Post_Project_Main) getActivity()).title.setText(getActivity().getResources().getString(R.string.post_a_project));

        if(getArguments().getString("sub_cat").equalsIgnoreCase("sub_cat"))
        {
            ((Post_Project_Main) getActivity()).cat_id=getArguments().getString("id");
            ((Post_Project_Main) getActivity()).cate_name=getArguments().getString("name");
            ((Post_Project_Main) getActivity()).unit=getArguments().getString("unit");

        }
        else
        {
//            ((Post_Project_Main) getActivity()).sub_cat_id=getArguments().getString("id");
//            ((Post_Project_Main) getActivity()).sub_cat=getArguments().getString("name");
//            ((Post_Project_Main) getActivity()).unit=getArguments().getString("unit");
        }

        recycler=(RecyclerView)view.findViewById(R.id.recycler);
        gmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(gmanager);
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
            params.put("category_id", getArguments().getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(), params, Url_Links.GetSize_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("sub-category").length()>0)
                        {
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("sub-category").length();i++)
                            {
                                Items data=new Items();
                                data.setId(obj.getJSONArray("sub-category").getJSONObject(i).getJSONObject("_id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("sub-category").getJSONObject(i).getString("title"));
                                data.setNumber(obj.getJSONArray("sub-category").getJSONObject(i).getString("child_cat"));
                                items.add(data);
                            }
                            if(getArguments().getString("sub_cat").equalsIgnoreCase("sub_cat_child"))
                            {
                                Category_Adapter recyclerView_Adapter = new Category_Adapter(getActivity(),items,"sub_cat_child");
                                recycler.setAdapter(recyclerView_Adapter);
                            }
                            else
                            {
                                Category_Adapter recyclerView_Adapter = new Category_Adapter(getActivity(),items,"size");
                                recycler.setAdapter(recyclerView_Adapter);
                            }

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


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.cont:
                break;
            default:
                break;
        }
    }
}
