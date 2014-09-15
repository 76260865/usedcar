package com.jason.usedcar.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jason.usedcar.Application;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.ShoppingCarAdapter;
import com.jason.usedcar.fragment.BaseFragment;
import com.jason.usedcar.model.ShoppingCarModel;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.response.CarListResponse;
import com.jason.usedcar.response.CartResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShoppingCarFragment extends
        BaseFragment<ShoppingCarFragmentPresenter, CallButtonUi>
        implements ShoppingCarFragmentPresenter.CallButtonUi {

    private ListView mShoppingCarListView;

    private ShoppingCarModel shoppingCarModel = new ShoppingCarModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoping_car, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.txt_shopping_car_str);
        if (Application.fromActivity(getActivity()).getAccessToken() == null) {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 10);
            return;
        }
        new RestClient().cart(Application.fromActivity(getActivity()).getAccessToken(),
                Build.SERIAL, new Callback<CartResponse>() {
            @Override
            public void success(final CartResponse response, final Response response2) {
                if (response != null && response.isExecutionResult()) {
                    shoppingCarModel.add(response.getCartList());
                    shoppingCarModel.notifyDataSetInvalidated();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                //
            }
        });
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShoppingCarListView = getView(view, R.id.list_shopping_car);
        mShoppingCarListView.setAdapter(new ShoppingCarAdapter(getActivity(), shoppingCarModel));
    }

    @Override
    public void setEnabled(boolean on) {
        // TODO Auto-generated method stub

    }

    @Override
    public ShoppingCarFragmentPresenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_shopping_car, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }

    @Override
    public void login(String response) {
        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
    }
}
