package com.swopx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.fragment.browse_category.Category_Main;
import com.swopx.fragment.browse_category.Request;
import com.swopx.fragment.my_project_pkg.Bid_List;
import com.swopx.fragment.my_project_pkg.Category_My_Project;
import com.swopx.fragment.my_project_pkg.Description_My_Project;
import com.swopx.fragment.my_project_pkg.Post_Project_Main;
import com.swopx.fragment.my_project_pkg.Size_My_Project;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Office Work on 23-12-2017.
 */

public class Work_In_Progress_Adapter extends RecyclerView.Adapter<Work_In_Progress_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> items;
    String values;

    public Work_In_Progress_Adapter(FragmentActivity activity, ArrayList<Items> items) {
        this.activity=activity;
        this.items=items;
        this.values=values;
    }

    @Override
    public Work_In_Progress_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.projects_row_layout, parent, false);
        return new Work_In_Progress_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Work_In_Progress_Adapter.MyViewHolder holder, int position) {
        holder.dots.setVisibility(View.VISIBLE);
        holder.days_left.setText(items.get(position).getTime());
        holder.bid.setText(items.get(position).getBid()+" bids");
        if(!TextUtils.isEmpty(items.get(position).getSub_category()))
        {

            holder.details.setText(items.get(position).getTitle()+" ("+items.get(position).getSub_category()+")");
        }
       else
        {
            holder.details.setText(items.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rupees,days_left,bid,details;
        ImageView dots1,dots;
        public MyViewHolder(View itemView) {
            super(itemView);
            rupees=(TextView)itemView.findViewById(R.id.rupees);
            days_left=(TextView)itemView.findViewById(R.id.days_left);
            bid=(TextView)itemView.findViewById(R.id.bid);
            dots1=(ImageView)itemView.findViewById(R.id.dots1);
            dots=(ImageView)itemView.findViewById(R.id.dots);
            details=(TextView) itemView.findViewById(R.id.details);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!items.get(getAdapterPosition()).getBid().equalsIgnoreCase("0"))
                    {
                        Intent i=new Intent(activity, Bid_List.class);
                        i.putExtra("bid_id",items.get(getAdapterPosition()).getId());
                        activity.startActivity(i);
                    }
                    else
                    {
                        Constant.ToastShort(activity,activity.getResources().getString(R.string.no_bids_till_now));
                    }
                }
            });
        }
    }
}
