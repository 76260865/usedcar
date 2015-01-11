package com.jason.usedcar.pm;

import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.view.ViewConfirmShopping;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.OrderResponse;
import org.robobinding.annotation.PresentationModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class ShoppingConfirmPM extends BasePM {

    private Product product;

    private int payTypeId;

    private ViewConfirmShopping view;

    public ShoppingConfirmPM(ViewConfirmShopping view, Product product) {
        this.view = view;
        this.product = product;
    }

    public void changed(int id) {
        payTypeId = id;
    }

    public void submitOrder() {
        view.showProgress("提交订单...");
        new RestClient().generateOrder(
                product.getProductId(),
                payTypeId == R.id.all ? 0 : 1,
                view.getAccessToken(),
                new Request().getDeviceId(),
                new Callback<OrderResponse>() {
                    @Override
                    public void success(OrderResponse response, Response response2) {
                        view.dismissProgress();
                        if (response != null) {
                            view.showMessage(response.getMessage());
                            if (response.isExecutionResult()) {
                                CacheUtils.setOrderedProductId(product.getProductId());
                                view.confirmOrder(response.getOrder());
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        view.dismissProgress();
                        view.showMessage("出错了");
                    }
                });
    }
}
