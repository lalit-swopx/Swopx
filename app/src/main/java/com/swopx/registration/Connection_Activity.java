package com.swopx.registration;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.Urls_Api.CustomLoader;
import com.swopx.adapter.Invite_Ten_Adapter;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Office Work on 04-01-2018.
 */

public class Connection_Activity extends AppCompatActivity {
    private RecyclerView recycler_contact;
    private LinearLayoutManager contact_manager;
//    public ArrayList<Items> phon_no_array;
    private TextView skip;
    private LinearLayout bottom_layout;
    private ArrayList<String> no;
    final int INVITE_COMPLETED = 505;
    private TextView text_invite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_layout);
        skip=(TextView)findViewById(R.id.skip);
        bottom_layout=(LinearLayout) findViewById(R.id.bottom_layout);
        recycler_contact=(RecyclerView)findViewById(R.id.recycler_contact);
        text_invite=(TextView) findViewById(R.id.text_invite);
        contact_manager = new LinearLayoutManager(Connection_Activity.this,LinearLayoutManager.VERTICAL,false);
        recycler_contact.setLayoutManager(contact_manager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Connection_Activity.this, Dashboard_Main.class);
//                startActivity(intent);
                finish();
            }
        });
        Invite_Ten_Adapter adapter=new Invite_Ten_Adapter(Connection_Activity.this, CustomPreference.phon_no_array, text_invite);
        recycler_contact.setAdapter(adapter);
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
                        Constant.ToastShort(Connection_Activity.this,""+str.replace("[","").replace(",",";").replace("]",""));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + str.replace("[","").replace(",",";").replace("]","")));
                        intent.putExtra("sms_body", "Just invited you to Swopx & sent you free money, use refer code:"+
                                CustomPreference.readString(Connection_Activity.this,CustomPreference.Refferal_Code,"")+"\n" +
                                "Download Now goo.gl/xsXANW");
                        intent.putExtra("exit_on_sent", true);
                        startActivityForResult(intent, INVITE_COMPLETED);
                    }
                }


            }
        });
//        new AsyncCaller().execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Constant.ToastShort(Invite_Ten_Activity.this,"OnActivity Result===:"+requestCode+resultCode);
        if (requestCode == INVITE_COMPLETED) {
//            Intent intent = new Intent(Connection_Activity.this, Dashboard_Main.class);
//            startActivity(intent);
//            finish();
        }

    }

    private boolean isValid() {
        if(no.size()<5)
        {
            Constant.ToastShort(Connection_Activity.this,"Please invite minimum 5 or maximum 10 friends");
            return  false;
        }

        else if(no.size()>10)
        {
            Constant.ToastShort(Connection_Activity.this,"Please invite maximum 10 friends");
            return  false;
        }

        return true;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        CustomLoader customLoader;

        public AsyncCaller() {

            customLoader = new CustomLoader(Connection_Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
            customLoader.show();
            customLoader.setCanceledOnTouchOutside(false);
            customLoader.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            phon_no_array=new ArrayList<>();
//            phon_no_array.clear();
            ContentResolver cr =getContentResolver();
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
                            i.setIs_checked(false);
                            i.setId(id);
                            CustomPreference.phon_no_array.add(i);
//                        Constant.Log("Name====", "Name: " + name);
//                        Constant.Log("Phone Number", "Phone Number: " + phoneNo);
                        }

                        pCur.close();
                    }
                }
            }

//            Constant.Log("Phone NumberArray=====", "Phone Number: " + phon_no_array.size()+"aRRAY"+phon_no_array);
            if(cur!=null){
                cur.close();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            loader(getActivity());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // add elements to al, including duplicates
//            Set<Items> hs = new HashSet<>();
//            hs.addAll(phon_no_array);
//            phon_no_array.clear();
//            phon_no_array.addAll(hs);
//            Constant.ToastShort(Invite_Ten_Activity.this,""+hs.size());
            Collections.sort(CustomPreference.phon_no_array,Items.BY_ALPHA);
//            Collections.sort(phon_no_array, new Comparator<Items>() {
//                @Override
//                public int compare(Items lhs, Items rhs) {
//                    return lhs.getName().compareTo(rhs.getName());
//                }
//            });
//            Constant.ToastShort(Invite_Ten_Activity.this,""+phon_no_array.size());
            customLoader.dismiss();
            Invite_Ten_Adapter adapter=new Invite_Ten_Adapter(Connection_Activity.this,CustomPreference.phon_no_array, text_invite);
            recycler_contact.setAdapter(adapter);
        }


    }

    public static <Items> ArrayList<Items> removeDuplicates(ArrayList<Items> list) {
        ArrayList<Items> list2 = new ArrayList<Items>();

        for(Items item : list) {
            if(!list2.contains(item)) {
                list2.add(item);
            }
        }

        return list2;
    }
}
