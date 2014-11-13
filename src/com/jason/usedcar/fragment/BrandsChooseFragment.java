package com.jason.usedcar.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jason.usedcar.R;
import com.jason.usedcar.adapter.BrandSearchFilterAdapter;
import com.jason.usedcar.model.data.BrandFilterEntity;
import java.util.List;

public class BrandsChooseFragment extends DialogFragment {
	private static final String TAG = "BrandsChooseFragment";

	// 使用这个接口的实例提供行动的事件
	private BrandsChooseDialogListener mChooseListener;

	private BrandSearchFilterAdapter mAdapter;

	private List<BrandFilterEntity> brandsFilters = new ArrayList<BrandFilterEntity>();

	/**
	 * 实现这个接口的类需要实现这两个方法
	 */
	public interface BrandsChooseDialogListener {
		void onBrandChoosed(BrandFilterEntity brand);
	}

	public BrandsChooseFragment(List<BrandFilterEntity> brandsFilters) {
		this.brandsFilters = brandsFilters;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Fullscreen);
		mAdapter = new BrandSearchFilterAdapter(getActivity(), brandsFilters);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("车型");
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_brands_choose,
				container, false);
		ListView listViewBrands = (ListView) view
				.findViewById(R.id.listView_brands);
		listViewBrands.setAdapter(mAdapter);
		listViewBrands.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mChooseListener != null) {
					mChooseListener.onBrandChoosed(mAdapter.getItem(position));
				}
			}
		});
		return view;
	}

	public void setBrandsChooseDialogListener(
			BrandsChooseDialogListener chooseListener) {
		mChooseListener = chooseListener;
	}
}
