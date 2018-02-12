package com.swopx.fragment.edit_package;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.db_package.DBHelper;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static com.swopx.utils.CustomPreference.name;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Basic_Info extends AppCompatActivity implements View.OnClickListener {
    private TextView type,info,done;
    private AutoCompleteTextView legal_entity,category;
    private ImageButton back;
    private EditText etyear,et_name;
    private TextView sub_cat;
    private Calendar myCalendar;
    private DBHelper db;
    private String sub_name="";
    private String sub_id="";
    private String category_id;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    private CustomPreference global_variables;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info_layout);
        intialize();
    }

    private void intialize() {

        type=(TextView)findViewById(R.id.type);
        info=(TextView)findViewById(R.id.info);
        done=(TextView)findViewById(R.id.done);
        etyear=(EditText) findViewById(R.id.year);
        et_name=(EditText) findViewById(R.id.name);
//        et_name=(EditText) findViewById(R.id.name);
        sub_cat=(TextView) findViewById(R.id.sub_cat);
        legal_entity=(AutoCompleteTextView) findViewById(R.id.legal_entity);
        category=(AutoCompleteTextView) findViewById(R.id.category);
        legal_entity.setAdapter(new Items(db, "legal", global_variables));
        legal_entity.setThreshold(1);
        legal_entity.setHint(getResources().getString(R.string.legal_entity));

        category.setAdapter(new Items(db, "category", global_variables));
        category.setThreshold(1);
        category.setHint("Category");
//        institute_auto.setTag(institiute_items.get(global_variables.id));
//        institute_auto.setText(institiute_items.get(global_variables.name));
        back=(ImageButton) findViewById(R.id.back); myCalendar = Calendar.getInstance();
        db = new DBHelper(Basic_Info.this);
        global_variables = new CustomPreference();
//        open_date_picker();

        if(Constant.isNetConnected(Basic_Info.this))
        {
            getCompany_Entity();

        }
        else
        {
            Constant.ToastShort(Basic_Info.this,getResources().getString(R.string.internet_connection));
        }
        setValues();
        onClick();
    }

    private void onClick() {
        done.setOnClickListener(this);
        back.setOnClickListener(this);
        etyear.setOnClickListener(this);
        sub_cat.setOnClickListener(this);
        legal_entity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(pos);
                String productINameStr = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.name));
                legal_entity.setText(productINameStr);
            }
        });

        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(pos);
                String productINameStr = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.name));
                category.setText(productINameStr);
                category_id = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.id));
