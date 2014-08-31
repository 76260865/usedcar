package com.jason.usedcar.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.BrandsAdapter;
import com.jason.usedcar.db.CarTablesInfo;
import com.jason.usedcar.db.CarTablesInfo.CarBrand;
import com.jason.usedcar.db.DBHelper;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.request.Request;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;

public class BrandsChooseFragment extends DialogFragment {
	private static final String TAG = "BrandsChooseFragment";

	// 使用这个接口的实例提供行动的事件
	//private BrandsChooseDialogListener mChooseListener;

	private DBHelper mDbHelper;

	private UsedCarModel mBrandModel = new UsedCarModel();

	/**
	 * 实现这个接口的类需要实现这两个方法
	 */
	public interface BrandsChooseDialogListener {
		void onBrandChoosed(int brandId);
	}

	/**
	 * 覆盖Fragment.onAttach()这个函数来处理NoticeDialogListener实例
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// 校验主Activity实现回调接口
		try {
			// 获得NoticeDialogListener实例，这样我们就能将事件发送到主Activity
			//mChooseListener = (BrandsChooseDialogListener) activity;
		} catch (ClassCastException e) {
			// activity没有实现这个接口则抛出异常
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		mDbHelper = new DBHelper(getActivity());
		if (mDbHelper.isBrandsInit()) {
            Cursor cursor = null;
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                cursor = db.query(CarTablesInfo.CarBrand.TABLE_NAME, CarTablesInfo.CarBrand.PROJECTION,
                    null, null, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    List<Brand> brandList = new ArrayList<Brand>(cursor.getCount());
                    cursor.moveToFirst();
                    do {
                        Brand brand = new Brand();
                        brand.setBrandId(cursor.getInt(cursor.getColumnIndex(CarBrand.BRAND_ID)));
                        brand.setName(cursor.getString(cursor.getColumnIndex(CarBrand.BRAND_NAME)));
                        brandList.add(brand);
                    } while (cursor.moveToNext());
                    mBrandModel.add(brandList);
                    mBrandModel.notifyDataSetChanged();
                }
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
		} else {
			initBrands();
		}
	}

	private void initBrands() {
        new RestClient().getBrands(new Request(), new Callback<List<Brand>>() {
            @Override
            public void success(List<Brand> brandList, retrofit.client.Response response) {
                mDbHelper.insertBrands(brandList);
                mBrandModel.add(brandList);
                mBrandModel.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_brands_choose,
				container, false);
		ListView listViewBrands = (ListView) view
				.findViewById(R.id.listView_brands);
		listViewBrands.setAdapter(new BrandsAdapter(getActivity(), mBrandModel));
		listViewBrands.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//if (mChooseListener != null) {
				//	mChooseListener.onBrandChoosed(mAdapter.getItem(position)
				//			.getBrandId());
			//	}
			}
		});
		return view;
	}
}
