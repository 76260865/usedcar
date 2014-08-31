package com.jason.usedcar.fragment;

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
import com.jason.usedcar.request.*;
import com.jason.usedcar.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;

import java.util.List;
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

import com.jason.usedcar.adapter.SeriersAdapter;
import com.jason.usedcar.db.DBHelper;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Series;
import retrofit.*;

public class SeriesChooseFragment extends DialogFragment {
	private static final String TAG = "SeriesChooseFragment";

	// 使用这个接口的实例提供行动的事件
	private SeriersChooseDialogListener mChooseListener;

	private DBHelper mDbHelper;

	private UsedCarModel<Series> mSeriersModel = new UsedCarModel<Series>();

	private SeriersAdapter mAdapter;

	/**
	 * 实现这个接口的类需要实现这两个方法
	 */
	public interface SeriersChooseDialogListener {
		void onSeriersChoosed(int brandId);
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
		mAdapter = new SeriersAdapter(getActivity(), mSeriersModel);
		if (mSeriersModel.isEmpty()) {
			initSeriers();
		}
		// 校验主Activity实现回调接口
		try {
			// 获得NoticeDialogListener实例，这样我们就能将事件发送到主Activity
			mChooseListener = (SeriersChooseDialogListener) activity;
		} catch (ClassCastException e) {
			// activity没有实现这个接口则抛出异常
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	private void initSeriers() {
		StringRequest request = new StringRequest(Method.POST,
				HttpUtil.GET_SERIERS_URI, mResponseListener, mErrorListener) {

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
				map.put("brandId", "1");
				return map;
			}
		};
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		queue.add(request);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO change the layout to seriers select layout
		View view = inflater.inflate(R.layout.fragment_seriers_choose,
				container, false);
		ListView listViewBrands = (ListView) view
				.findViewById(R.id.listView_seriers);
		listViewBrands.setAdapter(mAdapter);
		listViewBrands.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mChooseListener != null) {
					mChooseListener.onSeriersChoosed(mAdapter.getItem(position)
							.getSeriesId());
				}
			}
		});
		return view;
	}

	private Response.Listener<String> mResponseListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			ArrayList<Series> seriersList = new ArrayList<Series>();
			try {
				JSONArray object = new JSONArray(response);
				for (int i = 0; i < object.length(); i++) {
					Series seriers = new Series();
					seriers.setSeriesId(object.getJSONObject(i).getInt(
							"seriesId"));
					seriers.setSeriesName(object.getJSONObject(i).getString(
							"seriesName"));
					seriersList.add(seriers);
				}
			} catch (NumberFormatException e) {
				Log.e(TAG, e.getMessage());
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
			}

			mSeriersModel.add(seriersList);
			mAdapter.notifyDataSetChanged();
		}

	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			// error
			Log.e(TAG, error.getMessage());
		}
	};
}
