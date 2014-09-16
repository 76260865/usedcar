package com.jason.usedcar;

import android.os.Bundle;
import com.jason.usedcar.model.CollectionCar;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.model.impl.CollectionCarImpl;
import com.jason.usedcar.presentation_model.ViewCollectionPresentationModel;
import com.jason.usedcar.presentation_model.ViewCollectionView;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.response.FavoriteListResponse;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-09-16.
 */
public class CollectActivity extends BaseActivity implements ViewCollectionView {

    private ViewBinder viewBinder;

    private CollectionCar collectionCar = new CollectionCarImpl();

    private ViewCollectionPresentationModel presentationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentationModel = new ViewCollectionPresentationModel(this, collectionCar);
        viewBinder = new BinderFactoryBuilder().build().createViewBinder(this);
        setContentView(viewBinder.inflateAndBind(R.layout.activity_collection, presentationModel));
        if (collectionCar.isEmpty()) {
            loadData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presentationModel.refreshCollection();
    }

    private void loadData() {
        PagedRequest pagedRequest = new PagedRequest();
        pagedRequest.setAccessToken(Application.fromActivity(this).getAccessToken());
        new RestClient().favoriteList(pagedRequest, new Callback<FavoriteListResponse>() {
            @Override
            public void success(FavoriteListResponse carListResponse, Response response) {
                if (carListResponse.isExecutionResult()) {
                    collectionCar.addAll(carListResponse.getFavoriteList());
                    presentationModel.refreshCollection();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
            }
        });
    }

    @Override
    public void viewCollectionItem(Product product) {
        MessageToast.makeText(this, product.getProductName()).show();
    }
}
