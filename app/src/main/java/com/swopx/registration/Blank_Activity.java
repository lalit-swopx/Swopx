package com.swopx.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.swopx.R;
import com.swopx.utils.CustomDialog;

/**
 * Created by Office Work on 09-01-2018.
 */

public class Blank_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        CustomDialog cdd = new CustomDialog(Blank_Activity.this, "out");
        cdd.show();
    }
}
