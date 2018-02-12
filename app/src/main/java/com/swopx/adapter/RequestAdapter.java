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
import com.swopx.fragment.browse_category.Request_Detail;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;

/**
 * Created by Office Work on 19-12-2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Items> items;
    String values;

    public RequestAdapter(FragmentActivity activity, ArrayList<Items> items, String values) {
        this.activity = activity;
        this.items = items;
        this.values = values;
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.request_row, parent, false);
        return new RequestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.MyViewHolder holder, int position) {
        if (!items.get(position).getBid().equalsIgnoreCase("0")) {
           holder.dots.setVisibility(View.VISIBLE);
           holder.bid.setVisibility(View.VISIBLE);
            holder.bid.setText(items.get(position).getBid());
        }
        else {
            holder.dots.setVisibility(View.GONE);
            holder.bid.setVisibility(View.GONE);
        }
        holder.request.setText(items.get(position).getSub_category());
        holder.days_left.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView days_left, bid,request;
        ImageView dots;

        public MyViewHolder(View itemView) {
            super(itemView);
            days_left = (TextView) itemView.findViewById(R.id.days_left);
            request = (TextView) itemView.findViewById(R.id.request);
            dots = (ImageView) itemView.findViewById(R.id.dots);
            bid = (TextView) itemView.findViewById(R.id.bid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (values.equalsIgnoreCase("category")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", items.get(getAdapterPosition()).getId());
                        bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                         bundle.putString("type", "normal");
                        Request_Detail fragment = new Request_Detail();
                        fragment.setArguments(bundle);
                        ((Category_Main) activity).replaceFragment(fragment);
                    }
                }
            });
        }
    }
}