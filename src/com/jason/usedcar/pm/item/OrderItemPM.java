package com.jason.usedcar.pm.item;

import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.pm.view.OrderListView;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class OrderItemPM implements ItemPresentationModel<Order> {

    private String url = Constants.URL_BASE;

    private OrderListView orderListView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    private Order order;

    public OrderItemPM(final OrderListView orderListView) {
        this.orderListView = orderListView;
        presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getUrl() {
        return url + order.getProductName();
    }

    public String getProductName() {
        return order.getProductName();
    }

    public String getProductVinCode() {
        return format(R.string.car_vin, order.getProductCarVin());
    }

    public String getInfo() {
        return format(R.string.order_base_info, order.getGrantedLoanAmount(), order.getGrantedLoanAmount(),
                order.getCustomerServiceFee() + order.getCustomerServiceFee());
    }

    public String getOrderNumber() {
        return format(R.string.order_number, order.getOrderId());
    }

    public String getOrderDate() {
        return format(R.string.order_date, order.getSubmittedDate());
    }

    public String getSellerName() {
        return format(R.string.seller_name, order.getCustomerName());
    }

    public String getSellerId() {
        return format(R.string.seller_id, order.getCustomerIdInfo());
    }

    public String getOrderPrice() {
        return format(R.string.order_amount, order.getSubtotal());
    }

    public String getOrderState() {
        return format(R.string.order_state, order.getOrderState());
    }

    private String format(int format, Object... args) {
        return orderListView.getContext().getString(format, args);
    }

    public void updateData(int i, Order order) {
        this.order = order;
    }

    @Override
    public void updateData(Order order, ItemContext context) {
        this.order = order;
    }
}
