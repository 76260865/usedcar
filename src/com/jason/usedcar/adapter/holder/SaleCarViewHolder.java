package com.jason.usedcar.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.R;
import com.jason.usedcar.util.ViewFinder;

/**
 * @author t77yq @2014.06.14
 */
public class SaleCarViewHolder extends ViewHolder {

    public final ImageView pictureImage;

    public final TextView preSaleText;

    public final TextView mileageText;

    public final TextView buyTimeText;

    public SaleCarViewHolder(View view) {
        pictureImage = ViewFinder.findViewById(view, R.id.car_pic);
        preSaleText = ViewFinder.findViewById(view, R.id.pre_sale_price);
        mileageText = ViewFinder.findViewById(view, R.id.mileage);
        buyTimeText = ViewFinder.findViewById(view, R.id.buy_time);
    }
}
