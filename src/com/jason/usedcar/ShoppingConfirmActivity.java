package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.ShoppingConfirmPM;
import com.jason.usedcar.pm.view.ViewConfirmShopping;

/**
 * @author t77yq @2014-11-12.
 */
public class ShoppingConfirmActivity extends AbsActivity implements ViewConfirmShopping {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Product product = (Product) getIntent().getSerializableExtra("product");
        final ShoppingConfirmPM pm = new ShoppingConfirmPM(this, product);
        initContentView(R.layout.activity_trade_type, pm);
        ((RadioGroup) findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                pm.changed(i);
            }
        });
    }

    @Override
    public void confirmOrder(Order order) {
        Intent confirmOrderIntent = new Intent(getContext(), OrderConfirmActivity.class);
        confirmOrderIntent.putExtra("order", order);
        startActivity(confirmOrderIntent);
    }
}
