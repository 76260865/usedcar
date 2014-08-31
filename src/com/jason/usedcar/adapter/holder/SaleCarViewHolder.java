package com.jason.usedcar.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * @author t77yq @2014.06.14
 */
public class SaleCarViewHolder extends ViewHolder {

    public final ImageView pictureImage;

    public final TextView carNameText;

    public final TextView preSalePriceText;

    public final TextView mileageText;

    public final TextView buyTimeText;

    public SaleCarViewHolder(View view) {
        pictureImage = getView(view, R.id.imageCarShow);
        carNameText = getView(view, R.id.textCarName);
        preSalePriceText = getView(view, R.id.textPreSalePrice);
        mileageText = getView(view, R.id.textMileage);
        buyTimeText = getView(view, R.id.textBuyTime);
    }
}
