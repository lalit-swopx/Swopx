package com.swopx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.chat.Chat;
import com.swopx.chat.UserDetails;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;

/**
 * Created by Office Work on 05-12-2017.
 */

public class Messengs_Adapter extends RecyclerView.Adapter<Messengs_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> items;
    public Messengs_Adapter(FragmentActivity activity, ArrayList<Items> items) {
        this.activity=activity;
        this.items=items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.messenger_row, parent, false);
        return new Messengs_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(items.get(position).getName());
        holder.time.setText(items.get(position).getTime());
        holder.message.setText(items.get(position).getSub_category());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,time,message;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            time=(TextView)itemView.findViewById(R.id.time);
            message=(TextView)itemView.findViewById(R.id.message);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetails.chatWith=items.get(getAdapterPosition()).getName();
                    UserDetails.chatWith_id=items.get(getAdapterPosition()).getId();
                    Intent i=new Intent(activity, Chat.class);
//                    i.putExtra("project_name","");
                    activity.startActivity(i);
                }
            });
        }
    }
}
