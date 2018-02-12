package com.swopx.fragment.edit_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 07-12-2017.
 */

public class Project_preferences extends AppCompatActivity implements View.OnClickListener {
    private TextView type,info,done,et_dealing,et_sub;
    private ImageButton back;
    private EditText et_pincode;
    private Spinner spn_order,spn_max,spn_min,spn_primarily;
    private String ordr_value="",max_value="",min_value="",primarly_value="";
    private String sub_name="";
    private String sub_id="",cat_id="",cat_name="";
//    private FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_prefrences_layout);
        intialize();
    }

    private void intialize() {
        type=(TextView)findViewById(R.id.type);
        info=(TextView)findViewById(R.id.info);
        done=(TextView)findViewById(R.id.done);
        back=(ImageButton) findViewById(R.id.back);
        spn_order=(Spinner) findViewById(R.id.spn_order);
        et_pincode=(EditText) findViewById(R.id.et_pincode);
        spn_max=(Spinner) findViewById(R.id.spn_max);
        spn_min=(Spinner) findViewById(R.id.spn_min);
        spn_primarily=(Spinner) findViewById(R.id.spn_primarily);
        et_dealing=(TextView) findViewById(R.id.et_dealing);
        et_sub=(TextView) findViewById(R.id.et_sub);
//        fab=(FloatingActionButton) findViewById(R.id.fab);
        setValues();
        onClick();
        spinner_click();
    }


    private void spinner_click() {

        // Spinner click listener

        spn_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    ordr_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_max.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    max_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    min_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_primarily.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    primarly_value = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void onClick() {
        done.setOnClickListener(this);
        back.setOnClickListener(this);
        et_dealing.setOnClickListener(this);
        et_sub.setOnClickListener(this);
//        fab.setOnClickListener(this);
        // Spinner Drop down elements
        ArrayList categories = new ArrayList();
        categories.add("Select");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayList distance = new ArrayList();
        distance.add("Select");
        for(int i=0;i<20;i++)
        {

            distance.add("" + (i + 10));
        }
        ArrayAdapter distanceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, distance);

        // Drop down layout style - list view with radio button
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayList order = new ArrayList();
        order.add("Select");
        for(int i=0;i<20;i++)
        {

            order.add("" + (i + 10));
        }
        ArrayAdapter orderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, order);

        // Drop down layout style - list view with radio button
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_order.setAdapter(distanceAdapter);
        spn_max.setAdapter(orderAdapter);
        spn_min.setAdapter(orderAdapter);
        spn_primarily.setAdapter(dataAdapter);
    }

    private void setValues() {
        type.setText(getIntent().getStringExtra("type"));
        info.setText(getIntent().getStringExtra("info"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.done:
                if(isValid())
                    finish();
                break;
            case R.id.back:
                finish();
                break;


                case R.id.et_dealing:
                    Intent i=new Intent(Project_preferences.this,Category_Activity.class);
                    i.putExtra("category","Dealing Category");
//                    i.putExtra("category_id",category_id);
                    startActivityForResult(i,00006);



                    case R.id.et_sub:
                    Intent p=new Intent(Project_preferences.this,Subcategory_Activity.class);
                    p.putExtra("category","Sub category");
//                    p.putExtra("category",category.getText().toString());
                    p.putExtra("category_id",cat_id);
                    startActivityForResult(p,00005);
                break;

            case R.id.fab:

                break;

            default:
                break;
        }
    }


    private void Basic_Info() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id", CustomPreference.readString(Project_preferences.this,CustomPreference.Client_id,""));
//            params.put("company_name", et_name.getText().toString());
//            params.put("company_type", legal_entity.getText().toString());
//            params.put("company_year", etyear.getText().toString());
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url=null;
        if(!TextUtils.isEmpty(CustomPreference.readString(Project_preferences.this,CustomPreference.Company_Id,"")))
        {
            url= Url_Links.Company_Basic_Url+"/"+CustomPreference.readString(Project_preferences.this,CustomPreference.Company_Id,"");
        }
        else
        {
            url=Url_Links.Company_Basic_Url;
        }
        new LoginApi(Project_preferences.this, params, url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getString("status").equalsIgnoreCase("200"))
                    {


//                            CustomPreference.writeString(Basic_Info.this,CustomPreference.Company_Id,CustomPreference.client_id);

//                        Constant.ToastShort(Project_preferences.this,response.getString("msg"));
                        finish();
                    }
                    Constant.ToastShort(Project_preferences.this, response.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Project_preferences.this, error.getMessage());
            }
        };
    }

    public boolean isValid() {
        if(TextUtils.isEmpty(primarly_value))
        {
            Constant.ToastShort(Project_preferences.this,"Please select primarly");
            return false;
        }
        else if(TextUtils.isEmpty(min_value))
        {
            Constant.ToastShort(Project_preferences.this,"Please select minimum value");
            return false;
        }
        else if(TextUtils.isEmpty(max_value))
        {
            Constant.ToastShort(Project_preferences.this,"Please select maximum value");
            return false;
        }
        else if(TextUtils.isEmpty(et_dealing.getText().toString().trim()))
        {
            Constant.ToastShort(Project_preferences.this,"Please enter dealing category");
            return false;
        }
        else if(TextUtils.isEmpty(et_pincode.getText().toString().trim()))
        {
            Constant.ToastShort(Project_preferences.this,"Please enter pincode");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==00005&&resultCode==RESULT_OK)
        {


            sub_id=data.getStringExtra("sub_id");
            sub_name=data.getStringExtra("sub_name");
            et_sub.setText(sub_name);
//            Constant.ToastShort(Basic_Info.this,data.getStringExtra("sub_id")+"====="+data.getStringExtra("sub_name"));
        }

        else  if(requestCode==00006&&resultCode==RESULT_OK)
        {


            cat_id=data.getStringExtra("sub_id");
            cat_name=data.getStringExtra("sub_name");
            et_dealing.setText(cat_name);
//            Constant.ToastShort(Basic_Info.this,data.getStringExtra("sub_id")+"====="+data.getStringExtra("sub_name"));
        }

    }


}
