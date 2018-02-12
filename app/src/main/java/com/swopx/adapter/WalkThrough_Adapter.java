package com.swopx.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.registration.Coupon_Activity;
import com.swopx.registration.WalkThrough_Activity;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Office Work on 05-01-2018.
 */

public class WalkThrough_Adapter extends PagerAdapter {
    int[] mResources = {
            R.drawable.walkthrough1,
            R.drawable.walkthrough5,
            R.drawable.walkthrough2,
            R.drawable.walkthrough3,
            R.drawable.walkthrough4,

    };
    TextView getStarted;
    Activity mContext;
    LayoutInflater mLayoutInflater;

    public WalkThrough_Adapter(Activity context, TextView getStarted) {
        mContext = context;
        this.getStarted = getStarted;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView skip = (TextView) itemView.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Coupon_Activity.class);
                mContext.startActivity(intent);
                mContext.finish();
            }
        });
        if(position==4)
        {
            getStarted.setVisibility(View.VISIBLE);
        }
        else
        {
            getStarted.setVisibility(View.GONE);
        }
        imageView.setImageResource(mResources[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
