package com.jason.usedcar.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jason.usedcar.R;
import com.jason.usedcar.adapter.holder.BrandChooseViewHolder;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Series;

public class SeriersAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mInflater;

	private UsedCarModel<Series> mBrandModel;

	public SeriersAdapter(Context context, UsedCarModel<Series> model) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mBrandModel = model;
		mBrandModel.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				notifyDataSetChanged();
			}

			@Override
			public void onInvalidated() {
				notifyDataSetInvalidated();
			}
		});
	}

	@Override
	public int getCount() {
		return mBrandModel == null ? 0 : mBrandModel.size();
	}

	@Override
	public Series getItem(int position) {
		return mBrandModel == null ? null : mBrandModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO add the seriers layout, viewholder and implement the event
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.brands_item_layout,
					parent, false);
		}
		BrandChooseViewHolder viewHolder = (BrandChooseViewHolder) convertView
				.getTag();
		if (viewHolder == null) {
			viewHolder = new BrandChooseViewHolder(convertView);
			convertView.setTag(viewHolder);
		}
		Series param = getItem(position);
		viewHolder.txtBrandName.setText(param.getSeriesName());
		return convertView;
	}
}
