package com.jason.usedcar.pm;

import android.os.Build;
import android.view.View;
import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.ShoppingCarModel;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.footer.ShoppingFooterPM;
import com.jason.usedcar.pm.item.CarItemPM3;
import com.jason.usedcar.pm.view.ShoppingCarView;
import com.jason.usedcar.response.CartResponse;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class ShoppingPM extends BasePM {

    private ShoppingCarModel model = new ShoppingCarModel();

    private ShoppingCarView shoppingCarView;

    private ShoppingFooterPM footerPresentationModel;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    private Product product;

    private boolean initialized;

    private int lastPos = 0;

    private int checkedIndex = -1;

    public ShoppingPM(ShoppingCarView shoppingCarView) {
        this.shoppingCarView = shoppingCarView;
        this.footerPresentationModel = new ShoppingFooterPM(model);
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPM3.class,
            factoryMethod="newCarItemPresentationModel")
    public List<Product> getProducts() {
        List<Product> productList = removeOrderedProduct(model.getData());
        if (productList != null) {
            for (int i = productList.size() - 1; i >= 0; i--) {
                productList.get(i).setMark(i == checkedIndex);
            }
        }
        return productList;
    }

    private List<Product> removeOrderedProduct(List<Product> productList) {
        if (productList == null) {
            return null;
        }
        for (int i = productList.size() - 1; i >= 0; i--) {
            Product product = productList.get(i);
            if (product.getProductId().equals(CacheUtils.getOrderedProductId())) {
                productList.remove(product);
            }
        }
        CacheUtils.setOrderedProductId(null);
        return productList;
    }

    public CarItemPM3 newCarItemPresentationModel() {
        return new CarItemPM3(shoppingCarView);
    }

    public void loadData() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        model.setData(null);
        presentationModelChangeSupport.refreshPresentationModel();

        if (shoppingCarView.getAccessToken() == null) {
            shoppingCarView.login(Constants.REQUEST_LOGIN);
            return;
        }

        initialized = true;
        new RestClient().cart(shoppingCarView.getAccessToken(), Build.SERIAL, new Callback<CartResponse>() {
            @Override
            public void success(final CartResponse response, final Response response2) {
                if (response != null && response.isExecutionResult()) {
                    contentVisibility = View.VISIBLE;
                    progressVisibility = View.GONE;
                    model.setData(response.getCartList());
                    presentationModelChangeSupport.refreshPresentationModel();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                progressVisibility = View.GONE;
                nothingVisibility = View.VISIBLE;
                presentationModelChangeSupport.firePropertyChange("progressVisibility");
                presentationModelChangeSupport.firePropertyChange("nothingVisibility");
            }
        });
    }

    public void longClick(int position) {
        if (lastPos == position && model.get(lastPos).visibility == View.VISIBLE) {
            model.get(lastPos).visibility = View.GONE;
        } else {
            model.get(lastPos).visibility = View.GONE;
            lastPos = position;
            model.get(lastPos).visibility = View.VISIBLE;
        }
        presentationModelChangeSupport.refreshPresentationModel();
    }

    public void delete(Product product) {
        model.getData().remove(product);
        presentationModelChangeSupport.refreshPresentationModel();
    }

    public void end() {
        progressVisibility = View.GONE;
        nothingVisibility = View.VISIBLE;
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
    }

    public void check(Product product) {
        if (this.product == null) {
            this.product = product;
            product.setCheck(true);
        } else {
            if (product.isCheck()) {
                product.setCheck(false);
                this.product = null;
            } else {
                this.product.setCheck(false);
                product.setCheck(true);
                this.product = product;
            }
        }
        refreshProducts();
    }

    public void refreshProducts() {
        presentationModelChangeSupport.firePropertyChange("products");
    }

    public void viewProductDetails(ItemClickEvent event) {
        int position = event.getPosition();
        if (position < model.size()) { // ignore footer item
            shoppingCarView.viewProductDetails(model.get(event.getPosition()));
        }
    }

    public ShoppingFooterPM getFooter() {
        return footerPresentationModel;
    }

    public void refreshFooter() {
        footerPresentationModel.refresh();
    }

    public void confirmShopping() {
        shoppingCarView.confirmShopping(product);
    }
}
