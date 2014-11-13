package com.jason.usedcar.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.holder.BrandChooseViewHolder;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.BrandFilterEntity;
import java.util.List;

public class BrandSearchFilterAdapter extends BaseAdapter implements
		StickyListHeadersAdapter {

	private Context mContext;

	private LayoutInflater mInflater;

	private List<BrandFilterEntity> brandsFilters = new ArrayList<BrandFilterEntity>();

	public BrandSearchFilterAdapter(Context context,
			List<BrandFilterEntity> brandsFilters) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		this.brandsFilters = brandsFilters;
	}

	@Override
	public int getCount() {
		return brandsFilters == null ? 0 : brandsFilters.size();
	}

	@Override
	public BrandFilterEntity getItem(int position) {
		return brandsFilters == null ? null : brandsFilters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		BrandFilterEntity param = getItem(position);
		viewHolder.txtBrandName.setText(param.getName());
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return getItem(position).getBrandInitLetter().charAt(0);
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder mHeaderHolder;
		if (convertView == null) {
			mHeaderHolder = new HeaderViewHolder();
			convertView = mInflater.inflate(R.layout.header, parent, false);
			mHeaderHolder.mTextView = (TextView) convertView
					.findViewById(R.id.text1);
			convertView.setTag(mHeaderHolder);
		} else {
			mHeaderHolder = (HeaderViewHolder) convertView.getTag();
		}

		String headerText = getItem(position).getName();
		mHeaderHolder.mTextView.setText(headerText);

		return convertView;
	}

	public static class HeaderViewHolder {
		public TextView mTextView;
	}
}
