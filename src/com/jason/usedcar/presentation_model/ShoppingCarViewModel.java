package com.jason.usedcar.presentation_model;

import android.os.Build;
import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.ShoppingCarModel;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.response.CartResponse;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class ShoppingCarViewModel extends ViewModelBase {

    private ShoppingCarModel model = new ShoppingCarModel();

    private ShoppingCarView shoppingCarView;

    private FooterPresentationModel footerPresentationModel;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    private boolean initialized;

    public ShoppingCarViewModel(ShoppingCarView shoppingCarView) {
        this.shoppingCarView = shoppingCarView;
        this.footerPresentationModel = new FooterPresentationModel(model);
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPresentationModel.class,
            factoryMethod="newCarItemPresentationModel")
    public List<Product> getProducts() {
        return model.getData();
    }

    public CarItemPresentationModel newCarItemPresentationModel() {
        return new CarItemPresentationModel();
    }

    public void loadData() {
        if (initialized) {
            return;
        }

        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        presentationModelChangeSupport.firePropertyChange("contentVisibility");
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");

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

    public void end() {
        progressVisibility = View.GONE;
        nothingVisibility = View.VISIBLE;
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
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

    public FooterPresentationModel getFooter() {
        return footerPresentationModel;
    }

    public void refreshFooter() {
        footerPresentationModel.refresh();
    }

    @PresentationModel
    public static class FooterPresentationModel {

        private ShoppingCarModel model;

        private PresentationModelChangeSupport changeSupport;

        public FooterPresentationModel(ShoppingCarModel model) {
            this.model = model;
            this.changeSupport = new PresentationModelChangeSupport(this);
        }

        public int getFull() {
            return model.hasMore() ? View.GONE : View.VISIBLE;
        }

        public int getNotFull() {
            return model.hasMore() ? View.VISIBLE : View.GONE;
        }

        public void refresh() {
            changeSupport.refreshPresentationModel();
        }
    }
}
