package com.jason.usedcar.pm;

import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.item.CarItemPM;
import com.jason.usedcar.pm.view.ViewSellingCarView;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.SellingCarResponse;
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
public class SellingCarPM extends BasePM {

    private SaleCarModel model = new SaleCarModel();

    private boolean initialized;

    private ViewSellingCarView view;

    private int lastPos = 0;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public SellingCarPM(ViewSellingCarView view) {
        this.view = view;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPM.class, factoryMethod = "newItemModel")
    public List<Product> getProducts() {
        return model.getData();
    }

    public CarItemPM newItemModel() {
        return new CarItemPM(view);
    }

    public void refreshProducts() {
        presentationModelChangeSupport.firePropertyChange("products");
    }

    public void viewProductDetails(ItemClickEvent event) {
        model.get(event.getPosition()).visibility = View.GONE;
        presentationModelChangeSupport.firePropertyChange("products");
        view.viewProductDetails(model.get(event.getPosition()));
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

        if (view.getAccessToken() == null) {
            view.login(Constants.REQUEST_LOGIN);
            return;
        }

        initialized = true;
        Request request = new Request();
        request.setAccessToken(view.getAccessToken());
        new RestClient().getSellingCar(request, new Callback<SellingCarResponse>() {
            @Override
            public void success(SellingCarResponse sellingCarResponse, Response response) {
                contentVisibility = View.VISIBLE;
                progressVisibility = View.GONE;
                if (sellingCarResponse != null && sellingCarResponse.isExecutionResult()) {
                    model.setData(sellingCarResponse.getProductList());
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
        model.get(lastPos).visibility = View.GONE;
        lastPos = position;
        model.get(lastPos).visibility = View.VISIBLE;
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
}
