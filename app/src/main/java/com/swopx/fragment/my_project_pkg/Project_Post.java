package com.swopx.fragment.my_project_pkg;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.PostImage;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.browse_category.Bid_Main;
import com.swopx.fragment.browse_category.Placed_Bid;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;
import com.swopx.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Office Work on 22-12-2017.
 */

public class Project_Post extends AppCompatActivity implements View.OnClickListener {
    private TextView browse,no_file_selected,cat,sab_cat,place_bid,cancel,select,transpotation;
    private EditText amount,charge;
    private Spinner unit;
    private String unit_value="";
    private ImageView arrow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_myproject_layout);
        intial();
    }

    private void intial() {
        charge=(EditText)findViewById(R.id.charge);
        amount=(EditText)findViewById(R.id.amount);
        no_file_selected=(TextView)findViewById(R.id.no_file_selected);
        transpotation=(TextView)findViewById(R.id.transpotation);
        cancel=(TextView)findViewById(R.id.cancel);
        place_bid=(TextView)findViewById(R.id.place_bid);
        unit=(Spinner)findViewById(R.id.unit);
        cat=(TextView)findViewById(R.id.cat);
        sab_cat=(TextView)findViewById(R.id.sab_cat);
        browse=(TextView)findViewById(R.id.browse);
        arrow=(ImageView) findViewById(R.id.arrow);
        cancel.setOnClickListener(this);
        place_bid.setOnClickListener(this);
        arrow.setOnClickListener(this);
        cat.setText(getIntent().getStringExtra("cate_name"));
        sab_cat.setText(getIntent().getStringExtra("sub_cat"));

        unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    unit_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(Constant.isNetConnected(Project_Post.this))
        {
            getUnits();
        }
        else
        {
            Constant.ToastShort(Project_Post.this,getResources().getString(R.string.internet_connection));
        }
//        unit.setText("Quantity in "+getIntent().getStringExtra("unit"));


//        Html.fromHtml("X<sup>2</sup>"))
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.place_bid:

                if(isValid())
                {
                    alert_dialog();
                }
                break;
            case R.id.cancel:
                finish();
                break;

                case R.id.arrow:
                    unit.performClick() ;
                break;
            default:
                break;
        }
    }
    private void alert_dialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Project_Post.this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.commom_custom_dialog, null);
        dialogBuilder.setView(dialogView);
        TextView btn_no=(TextView)dialogView.findViewById(R.id.btn_no);
        TextView btn_yes=(TextView)dialogView.findViewById(R.id.btn_yes);
        TextView header=(TextView)dialogView.findViewById(R.id.header);
        TextView title=(TextView)dialogView.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.post_project));
        header.setText((getResources().getString(R.string.post_project_content)));
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
                if(Constant.isNetConnected(Project_Post.this))
                {
                    getCompany_Entity();
                }
                else
                {
                    Constant.ToastShort(Project_Post.this,getResources().getString(R.string.internet_connection));
                }

            }
        });

        alertDialog.show();
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(amount.getText().toString().trim()))
        {
            Constant.ToastShort(Project_Post.this,"Please enter the quantity");
            return false;
        }

        else if((TextUtils.isEmpty(unit_value.trim())))
        {
            Constant.ToastShort(Project_Post.this,"Please select unit");
            return false;
        }
            else if((TextUtils.isEmpty(charge.getText().toString().trim())))
        {
//            if(TextUtils.isEmpty(charge.getText().toString().trim()))
            {
                Constant.ToastShort(Project_Post.this,"Please enter the number of days");
                return false;
            }
        }

        return true;
    }

    private void getUnits() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("cat_name" ,getIntent().getStringExtra("cate_name"));
            params.put("subcat_name" ,getIntent().getStringExtra("sub_cat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Project_Post.this,params, Url_Links.Get_Unit_Code, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {

                        ArrayList categories = new ArrayList();
                        categories.add("Select Unit");

                                     for(int i=0;i<obj.getJSONArray("data").length();i++)
                                     {
                                         categories.add(obj.getJSONArray("data").getJSONObject(i).getString("unit"));
                                     }
                        ArrayAdapter dataAdapter = new ArrayAdapter(Project_Post.this, R.layout.spinner_layout1, categories);
                        dataAdapter.setDropDownViewResource(R.layout.spinner2);
                        unit.setAdapter(dataAdapter);
                    }
                    else
                    {
                        Constant.ToastShort(Project_Post.this,obj.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Project_Post.this, error.getMessage());
            }
        };
    }


    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {

            params.put("subindustry_id" ,getIntent().getStringExtra("sub_Indud_id"));
            params.put("subindustry" ,getIntent().getStringExtra("sub_Indus"));
            params.put("category_id" ,getIntent().getStringExtra("cat_id"));
            params.put("category" ,getIntent().getStringExtra("cate_name"));
            params.put("subcategory_id" ,getIntent().getStringExtra("sub_cat_id"));
            params.put("subcategory" ,getIntent().getStringExtra("sub_cat"));



            ArrayList<Items> child_cat = new ArrayList<Items>();
            ArrayList<String> child_cat_name = new ArrayList<String>();
            ArrayList<String> child_cat_id = new ArrayList<String>();
            child_cat = (ArrayList<Items>)getIntent().getSerializableExtra("child_cat");
            if(child_cat.size()>0)
            {
                for(int i=0;i<child_cat.size();i++)
                {
                    child_cat_name.add(child_cat.get(i).getName());
                    child_cat_id.add(child_cat.get(i).getId());
                }
                String str = Arrays.toString(child_cat_name.toArray());
                String str1 = Arrays.toString(child_cat_id.toArray());
                params.put("child_id" ,str1.replace("[","").replace("]",""));
                params.put("child_name" ,str.replace("[","").replace("]",""));

            }
            else
            {
                params.put("child_id" ,"");
                params.put("child_name" ,"");
            }

            /*JSONArray child_subcat = new JSONArray();
            for(int i=0;i<child_cat.size();i++)
            {
                child_subcat.put(i,child_cat.get(i).getId());
                child_subcat.put(i,child_cat.get(i).getName());
            }*/
            params.put("qty" ,amount.getText().toString());
            params.put("when" ,charge.getText().toString());
            params.put("unit" ,unit_value);
//            params.put("price" ,amount.getText().toString());
            params.put("description" ,getIntent().getStringExtra("descrip"));
            params.put("client_id" , CustomPreference.readString(Project_Post.this,CustomPreference.Client_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(Project_Post.this,params, Url_Links.Post_Project, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        Intent i=new Intent(Project_Post.this,Posted_Project.class);
                        i.putExtra("category",obj.getString("category"));
                        i.putExtra("subcategory",obj.getString("subcategory"));
                        i.putExtra("price",obj.getString("price"));
                        i.putExtra("qty",obj.getString("qty"));
                        i.putExtra("when",obj.getString("when"));
                        i.putExtra("unit",obj.getString("unit"));
//                        i.putExtra("unit","Ton");
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Constant.ToastShort(Project_Post.this,obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Project_Post.this, error.getMessage());
            }
        };
    }

}
