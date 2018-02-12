package com.swopx.fragment.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.adapter.Grid_Adapter;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import java.util.ArrayList;

/**
 * Created by Office Work on 14-12-2017.
 */

public class When extends Fragment {

    private RadioGroup rd_grp;
    private RadioButton flex,soon,within,week;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.when_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init_views(view);
    }

    private void init_views(View view) {
        rd_grp=(RadioGroup) view.findViewById(R.id.rd_grp);
        flex=(RadioButton) view.findViewById(R.id.flex);
        within=(RadioButton) view.findViewById(R.id.within);
        week=(RadioButton) view.findViewById(R.id.week);
        soon=(RadioButton) view.findViewById(R.id.soon);
        ((Dashboard_Main)getActivity()).subcat_id=getArguments().getString("id");
        ((Dashboard_Main)getActivity()).subcat_name=getArguments().getString("name");
        rd_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.flex)
                {
                    ((Dashboard_Main)getActivity()).when=flex.getText().toString().trim();
                    flex.setTextColor(getResources().getColor(R.color.colorWhite));
                    soon.setTextColor(getResources().getColor(R.color.colorBlack));
                    within.setTextColor(getResources().getColor(R.color.colorBlack));
                    week.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                else  if(checkedId==R.id.soon)
                {

                    ((Dashboard_Main)getActivity()).when=soon.getText().toString().trim();
                    soon.setTextColor(getResources().getColor(R.color.colorWhite));
                    flex.setTextColor(getResources().getColor(R.color.colorBlack));
                    within.setTextColor(getResources().getColor(R.color.colorBlack));
                    week.setTextColor(getResources().getColor(R.color.colorBlack));

                }
                else  if(checkedId==R.id.within)
                {
                    ((Dashboard_Main)getActivity()).when=within.getText().toString().trim();
                    within.setTextColor(getResources().getColor(R.color.colorWhite));
                    flex.setTextColor(getResources().getColor(R.color.colorBlack));
                    soon.setTextColor(getResources().getColor(R.color.colorBlack));
                    week.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                else  if(checkedId==R.id.week)
                {
                    ((Dashboard_Main)getActivity()).when=week.getText().toString().trim();
                    week.setTextColor(getResources().getColor(R.color.colorWhite));
                    flex.setTextColor(getResources().getColor(R.color.colorBlack));
                    within.setTextColor(getResources().getColor(R.color.colorBlack));
                    soon.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                ((Dashboard_Main) getActivity()).replaceFragment(new Quantity());

            }
        });

    }



}
