package com.swopx.fragment.dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Contact_Adapter;
import com.swopx.adapter.Invite_Ten_Adapter;
import com.swopx.adapter.Messengs_Adapter;
import com.swopx.chat.Chat;
import com.swopx.chat.UserDetails;
import com.swopx.fragment.browse_category.Bid_Main;
import com.swopx.registration.Dashboard_Main;
import com.swopx.registration.Invite_Ten_Activity;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Office Work on 05-12-2017.
 */

public class Messenger extends Fragment implements View.OnClickListener {
    private RecyclerView recycler,recycler_contact;
    private LinearLayoutManager Lmanager,contact_manager;
    private RadioGroup radio_grp;
    private RadioButton messages,contacts;
    private LinearLayout bottom_layout,contct_layout;
    private int My_Permission_request_Read=1055;
    private ArrayList<String> no;
    private TextView text_invite;
    final int INVITE_COMPLETED = 505;
    ArrayList<String> alphabets;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messenger_layout, container, false);
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
        recycler_contact=(RecyclerView)view.findViewById(R.id.recycler_contact);
        radio_grp=(RadioGroup) view.findViewById(R.id.radio_grp);
        messages=(RadioButton) view.findViewById(R.id.messages);
        contacts=(RadioButton) view.findViewById(R.id.contacts);
        text_invite=(TextView) view.findViewById(R.id.text_invite);
        bottom_layout=(LinearLayout) view.findViewById(R.id.bottom_layout);
        contct_layout=(LinearLayout) view.findViewById(R.id.contct_layout);
        alphabets=new ArrayList<>();
        alphabets.add("A");
        alphabets.add("B");
        alphabets.add("C");
        alphabets.add("D");
        alphabets.add("E");
        alphabets.add("F");
        alphabets.add("G");
        alphabets.add("H");
        alphabets.add("I");
        alphabets.add("J");
        alphabets.add("K");
        alphabets.add("L");
        alphabets.add("M");
        alphabets.add("N");
        alphabets.add("O");
        alphabets.add("P");
        alphabets.add("Q");
        alphabets.add("R");
        alphabets.add("S");
        alphabets.add("T");
        alphabets.add("U");
        alphabets.add("V");
        alphabets.add("W");
        alphabets.add("X");
        alphabets.add("Y");
        alphabets.add("Z");
//        getIndexList(CustomPreference.phon_no_array);
        getIndexList(alphabets);
        displayIndex(view);
        Lmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        contact_manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(Lmanager);
        recycler_contact.setLayoutManager(contact_manager);
//        Contact_Adapter adapter=new Contact_Adapter(getActivity(),((Dashboard_Main)getActivity()).phon_no_array);
//        Collections.sort(CustomPreference.phon_no_array,Items.BY_ALPHA);
        Collections.sort(CustomPreference.phon_no_array, new CustomComparator());
      /*  Collections.sort(CustomPreference.phon_no_array, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });*/
        Invite_Ten_Adapter adapter=new Invite_Ten_Adapter(getActivity(),CustomPreference.phon_no_array,text_invite);
        recycler_contact.setAdapter(adapter);

        recycler.setVisibility(View.VISIBLE);
        contct_layout.setVisibility(View.GONE);
        bottom_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                no=new ArrayList<>();
                for(int i=0;i<CustomPreference.phon_no_array.size();i++)
                {
                    if(CustomPreference.phon_no_array.get(i).getIs_checked())
                    {
                        no.add(CustomPreference.phon_no_array.get(i).getNumber());
                    }
                }
                if(no.size()>0)
                {
//                    if(isValid())
                    {

                        String str = Arrays.toString(no.toArray());
//                        Constant.ToastShort(getActivity(),""+str.replace("[","").replace(",",";").replace("]",""));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + str.replace("[","").replace(",",";").replace("]","")));
                        intent.putExtra("sms_body", getResources().getString(R.string.just_2000) +
                                Url_Links.App_Play_Store_link);
                        intent.putExtra("exit_on_sent", true);
                        startActivityForResult(intent, INVITE_COMPLETED);
                    }
                }


               /* if(Constant.isNetConnected(getActivity()))
                {
                    sent_invitation();
                }
                else
                {
                    Constant.ToastShort(getActivity(),getActivity().getResources().getString(R.string.internet_connection));
                }*/

            }
        });
        if(Constant.isNetConnected(getActivity()))
        {
            get_Message();
        }
        else
        {
            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
        }
