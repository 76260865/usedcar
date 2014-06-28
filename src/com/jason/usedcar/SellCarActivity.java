package com.jason.usedcar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.jason.usedcar.util.ViewFinder;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);
        SellCarViewHolder sellCarViewHolder = new SellCarViewHolder(this);
    }

    private static class SellCarViewHolder {

        public final TextView tradeAddress;

        public final TextView street;

        public final TextView carType;

        public final TextView boughtTime;

        public final TextView distance;

        public final TextView price;

        public final ToggleButton fixedPrice;

        public final TextView vin;

        public SellCarViewHolder(Activity activity) {
            tradeAddress = ViewFinder.findViewById(activity, R.id.car_info_address);
            street = ViewFinder.findViewById(activity, R.id.car_info_street);
            carType = ViewFinder.findViewById(activity, R.id.car_info_type);
            boughtTime = ViewFinder.findViewById(activity, R.id.car_info_time_bought);
            distance = ViewFinder.findViewById(activity, R.id.car_info_distance);
            price = ViewFinder.findViewById(activity, R.id.car_info_price);
            fixedPrice = ViewFinder.findViewById(activity, R.id.price_check);
            vin = ViewFinder.findViewById(activity, R.id.car_info_vin);
        }
    }
}
