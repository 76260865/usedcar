package com.jason.usedcar.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.holder.SaleCarViewHolder;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.param.PublishUsedCarParam;

/**
 * @author t77yq @2014.06.14
 */
public class SaleCarAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater inflater;

    private SaleCarModel model;

    public SaleCarAdapter(Context context, SaleCarModel model) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.model.registerDataSetObserver(new DataSetObserver() {
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
        return model == null ? 0 : model.size();
    }

    @Override
    public PublishUsedCarParam getItem(int position) {
        return model == null ? null : model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_shopping_car_layout, parent, false);
        }
        SaleCarViewHolder viewHolder = (SaleCarViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SaleCarViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        PublishUsedCarParam param = getItem(position);
        viewHolder.preSaleText.setText(context.getString(R.string.sale_car_pre_sale_price,
                param.getListPrice()));
        viewHolder.mileageText.setText(context.getString(R.string.sale_car_mileage,
                param.getOdometer()));
        viewHolder.buyTimeText.setText(context.getString(R.string.sale_car_buy_time,
                param.getPurchaseDate()));
        return convertView;
    }
}