//                Constant.ToastShort(Basic_Info.this,"productINameStrdffgdgdhdh" +productINameStr+ category_id);
            }
        });
    }

    private void setValues() {
        type.setText(getIntent().getStringExtra("type"));
//        info.setText(getIntent().getStringExtra("info"));
        info.setText(CustomPreference.readString(Basic_Info.this,CustomPreference.Client_name,""));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.done:

                if(isValid())
                    if(Constant.isNetConnected(Basic_Info.this))
                    {
                        Basic_Info();
                    }
                    else
                    {
                        Constant.ToastShort(Basic_Info.this,getResources().getString(R.string.internet_connection));
                    }
                break;

            case R.id.back:
                finish();
                break;
            case R.id.sub_cat:
                if(!TextUtils.isEmpty(category.getText().toString()))
                {
                    Intent i=new Intent(Basic_Info.this,Subcategory_Activity.class);
                    i.putExtra("category",category.getText().toString());
                    i.putExtra("category_id",category_id);
                    startActivityForResult(i,00005);
                }
                else
                    {
                    Constant.ToastShort(Basic_Info.this,"Select Category");
                }

                break;

            case R.id.year:

                showYearDialog();
//                new DatePickerDialog(Basic_Info.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            default:
                break;
        }
    }

    public void showYearDialog()
    {

        final Dialog d = new Dialog(Basic_Info.this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(""+year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year+50);
        nopicker.setMinValue(year-50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                etyear.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }
//    private DatePickerDialog createDialogWithoutDateField() {
//        DatePickerDialog dpd = new DatePickerDialog(this, null, 2014, 1, 24);
//        try {
//            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
//            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
//                if (datePickerDialogField.getName().equals("mDatePicker")) {
//                    datePickerDialogField.setAccessible(true);
//                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
//                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
//                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
//                        Log.i("test", datePickerField.getName());
//                        if ("mDaySpinner".equals(datePickerField.getName())) {
//                            datePickerField.setAccessible(true);
//                            Object dayPicker = datePickerField.get(datePicker);
//                            ((View) dayPicker).setVisibility(View.GONE);
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception ex) {
//        }
//        return dpd;
//    }


    private void Basic_Info() {

        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("client_id", CustomPreference.readString(Basic_Info.this,CustomPreference.Client_id,""));
            params.put("company_name", et_name.getText().toString());
            params.put("company_type", legal_entity.getText().toString());
            params.put("company_year", etyear.getText().toString());
            params.put("cat_name", category.getText().toString());
            params.put("cat_id", category_id);
            params.put("sub_id", sub_id);
            params.put("sub_name", sub_name);
            Constant.Log("VAlues Login", "" + params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url=null;
        if(!TextUtils.isEmpty(CustomPreference.readString(Basic_Info.this,CustomPreference.Company_Id,"")))
        {
            url=Url_Links.Company_Basic_Url+"/"+CustomPreference.readString(Basic_Info.this,CustomPreference.Company_Id,"");
        }
        else
        {
            url=Url_Links.Company_Basic_Url;
        }
        new LoginApi(Basic_Info.this, params, url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    if(response.getString("status").equalsIgnoreCase("200"))
                    {
                        CustomPreference.writeString(Basic_Info.this,CustomPreference.Company_registered,response.getString("is_company"));
//                        CustomPreference.writeString(Basic_Info.this,CustomPreference.Company_Id,CustomPreference.client_id);
//                        Constant.ToastShort(Basic_Info.this,response.getString("msg"));
                        finish();
                    }
                    Constant.ToastShort(Basic_Info.this, response.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Basic_Info.this, error.getMessage());
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==00005&&resultCode==RESULT_OK)
        {


            sub_id=data.getStringExtra("sub_id");
            sub_name=data.getStringExtra("sub_name");
            sub_cat.setText(sub_name);
//            Constant.ToastShort(Basic_Info.this,data.getStringExtra("sub_id")+"====="+data.getStringExtra("sub_name"));
        }

    }

    private boolean isValid() {
        if(TextUtils.isEmpty(et_name.getText().toString().trim()))
        {
            Constant.ToastShort(Basic_Info.this,getResources().getString(R.string.etr_cmpny_name));
            return false;
        }
        else  if(TextUtils.isEmpty(legal_entity.getText().toString().trim()))
        {
            Constant.ToastShort(Basic_Info.this,getResources().getString(R.string.legal_entity_entr));
            return false;
        }
        else  if(TextUtils.isEmpty(etyear.getText().toString().trim()))
        {
            Constant.ToastShort(Basic_Info.this,getResources().getString(R.string.enter_year));
            return false;
        }
        else  if(TextUtils.isEmpty(category.getText().toString().trim()))
        {
            Constant.ToastShort(Basic_Info.this,"Select Category");
            return false;
        }

        else  if(TextUtils.isEmpty(sub_name))
        {
            Constant.ToastShort(Basic_Info.this,"Select Sub Category");
            return false;
        }

        return true;
    }
    DatePickerDialog.OnDateSetListener date;
    private void open_date_picker() {
         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etyear.setText(sdf.format(myCalendar.getTime()));
    }


    class Items extends CursorAdapter
            implements AdapterView.OnItemClickListener {
        String values;
        CustomPreference global_variables;

        Items(DBHelper dbHelper, String value, CustomPreference global_variable) {
            super(Basic_Info.this, null);
            values = value;
            global_variables = global_variable;
        }


        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }
            if (values.equalsIgnoreCase("legal")) {
                return db.fetchItemsByDescSqliteExternal(
                        (constraint != null ? constraint.toString() : "@@@@"), db.TABLE_LEGAL_ENTITY);
            }
            else /*if(values.equalsIgnoreCase("qualification"))*/ {
                return db.fetchItemsByDescSqliteExternal(
                        (constraint != null ? constraint.toString() : "@@@@"), db.TABLE_CATEGORY);
            }

//            else
//
//            {
//                return db.fetchItemsByDescSqliteExternal(
//                        (constraint != null ? constraint.toString() : "@@@@"),db.TABLE_NAME_STATE);
//            }


        }

        @Override
        public String convertToString(Cursor cursor) {
            final int columnIndex = cursor.getColumnIndexOrThrow(global_variables.name);
            return cursor.getString(columnIndex);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView text1 = (TextView) view.findViewById(R.id.text1);

            final int itemColumnIndex = cursor.getColumnIndexOrThrow(name);
            final int itemColumnIndex3 = cursor.getColumnIndexOrThrow(global_variables.id);
            if (values.equalsIgnoreCase("legal") || values.equalsIgnoreCase("category")) {

            }
            String name = cursor.getString(itemColumnIndex)/* + " (" + cursor.getString(itemColumnIndex3) + ") "*/;

            text1.setText(name);

        }


        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.row_people, parent, false);
        }

        @Override
        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
            // Get the cursor, positioned to the corresponding row in the result set
            Cursor cursor = (Cursor) listView.getItemAtPosition(position);
            String productIDCode="";
            if (values.equalsIgnoreCase("legal")) {
                 productIDCode = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.id));
            }
            else
            {
                 productIDCode = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.id));
            }

            String productINameStr = cursor.getString(cursor.getColumnIndexOrThrow(global_variables.name));


        }
    }


    private void getEntity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();

        new LoginApi(Basic_Info.this,Url_Links.Legal_Entity_Url, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "" + response);
                    getCategory();
                    if (response.getString("status").equalsIgnoreCase("200")) {

                        JSONArray data = response.getJSONArray("entity");
                        DBHelper db=new DBHelper(Basic_Info.this);
                        if(data.length()>0)
                        {
                            db.truncateData(db.TABLE_LEGAL_ENTITY);
                            for (int i = 0; i < data.length(); i++) {
                                HashMap<String, String> hashMapcountry = new HashMap<>();
                                hashMapcountry.put(global_variables.name, data.getJSONObject(i).getString("title"));
                                hashMapcountry.put(global_variables.id, data.getJSONObject(i).getString("id"));
                                db.insert(hashMapcountry, DBHelper.TABLE_LEGAL_ENTITY);
                                Constant.Log("No of Rows in Country Table","INstitution Table========"+db.numberOfRows(DBHelper.TABLE_LEGAL_ENTITY));
                            }
                        }

                    }
                    else
                    {
                        Constant.ToastShort(Basic_Info.this,response.getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Basic_Info.this, error.getMessage());
            }
        };
    }


    private void getCategory() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();

        new LoginApi(Basic_Info.this,Url_Links.GetRoot_Category, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "" + response);

