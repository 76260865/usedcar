package com.jason.usedcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.jason.usedcar.util.HttpUtil;

/**
 * 筛选活动
 *
 * @author Administrator
 */
public class FindUsedActivity extends FragmentActivity implements
        BrandsChooseDialogListener, PriceChooseDialogListener,
        CarMilesChooseDialogListener, CarAgeChooseDialogListener {
    private static final String TAG = "FindUsedActivity";
    private static final String MANUAL_STR = "manufacturerVerified:";

    private TextView mTxtBrand;
    private TextView mTxtSeriers;
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
    private FilterEntity selectedPrice;
    private FilterEntity selectedCarMile;
    private FilterEntity selectedCarAge;

    private ArrayList<BrandFilterEntity> brandsFilters = new ArrayList<BrandFilterEntity>();
    private ArrayList<FilterEntity> prices = new ArrayList<FilterEntity>();
    private ArrayList<FilterEntity> odometers = new ArrayList<FilterEntity>();
    private ArrayList<FilterEntity> ages = new ArrayList<FilterEntity>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_find_used_car);
        mTxtBrand = (TextView) findViewById(R.id.txt_brand);
        mTxtPrice = (TextView) findViewById(R.id.txt_price);
        mTxtAge = (TextView) findViewById(R.id.txt_age);
        mTxtMiles = (TextView) findViewById(R.id.txt_miles);
        mTxtBrand.setOnClickListener(mOnTxtBrandsClickListener);
        mTxtSeriers = (TextView) findViewById(R.id.txt_seriers);
        mTxtSeriers.setOnClickListener(mOnTxtSerierssClickListener);
        mTxtPrice.setOnClickListener(mOnTxtPriceClickListener);
        mTxtAge.setOnClickListener(mOnTxtAgeClickListener);
        mTxtMiles.setOnClickListener(mOnTxtMilesClickListener);
        toggleBtnAuth = (ToggleButton) findViewById(R.id.toggle_btn_auth);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new OnBtnSearchClickListener());
        initializeSearchFilter();
    }

    private class OnBtnSearchClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO start a intent with the extras to main screen
            String filter = "";
            if (mBrand != null) {
                filter += mBrand.getFacetSelection() + ",";
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
        JsonObjectRequest request = new JsonObjectRequest(Method.POST,
                HttpUtil.SEARCH_FILTER_URI, null, mResponseListener,
                mErrorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("User-Agent", Config.USER_AGENT);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private Response.Listener<JSONObject> mResponseListener = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject jsonObject) {
            Log.d(TAG, jsonObject.toString());
            try {
                Gson gson = new Gson();
                String brandArray = jsonObject.getString("brand");
                brandsFilters = gson.fromJson(brandArray,
                        new TypeToken<ArrayList<BrandFilterEntity>>() {
                        }.getType());
                Log.d(TAG, "brandsFilters.size():" + brandsFilters.size());

                String priceArray = jsonObject.getString("price");
                prices = gson.fromJson(priceArray,
                        new TypeToken<ArrayList<FilterEntity>>() {
                        }.getType());
                Log.d(TAG, "prices.size():" + prices.size());

                String odometer = jsonObject.getString("odometer");
                odometers = gson.fromJson(odometer,
                        new TypeToken<ArrayList<FilterEntity>>() {
                        }.getType());
                Log.d(TAG, "odometers.size():" + odometers.size());

                String age = jsonObject.getString("age");
                ages = gson.fromJson(age,
                        new TypeToken<ArrayList<FilterEntity>>() {
                        }.getType());
                Log.d(TAG, "ages.size():" + ages.size());

            } catch (NumberFormatException e) {
                Log.e(TAG, e.getMessage());
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // error
            Log.e(TAG, new String(error.networkResponse.data).toString());
        }
    };

    private OnClickListener mOnTxtAgeClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mCarAgeChooseFragment == null) {
                mCarAgeChooseFragment = new CarAgeChooseFragment(ages);
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
                mCarMilesChooseFragment = new CarMilesChooseFragment(odometers);
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
                mPriceChooseFragment = new PriceChooseFragment(prices);
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
            mSeriesChooseFragment.show(getSupportFragmentManager(),
                    "seriesChooseDialog");
        }
    };

    private OnClickListener mOnTxtBrandsClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mBrandsChooseFragment == null) {
                mBrandsChooseFragment = new BrandsChooseFragment(brandsFilters);
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
        mBrandsChooseFragment.dismiss();

    }

    @Override
    public void onPriceChoosed(FilterEntity filter) {
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
