package com.swopx.fragment.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.adapter.Messengs_Adapter;
import com.swopx.adapter.Past_Project_Adapter;
import com.swopx.adapter.Work_In_Progress_Adapter;
import com.swopx.fragment.my_project_pkg.Post_Project_Main;
import com.swopx.fragment.my_project_pkg.Project_Post;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 06-12-2017.
 */

public class My_Projects extends Fragment {
    private RecyclerView recycler,pastrecycler;
    private LinearLayoutManager Lmanager,pastmanager;
    private RadioGroup radio_grp;
    private RadioButton work_in,past_projects;
    private FloatingActionButton fab;
    private CardView cardview;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_project_layout, container, false);
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
        recycler=(RecyclerView)view.findViewById(R.id.recycler);
        pastrecycler=(RecyclerView)view.findViewById(R.id.pastrecycler);
        radio_grp=(RadioGroup) view.findViewById(R.id.radio_grp);
        fab=(FloatingActionButton) view.findViewById(R.id.fab);
        work_in=(RadioButton) view.findViewById(R.id.work_in);
        cardview=(CardView) view.findViewById(R.id.cardview);
        past_projects=(RadioButton) view.findViewById(R.id.past_projects);
        Lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pastmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(Lmanager);
        pastrecycler.setLayoutManager(pastmanager);
//        Messengs_Adapter adapter=new Messengs_Adapter(getActivity());
//        recycler.setAdapter(adapter);
        work_in.setChecked(true);
        recycler.setVisibility(View.VISIBLE);
        pastrecycler.setVisibility(View.GONE);
        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.work_in)
                {
                    work_in.setTextColor(getResources().getColor(R.color.colorWhite));
                    past_projects.setTextColor(getResources().getColor(R.color.colorBlack));

                    if(Constant.isNetConnected(getActivity()))
                    {
                        getCompany_Work();
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                    }
                }
                else
                {
                    past_projects.setTextColor(getResources().getColor(R.color.colorWhite));
                    work_in.setTextColor(getResources().getColor(R.color.colorBlack));
//                    pastrecycler.setVisibility(View.VISIBLE);
//                    recycler.setVisibility(View.GONE);
                    if(Constant.isNetConnected(getActivity()))
                    {
                        getLeads();
                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), Post_Project_Main.class);
                startActivity(i);
            }
        });

        if(Constant.isNetConnected(getActivity()))
        {
            getCompany_Work();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }

    }

    private void getCompany_Work() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
            params.put("device_id", FirebaseInstanceId.getInstance().getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params,Url_Links.Get_Buy_Request, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    ArrayList<Items> wrk_in=new ArrayList<>();
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("data").length()>0)
                        {

                            for(int i=0;i<obj.getJSONArray("data").length();i++)
                            {
                                if(obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("new")||
                                        obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("approved")
                                        ||obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("booked"))
                                {
                                    Items data=new Items();
                                    data.setId(obj.getJSONArray("data").getJSONObject(i).getJSONObject("id").getString("$oid"));
                                    data.setTime(obj.getJSONArray("data").getJSONObject(i).getString("time"));
//                                    data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("category"));
                                    data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("subcategory"));
                                    data.setBid(obj.getJSONArray("data").getJSONObject(i).getString("bids"));
//                                    data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("subcategory"));
                                    data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("category"));
                                    wrk_in.add(data);
                                }
                                else
                                {
                                    Items data=new Items();
                                    data.setId(obj.getJSONArray("data").getJSONObject(i).getJSONObject("id").getString("$oid"));
                                    data.setTime(obj.getJSONArray("data").getJSONObject(i).getString("time"));
                                    data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("subcategory"));
                                    data.setBid(obj.getJSONArray("data").getJSONObject(i).getString("bids"));
                                    data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("category"));
//                                    past.add(data);
                                }
                            }
                            Work_In_Progress_Adapter recyclerView_Adapter = new Work_In_Progress_Adapter(getActivity(),wrk_in);
                            recycler.setAdapter(recyclerView_Adapter);
//                            Past_Project_Adapter recyclerView_Adapter1 = new Past_Project_Adapter(getActivity(),past);
//                            pastrecycler.setAdapter(recyclerView_Adapter1);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),obj.getString("msg"));
                    }
                    if(wrk_in.size()>0)

                    {
                        recycler.setVisibility(View.VISIBLE);
                        pastrecycler.setVisibility(View.GONE);
                        cardview.setVisibility(View.GONE);
                    }
                    else
                    {
                        cardview.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                        pastrecycler.setVisibility(View.GONE);
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

    private void getLeads() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new LoginApi(getActivity(), params,Url_Links.Get_Leads, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    ArrayList<Items> past=new ArrayList<>();
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        if(obj.getJSONArray("data").length()>0)
                        {

                            for(int i=0;i<obj.getJSONArray("data").length();i++)
                            {
                                if(obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("new")||
                                        obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("approved")
                                        ||obj.getJSONArray("data").getJSONObject(i).getString("is_status").equalsIgnoreCase("booked"))
                                {
                                    Items data=new Items();
                                    data.setId(obj.getJSONArray("data").getJSONObject(i).getString("project_id"));
                                    data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("cat_name"));
                                  data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("subcat_name"));
                                    data.setName(obj.getJSONArray("data").getJSONObject(i).getString("buyer_name"));
                                    data.setQuantity(obj.getJSONArray("data").getJSONObject(i).getString("bid_price"));
                                    data.setUnit(obj.getJSONArray("data").getJSONObject(i).getString("unit"));
                                    past.add(data);
                                }
                                else
                                {
                                    Items data=new Items();
                                    data.setId(obj.getJSONArray("data").getJSONObject(i).getString("project_id"));
                                    data.setTitle(obj.getJSONArray("data").getJSONObject(i).getString("cat_name"));
                                    data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("subcat_name"));
                                    data.setName(obj.getJSONArray("data").getJSONObject(i).getString("buyer_name"));
                                    data.setQuantity(obj.getJSONArray("data").getJSONObject(i).getString("bid_price"));
                                    data.setUnit(obj.getJSONArray("data").getJSONObject(i).getString("unit"));
//                                    past.add(data);
                                }
                            }

//                            Work_In_Progress_Adapter recyclerView_Adapter = new Work_In_Progress_Adapter(getActivity(),wrk_in);
//                            recycler.setAdapter(recyclerView_Adapter);
                            Past_Project_Adapter recyclerView_Adapter1 = new Past_Project_Adapter(getActivity(),past);
                            pastrecycler.setAdapter(recyclerView_Adapter1);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(getActivity(),obj.getString("msg"));
                    }

                    if(past.size()>0)

                    {
                        recycler.setVisibility(View.GONE);
                        pastrecycler.setVisibility(View.VISIBLE);
                        cardview.setVisibility(View.GONE);
                    }
                    else
                    {
                        cardview.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                        pastrecycler.setVisibility(View.GONE);
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
