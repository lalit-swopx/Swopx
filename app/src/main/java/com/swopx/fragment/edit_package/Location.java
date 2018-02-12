package com.swopx.fragment.edit_package;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swopx.R;

/**
 * Created by Office Work on 06-12-2017.
 */

public class Location  extends AppCompatActivity implements View.OnClickListener {
    private TextView type,info,done;
    private ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_layout);
        intialize();
    }

    private void intialize() {
        type=(TextView)findViewById(R.id.type);
        info=(TextView)findViewById(R.id.info);
        done=(TextView)findViewById(R.id.done);
        back=(ImageButton) findViewById(R.id.back);
        setValues();
        onClick();
    }

    private void onClick() {
        done.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void setValues() {
        type.setText(getIntent().getStringExtra("type"));
        info.setText(getIntent().getStringExtra("info"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.done:
                finish();

                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
