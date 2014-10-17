package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.jason.usedcar.Application;
import com.jason.usedcar.CarDetails2Activity;
import com.jason.usedcar.CarDetailsActivity;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.SellCarActivity;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.presentation_model.MenuSellCarViewModel;
import com.jason.usedcar.presentation_model.ViewSellingCarPresentationModel;
import com.jason.usedcar.presentation_model.ViewSellingCarView;
import org.robobinding.MenuBinder;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarFragment2 extends AbsFragment implements ViewSellingCarView {

    private ViewSellingCarPresentationModel presentationModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_sell_car2;
    }

    @Override
    protected Object presentationModel() {
        if (presentationModel == null) {
            presentationModel = new ViewSellingCarPresentationModel(this);
        }
        return presentationModel;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.sell_car_text);
        presentationModel.loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuSellCarViewModel menuSellCarViewModel = new MenuSellCarViewModel(this);
        MenuBinder menuBinder = new BinderFactoryBuilder().build().createMenuBinder(menu, inflater, getContext());
        menuBinder.inflateAndBind(R.menu.menu_fragment_sell_car, menuSellCarViewModel);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_car:
                //getPresenter().addUsedCar(getActivity());
                return true;
            case R.id.action_edit_car:
                //getPresenter().editUsedCar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    presentationModel.end();
                    break;
                case Activity.RESULT_OK:
                    presentationModel.loadData();
                    break;
            }
        }
    }

    @Override
    public void viewProductDetails(Product product) {
        Intent viewProductDetails = new Intent(getActivity(), CarDetails2Activity.class);
        viewProductDetails.putExtra("product_id", product.getProductId());
        viewProductDetails.putExtra("type", Constants.CarDetailsType.SELL);
        startActivity(viewProductDetails);
    }

    @Override
    public void addNewCar() {
        startActivity(new Intent(getActivity(), SellCarActivity.class));
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
