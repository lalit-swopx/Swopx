package com.swopx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.fragment.browse_category.Category_Main;
import com.swopx.fragment.browse_category.Request;
import com.swopx.fragment.my_project_pkg.Category_My_Project;
import com.swopx.fragment.my_project_pkg.Description_My_Project;
import com.swopx.fragment.my_project_pkg.Post_Project_Main;
import com.swopx.fragment.my_project_pkg.Size_My_Project;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;

/**
 * Created by SONY on 16-01-2018.
 */

public class SubCategory_Adapter extends RecyclerView.Adapter<SubCategory_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Items> items;
    String values;

    public SubCategory_Adapter(FragmentActivity activity, ArrayList<Items> items, String values) {
        this.activity=activity;
        this.items=items;
        this.values=values;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public SubCategory_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.categories_row, parent, false);
        return new SubCategory_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubCategory_Adapter.MyViewHolder holder, int position) {
        if(values.equalsIgnoreCase("my_projectcategory")||values.equalsIgnoreCase("category"))
        {
            holder.sub_category.setVisibility(View.VISIBLE);
            holder.sub_category.setText(items.get(position).getSub_category());
        }
        else
        {
            holder.sub_category.setVisibility(View.GONE);
        }
        holder.category.setText(items.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category,sub_category;
        LinearLayout parent_layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            category=(TextView)itemView.findViewById(R.id.category);
            sub_category=(TextView)itemView.findViewById(R.id.sub_category);
            parent_layout=(LinearLayout)itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(!items.get(getAdapterPosition()).is_checked)
                   {
                      items.get(getAdapterPosition()).setIs_checked(true);
                       parent_layout.setBackgroundColor(activity.getResources().getColor(R.color.colorgrey));
                   }
                   else
                   {
                       items.get(getAdapterPosition()).setIs_checked(false);
                       parent_layout.setBackgroundColor(activity.getResources().getColor(R.color.colorWhite));
                   }
                }
            });
        }
    }
}
