package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.swopx.R;

/**
 * Created by Office Work on 05-01-2018.
 */

public class Coupon_Activity extends AppCompatActivity {
    private ImageButton cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_layout);
        cancel=(ImageButton)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Coupon_Activity.this, Dashboard_Main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
