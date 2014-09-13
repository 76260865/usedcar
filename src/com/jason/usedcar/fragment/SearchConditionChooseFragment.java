package com.jason.usedcar.fragment;

import java.util.ArrayList;

import com.jason.usedcar.R;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class SearchConditionChooseFragment extends DialogFragment {
	protected SearchConditionAdapter mAdapter;
	private ArrayList<FilterEntity> filters = new ArrayList<FilterEntity>();

	public SearchConditionChooseFragment(ArrayList<FilterEntity> filters) {
		this.filters = filters;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Fullscreen);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAdapter = new SearchConditionAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_seriers_choose,
				container, false);
		ListView listViewBrands = (ListView) view
				.findViewById(R.id.listView_seriers);
		listViewBrands.setAdapter(mAdapter);
		return view;
	}

	class SearchConditionAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return filters.size();
		}

		@Override
		public Object getItem(int position) {
			return filters.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.brands_item_layout, parent, false);
			}
			TextView txtPrice = (TextView) convertView
					.findViewById(R.id.txt_brand_name);
			txtPrice.setText(filters.get(position).getName());
			return convertView;
		}

	}
}
