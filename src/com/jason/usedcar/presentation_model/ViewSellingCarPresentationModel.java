package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.SellingCarResponse;
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
public class ViewSellingCarPresentationModel extends ViewModelBase {

    private SaleCarModel model = new SaleCarModel();

    private boolean initialized;

    private ViewSellingCarView view;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public ViewSellingCarPresentationModel(ViewSellingCarView view) {
        this.view = view;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPresentationModel.class, factoryMethod = "newItemModel")
    public List<Product> getProducts() {
        return model.getData();
    }

    public CarItemPresentationModel newItemModel() {
        return new CarItemPresentationModel();
    }

    public void refreshProducts() {
        presentationModelChangeSupport.firePropertyChange("products");
    }

    public void viewProductDetails(ItemClickEvent event) {
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

    public void end() {
        progressVisibility = View.GONE;
        nothingVisibility = View.VISIBLE;
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
    }
}
