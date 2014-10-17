package com.jason.usedcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.presentation_model.CarDetailsView;
import com.jason.usedcar.presentation_model.CarDetailsViewModel;
import com.jason.usedcar.presentation_model.MenuDetailsViewModel;
import com.jason.usedcar.response.CarResponse3;
import org.robobinding.MenuBinder;

/**
 * @author t77yq @2014-09-29.
 */
public class CarDetails2Activity extends AbsActivity implements CarDetailsView {

    private CarDetailsViewModel carDetailsViewModel;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setIcon(android.R.color.transparent);
        String productId = getIntent().getStringExtra("product_id");
        int type = getIntent().getIntExtra("type", Constants.CarDetailsType.OTHER);
        carDetailsViewModel = new CarDetailsViewModel(productId, type, this);
        initContentView(R.layout.activity_car_details2, carDetailsViewModel);
        carDetailsViewModel.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        int type = getIntent().getIntExtra("type", Constants.CarDetailsType.OTHER);
        MenuDetailsViewModel menuDetailsViewModel = new MenuDetailsViewModel(type, this);
        MenuBinder menuBinder = createMenuBinder(menu, getMenuInflater());
        menuBinder.inflateAndBind(R.menu.menu_save, menuDetailsViewModel);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callCarOwner(String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    @Override
    public void openCalculator(Double carPrice) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("car_price", carPrice);
        startActivity(intent);
    }

    @Override
    public void before() {
        loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void after() {
        if (loadingFragment != null && loadingFragment.isAdded()) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public String getProductId() {
        return carDetailsViewModel.getProductId();
    }

    @Override
    public void editCar() {
        Intent edit = new Intent(this, SellCarActivity.class);
        edit.putExtra("car_response", carDetailsViewModel.getCarResponse());
        startActivity(edit);
    }
}
