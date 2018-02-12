package com.swopx.fragment.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swopx.R;
import com.swopx.adapter.View_Pager_Adapter;
import com.swopx.registration.Dashboard_Main;

/**
 * Created by Office Work on 05-12-2017.
 */

public class Home_Class extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }
    private void intialise_views(View view) {
        ((Dashboard_Main) getActivity()).currentFragment = this;
        ((Dashboard_Main) getActivity()).header.setVisibility(View.GONE);
        ((Dashboard_Main) getActivity()).header2.setVisibility(View.GONE);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        View_Pager_Adapter adapter = new View_Pager_Adapter(getChildFragmentManager());
        adapter.addFragment(new Dashboard(), "Popular");
//        adapter.addFragment(new TwoFragment(), "TWO");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
}
