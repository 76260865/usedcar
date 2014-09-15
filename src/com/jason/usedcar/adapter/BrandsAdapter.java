package com.jason.usedcar.adapter;

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

public class BrandsAdapter extends BaseAdapter implements
        StickyListHeadersAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private UsedCarModel<Brand> mBrandModel;

    public BrandsAdapter(Context context, UsedCarModel<Brand> model) {
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
    public Brand getItem(int position) {
        return mBrandModel == null ? null : mBrandModel.get(position);
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
        Brand param = getItem(position);
        String brandName = param.getBrandName();
        viewHolder.txtBrandName.setText(brandName.substring(brandName.indexOf(" ") + 1));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getBrandName().subSequence(0, 1).charAt(0);
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

        String headerText = ""
                + getItem(position).getBrandName().subSequence(0, 1).charAt(0);
        mHeaderHolder.mTextView.setText(headerText);

        return convertView;
    }

    public static class HeaderViewHolder {
        public TextView mTextView;
    }
}
