package com.jason.usedcar.fragment;

import java.util.ArrayList;

import com.jason.usedcar.R;
import com.jason.usedcar.model.data.FilterEntity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class PriceChooseFragment extends SearchConditionChooseFragment {

	public PriceChooseFragment(ArrayList<FilterEntity> filters) {
		super(filters);
	}

	private PriceChooseDialogListener mChooseListener;

	public interface PriceChooseDialogListener {
		void onPriceChosen(FilterEntity filter);
	}

	public void setPriceChooseDialogListener(PriceChooseDialogListener listener) {
		mChooseListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("价格");
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ListView listView = (ListView) view.findViewById(R.id.listView_seriers);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mChooseListener != null) {
					mChooseListener.onPriceChosen((FilterEntity) mAdapter.getItem(position));
				}
			}
		});
		return view;
	}

	private class PriceAdapter extends BaseAdapter {
		private final String[] prices = new String[] { "0-5万", "5-10万",
				"10-15万", "15-20万", "20-30万", "30-50万", "50万以上" };

		@Override
		public int getCount() {
			return prices.length;
		}

		@Override
		public Object getItem(int position) {
			return prices[position];
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
			txtPrice.setText(prices[position]);
			return convertView;
		}

	}
}
