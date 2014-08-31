package com.jason.usedcar.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.jason.usedcar.R;
import com.jason.usedcar.model.UsedCar;
import com.jason.usedcar.model.UsedCarModel;

/**
 * @author t77yq @2014-08-16.
 */
public class BuyCarHistoryAdapter extends BaseAdapter {

    private UsedCarModel<UsedCar> model;

    private LayoutInflater inflater;

    public BuyCarHistoryAdapter(Context context, UsedCarModel<UsedCar> model) {
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
        return 10;
    }

    @Override
    public UsedCar getItem(final int position) {
        return (model == null) ? null : model.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.history_list_item, parent, false);
        }
        return convertView;
    }
}
