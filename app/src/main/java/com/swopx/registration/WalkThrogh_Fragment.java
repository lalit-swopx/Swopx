package com.swopx.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swopx.R;

/**
 * Created by Office Work on 05-01-2018.
 */

public class WalkThrogh_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walk_through_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ViewPager pager = (ViewPager) view.findViewById(R.id.photos_viewpager);
////        PagerAdapter adapter = new PhotosAdapter(getChildFragmentManager(), photosUrl);
////        pager.setAdapter(adapter);
//
//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(pager, true);
    }
}
