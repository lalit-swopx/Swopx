package com.swopx.registration;

import android.app.Activity;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Office Work on 03-01-2018.
 */

public class Invite_Ten_Activity extends AppCompatActivity {
    private RecyclerView recycler_contact;
    private LinearLayoutManager contact_manager;
    public ArrayList<Items> phon_no_array;
    private TextView skip,text_invite;
    private LinearLayout bottom_layout;
    private ArrayList<String> no;
    final int INVITE_COMPLETED = 505;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_ten_layout);
        skip=(TextView)findViewById(R.id.skip);
        text_invite=(TextView)findViewById(R.id.text_invite);
        bottom_layout=(LinearLayout) findViewById(R.id.bottom_layout);
        recycler_contact=(RecyclerView)findViewById(R.id.recycler_contact);
        contact_manager = new LinearLayoutManager(Invite_Ten_Activity.this,LinearLayoutManager.VERTICAL,false);
        recycler_contact.setLayoutManager(contact_manager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Invite_Ten_Activity.this, Dashboard_Main.class);
                startActivity(intent);
                finish();
            }
        });
        bottom_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no=new ArrayList<>();
                for(int i=0;i<phon_no_array.size();i++)
                {
                    if(phon_no_array.get(i).getIs_checked())
                    {
                        no.add(phon_no_array.get(i).getNumber());
                    }
                }
                if(no.size()>0)
                {
                    if(isValid())
                    {

                        String str = Arrays.toString(no.toArray());
                        Constant.ToastShort(Invite_Ten_Activity.this,""+str.replace("[","").replace(",",";").replace("]",""));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + str.replace("[","").replace(",",";").replace("]","")));
                        intent.putExtra("sms_body", "Just invited you to Swopx & sent you free money, use refer code:"+
                                CustomPreference.readString(Invite_Ten_Activity.this,CustomPreference.Refferal_Code,"")+"\n" +
                                "Download Now goo.gl/xsXANW");
                        intent.putExtra("exit_on_sent", true);
                        startActivityForResult(intent, INVITE_COMPLETED);
                    }
                }


            }
        });
        new AsyncCaller().execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INVITE_COMPLETED && resultCode == Activity.RESULT_OK && data.getData() != null) {
            Intent intent = new Intent(Invite_Ten_Activity.this, Dashboard_Main.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean isValid() {
        if(no.size()<5)
        {
            Constant.ToastShort(Invite_Ten_Activity.this,"Please invite minimum 5 or maximum 10 friends");
            return  false;
        }

       else if(no.size()>10)
        {
            Constant.ToastShort(Invite_Ten_Activity.this,"Please invite maximum 10 friends");
            return  false;
        }

        return true;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        CustomLoader customLoader;

        public AsyncCaller() {

            customLoader = new CustomLoader(Invite_Ten_Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
            customLoader.show();
            customLoader.setCanceledOnTouchOutside(false);
            customLoader.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            phon_no_array=new ArrayList<>();
            phon_no_array.clear();
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
                            phon_no_array.add(i);
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
            Set<Items> hs = new HashSet<>();
            hs.addAll(phon_no_array);
            phon_no_array.clear();
            phon_no_array.addAll(hs);
            Constant.ToastShort(Invite_Ten_Activity.this,""+hs.size());
            Collections.sort(phon_no_array,Items.BY_ALPHA);
//            Collections.sort(phon_no_array, new Comparator<Items>() {
//                @Override
//                public int compare(Items lhs, Items rhs) {
//                    return lhs.getName().compareTo(rhs.getName());
//                }
//            });
//            Constant.ToastShort(Invite_Ten_Activity.this,""+phon_no_array.size());
            customLoader.dismiss();
            Invite_Ten_Adapter adapter=new Invite_Ten_Adapter(Invite_Ten_Activity.this,phon_no_array, text_invite);
            recycler_contact.setAdapter(adapter);
        }


    }
}
