package com.example.pixiefruit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class introActivity extends AppCompatActivity {
    ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabLayout;
    Button mainActivitybtn;
    Button getstartedBtn;
    int position;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        if(restorePref()){
            Intent intent=new Intent(getApplicationContext(),SignIn.class);
            startActivity(intent);
            finish();
        }
        screenPager=findViewById(R.id.pager);
        tabLayout=findViewById(R.id.tabLayout);
        final List<ScreenItem> mlist=new ArrayList<>();
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        mlist.add(new ScreenItem("Pixie Fruit", "Citrus Fruit Counting App!", R.drawable.common_full_open_on_phone));
        mlist.add(new ScreenItem("Capture and Count", "Using the Power of Machine Learning Count and Estimate Yield Just With a Click", R.drawable.ic_baseline_find_in_page_24));
        mlist.add(new ScreenItem("Detect Diseases","Find Diseases on fruits and leaves",R.drawable.ic_info_black_24dp));
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mlist);
        screenPager.setAdapter(introViewPagerAdapter);
        tabLayout.setupWithViewPager(screenPager);
        mainActivitybtn=findViewById(R.id.button);
        getstartedBtn=findViewById(R.id.button_getstarted);
        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mlist.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainActivitybtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                position=screenPager.getCurrentItem();
                if(position<mlist.size()-1){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position==mlist.size()){
                    loadLastScreen();
                }
            }
        });
        getstartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
                savePrefs();
                finish();
            }
        });
    }

    private boolean restorePref() {
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("myprefs",MODE_PRIVATE);
        boolean data=    sharedPreferences.getBoolean("isIntroOpened",false);
        return data;
    }

    private void savePrefs() {
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private void loadLastScreen() {
        mainActivitybtn.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        getstartedBtn.setVisibility(View.VISIBLE);
        getstartedBtn.setAnimation(animation);
    }
}
