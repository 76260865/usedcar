package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.jason.usedcar.Application;
import com.jason.usedcar.CarDetails2Activity;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.presentation_model.ShoppingCarView;
import com.jason.usedcar.presentation_model.ShoppingCarViewModel;

/**
 * @author t77yq @2014-09-29.
 */
public class ShoppingCar2Fragment extends AbsFragment implements ShoppingCarView {

    private ShoppingCarViewModel shoppingCarViewModel;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.txt_shopping_car_str);
        shoppingCarViewModel.loadData();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case Constants.REQUEST_LOGIN:
                    shoppingCarViewModel.end();
                    break;
            }
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_LOGIN:
                    shoppingCarViewModel.loadData();
                    break;
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_shopping_car2;
    }

    @Override
    protected Object presentationModel() {
        if (shoppingCarViewModel == null) {
            shoppingCarViewModel = new ShoppingCarViewModel(this);
        }
        return shoppingCarViewModel;
    }

    @Override
    public void viewProductDetails(final Product product) {
        Intent detailsIntent = new Intent(getContext(), CarDetails2Activity.class);
        detailsIntent.putExtra("product_id", product.getProductId());
        detailsIntent.putExtra("type", Constants.CarDetailsType.OTHER);
        startActivity(detailsIntent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public boolean isLogin() {
        return getAccessToken() != null;
    }

    @Override
    public void login() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void login(final int requestCode) {
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), requestCode);
    }

    @Override
    public String getAccessToken() {
        return Application.fromActivity(getActivity()).getAccessToken();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
