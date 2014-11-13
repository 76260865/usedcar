package com.jason.usedcar.pm;

import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.pm.view.OrderConfirmView;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.Response;
import org.robobinding.annotation.PresentationModel;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-11-07.
 */
@PresentationModel
public class OrderConfirmPM {

    private String url = Constants.URL_BASE;

    private Order order;

    private OrderConfirmView orderConfirmView;

    public OrderConfirmPM(Order order, OrderConfirmView orderConfirmView) {
        this.order = order;
        this.orderConfirmView = orderConfirmView;
    }

    public String getProductPhotoUrl() {
        return url + order.getProductImage();
    }

    public String getProductName() {
        return order.getProductName();
    }

    public String getProductVinCode() {
        return format(R.string.car_vin, order.getProductCarVin());
    }

    public String getLoanAmount() {
        return format(R.string.loan_amount, order.getGrantedLoanAmount());
    }

    public String getProductPrice() {
        return format(R.string.product_price, String.valueOf(order.getSubtotal()));
    }

    public String getTradeFee() {
        return format(R.string.trade_fee, String.valueOf(order.getCustomerServiceFee() + order.getRetailerServiceFee()));
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
        return orderConfirmView.getContext().getString(format, args);
    }

    public void cancelOrder() {
        orderConfirmView.before();
        new RestClient().deleteOrder(order.getOrderId(), orderConfirmView.getAccessToken(),
                new Request().getDeviceId(), new Callback<Response>() {
                    @Override
                    public void success(Response response, retrofit.client.Response response2) {
                        if (response != null) {
                            orderConfirmView.showMessage(response.getMessage());
                            if (response.isExecutionResult()) {
                                CacheUtils.setCancelledOrderId(order.getOrderId());
                                orderConfirmView.finish();
                            }
                        }
                        orderConfirmView.end();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        orderConfirmView.end();
                    }
                });
    }

    public void pay() {
        orderConfirmView.showMessage("目前还不支持手机端大额付款，请使用电脑完成支付，谢谢！");
    }
}
