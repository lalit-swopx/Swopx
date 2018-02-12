package com.swopx;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.swopx.fragment.dashboard.Dashboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Fragment currentFragment;
    public DrawerLayout drawer;
    private ImageButton menu_button;
    private ActionBarDrawerToggle actionBarDrawerToggle, actionBarDrawerSubToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_view();
    }

    private void init_view() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        menu_button=(ImageButton) toolbar.findViewById(R.id.menu_button);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(actionBarDrawerToggle);
        menu_button.setOnClickListener(this);
        replaceFragment(new Dashboard());
    }
    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }
    public void replaceFragmentBack(Fragment fragment) {
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();/*commitAllowingStateLoss()*/;
    }
    @Override
    public void onBackPressed() {
         if(currentFragment instanceof Dashboard)
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.menu_button:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
                default:break;
        }
    }
}
