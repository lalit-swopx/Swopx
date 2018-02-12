package com.swopx.fragment.my_project_pkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swopx.R;
import com.swopx.fragment.browse_category.Category;
import com.swopx.setter_getter.Items;
import com.swopx.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Office Work on 22-12-2017.
 */

public class Post_Project_Main extends AppCompatActivity {
    public Fragment currentFragment;
    public TextView back,title,next,cancel;
    public String sub_Indus,sub_Indud_id,cate_name,cat_id,sub_cat,sub_cat_id,royalty,descrip,unit;
    public Items child=new Items();
    public ArrayList<Items> child_cat=new ArrayList<>();
    public LinearLayout back_layout,cancel_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_project_header);
        back=(TextView)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title);
        next=(TextView)findViewById(R.id.next);
        cancel=(TextView)findViewById(R.id.cancel);
        cancel_layout=(LinearLayout) findViewById(R.id.cancel_layout);
        back_layout=(LinearLayout) findViewById(R.id.back_layout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(descrip))
                {
                    Intent i=new Intent(Post_Project_Main.this,Project_Post.class);
                    i.putExtra("sub_Indus",sub_Indus);
                    i.putExtra("sub_Indud_id",sub_Indud_id);
                    i.putExtra("cat_id",cat_id);
                    i.putExtra("cate_name",cate_name);
                    i.putExtra("sub_cat",sub_cat);
                    i.putExtra("sub_cat_id",sub_cat_id);
                    i.putExtra("descrip",descrip);
                    i.putExtra("unit",unit);
                    i.putExtra("child_cat", child_cat);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Constant.ToastShort(Post_Project_Main.this,"Please enter the description");
                }
            }
        });
            Sub_Industry_My_Project fragment = new Sub_Industry_My_Project();
            replaceFragment(fragment);

    }

    @Override
    public void onBackPressed() {
        Constant.Log("Current Fragment=====",":"+currentFragment);
        if(currentFragment instanceof Sub_Industry_My_Project)
        {
            finish();
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        currentFragment.onActivityResult(requestCode, resultCode, data);

    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }
}
