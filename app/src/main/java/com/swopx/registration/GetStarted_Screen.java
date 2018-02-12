package com.swopx.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.swopx.R;

/**
 * Created by Office Work on 01-12-2017.
 */

public class GetStarted_Screen extends AppCompatActivity {
 private TextView mobile_no;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started_layout);
        mobile_no=(TextView)findViewById(R.id.mobile_no);
        mobile_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GetStarted_Screen.this,Login_Main.class);
                startActivity(i);
                finish();
            }
        });
    }
}
