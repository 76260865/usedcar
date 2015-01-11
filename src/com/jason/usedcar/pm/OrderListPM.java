package com.jason.usedcar.pm;

import android.view.View;
import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.pm.footer.OrderFooterPM;
import com.jason.usedcar.model.OrderListModel;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.pm.item.OrderItemPM;
import com.jason.usedcar.pm.view.OrderListView;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.response.OrderListResponse;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-11-02.
 */
@PresentationModel
public class OrderListPM extends BasePM {

    private OrderListModel orderListModel;

    private OrderListView orderListView;

    private OrderFooterPM footerPresentationModel;

    private PresentationModelChangeSupport changeSupport;

    public OrderListPM(OrderListModel orderListModel, OrderListView orderListView) {
        this.orderListModel = orderListModel;
        this.orderListView = orderListView;
        footerPresentationModel = new OrderFooterPM(orderListModel);
        changeSupport = new PresentationModelChangeSupport(this);
    }

    public OrderFooterPM getFooter() {
        return footerPresentationModel;
    }

    @ItemPresentationModel(value = OrderItemPM.class,
            factoryMethod="newCarItemPresentationModel")
    public List<Order> getData() {
        return removeCancelledOrder(orderListModel.getData());
    }

    private List<Order> removeCancelledOrder(List<Order> orderList) {
        if (orderList == null) {
            return null;
        }
        for (int i = orderList.size() - 1; i >= 0; i--) {
            Order order = orderList.get(i);
            if (order.getOrderId().equals(CacheUtils.getCancelledOrderId())) {
                orderList.remove(order);
            }
        }
        CacheUtils.setCancelledOrderId(null);
        return orderList;
    }

    public OrderItemPM newCarItemPresentationModel() {
        return new OrderItemPM(orderListView);
    }

    public void onItemClick(ItemClickEvent event) {
        orderListView.confirmOrder(orderListModel.get(event.getPosition()));
    }

    public void refresh() {
        changeSupport.refreshPresentationModel();
    }

    public void load() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
        PagedRequest pagedRequest = new PagedRequest();
        pagedRequest.setAccessToken(orderListView.getAccessToken());
        new RestClient().buyUsedCarList(pagedRequest, new Callback<OrderListResponse>() {
            @Override
            public void success(OrderListResponse response, Response response2) {
                if (response.isExecutionResult()) {
                    orderListModel.add(response.getOrders());
                    changeSupport.firePropertyChange("data");
                    finish();
                } else {
                    error();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error();
            }
        });
    }

    public void finish() {
        contentVisibility = View.VISIBLE;
        progressVisibility = View.GONE;
        nothingVisibility = View.GONE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
    }

    public void error() {
        contentVisibility = View.GONE;
        progressVisibility = View.GONE;
        nothingVisibility = View.VISIBLE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
    }
}
