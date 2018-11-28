package com.abelli.estudoapiactivitys;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultFragment();
    }

    public void setDefaultFragment(){
        Fragment fragment = null;
        Class fragmentClass = ListFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,fragment).commit();
    }
}

