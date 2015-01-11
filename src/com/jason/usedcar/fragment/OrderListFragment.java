package com.jason.usedcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import com.jason.usedcar.OrderConfirmActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.pm.OrderListPM;
import com.jason.usedcar.model.OrderListModel;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.pm.view.OrderListView;

/**
 * @author t77yq @2014-11-02.
 */
public class OrderListFragment extends AbsFragment implements OrderListView {

    private OrderListModel model;

    private OrderListPM viewModel;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new OrderListModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refresh();
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = findViewById(R.id.orderList);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView lv, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        if (model.isEmpty()) {
            viewModel.load();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_buy_car_history;
    }

    @Override
    protected Object presentationModel() {
        return viewModel = new OrderListPM(model, this);
    }

    @Override
    public void confirmOrder(Order order) {
        Intent confirmOrder = new Intent(getContext(), OrderConfirmActivity.class);
        confirmOrder.putExtra("order", order);
        startActivity(confirmOrder);
    }
}
