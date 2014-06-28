package com.jason.usedcar.adapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.holder.ShoppingCarViewHolder;
import com.jason.usedcar.adapter.holder.ViewHolder;
import com.jason.usedcar.model.ShoppingCar;
import com.jason.usedcar.model.ShoppingCarModel;

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
    public ShoppingCar getItem(int position) {
        return getCount() == 0 ? null : model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.history_list_item, parent, false);
        }
        ShoppingCarViewHolder holder = getViewHolder(convertView);
        if (holder == null) {
            holder = new ShoppingCarViewHolder(convertView);
            convertView.setTag(holder);
        }
        ShoppingCar data = getItem(position);
        if (data != null) {
            //holder.imageView.setImageResource();
            holder.historyCarInfoText.setText(activity.getString(R.string.history_car_info,
                data.getListPrice(), data.getOdometer(), data.getPurchaseDate()));
            holder.orderNumberText.setText(activity.getString(R.string.history_order_number, data.getListPrice()));
            holder.orderPayTimeText.setText(activity.getString(R.string.history_order_pay_time, data.getPurchaseDate()));
            holder.orderPriceText.setText(activity.getString(R.string.history_order_price, data.getListPrice()));
            holder.orderStateText.setText(activity.getString(R.string.history_order_state, data.getPriceType()));
            if (holder.orderAction.getText().equals("未付款")) {
                holder.orderAction.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "目前还不支持手机端付款，请在电脑上支付!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.orderAction.setOnClickListener(null);
            }
        }
        return convertView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends ViewHolder> T getViewHolder(View view) {
        return (T) view.getTag();
    }
}
