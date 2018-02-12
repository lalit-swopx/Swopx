package com.swopx.registration;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.adapter.WalkThrough_Adapter;
import com.swopx.setter_getter.Items;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Office Work on 05-01-2018.
 */

public class WalkThrough_Activity extends AppCompatActivity {


    private TextView getStarted,skip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_through_layout);
//        skip=(TextView)findViewById(R.id.skip);
        getStarted=(TextView)findViewById(R.id.getStarted);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        viewpager.setAdapter(new WalkThrough_Adapter(this,getStarted));
        indicator.setViewPager(viewpager);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalkThrough_Activity.this, Coupon_Activity.class);
                startActivity(intent);
                finish();
            }
        });
//        skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    private void add_images(Drawable drawable, ArrayList<Items> images_arr) {
        Items i=new Items();
        i.setDrawable_images(drawable);
        images_arr.add(i);


    }
}
