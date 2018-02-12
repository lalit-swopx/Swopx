package com.swopx.fragment.my_project_pkg;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Bid_Adapter;
import com.swopx.adapter.Category_Adapter;
import com.swopx.fragment.browse_category.Category_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 26-12-2017.
 */

public class Bid_List extends AppCompatActivity  {
    private RecyclerView recycler_categories;
    private LinearLayoutManager Lmanagercat;
    private TextView back,view_content;
    final int Call_COMPLETED = 506;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_detail_main_layout);
        intialise();
    }

    private void intialise() {
        recycler_categories=(RecyclerView)findViewById(R.id.recycler_categories);
        Lmanagercat = new LinearLayoutManager(Bid_List.this,LinearLayoutManager.VERTICAL,false);
        recycler_categories.setLayoutManager(Lmanagercat);
        back=(TextView)findViewById(R.id.back);
        view_content=(TextView)findViewById(R.id.view_content);
//        EndCallListener callListener = new EndCallListener();
//        TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ActivityCompat.requestPermissions(Bid_List.this,
//                new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE},
//                Call_COMPLETED);
        if(Constant.isNetConnected(Bid_List.this))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(Bid_List.this,getResources().getString(R.string.internet_connection));
        }
    }

    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("project_id" ,getIntent().getStringExtra("bid_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Bid_List.this,params, Url_Links.Get_Bid, true) {
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

//                            view_content.setText(obj.getString("project_name"));
                            ArrayList<Items> items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("data").length();i++)
                            {
                                Items data=new Items();
                                data.setProject_name(obj.getJSONArray("data").getJSONObject(i).getString("project_name"));
                                data.setName(obj.getJSONArray("data").getJSONObject(i).getString("name"));
                                data.setMobile(obj.getJSONArray("data").getJSONObject(i).getString("mobile"));
                                data.setBid_id(obj.getJSONArray("data").getJSONObject(i).getString("bid_id"));
                                data.setId(obj.getJSONArray("data").getJSONObject(i).getString("id"));
                                if(obj.getJSONArray("data").getJSONObject(i).has("profile_pic"))
                                {
                                    data.setImage_url(obj.getJSONArray("data").getJSONObject(i).getString("profile_pic"));
                                }

                                data.setRate(obj.getJSONArray("data").getJSONObject(i).getString("rating"));
                                data.setBid(obj.getJSONArray("data").getJSONObject(i).getString("price"));
                                data.setTime(obj.getJSONArray("data").getJSONObject(i).getString("time"));
                                data.setSub_category(obj.getJSONArray("data").getJSONObject(i).getString("description"));
                                items.add(data);
                            }
                            Bid_Adapter adapter=new Bid_Adapter(Bid_List.this,items,Call_COMPLETED);
                            recycler_categories.setAdapter(adapter);
                        }


                    }
                    Constant.ToastShort(Bid_List.this,obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Bid_List.this, error.getMessage());
            }
        };

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Constant.ToastShort(Bid_List.this,"OnActivity Result===:"+requestCode+resultCode);
        if (requestCode == Call_COMPLETED) {
//            Intent intent = new Intent(Connection_Activity.this, Dashboard_Main.class);
//            startActivity(intent);
//            finish();
        }

    }
}
