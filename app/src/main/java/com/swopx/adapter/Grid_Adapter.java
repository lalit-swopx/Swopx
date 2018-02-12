package com.swopx.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swopx.R;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.fragment.category.Quantity;
import com.swopx.fragment.category.Size;
import com.swopx.fragment.category.Subcategory;
import com.swopx.fragment.category.When;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Office Work on 22-11-2017.
 */

public class Grid_Adapter extends RecyclerView.Adapter<Grid_Adapter.MyViewHolder> {
    Activity activity;
    ArrayList<Items> items;
    String value;
    EditText search_edt;

    public Grid_Adapter(Activity activity, ArrayList<Items> items, String value, EditText search_edt) {
        this.items=items;
        this.activity=activity;
        this.value=value;
        this.search_edt=search_edt;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(activity).inflate(R.layout.grid_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

     if(value.equalsIgnoreCase("category"))
     {
         holder.linear_layout.setVisibility(View.VISIBLE);
         holder.title1.setVisibility(View.GONE);
         holder.title.setText(items.get(position).getTitle());
         holder.title.setTag(items.get(position).getId());
         Constant.Log("IMage PAtgh==","====="+Url_Links.Image_Show_Url+items.get(position).getImage_url());
         Glide.with(activity).load(Url_Links.Image_Show_Url+items.get(position).getImage_url())
                 .into(holder.item_image);

     }
     else
     {
         holder.linear_layout.setVisibility(View.GONE);
         holder.title1.setVisibility(View.VISIBLE);
         holder.title1.setText(items.get(position).getTitle());
         holder.title1.setTag(items.get(position).getId());
     }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView title,title1;
        LinearLayout linear_layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            item_image=(ImageView)itemView.findViewById(R.id.item_image);
            title=(TextView) itemView.findViewById(R.id.title);
            title1=(TextView) itemView.findViewById(R.id.title1);
            linear_layout=(LinearLayout) itemView.findViewById(R.id.linear_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", items.get(getAdapterPosition()).getId());
                    bundle.putString("name", items.get(getAdapterPosition()).getTitle());
                    if(value.equalsIgnoreCase("category"))
                    {
                        Subcategory fragment = new Subcategory();
                        fragment.setArguments(bundle);
                        ((Dashboard_Main) activity).replaceFragment(fragment);
                    }
                   else  if(value.equalsIgnoreCase("subcategory"))
                    {
                        Size fragment = new Size();
                        fragment.setArguments(bundle);
                        ((Dashboard_Main) activity).replaceFragment(fragment);
                    }
                    else  if(value.equalsIgnoreCase("subcategorys"))
                    {
                        When fragment = new When();
                        fragment.setArguments(bundle);
                        ((Dashboard_Main) activity).replaceFragment(fragment);
                    }

                    else  if(value.equalsIgnoreCase("quantity"))
                    {
                        search_edt.setText(items.get(getAdapterPosition()).getTitle());

                    }


                }
            });
        }
    }
}
