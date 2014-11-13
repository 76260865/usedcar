package com.jason.usedcar;

import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.pm.OrderConfirmPM;
import com.jason.usedcar.pm.view.OrderConfirmView;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * @author t77yq @2014-11-07.
 */
public class OrderConfirmActivity extends AbsActivity implements OrderConfirmView {

    private LoadingFragment loadingFragment;

    private OrderConfirmPM orderConfirmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Order order = (Order) getIntent().getSerializableExtra("order");
        orderConfirmViewModel = new OrderConfirmPM(order, this);
        initContentView(R.layout.activity_order_confirm, orderConfirmViewModel);
    }

    @Override
    protected ViewBinder createViewBinder() {
        return new BinderFactoryBuilder().mapView(ImageView.class, new ImageViewBinding2())
                .mapView(CheckedTextView.class, new CheckedTextViewBinding())
                .mapView(TextView.class, new HtmlTextViewBinding())
                .build().createViewBinder(getContext(), true);
    }

    @Override
    public void before() {
        loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void end() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        MessageToast.makeText(this, message).show();
    }
}
