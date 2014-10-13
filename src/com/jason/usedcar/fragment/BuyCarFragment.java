package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jason.usedcar.*;
import com.jason.usedcar.adapter.BuyCarAdapter;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.UsedCar;
import com.jason.usedcar.request.*;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.response.*;
import com.jason.usedcar.view.DropDownListView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class BuyCarFragment extends
    BaseFragment<BuyCarFragmentPresenter, CallButtonUi> implements
    CallButtonUi, OnClickListener {

    @Required(order = 1)
    private EditText filterText;

    private SaleCarModel saleCarModel = new SaleCarModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("我要买车");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_car, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().login(getActivity());
        filterText = getView(view, R.id.textSaleCarFilter);
//        filterText.setOnClickListener(this);
        getView(view, R.id.saleCarFilterButton).setOnClickListener(this);
        final DropDownListView saleCarlList = getView(view, R.id.usedCarList);
        saleCarlList.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                SearchProductRequest searchProductRequest = new SearchProductRequest();
                searchProductRequest.setPageSize(SaleCarModel.PAGE_SIZE);
                new RestClient().searchProduct(searchProductRequest, new Callback<SearchProductResponse>() {
                    @Override
                    public void success(final SearchProductResponse response, final Response response2) {
                        if (response != null && response.isExecutionResult()) {
                            saleCarModel.add(response.getProductList());
                            saleCarModel.notifyDataSetInvalidated();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                    }
                });
            }
        });
        saleCarlList.setOnBottomListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchProductRequest searchProductRequest = new SearchProductRequest();
                searchProductRequest.setPageSize(SaleCarModel.PAGE_SIZE);
                searchProductRequest.setStartPage(saleCarModel.getPageSize());
                new RestClient().searchProduct(searchProductRequest, new Callback<SearchProductResponse>() {
                    @Override
                    public void success(final SearchProductResponse response, final Response response2) {
                        if (response != null && response.isExecutionResult()) {
                            if (response.getNumFound() < SaleCarModel.PAGE_SIZE) {
                                saleCarlList.setHasMore(false);
                            }
                            saleCarModel.add(response.getProductList());
                            saleCarModel.notifyDataSetInvalidated();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                    }
                });
            }
        });
        saleCarlList.setAdapter(new BuyCarAdapter(getActivity(), saleCarModel));
        saleCarlList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().clickItem(getActivity(), saleCarModel.get(position));
            }
        });
        getActivity().setTitle(R.string.buy_car_text);
        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.setPageSize(SaleCarModel.PAGE_SIZE);
        new RestClient().searchProduct(searchProductRequest, new Callback<SearchProductResponse>() {
            @Override
            public void success(final SearchProductResponse response, final Response response2) {
                if (response != null && response.isExecutionResult()) {
                    saleCarModel.add(response.getProductList());
                    saleCarModel.notifyDataSetInvalidated();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_buy_car, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivityForResult(new Intent(getActivity(), FindUsedActivity.class), 1000);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                String filter = data.getStringExtra("filter");
                getPresenter().filterCar(getActivity(), filter);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setEnabled(boolean on) {
    }

    @Override
    public BuyCarFragmentPresenter createPresenter() {
        return new BuyCarFragmentPresenter();
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }

    @Override
    public void login(String response) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSaleCarFilter:
                break;
            case R.id.saleCarFilterButton:
//                getValidator().validate();
                getPresenter().filterCar(getActivity(), filterText.getText().toString());
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        startActivity(new Intent(getActivity(), FindUsedActivity.class));
//        getPresenter().filterCar(getActivity(), String.valueOf(filterText.getText()));
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.textSaleCarFilter:
                Toast.makeText(getActivity(), "请选择筛选条件", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
