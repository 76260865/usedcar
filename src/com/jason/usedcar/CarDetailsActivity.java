package com.jason.usedcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.pm.CarDetailsPM;
import com.jason.usedcar.pm.menu.MenuDetailsPM;
import com.jason.usedcar.pm.view.CarDetailsView;
import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * @author t77yq @2014-09-29.
 */
public class CarDetailsActivity extends AbsActivity implements CarDetailsView {

    private CarDetailsPM carDetailsViewModel;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        String productId = getIntent().getStringExtra("product_id");
        int type = getIntent().getIntExtra("type", Constants.CarDetailsType.OTHER);
        carDetailsViewModel = new CarDetailsPM(productId, type, this);
        initContentView(R.layout.activity_car_details2, carDetailsViewModel);
        carDetailsViewModel.loadData();
        WebView webView = (WebView)findViewById(R.id.webview);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        int type = getIntent().getIntExtra("type", Constants.CarDetailsType.OTHER);
        MenuDetailsPM menuDetailsViewModel = new MenuDetailsPM(type, this);
        MenuBinder menuBinder = createMenuBinder(menu, getMenuInflater());
        menuBinder.inflateAndBind(R.menu.menu_save, menuDetailsViewModel);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected ViewBinder createViewBinder() {
        return new BinderFactoryBuilder().mapView(WebView.class, new WebViewBinding())
                .build().createViewBinder(this, true);
    }

    @Override
    public void callCarOwner(String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    @Override
    public void openCalculator(double carPrice) {
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return carDetailsViewModel.getCarResponse().getCarImages().size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getApplicationContext());
                Glide.with(getApplicationContext()).load("http://www.2soce.com"
                + carDetailsViewModel.getCarResponse().getCarImages().get(position).getSizeJumbo()).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
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
