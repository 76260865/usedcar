package com.jason.usedcar;

import android.support.v4.app.FragmentActivity;

import com.jason.usedcar.fragment.BrandsChooseFragment.BrandsChooseDialogListener;
import com.jason.usedcar.fragment.ModelChooseFragment.ModelChooseDialogListener;
import com.jason.usedcar.fragment.SeriesChooseFragment.SeriersChooseDialogListener;

/**
 * 筛选活动
 * 
 * @author Administrator
 * 
 */
public class FindUsedActivity extends FragmentActivity implements
		BrandsChooseDialogListener, SeriersChooseDialogListener,
		ModelChooseDialogListener {

	@Override
	public void onBrandChoosed(int brandId) {
		// TODO Auto-generated method stub

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
