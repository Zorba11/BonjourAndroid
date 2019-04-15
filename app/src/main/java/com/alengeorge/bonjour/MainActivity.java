package com.alengeorge.bonjour;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;

    private TabsAccessorAdapter myTabsAccessorAdapter;


    @SuppressLint({"RestrictedApi", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = findViewById(R.id.mainPageToolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setWindowTitle("Bonjour");

        myViewPager = findViewById(R.id.mainTabs_pager);
        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        myTabLayout = findViewById(R.id.mainTabs);
        myTabLayout.setupWithViewPager(myViewPager);
    }
}
