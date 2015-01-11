package com.jason.usedcar;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.jason.usedcar.fragment.BuyCarFragment;
import com.jason.usedcar.fragment.PersonalCenterFragment;
import com.jason.usedcar.fragment.SellCarFragment;
import com.jason.usedcar.fragment.ShoppingCarFragment;

public class MainTabActivity extends ActionBarActivity {
    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;

    private Class<?> fragmentArray[] = { BuyCarFragment.class, SellCarFragment.class,
            ShoppingCarFragment.class, PersonalCenterFragment.class };

    private int mImageViewArray[] = { R.drawable.tab_buy_car_btn, R.drawable.tab_sell_car_btn,
            R.drawable.tab_shop_car_btn, R.drawable.tab_personal_car_btn,
            R.drawable.tab_more_car_btn };

    private String mTextviewArray[] = { "BuyCar", "ShoppingChart", "SellCar", "PersonalCenter",
            "More" };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setIcon(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initView();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }
}
