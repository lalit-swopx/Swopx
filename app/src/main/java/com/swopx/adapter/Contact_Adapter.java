package com.swopx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 30-12-2017.
 */

public class Contact_Adapter extends RecyclerView.Adapter<Contact_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> phon_no_array;
    public Contact_Adapter(FragmentActivity activity, ArrayList<Items> phon_no_array) {
        this.activity=activity;
        this.phon_no_array=phon_no_array;
    }

    @Override
    public Contact_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.connection_row, parent, false);
        return new Contact_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Contact_Adapter.MyViewHolder holder, int position) {
     holder.name.setText(phon_no_array.get(position).getName());
     holder.pho_no.setText(phon_no_array.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return phon_no_array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,pho_no;
        CheckBox invite_button;
        public MyViewHolder(View itemView) {
            super(itemView);
            pho_no=(TextView)itemView.findViewById(R.id.pho_no);
            name=(TextView)itemView.findViewById(R.id.name);
            invite_button=(CheckBox) itemView.findViewById(R.id.invite_button);
            invite_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!phon_no_array.get(getAdapterPosition()).getIs_checked())
                    {

//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phon_no_array.get(getAdapterPosition()).getNumber()));
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "9140380245"));
//                        intent.putExtra("sms_body", "Swopx");
//                        activity.startActivity(intent);
                        if(Constant.isNetConnected(activity))
                        {
                            sent_invitation(getAdapterPosition(),invite_button);
                        }
                        else
                        {
                            Constant.ToastShort(activity,activity.getResources().getString(R.string.internet_connection));
                        }
                    }
                   /* else
                    {

                        invite_button.setText("Invite");
                        phon_no_array.get(getAdapterPosition()).setIs_checked(false);
                    }*/
                }
            });
        }
    }


    private void sent_invitation(final int adapterPosition, final CheckBox invite_button) {
        JSONObject params = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            params.put("referral" , CustomPreference.readString(activity,CustomPreference.Refferal_Code,""));
            params.put("mobile" , phon_no_array.get(adapterPosition).getNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.Log("reponse Login", "=================" +params);
        new LoginApi(activity,params, Url_Links.Sent_Invitatione, true) {
            @Override
            public void response(JSONObject response) {
                super.response(response);
                try {
                    Constant.Log("reponse Login", "=================" + response);
                    JSONObject obj= response.getJSONObject("result");
                    if (obj.getString("status").equalsIgnoreCase("200"))
                    {
                        invite_button.setText("Invited");
                        phon_no_array.get(adapterPosition).setIs_checked(true);
                    }
                    Constant.ToastShort(activity,obj.getString("msg"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {
                super.error(error);
                Constant.ToastShort(activity, error.getMessage());
            }
        };

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
