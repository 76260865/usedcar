package com.jason.usedcar;

import android.content.Intent;
import com.jason.usedcar.response.SearchFilterResponse;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jason.usedcar.fragment.BrandsChooseFragment;
import com.jason.usedcar.fragment.BrandsChooseFragment.BrandsChooseDialogListener;
import com.jason.usedcar.fragment.CarAgeChooseFragment;
import com.jason.usedcar.fragment.CarAgeChooseFragment.CarAgeChooseDialogListener;
import com.jason.usedcar.fragment.CarMilesChooseFragment;
import com.jason.usedcar.fragment.CarMilesChooseFragment.CarMilesChooseDialogListener;
import com.jason.usedcar.fragment.PriceChooseFragment;
import com.jason.usedcar.fragment.PriceChooseFragment.PriceChooseDialogListener;
import com.jason.usedcar.fragment.SeriesChooseFragment;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * 筛选活动
 * 
 * @author Administrator
 * 
 */
public class FindUsedActivity extends BaseActivity implements
        BrandsChooseDialogListener, SeriesChooseFragment.SeriersChooseDialogListener, PriceChooseDialogListener,
        CarMilesChooseDialogListener, CarAgeChooseDialogListener {
    private static final String TAG = "FindUsedActivity";
    private static final String MANUAL_STR = "manufacturerVerified:";

    private TextView mTxtBrand;
    private TextView mTxtSeries;
    private TextView mTxtPrice;
    private TextView mTxtAge;
    private TextView mTxtMiles;
    private ToggleButton toggleBtnAuth;
    private Button btnSearch;
    private BrandsChooseFragment mBrandsChooseFragment;
    private SeriesChooseFragment mSeriesChooseFragment;
    private PriceChooseFragment mPriceChooseFragment;
    private CarAgeChooseFragment mCarAgeChooseFragment;
    private CarMilesChooseFragment mCarMilesChooseFragment;
    private BrandFilterEntity mBrand;
    private FilterEntity selectedSeries;
    private FilterEntity selectedPrice;
    private FilterEntity selectedCarMile;
    private FilterEntity selectedCarAge;

    private List<BrandFilterEntity> brandFilter = new ArrayList<BrandFilterEntity>();
    private List<FilterEntity> priceFilter = new ArrayList<FilterEntity>();
    private List<FilterEntity> odometerFilter = new ArrayList<FilterEntity>();
    private List<FilterEntity> ageFilter = new ArrayList<FilterEntity>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("筛选");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        setContentView(R.layout.activity_find_used_car);
        mTxtBrand = (TextView) findViewById(R.id.txt_brand);
        mTxtPrice = (TextView) findViewById(R.id.txt_price);
        mTxtAge = (TextView) findViewById(R.id.txt_age);
        mTxtMiles = (TextView) findViewById(R.id.txt_miles);
        mTxtBrand.setOnClickListener(mOnTxtBrandsClickListener);
        mTxtSeries = (TextView) findViewById(R.id.txt_seriers);
        mTxtSeries.setOnClickListener(mOnTxtSerierssClickListener);
        mTxtPrice.setOnClickListener(mOnTxtPriceClickListener);
        mTxtAge.setOnClickListener(mOnTxtAgeClickListener);
        mTxtMiles.setOnClickListener(mOnTxtMilesClickListener);
        toggleBtnAuth = (ToggleButton) findViewById(R.id.toggle_btn_auth);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new OnBtnSearchClickListener());
        initializeSearchFilter();
    }

    @Override
    public void onSeriesChoosed(FilterEntity filter) {
        selectedSeries = filter;
        mTxtSeries.setText(filter.getName());
        mSeriesChooseFragment.dismiss();
    }

    private class OnBtnSearchClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO start a intent with the extras to main screen
            String filter = "";
            if (mBrand != null) {
                filter += mBrand.getFacetSelection() + ",";
            }
            if (selectedSeries != null) {
                filter += selectedSeries.getFacetSelection() + ",";
            }
            if (selectedPrice != null) {
                filter += selectedPrice.getFacetSelection() + ",";
            }
            if (selectedCarMile != null) {
                filter += selectedCarMile.getFacetSelection() + ",";
            }
            if (selectedCarAge != null) {
                filter += selectedCarAge.getFacetSelection() + ",";
            }
            if (toggleBtnAuth.isChecked()) {
                //TODO: 添加过滤条件，写死
                filter += " manufacturerVerified:true";
            } else {
                filter += " manufacturerVerified:false";
            }

            Intent intent = new Intent();
            intent.putExtra("filter", filter);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void initializeSearchFilter() {
        new RestClient().searchFilter(new Callback<SearchFilterResponse>() {
            @Override
            public void success(SearchFilterResponse response, retrofit.client.Response response2) {
                if (response != null && response.isExecutionResult()) {
                    brandFilter = response.getBrand();
                    priceFilter = response.getPrice();
                    odometerFilter = response.getOdometer();
                    ageFilter = response.getAge();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.getMessage();
            }
        });
    }

    private OnClickListener mOnTxtAgeClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mCarAgeChooseFragment == null) {
                mCarAgeChooseFragment = new CarAgeChooseFragment(ageFilter);
                mCarAgeChooseFragment
                        .setCarAgeChooseDialogListener(FindUsedActivity.this);
            }
            mCarAgeChooseFragment.show(getSupportFragmentManager(),
                    "ageChooseDialog");
        }
    };
    private OnClickListener mOnTxtMilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mCarMilesChooseFragment == null) {
                mCarMilesChooseFragment = new CarMilesChooseFragment(odometerFilter);
                mCarMilesChooseFragment
                        .setCarMilesChooseDialogListener(FindUsedActivity.this);
            }
            mCarMilesChooseFragment.show(getSupportFragmentManager(),
                    "milesChooseDialog");
        }
    };

    private OnClickListener mOnTxtPriceClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mPriceChooseFragment == null) {
                mPriceChooseFragment = new PriceChooseFragment(priceFilter);
                mPriceChooseFragment
                        .setPriceChooseDialogListener(FindUsedActivity.this);
            }
            mPriceChooseFragment.show(getSupportFragmentManager(),
                    "priceChooseDialog");
        }
    };
    private OnClickListener mOnTxtSerierssClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mSeriesChooseFragment == null) {
                mSeriesChooseFragment = new SeriesChooseFragment();
            }
            if (mBrand == null || TextUtils.isEmpty(mBrand.getFacetSelection())) {
                Toast.makeText(getApplicationContext(), "请先选择品牌", Toast.LENGTH_SHORT).show();
                return;
            }
            mSeriesChooseFragment.facetSelection = mBrand.getFacetSelection();
            mSeriesChooseFragment.show(getSupportFragmentManager(),
                    "seriesChooseDialog");
        }
    };

    private OnClickListener mOnTxtBrandsClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mBrandsChooseFragment == null) {
                mBrandsChooseFragment = new BrandsChooseFragment(brandFilter);
                mBrandsChooseFragment
                        .setBrandsChooseDialogListener(FindUsedActivity.this);
            }
            mBrandsChooseFragment.show(getSupportFragmentManager(),
                    "brandsChooseDialog");
        }
    };

    @Override
    public void onBrandChoosed(BrandFilterEntity brand) {
        Log.d(TAG, "brandId: " + brand.getName());
        mBrand = brand;
        mTxtBrand.setText(mBrand.getName());
        mTxtSeries.setText(null);
        mBrandsChooseFragment.dismiss();

    }

    @Override
    public void onPriceChosen(FilterEntity filter) {
        Log.d(TAG, "selected price : " + filter.getName());
        selectedPrice = filter;
        mTxtPrice.setText(filter.getName());
        mPriceChooseFragment.dismiss();
    }

    @Override
    public void onCarMilesSelected(FilterEntity filter) {
        Log.d(TAG, "selected omerter : " + filter.getName());
        selectedCarMile = filter;
        mTxtMiles.setText(filter.getName());
        mCarMilesChooseFragment.dismiss();
    }

    @Override
    public void onCarAgeChoosed(FilterEntity filter) {
        Log.d(TAG, "selected car age : " + filter.getName());
        selectedCarAge = filter;
        mTxtAge.setText(filter.getName());
        mCarAgeChooseFragment.dismiss();
    }

}
