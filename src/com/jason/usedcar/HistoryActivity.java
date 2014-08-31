package com.jason.usedcar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.jason.usedcar.fragment.BuyCarHistoryFragment;
import com.jason.usedcar.fragment.SellCarHistoryFragment;
import com.jason.usedcar.model.UsedCar;
import java.util.List;


public class HistoryActivity extends BaseActivity implements
        ActionBar.OnNavigationListener,
        BuyCarHistoryFragment.Data,
        SellCarHistoryFragment.Data {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    private List<UsedCar> buyCarHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.text1,
                android.R.id.text1,
                new String[] {
                    getString(R.string.activity_history_car_bought),
                    getString(R.string.activity_history_car_sold)
                }),
            this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
            getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                ft.replace(R.id.container, new BuyCarHistoryFragment()).commit();
                break;
            case 1:
                ft.replace(R.id.container, new SellCarHistoryFragment()).commit();
                break;
        }
        return true;
    }

    @Override
    public List<UsedCar> getData(Fragment fragment) {
        if (fragment instanceof BuyCarHistoryFragment) {
            return buyCarHistory;
        }
        return null;
    }

    @Override
    public void setData(Fragment fragment, final List<UsedCar> data) {
        if (fragment instanceof BuyCarHistoryFragment) {
            buyCarHistory = data;
        }
    }
}
