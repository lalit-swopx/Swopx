package com.swopx.fragment.browse_category;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Category_Adapter;
import com.swopx.fragment.dashboard.Dashboard;
import com.swopx.fragment.edit_package.Basic_Info;
import com.swopx.fragment.edit_package.Edit;
import com.swopx.fragment.my_project_pkg.Project_Post;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 19-12-2017.
 */

public class Request_Detail extends Fragment {

    private TextView title1,status,time,description,quantity,project_id,rating_count,place_bid,subcat,total_bid,avg_bid;
    private Spinner spn_convence;
    private String convence_value="";
    private RatingBar rate;
    private String is_company="",unit="";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_detail_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }
    private void intialise_views(View view) {
        if(getArguments().getString("type").equalsIgnoreCase("notification"))
        {
//            Constant.ToastShort(getActivity(),getArguments().getString("type"));
            ((Dashboard_Main) getActivity()).currentFragment = this;
//            ((Dashboard_Main)getActivity()).title1.setText(getArguments().getString("title"));
            ((Dashboard_Main)getActivity()).header3.setVisibility(View.GONE);
            ((Dashboard_Main)getActivity()).header2.setVisibility(View.GONE);
            ((Dashboard_Main)getActivity()).header.setVisibility(View.VISIBLE);
        }
        else
        {
            ((Category_Main) getActivity()).currentFragment = this;
            ((Category_Main)getActivity()).title.setText(getArguments().getString("title"));
        }

        title1=(TextView)view.findViewById(R.id.title);
        status=(TextView)view.findViewById(R.id.status);
        subcat=(TextView)view.findViewById(R.id.subcat);
        time=(TextView)view.findViewById(R.id.time);
        avg_bid=(TextView)view.findViewById(R.id.avg_bid);
        total_bid=(TextView)view.findViewById(R.id.total_bid);
        description=(TextView)view.findViewById(R.id.description);
        quantity=(TextView)view.findViewById(R.id.quantity);
        project_id=(TextView)view.findViewById(R.id.project_id);
        rating_count=(TextView)view.findViewById(R.id.rating_count);
        place_bid=(TextView)view.findViewById(R.id.place_bid);
        spn_convence=(Spinner) view.findViewById(R.id.spn_convence);
        rate=(RatingBar) view.findViewById(R.id.rate);
        ArrayList categories = new ArrayList();
        categories.add("Select");
        categories.add("Not Required");
        categories.add("Required");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
        spn_convence.setAdapter(dataAdapter);
        place_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(CustomPreference.readString(getActivity(),CustomPreference.Company_registered,"").equalsIgnoreCase("yes"))
                    {
                        String quan[]=quantity.getText().toString().split(" ");
                        Constant.Log("Quantity====","Quantity"+quantity.getText()+quan[0]);
                        Intent i=new Intent(getActivity(),Bid_Main.class);
                        if(getArguments().getString("type").equalsIgnoreCase("notification"))
                        {
                            i.putExtra("request_id",getArguments().getString("id"));
                            i.putExtra("sub_Indus",getArguments().getString("sub_industry"));
                            i.putExtra("royalty",getArguments().getString("roylty"));
                        }
                        else
                        {
                            ((Category_Main)getActivity()).request_id=(getArguments().getString("id"));
                            i.putExtra("request_id",((Category_Main)getActivity()).request_id);
                            i.putExtra("sub_Indus",((Category_Main)getActivity()).sub_Indus);
                            i.putExtra("royalty",((Category_Main)getActivity()).royalty);
                        }
                        i.putExtra("cate_name",title1.getText().toString());

                        i.putExtra("sub_cat",subcat.getText().toString());
                        i.putExtra("unit",unit);

                        i.putExtra("quantity",quan[0]);
                        i.putExtra("quantity1",quan[1]);
                        startActivity(i);
                        getActivity().finish();
                    }
                    else
                    {
                        alert_dialog();
//                    Constant.ToastShort(getActivity(),"You are not able to place a bid");
                    }
            }
        });
        spn_convence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    convence_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(Constant.isNetConnected(getActivity()))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
    }



    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.commom_custom_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView btn_no=(TextView)dialogView.findViewById(R.id.btn_no);
        TextView btn_yes=(TextView)dialogView.findViewById(R.id.btn_yes);
        TextView header=(TextView)dialogView.findViewById(R.id.header);
        TextView title=(TextView)dialogView.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.place_a_bid));
        header.setText(getResources().getString(R.string.registered_cmpny));
        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));;
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent basic=new Intent(getActivity(),Basic_Info.class);
                basic.putExtra("type",getResources().getString(R.string.basic_info));
                basic.putExtra("info",getResources().getString(R.string.basic_company_information));
                startActivity(basic);


            }
        });

        alertDialog.show();
    }
    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

//            ((Category_Main)getActivity()).request_id=(getArguments().getString("id"));
            params.put("request_id" ,getArguments().getString("id"));
            params.put("client_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.Get_BuyerDetailById, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                       title1.setText(obj.getString("category"));
                        time.setText(obj.getString("time"));
                       description.setText(obj.getString("description"));
                       quantity.setText(obj.getString("qty"));
                        unit=obj.getString("unit");
                       total_bid.setText(obj.getString("total_bid"));
                       avg_bid.setText(obj.getString("avg_bid"));
                        CustomPreference.writeString(getActivity(),CustomPreference.Company_registered,obj.getString("is_company"));
                        is_company=obj.getString("is_company");

                       project_id.setText(obj.getJSONObject("project_id").getString("$oid"));
                       if(obj.getString("rating").equalsIgnoreCase("0"))
                       {
                           rating_count.setText("0.0");
                       }
                       else
                       {
                           rating_count.setText(obj.getString("rating"));
                       }

                        rate.setRating(Float.parseFloat(obj.getString("rating")));
                       if(obj.has("category_id"))
                       {
                           title1.setTag(obj.getString("category_id"));
                       }
                        if(obj.has("sub_category"))
                        {
                            subcat.setText(obj.getString("sub_category"));
//                            ((Category_Main)getActivity()).sub_cat=(obj.getString("sub_category"));
                        }

                        if(obj.has("sub_category_id"))
                        {
//                            title1.setTag(obj.getString("sub_category"));
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
