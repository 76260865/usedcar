package com.jason.usedcar.pm;

import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.CollectionCar;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.model.impl.CollectionCarImpl;
import com.jason.usedcar.pm.item.CollectionItemPM;
import com.jason.usedcar.pm.view.ViewCollectionView;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.response.FavoriteListResponse;
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
public class CollectionPM {

    private int contentVisibility;

    private int progressVisibility;

    private int nothingVisibility;

    private final ViewCollectionView view;

    private final CollectionCar car = new CollectionCarImpl();

    private final PresentationModelChangeSupport presentationModelChangeSupport;

    private boolean isEditable;

    public CollectionPM(ViewCollectionView view) {
        this.view = view;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public void s1etEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    @ItemPresentationModel(value = CollectionItemPM.class,
    factoryMethod = "newItem")
    public List<Product> getProducts() {
        List<Product> productList = car.getAll();
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                product.setMark(isEditable);
            }
        }
        return productList;
    }

    public CollectionItemPM newItem() {
        return new CollectionItemPM(view);
    }

    public void refreshCollection() {
        presentationModelChangeSupport.firePropertyChange("products");
    }

    public void viewCollectionItem(ItemClickEvent event) {
        viewCollectionItem(event.getPosition());
    }

    public void viewCollectionItem(int selectedPosition) {
        view.viewCollectionItem(car.getByIndex(selectedPosition));
    }

    public int getContentVisibility() {
        return contentVisibility;
    }

    public int getProgressVisibility() {
        return progressVisibility;
    }

    public int getNothingVisibility() {
        return nothingVisibility;
    }

    public void loadData() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        presentationModelChangeSupport.firePropertyChange("contentVisibility");
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
        PagedRequest pagedRequest = new PagedRequest();
        pagedRequest.setAccessToken(view.getAccessToken());
        new RestClient().favoriteList(pagedRequest, new Callback<FavoriteListResponse>() {
            @Override
            public void success(FavoriteListResponse favoriteListResponse, Response response) {
                contentVisibility = View.VISIBLE;
                progressVisibility = View.GONE;
                if (favoriteListResponse != null && favoriteListResponse.isExecutionResult()) {
                    car.addAll(favoriteListResponse.getFavoriteList());
                    presentationModelChangeSupport.refreshPresentationModel();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                progressVisibility = View.GONE;
                nothingVisibility = View.VISIBLE;
                presentationModelChangeSupport.firePropertyChange("progressVisibility");
                presentationModelChangeSupport.firePropertyChange("nothingVisibility");
            }
        });
    }

    public void deleteFavorite(final Product product) {
        view.start();
        new RestClient().deleteFavorite(product.getProductId(), view.getAccessToken(),
                android.os.Build.SERIAL, new Callback<com.jason.usedcar.response.Response>() {
                    @Override
                    public void success(final com.jason.usedcar.response.Response response, final Response response2) {
                        view.end();
                        car.removeById(product.getProductId());
                        presentationModelChangeSupport.refreshPresentationModel();
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        view.end();
                    }
                });
    }
}
