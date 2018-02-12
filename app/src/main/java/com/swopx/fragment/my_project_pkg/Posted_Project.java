package com.swopx.fragment.my_project_pkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.fragment.browse_category.Placed_Bid;
import com.swopx.registration.Connection_Activity;

/**
 * Created by Office Work on 22-12-2017.
 */

public class Posted_Project extends AppCompatActivity implements View.OnClickListener {

    private TextView invite_all, skip, cat_sub, qty,days;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posted_my_project_layout);
        intial();
    }

    private void intial() {
        invite_all = (TextView) findViewById(R.id.invite_all);
        skip = (TextView) findViewById(R.id.skip);
        cat_sub = (TextView) findViewById(R.id.cat_sub);
        qty = (TextView) findViewById(R.id.qty);
        days = (TextView) findViewById(R.id.days);
        skip.setOnClickListener(this);
        invite_all.setOnClickListener(this);
        cat_sub.setText(getIntent().getStringExtra("category")+"("+getIntent().getStringExtra("subcategory")+")");
        qty.setText(Html.fromHtml("<sup><small>Qty</small></sup> " + getIntent().getStringExtra("qty") +" " + getIntent().getStringExtra("unit")));
        days.setText(getIntent().getStringExtra("when"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invite_all:
                Intent invite=new Intent(Posted_Project.this,Connection_Activity.class);
                startActivity(invite);
                finish();
                break;

            case R.id.skip:
                finish();
                break;
            default:
                break;
        }
    }
}
