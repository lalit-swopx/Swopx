package com.swopx.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;

/**
 * Created by Office Work on 03-01-2018.
 */

public class Invite_Ten_Adapter extends RecyclerView.Adapter<Invite_Ten_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> phon_no_array;
    ArrayList<String > item_added;
    TextView text_invite;
    public Invite_Ten_Adapter(FragmentActivity activity, ArrayList<Items> phon_no_array, TextView text_invite) {
        this.activity=activity;
        this.phon_no_array=phon_no_array;
        this.text_invite=text_invite;
        item_added=new ArrayList<>();
    }

    @Override
    public Invite_Ten_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.invite_ten_row_layout, parent, false);
        return new Invite_Ten_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Invite_Ten_Adapter.MyViewHolder holder, int position) {
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
                        phon_no_array.get(getAdapterPosition()).setIs_checked(true);
                        invite_button.setText("");
                        invite_button.setChecked(true);
                        item_added.add(phon_no_array.get(getAdapterPosition()).getName());
                        if(item_added.size()>0)
                        {
                            text_invite.setText("Invite");
                        }
                        else
                        {
                            text_invite.setText("Invite All");
                        }
                    }
                    else
                    {
                        invite_button.setText("Invite");
                        phon_no_array.get(getAdapterPosition()).setIs_checked(false);
                        invite_button.setChecked(false);
                        item_added.remove(phon_no_array.get(getAdapterPosition()).getName());
                        if(item_added.size()>0)
                        {
                            text_invite.setText("Invite");
                        }
                        else
                        {
                            text_invite.setText("Invite All");
                        }
                    }

                }
            });
        }
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
