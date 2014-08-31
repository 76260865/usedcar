package com.jason.usedcar.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * @author t77yq @14-6-28.
 */
public class ShoppingCarViewHolder extends ViewHolder {

    public final ImageView imageView;

    public final TextView historyCarInfoText;

    public final TextView orderNumberText;

    public final TextView orderPriceText;

    public final TextView orderPayTimeText;

    public final TextView orderStateText;

    public final TextView orderAction;

    public ShoppingCarViewHolder(View view) {
        imageView = getView(view, R.id.car_image);
        historyCarInfoText = getView(view, R.id.history_car_info);
        orderNumberText = getView(view, R.id.order_number);
        orderPriceText = getView(view, R.id.order_price);
        orderPayTimeText = getView(view, R.id.order_pay_time);
        orderStateText = getView(view, R.id.order_state);
        orderAction = getView(view, R.id.order_action);
    }
}
