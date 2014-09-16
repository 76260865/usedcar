package com.jason.usedcar.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.holder.SaleCarViewHolder;
import com.jason.usedcar.adapter.holder.ViewHolder;
import com.jason.usedcar.model.ShoppingCarModel;
import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @14-6-28.
 */
public class ShoppingCarAdapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;

    private ShoppingCarModel model;

    public ShoppingCarAdapter(Activity activity, ShoppingCarModel model) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
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
    public Product getItem(int position) {
        return getCount() == 0 ? null : model.get(position);
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
        Product param = getItem(position);
        viewHolder.carNameText.setText(activity.getString(R.string.sale_car_name, param.getProductName()));
        viewHolder.preSalePriceText.setText(activity.getString(R.string.sale_car_pre_sale_price,
                param.getPrice()));
        viewHolder.mileageText.setVisibility(View.GONE);
        viewHolder.buyTimeText.setVisibility(View.GONE);
        return convertView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends ViewHolder> T getViewHolder(View view) {
        return (T) view.getTag();
    }
}
