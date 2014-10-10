package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import com.jason.usedcar.CarDetails2Activity;
import com.jason.usedcar.CarDetailsActivity;
import com.jason.usedcar.FindUsedActivity;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.presentation_model.ViewBuyCarPresentationModel;
import com.jason.usedcar.presentation_model.ViewBuyCarView;
import com.jason.usedcar.request.SearchProductRequest;
import com.jason.usedcar.response.SearchProductResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-09-17.
 */
public class BuyCarFragment2 extends AbsFragment implements ViewBuyCarView {

    private SaleCarModel model;

    private ViewBuyCarPresentationModel presentationModel;

    private String facetSelections;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new SaleCarModel();
        if (model.isEmpty()) {
            loadData(1);
        }
    }

    private void loadData(final int startPage) {
        model.setLoading(true);
        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.setStartPage(startPage);
        searchProductRequest.setPageSize(SaleCarModel.PAGE_SIZE);
        searchProductRequest.setFacetSelections(facetSelections);
        new RestClient().searchProduct(searchProductRequest, new Callback<SearchProductResponse>() {
            @Override
            public void success(final SearchProductResponse response, final Response response2) {
                boolean hasMore = false;
                if (response != null && response.isExecutionResult()) {
                    hasMore = response.getNumFound() >= startPage * SaleCarModel.PAGE_SIZE;
                    model.add(response.getProductList());
                    presentationModel.refreshProducts();
                }
                model.setLoading(false);
                model.setHasMore(hasMore);
                presentationModel.refreshFooter();
            }

            @Override
            public void failure(final RetrofitError error) {
                model.setLoading(false);
                model.setHasMore(false);
            }
        });
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = findViewById(R.id.usedCarList);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(final AbsListView lv, final int scrollState) {
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {
                if (model.hasMore() && !model.isLoading()
                        && firstVisibleItem + visibleItemCount == totalItemCount) {
                    loadData(model.getPageSize() + 1);
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_buy_car2;
    }

    @Override
    protected Object presentationModel() {
        return presentationModel = new ViewBuyCarPresentationModel(model, this);
    }

    @Override
    public void fillFilter() {
        startActivityForResult(new Intent(getActivity(), FindUsedActivity.class), 1000);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    if (data != null) {
                        BrandFilterEntity brandFilterEntity = (BrandFilterEntity) data.getSerializableExtra("brandFilter");
                        presentationModel.setBrandFilterEntity(brandFilterEntity);
                        FilterEntity priceFilterEntity = (FilterEntity) data.getSerializableExtra("priceFilter");
                        FilterEntity mileFilterEntity = (FilterEntity) data.getSerializableExtra("mileFilter");
                        FilterEntity ageFilterEntity = (FilterEntity) data.getSerializableExtra("ageFilter");
                        facetSelections = brandFilterEntity.getFacetSelection() + ","
                                + priceFilterEntity.getFacetSelection() + ","
                                + mileFilterEntity.getFacetSelection() + ","
                                + ageFilterEntity.getFacetSelection();
                        presentationModel.setFilter(brandFilterEntity.getName());
                    }
                    break;
            }
        }
    }

    @Override
    public void search() {
        model.setData(null);
        loadData(1);
    }

    @Override
    public void viewProductDetails(Product product) {
        Intent viewCarDetails = new Intent(getActivity(), CarDetails2Activity.class);
        viewCarDetails.putExtra("product", product);
        startActivity(viewCarDetails);
    }

    @Override
    public void showMessage(final int messageId) {
        showMessage(getString(messageId));
    }

    @Override
    public void showMessage(final String message) {
        MessageToast.makeText(getActivity(), message).show();
    }

    @Override
    public boolean isFull() {
        return !model.hasMore();
    }
}