//                    if (response.getString("status").equalsIgnoreCase("200"))
                    {

                        JSONArray data = response.getJSONObject("result").getJSONArray("data");
                        DBHelper db=new DBHelper(Basic_Info.this);
                        if(data.length()>0)
                        {
                            db.truncateData(db.TABLE_CATEGORY);
                            for (int i = 0; i < data.length(); i++) {
                                HashMap<String, String> hashMapcountry = new HashMap<>();
                                hashMapcountry.put(global_variables.name, data.getJSONObject(i).getString("category"));
                                hashMapcountry.put(global_variables.id, data.getJSONObject(i).getJSONObject("id").getString("$oid"));
                                db.insert(hashMapcountry, DBHelper.TABLE_CATEGORY);
                                Constant.Log("No of Rows in Country Table","INstitution Table========"+db.numberOfRows(DBHelper.TABLE_CATEGORY));
                            }
                        }

                    }
                   /* else
                    {
                        Constant.ToastShort(Basic_Info.this,response.getString("msg"));
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Basic_Info.this, error.getMessage());
            }
        };
    }
    private void getCompany_Entity() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
//        Constant.Log("reponse Login", "=================" + Url_Links.Company_Entity_Url+CustomPreference.readString(Basic_Info.this,CustomPreference.Client_id,""));
        new LoginApi(Basic_Info.this,Url_Links.Company_Entity_Url+CustomPreference.readString(Basic_Info.this,CustomPreference.Client_id,""), true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    getEntity();
                    et_name.setText(response.getString("company_name"));
                    legal_entity.setText(response.getString("company_type"));
                    etyear.setText(response.getString("company_year"));
//                    if (response.getString("status").equalsIgnoreCase("200")) {
//
//
//                    }

                    Constant.ToastShort(Basic_Info.this,response.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(Basic_Info.this, error.getMessage());
            }
        };
    }

}
