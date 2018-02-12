package com.swopx.fragment.browse_category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.registration.Connection_Activity;

/**
 * Created by Office Work on 22-12-2017.
 */

public class Placed_Bid extends AppCompatActivity implements View.OnClickListener {

    private TextView invite_all,skip,quantity,price,unit1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_placed_layout);
        intial();
    }

    private void intial() {
        invite_all=(TextView)findViewById(R.id.invite_all);
        skip=(TextView)findViewById(R.id.skip);
        price=(TextView)findViewById(R.id.price);
        quantity=(TextView)findViewById(R.id.quantity);
        unit1=(TextView)findViewById(R.id.unit1);
        skip.setOnClickListener(this);
        invite_all.setOnClickListener(this);
        unit1.setText("Your Current Bid(₹/"+getIntent().getStringExtra("unit")+")");
        quantity.setText(getIntent().getStringExtra("quantity")+getIntent().getStringExtra("unit"));
        price.setText(Html.fromHtml("<sup><small>\u20B9</small></sup>"+getIntent().getStringExtra("price")+"/"+getIntent().getStringExtra("unit")));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.invite_all:
                Intent invite=new Intent(Placed_Bid.this,Connection_Activity.class);
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
