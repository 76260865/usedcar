package com.jason.usedcar.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.Config;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.BrandsAdapter;
import com.jason.usedcar.db.CarTablesInfo;
import com.jason.usedcar.db.CarTablesInfo.CarBrand;
import com.jason.usedcar.db.DBHelper;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;

public class BrandsChooseFragment extends DialogFragment {
	private static final String TAG = "BrandsChooseFragment";

	// 使用这个接口的实例提供行动的事件
	private BrandsChooseDialogListener mChooseListener;

	private DBHelper mDbHelper;
	
	private BrandsAdapter mAdapter;

	private UsedCarModel<Brand> mBrandModel = new UsedCarModel<Brand>();

	/**
	 * 实现这个接口的类需要实现这两个方法
	 */
	public interface BrandsChooseDialogListener {
		void onBrandChoosed(Brand brand);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Fullscreen);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("车型");
		return dialog;
	}

	/**
	 * 覆盖Fragment.onAttach()这个函数来处理NoticeDialogListener实例
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mDbHelper = new DBHelper(getActivity());
		mAdapter = new BrandsAdapter(getActivity(), mBrandModel);

		// 校验主Activity实现回调接口
		try {
			Log.d("xxxxx", "onAttach");
			// 获得NoticeDialogListener实例，这样我们就能将事件发送到主Activity
			//mChooseListener = (BrandsChooseDialogListener) activity;
			mChooseListener = (BrandsChooseDialogListener) activity;
			if (mDbHelper.isBrandsInit()) {
				ArrayList<Brand> brands = mDbHelper.getBrands();
				mBrandModel.add(brands);
				mAdapter.notifyDataSetChanged();
			} else {
				initBrands();
			}
		} catch (ClassCastException e) {
			// activity没有实现这个接口则抛出异常
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	private void initBrands() {
		StringRequest request = new StringRequest(Method.POST,
				HttpUtil.GET_BRANDS_URI, mResponseListener, mErrorListener) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("User-Agent", Config.USER_AGENT);
				return headers;
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("deviceId", "11111");
				map.put("accessToken",
						"S1U5TjM2VkZPMTNJQTFJMEZOTUEySU9RMkc9PT09PT0xMDAwNSYxNDA5NDkwMTc5NDg2");
				// map.put("phoneOrEmail", "15008488463");
				// map.put("password", "111111");

				return map;
			}
		};
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
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

	private Response.Listener<String> mResponseListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			Log.d(TAG, response);
			ArrayList<Brand> brandList = new ArrayList<Brand>();
			try {
				JSONArray object = new JSONArray(response);
				// Iterator<String> iterator = object.keys();
				// while (iterator.hasNext()) {
				for (int i = 0; i < object.length(); i++) {
					Brand brand = new Brand();
					brand.setBrandId(object.getJSONObject(i).getInt("brandId"));
					brand.setBrandName(object.getJSONObject(i).getString(
							"brandName"));
					brandList.add(brand);
				}
				// Collections.sort(brandList, new SortByName());
			} catch (NumberFormatException e) {
				Log.e(TAG, e.getMessage());
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
			}

			mDbHelper.insertBrands(brandList);
			mBrandModel.add(brandList);
			mAdapter.notifyDataSetChanged();
		}

	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			// error
			Log.e(TAG, error.toString());
			error.printStackTrace();
		}
	};

	class SortByName implements Comparator {
		public int compare(Object o1, Object o2) {
			Brand s1 = (Brand) o1;
			Brand s2 = (Brand) o2;
			if (s1.getBrandName().charAt(0) > s2.getBrandName().charAt(0))
				return 1;
			return 0;
		}
	}
}
