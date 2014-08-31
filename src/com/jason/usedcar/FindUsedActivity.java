package com.jason.usedcar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jason.usedcar.fragment.BrandsChooseFragment;
import com.jason.usedcar.fragment.BrandsChooseFragment.BrandsChooseDialogListener;
import com.jason.usedcar.fragment.CarAgeChooseFragment;
import com.jason.usedcar.fragment.CarMilesChooseFragment;
import com.jason.usedcar.fragment.ModelChooseFragment.ModelChooseDialogListener;
import com.jason.usedcar.fragment.PriceChooseFragment;
import com.jason.usedcar.fragment.SeriesChooseFragment;
import com.jason.usedcar.fragment.SeriesChooseFragment.SeriersChooseDialogListener;
import com.jason.usedcar.model.data.Brand;

/**
 * 筛选活动
 * 
 * @author Administrator
 * 
 */
public class FindUsedActivity extends FragmentActivity implements
		BrandsChooseDialogListener, SeriersChooseDialogListener,
		ModelChooseDialogListener {
	private static final String TAG = "FindUsedActivity";

	private TextView mTxtBrand;
	private TextView mTxtSeriers;
	private TextView mTxtPrice;
	private TextView mTxtAge;
	private TextView mTxtMiles;
	private BrandsChooseFragment mBrandsChooseFragment;
	private SeriesChooseFragment mSeriesChooseFragment;
	private PriceChooseFragment mPriceChooseFragment;
	private CarAgeChooseFragment mCarAgeChooseFragment;
	private CarMilesChooseFragment mCarMilesChooseFragment;
	private Brand mBrand;

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
	}

	private OnClickListener mOnTxtAgeClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mCarAgeChooseFragment == null) {
				mCarAgeChooseFragment = new CarAgeChooseFragment();
			}
			mCarAgeChooseFragment.show(getSupportFragmentManager(),
					"ageChooseDialog");
		}
	};
	private OnClickListener mOnTxtMilesClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mCarMilesChooseFragment == null) {
				mCarMilesChooseFragment = new CarMilesChooseFragment();
			}
			mCarMilesChooseFragment.show(getSupportFragmentManager(),
					"milesChooseDialog");
		}
	};

	private OnClickListener mOnTxtPriceClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mPriceChooseFragment == null) {
				mPriceChooseFragment = new PriceChooseFragment();
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
				mBrandsChooseFragment = new BrandsChooseFragment();
			}
			mBrandsChooseFragment.show(getSupportFragmentManager(),
					"brandsChooseDialog");
		}
	};

	@Override
	public void onBrandChoosed(Brand brand) {
		Log.d(TAG, "brandId: " + brand.getBrandId());
		mBrand = brand;
		mTxtBrand.setText(getString(R.string.txt_brands)
				+ mBrand.getBrandName());
		mBrandsChooseFragment.dismiss();
	}

	@Override
	public void onModelsChoosed(int seriersId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSeriersChoosed(int modelId) {
		// TODO Auto-generated method stub

	}

}
