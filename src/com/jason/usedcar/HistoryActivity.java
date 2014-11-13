package com.jason.usedcar;

import android.widget.RadioButton;
import com.jason.usedcar.fragment.OrderListFragment;
import com.jason.usedcar.fragment.SaleCarFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class HistoryActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final int FRAGMENT_ORDER = 0;

    private static final int FRAGMENT_SALE = 1;

    private ViewPager mViewPager;

    private RadioButton historyOrderRadio;

    private RadioButton historySaleRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.history_ab);

        historyOrderRadio = (RadioButton) findViewById(R.id.left);
        historySaleRadio = (RadioButton) findViewById(R.id.right);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                mViewPager.setCurrentItem(FRAGMENT_ORDER);
                break;
            case R.id.right:
                mViewPager.setCurrentItem(FRAGMENT_SALE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //
    }

    @Override
    public void onPageSelected(final int position) {
        if (position == FRAGMENT_ORDER) {
            historyOrderRadio.setChecked(true);
        }
        else if (position == FRAGMENT_SALE) {
            historySaleRadio.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        //
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == FRAGMENT_ORDER) {
                return new OrderListFragment();
            } else if (position == FRAGMENT_SALE) {
                return new SaleCarFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.history_order);
                case 1:
                    return getResources().getString(R.string.history_sale);
            }
            return null;
        }
    }
}
