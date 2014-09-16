package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.jason.usedcar.Application;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.SellCarAdapter;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.SaleCarModel2;
import com.jason.usedcar.presenter.SellCarFragmentPresenter;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.SellingCarResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarFragment extends BaseFragment<SellCarFragmentPresenter, Ui> {


    private SaleCarModel2 saleCarModel = new SaleCarModel2();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_car, container, false);
        ListView listView = getView(view, R.id.selling_car_list);
        listView.setAdapter(new SellCarAdapter(getActivity(), saleCarModel));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.sell_car_text);
        if (Application.fromActivity(getActivity()).getAccessToken() == null) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 10);
            return;
        }
        Request request = new Request();
        request.setAccessToken(Application.fromActivity(getActivity()).getAccessToken());
        new RestClient().getSellingCar(request, new Callback<SellingCarResponse>() {
            @Override
            public void success(SellingCarResponse sellingCarResponse, Response response) {
                if (sellingCarResponse != null && sellingCarResponse.isExecutionResult()) {
                    saleCarModel.setData(sellingCarResponse.getProductList());
                    saleCarModel.notifyDataSetInvalidated();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_shopping_car, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_car:
                getPresenter().addUsedCar(getActivity());
                return true;
            case R.id.action_edit_car:
                getPresenter().editUsedCar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 10:
                    Request request = new Request();
                    request.setAccessToken(Application.fromActivity(getActivity()).getAccessToken());
                    new RestClient().getSellingCar(request, new Callback<SellingCarResponse>() {
                        @Override
                        public void success(SellingCarResponse sellingCarResponse, Response response) {
                            if (sellingCarResponse != null && sellingCarResponse.isExecutionResult()) {
                                saleCarModel.setData(sellingCarResponse.getProductList());
                                saleCarModel.notifyDataSetInvalidated();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                        }
                    });
                    break;
            }
        }
    }

    @Override
    public SellCarFragmentPresenter createPresenter() {
        return new SellCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return null;
    }
}
