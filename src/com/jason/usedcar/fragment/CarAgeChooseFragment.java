package com.jason.usedcar.fragment;

import com.jason.usedcar.R;

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

public class CarAgeChooseFragment extends DialogFragment {
	private PriceChooseDialogListener mChooseListener;
	private PriceAdapter mAdapter;

	public interface PriceChooseDialogListener {
		void onPriceChoosed(int brandId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Fullscreen);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("车龄");
		return dialog;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mAdapter = new PriceAdapter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
					// mChooseListener.onBrandChoosed(mAdapter.getItem(position));
				}
			}
		});
		return view;
	}

	private class PriceAdapter extends BaseAdapter {
		private final String[] carage = new String[] { "0-1年", "1-3年", "3-5年",
				"5-8年", "8年以上" };

		@Override
		public int getCount() {
			return carage.length;
		}

		@Override
		public Object getItem(int position) {
			return carage[position];
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
			txtPrice.setText(carage[position]);
			return convertView;
		}

	}
}
