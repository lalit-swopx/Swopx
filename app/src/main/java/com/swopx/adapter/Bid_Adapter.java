package com.swopx.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swopx.R;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.chat.Chat;
import com.swopx.chat.UserDetails;
import com.swopx.fragment.my_project_pkg.Bid_List;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Office Work on 26-12-2017.
 */

public class Bid_Adapter extends RecyclerView.Adapter<Bid_Adapter.MyViewHolder> {
    Activity activity;
    ArrayList<Items> items;
    String values;
    int call_COMPLETED;

    public Bid_Adapter(FragmentActivity activity, ArrayList<Items> items, int call_COMPLETED) {
        this.activity = activity;
        this.items = items;
        this.values = values;
        this.call_COMPLETED = call_COMPLETED;
    }

    @Override
    public Bid_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(activity).inflate(R.layout.bid_row_layout, parent, false);
        return new Bid_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Bid_Adapter.MyViewHolder holder, int position) {

        holder.name.setText(items.get(position).getName());
        holder.rating.setText(items.get(position).getRate());
        holder.category.setText(activity.getResources().getString(R.string.rupee_symbol) + " " + items.get(position).getBid() + " per " +
                items.get(position).getTime());
        if (!TextUtils.isEmpty(items.get(position).getSub_category())) {
            holder.chat.setVisibility(View.VISIBLE);
            holder.chat.setText(items.get(position).getSub_category());
        } else {
            holder.chat.setVisibility(View.GONE);
        }
        holder.bar.setRating(Float.parseFloat(items.get(position).getRate()));
        if (!TextUtils.isEmpty(items.get(position).getImage_url())) {
            Glide.with(activity).load(Url_Links.Profile_Pic_Show_Url + items.get(position).getImage_url())
                    .into(holder.pic);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, rating, chat, category;
        ImageView pic, dots;
        RatingBar bar;
        LinearLayout radio_grp;
        CheckBox award, chat_rad;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            rating = (TextView) itemView.findViewById(R.id.rating);
            category = (TextView) itemView.findViewById(R.id.category);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            chat = (TextView) itemView.findViewById(R.id.chat);
            bar = (RatingBar) itemView.findViewById(R.id.bar);

            radio_grp = (LinearLayout) itemView.findViewById(R.id.radio_grp);
            award = (CheckBox) itemView.findViewById(R.id.award);
            chat_rad = (CheckBox) itemView.findViewById(R.id.chat_rad);
            award.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        award.setTextColor(activity.getResources().getColor(R.color.colorWhite));

                        if(chat_rad.isChecked())
                        {
                            chat_rad.setTextColor(activity.getResources().getColor(R.color.colorblue));
                            chat_rad.setChecked(false);
                        }

//                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorblue));
                        Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel", items.get(getAdapterPosition()).getMobile(), null));
                        Constant.ToastShort(activity,""+items.get(getAdapterPosition()).getMobile());
                        activity.startActivityForResult(callIntent,call_COMPLETED);
                    }
                    else
                    {
                        award.setTextColor(activity.getResources().getColor(R.color.colorblue));
                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                    }
                }
            });
            chat_rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                        if(award.isChecked())
                        {
                            award.setTextColor(activity.getResources().getColor(R.color.colorblue));
                            award.setChecked(false);
                        }
                        UserDetails.chatWith=items.get(getAdapterPosition()).getName();
                        UserDetails.chatWith_id=items.get(getAdapterPosition()).getId();
                        Intent i=new Intent(activity, Chat.class);
//                        i.putExtra("project_name",items.get(getAdapterPosition()).getProject_name());
                        activity.startActivity(i);
                    }
                    else
                    {
                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorblue));
                        award.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                    }
                }
            });
            /*radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.award) {
//                  resend.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_black));
                        award.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorblue));
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel", items.get(getAdapterPosition()).getMobile(), null));
                        Constant.ToastShort(activity,""+items.get(getAdapterPosition()).getMobile());
//                        callIntent.setData(Uri.parse(items.get(getAdapterPosition()).getMobile()));
//                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
                        activity.startActivityForResult(callIntent,call_COMPLETED);
                    }
                    else  if(checkedId==R.id.chat_rad)
                    {
//                    resend.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_black));
                        chat_rad.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                        award.setTextColor(activity.getResources().getColor(R.color.colorblue));
                        UserDetails.chatWith=items.get(getAdapterPosition()).getName();
                        UserDetails.chatWith_id=items.get(getAdapterPosition()).getId();
                        Intent i=new Intent(activity, Chat.class);
                        activity.startActivity(i);
                    }
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(!items.get(getAdapterPosition()).getBid().equalsIgnoreCase("0"))
//                    {
//                        Intent i=new Intent(activity, Bid_List.class);
//                        i.putExtra("bid_id",items.get(getAdapterPosition()).getId());
//                        activity.startActivity(i);
//                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
