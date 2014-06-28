package com.jason.usedcar.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.R;
import com.jason.usedcar.util.ViewFinder;

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
        imageView = ViewFinder.findViewById(view, R.id.car_image);
        historyCarInfoText = ViewFinder.findViewById(view, R.id.history_car_info);
        orderNumberText = ViewFinder.findViewById(view, R.id.order_number);
        orderPriceText = ViewFinder.findViewById(view, R.id.order_price);
        orderPayTimeText = ViewFinder.findViewById(view, R.id.order_pay_time);
        orderStateText = ViewFinder.findViewById(view, R.id.order_state);
        orderAction = ViewFinder.findViewById(view, R.id.order_action);
    }
}
