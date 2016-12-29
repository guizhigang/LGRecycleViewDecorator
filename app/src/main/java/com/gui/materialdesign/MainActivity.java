package com.gui.materialdesign;

import android.support.annotation.BinderThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //去除title
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(null);
        actionBar.setDisplayShowTitleEnabled(false);
    }
}
