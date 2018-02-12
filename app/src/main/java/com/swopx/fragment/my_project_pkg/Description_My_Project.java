package com.swopx.fragment.my_project_pkg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.swopx.R;
import com.swopx.Urls_Api.LoginApi;
import com.swopx.Urls_Api.Url_Links;
import com.swopx.adapter.Category_Adapter;
import com.swopx.fragment.edit_package.Edit;
import com.swopx.registration.Dashboard_Main;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Office Work on 23-12-2017.
 */

public class Description_My_Project extends Fragment {
   private EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description_my_project_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialise_views(view);
    }

    private void intialise_views(View view) {
        ((Post_Project_Main) getActivity()).currentFragment = this;
        ((Post_Project_Main) getActivity()).cancel_layout.setVisibility(View.GONE);
        ((Post_Project_Main) getActivity()).back_layout.setVisibility(View.VISIBLE);
        ((Post_Project_Main) getActivity()).next.setVisibility(View.VISIBLE);
        ((Post_Project_Main) getActivity()).title.setText(getActivity().getResources().getString(R.string.post_a_project));
//        ((Post_Project_Main) getActivity()).sub_cat_id=getArguments().getString("id");
//        ((Post_Project_Main) getActivity()).sub_cat=getArguments().getString("name");
         description=(EditText)view.findViewById(R.id.description);
         description.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 ((Post_Project_Main) getActivity()).descrip=description.getText().toString().trim();
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });


    }

}