//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
//                My_Permission_request_Read);
        radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.messages)
                {
                    messages.setTextColor(getResources().getColor(R.color.colorWhite));
                    contacts.setTextColor(getResources().getColor(R.color.colorBlack));
                    recycler.setVisibility(View.VISIBLE);
                    contct_layout.setVisibility(View.GONE);
                    bottom_layout.setVisibility(View.GONE);
                        if(Constant.isNetConnected(getActivity()))
                        {
                            get_Message();
                        }
                        else
                        {
                            Constant.ToastShort(getActivity(),getResources().getString(R.string.internet_connection));
                        }
                }
                else
                {
                    recycler.setVisibility(View.GONE);
                    contct_layout.setVisibility(View.VISIBLE);
                    bottom_layout.setVisibility(View.VISIBLE);
//                    getContactList();
                    contacts.setTextColor(getResources().getColor(R.color.colorWhite));
                    messages.setTextColor(getResources().getColor(R.color.colorBlack));
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//          Constant.ToastShort(getActivity(),"OnActivity  "+requestCode+"  "+resultCode);
        if (requestCode == INVITE_COMPLETED) {
//            if(resultCode == RESULT_OK)
//            {
//                Constant.ToastShort(getActivity(),"OnActivity1  "+requestCode+"  "+resultCode);
//            }

        }

    }
    private boolean isValid() {
        if(no.size()<5)
        {
            Constant.ToastShort(getActivity(),"Please invite minimum 5 or maximum 10 friends");
            return  false;
        }

        else if(no.size()>10)
        {
            Constant.ToastShort(getActivity(),"Please invite maximum 10 friends");
            return  false;
        }

        return true;
    }
    private void sent_invitation() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        JSONObject result = new JSONObject();
        try {
            JSONArray phn_arr=new JSONArray();
            for(int i=0;i<CustomPreference.phon_no_array.size();i++)
            {
                JSONObject obj=new JSONObject();
                obj.put("mobile_no", CustomPreference.phon_no_array.get(i).getNumber());
                phn_arr.put(obj);
            }
            result.put("referral" , CustomPreference.readString(getActivity(),CustomPreference.Refferal_Code,""));
            result.put("mobile" , phn_arr);
            params.put("result" , result);
//          params.put("mobile" , phon_no_array.get(adapterPosition).getNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.All_Invitatione, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
//                        invite_button.setText("Invited");
//                        phon_no_array.get(adapterPosition).setIs_checked(true);
                    }
                    Constant.ToastShort(getActivity(),obj.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(getActivity()
                        , error.getMessage());
            }
        };

    }
    private void getContactList() {
        ArrayList<Items> phon_no_array=new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Items i=new Items();
                        i.setName(name);
                        i.setNumber(phoneNo);
                        phon_no_array.add(i);
//                        Constant.Log("Name====", "Name: " + name);
//                        Constant.Log("Phone Number", "Phone Number: " + phoneNo);
                    }
                    Constant.Log("Phone Number", "Phone Number: " + phon_no_array.size()+"aRRAY"+phon_no_array);

                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }
    private void get_Message() {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("receiver_id" , CustomPreference.readString(getActivity(),CustomPreference.Client_id,""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(getActivity(),params, Url_Links.GetMessage, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        ArrayList<Items> items=new ArrayList<>();
                        if(obj.has("data"))
                        {
                            JSONArray jsonarry=obj.getJSONArray("data");
                            if(jsonarry.length()>0)
                            {
                                for(int i=0;i<jsonarry.length();i++)
                                {
                                    Items j=new Items();
                                    j.setId(jsonarry.getJSONObject(i).getString("sender_id"));
                                    j.setName(jsonarry.getJSONObject(i).getString("sender_name"));
                                    j.setSub_category(jsonarry.getJSONObject(i).getString("message"));
                                    j.setTime(jsonarry.getJSONObject(i).getString("time"));
//                                    j.setProject_name(obj.getJSONArray("data").getJSONObject(i).getString("project_name"));
                                    items.add(j);
                                }
                            }
                            Messengs_Adapter adapter=new Messengs_Adapter(getActivity(),items);
                            recycler.setAdapter(adapter);
                        }



                    }

                    Constant.ToastShort(getActivity(),obj.getString("msg"));

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

    Map<String, Integer> mapIndex;
    private void getIndexList(ArrayList<String> fruits) {
//        mapIndex = new LinkedHashMap<String, Integer>();
        mapIndex = new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < fruits.size(); i++) {
            String fruit = fruits.get(i);
            String index = fruit.substring(0, 1);
//            String index = fruits.get(i);

            if (mapIndex.get(index) == null)

                mapIndex.put(index, i);
        }
    }

    private void displayIndex(View view) {
        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);

            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;

//        CustomPreference.phon_no_array.set(mapIndex.get(selectedIndex.getText()));
        for(int i=0;i<CustomPreference.phon_no_array.size();i++)
        {
            if(CustomPreference.phon_no_array.get(i).getName().contains(selectedIndex.getText().toString()))
            {
                if(CustomPreference.phon_no_array.get(i).getName().substring(0,1).equalsIgnoreCase(selectedIndex.getText().toString()))
                {
//                Constant.ToastShort(getActivity(),""+i);
//                recycler_contact.smoothScrollToPosition(i);

                    contact_manager.scrollToPositionWithOffset(i,0);
                    break;
                }
            }

        }

//        contact_manager.scrollToPositionWithOffset(mapIndex.get(selectedIndex.getText()), 0);
    }


    public class CustomComparator implements Comparator<Items> {
        @Override
        public int compare(Items o1, Items o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }
}
