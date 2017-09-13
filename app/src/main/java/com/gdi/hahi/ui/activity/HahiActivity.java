package com.gdi.hahi.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gdi.hahi.R;
import com.gdi.hahi.base.BaseFragment;
import com.gdi.hahi.ui.fragment.HomeFragment;
import com.gdi.hahi.ui.fragment.LiveFragment;
import com.gdi.hahi.ui.fragment.ShowFragment;

public class HahiActivity extends AppCompatActivity {

    private TextView mTextMessage;

    Toolbar toolbar;
    TextView tvTitle;

    private BaseFragment[] fragments;
    private int index;
    private int currentTabIndex;
    HomeFragment homeFragment;
    LiveFragment liveFragment;
    ShowFragment showFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    index = 0;
//                    tvTitle.setText(getResources().getString(R.string.title_photo));
                    break;
                case R.id.navigation_dashboard:
                    index = 1;
//                    tvTitle.setText(getResources().getString(R.string.title_video));
                    break;
                case R.id.navigation_notifications:
                    index = 2;
//                    tvTitle.setText(getResources().getString(R.string.title_novel));
                    break;
            }
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments[currentTabIndex]);
                if (!fragments[index].isAdded()) {
                    trx.add(R.id.fragment_container, fragments[index]);
                }
                trx.show(fragments[index]).commit();
            } else {
                fragments[currentTabIndex].smoothScrollToPosition(0);
            }
            currentTabIndex = index;
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hahi);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
    }

    private void init() {
        homeFragment = new HomeFragment();
        liveFragment = new LiveFragment();
        showFragment = new ShowFragment();
        fragments = new BaseFragment[]{homeFragment, liveFragment, showFragment};
        // add and show first fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, liveFragment).hide(liveFragment).show(homeFragment)
                .commit();

    }
}
