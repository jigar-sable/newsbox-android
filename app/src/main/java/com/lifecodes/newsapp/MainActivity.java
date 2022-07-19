package com.lifecodes.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lifecodes.newsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String appname = "";

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Toast.makeText(this, "Dark Mode", Toast.LENGTH_LONG).show();
                appname = "<font color=#ffffff>News</font><font color=#5800FC>Box</font>";
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // Toast.makeText(this, "Light Mode", Toast.LENGTH_LONG).show();
                appname = "<font color=#424242>News</font><font color=#5800FC>Box</font>";
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
        binding.appNameTv.setText(Html.fromHtml(appname));

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(new HomeFragment(), "Home");
        pagerAdapter.addFragment(new SportsFragment(), "Sports");
        pagerAdapter.addFragment(new TechFragment(), "Tech");
        pagerAdapter.addFragment(new HealthFragment(), "Health");
        pagerAdapter.addFragment(new EntertainmentFragment(), "Entertainment");
        pagerAdapter.addFragment(new ScienceFragment(), "Science");

        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        int betweenSpace = 30;

        ViewGroup slidingTabStrip = (ViewGroup) binding.tabLayout.getChildAt(0);

        for (int i = 0; i < slidingTabStrip.getChildCount() - 1; i++) {
            View v = slidingTabStrip.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.rightMargin = betweenSpace;
            params.height = 110;
        }
    }
}