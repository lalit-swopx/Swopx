package com.swopx.fragment.edit_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Category_Adapter;
import com.swopx.adapter.SubCategory_Adapter;
import com.swopx.fragment.category.Subcategory;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SONY on 16-01-2018.
 */

public class Subcategory_Activity extends AppCompatActivity {
    private TextView back,title,done;
    private RecyclerView recycler;
    private LinearLayoutManager gmanager;
    private ArrayList<Items> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory_layout);
        back = (TextView) findViewById(R.id.back);
        done = (TextView) findViewById(R.id.done);
        title = (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("category"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ArrayList<String> sub_name=new ArrayList<>();
                 ArrayList<String> sub_id=new ArrayList<>();

                for(int i=0;i<items.size();i++)
                {
                    if(items.get(i).is_checked)
                    {
                        sub_name.add(items.get(i).getTitle());
                        sub_id.add(items.get(i).getId());
                    }
                }
                if(sub_id.size()<10)
                {
                    Intent i=new Intent();
                    i.putExtra("sub_id",sub_id.toString().replace("[","").replace("]",""));
                    i.putExtra("sub_name",sub_name.toString().replace("[","").replace("]",""));
                    setResult(RESULT_OK,i);
                    finish();
                }
                else
                {
                    Constant.ToastShort(Subcategory_Activity.this,"Select maximun ten sub category.");
                }



            }
        });
        recycler=(RecyclerView)findViewById(R.id.recycler);
        gmanager = new LinearLayoutManager(Subcategory_Activity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(gmanager);
        if(Constant.isNetConnected(Subcategory_Activity.this))
        {
            getCompany_Entity();
        }
        else
        {
            Constant.ToastShort(Subcategory_Activity.this,getResources().getString(R.string.internet_connection));
        }
    }


    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();

        try {
            params.put("category_id",getIntent().getStringExtra("category_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Subcategory_Activity.this, params, Url_Links.GetSize_Url, true) {
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
                            items=new ArrayList<>();
                            for(int i=0;i<obj.getJSONArray("sub-category").length();i++)
                            {
                                Items data=new Items();
                                data.setIs_checked(false);
                                data.setId(obj.getJSONArray("sub-category").getJSONObject(i).getJSONObject("_id").getString("$oid"));
//                                data.setImage_url(obj.getJSONArray("category").getJSONObject(i).getString("image"));
                                data.setTitle(obj.getJSONArray("sub-category").getJSONObject(i).getString("title"));
                                items.add(data);
                            }
                            SubCategory_Adapter recyclerView_Adapter = new SubCategory_Adapter(Subcategory_Activity.this,items,"size");
                            recycler.setAdapter(recyclerView_Adapter);
                        }

                    }
                    else
                    {
                        Constant.ToastShort(Subcategory_Activity.this,obj.getString("msg"));
                    }
//                    Constant.ToastShort(getActivity(),obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Subcategory_Activity.this, error.getMessage());
            }
        };
    }
}
