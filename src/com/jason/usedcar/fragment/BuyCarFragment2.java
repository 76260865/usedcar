package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import com.jason.usedcar.CarDetails2Activity;
import com.jason.usedcar.CarDetailsActivity;
import com.jason.usedcar.FindUsedActivity;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.presentation_model.MenuBuyCarViewModel;
import com.jason.usedcar.presentation_model.MenuSellCarViewModel;
import com.jason.usedcar.presentation_model.ViewBuyCarPresentationModel;
import com.jason.usedcar.presentation_model.ViewBuyCarView;
import com.jason.usedcar.request.SearchProductRequest;
import com.jason.usedcar.response.SearchProductResponse;
import org.robobinding.MenuBinder;
import org.robobinding.binder.BinderFactoryBuilder;
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

    private String queryStr;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        model = new SaleCarModel();
    }

    private void loadData(final int startPage, String facetSelections, String queryStr) {
        model.setLoading(true);
        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.setStartPage(startPage);
        searchProductRequest.setPageSize(SaleCarModel.PAGE_SIZE);
        searchProductRequest.setFacetSelections(facetSelections);
        searchProductRequest.setQueryString(queryStr);
        if (startPage == 1) {
            presentationModel.load();
        }
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
                if (startPage == 1) {
                    presentationModel.finish();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                if (startPage == 1) {
                    presentationModel.error();
                }
                model.setLoading(false);
                model.setHasMore(false);
            }
        });
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = findViewById(R.id.usedCarList);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(final AbsListView lv, final int scrollState) {
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {
                if (model.hasMore() && !model.isLoading()
                        && firstVisibleItem + visibleItemCount == totalItemCount) {
                    loadData(model.getPageSize() + 1, facetSelections, queryStr);
                }
            }
        });
        if (model.isEmpty()) {
            loadData(1, facetSelections, null);
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        MenuBuyCarViewModel menuBuyCarViewModel = new MenuBuyCarViewModel(this);
        MenuBinder menuBinder = new BinderFactoryBuilder().build().createMenuBinder(menu, inflater, getActivity());
        menuBinder.inflateAndBind(R.menu.menu_buy_car, menuBuyCarViewModel);
        super.onCreateOptionsMenu(menu, inflater);
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
                case 1001:
                    if (data != null) {
                        BrandFilterEntity brandFilterEntity = (BrandFilterEntity) data.getSerializableExtra("brandFilter");
                        presentationModel.setBrandFilterEntity(brandFilterEntity);
                        FilterEntity priceFilterEntity = (FilterEntity) data.getSerializableExtra("priceFilter");
                        FilterEntity mileFilterEntity = (FilterEntity) data.getSerializableExtra("mileFilter");
                        FilterEntity ageFilterEntity = (FilterEntity) data.getSerializableExtra("ageFilter");
                        if (brandFilterEntity != null) {
                            facetSelections = brandFilterEntity.getBrandInitLetter() + ",";
                        }
                        if (priceFilterEntity != null) {
                            facetSelections = priceFilterEntity.getFacetSelection() + ",";
                        }
                        if (mileFilterEntity != null) {
                            facetSelections = mileFilterEntity.getFacetSelection() + ",";
                        }
                        if (ageFilterEntity != null) {
                            facetSelections = ageFilterEntity.getFacetSelection() + ",";
                        }
                        if (facetSelections != null) {
                            facetSelections = facetSelections.substring(0, facetSelections.length() - 1);
                        }
                        if (!TextUtils.isEmpty(facetSelections)) {
                            queryStr = null;
                            model.setData(null);
                            presentationModel.refreshProducts();
                            loadData(1, facetSelections, null);
                        }
                    }
            }
        }
    }

    @Override
    public void search(String query) {
        queryStr = query;
        facetSelections = null;
        model.setData(null);
        loadData(1, null, queryStr);
    }

    @Override
    public void search2(final String where) {
        startActivityForResult(new Intent(getActivity(), FindUsedActivity.class), 1001);
    }

    @Override
    public void viewProductDetails(Product product) {
        Intent viewCarDetails = new Intent(getActivity(), CarDetails2Activity.class);
        viewCarDetails.putExtra("product_id", product.getProductId());
        viewCarDetails.putExtra("type", Constants.CarDetailsType.BUY);
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
