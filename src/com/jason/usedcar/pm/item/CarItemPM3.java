package com.jason.usedcar.pm.item;

import android.view.View;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.view.ShoppingCarView;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.response.Response;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class CarItemPM3 implements ItemPresentationModel<Product> {

    String url = "http://112.124.62.114:80";

    private Product product;

    private ShoppingCarView shoppingCarView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public CarItemPM3(ShoppingCarView view) {
        shoppingCarView = view;
        presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getUrl() {
        return url + product.getRegularImage();
    }

    public String getName() {
        return product.getProductName();
    }

    public String getPrice() {
        return product.getPrice();
    }

    public String getOdometer() {
        return product.getOdometer();
    }

    public String getPurchaseDate() {
        return product.getPurchaseDate();
    }

    public String getPayType() {
        return product.getPaymentMethod() == 0 ? "可贷款" : "全款付";
    }

    public int getPayTypeVisibility() {
        return View.GONE;
    }

    public void edit() {
        //
    }

    public void delete() {
        CarRequest carRequest = new CarRequest();
        new RestClient().deleteCartItem(product.getProductId(), shoppingCarView.getAccessToken(),
                carRequest.getDeviceId(), new Callback<Response>() {
                    @Override
                    public void success(final Response response, final retrofit.client.Response response2) {
                        if (response.isExecutionResult()) {
                            shoppingCarView.delete(product);
                        }
                        product.visibility = View.GONE;
                        presentationModelChangeSupport.firePropertyChange("visibility");
                        MessageToast.makeText(shoppingCarView.getContext(), response.getMessage()).show();
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        product.visibility = View.GONE;
                        presentationModelChangeSupport.firePropertyChange("visibility");
                        MessageToast.makeText(shoppingCarView.getContext(), error.getMessage()).show();
                    }
                });
    }

    public int getVisibility() {
        return product.visibility;
    }

    public int getCheckVisibility() {
        return View.VISIBLE;
    }

    public boolean isChecked() {
        return product.isCheck();
    }

    public void check() {
        shoppingCarView.check(product);
    }

    public void updateData(int i, Product product) {
        this.product = product;
    }

    @Override
    public void updateData(Product product, ItemContext context) {
        this.product = product;
    }
}
