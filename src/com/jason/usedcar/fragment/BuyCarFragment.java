package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jason.usedcar.R;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;

public class BuyCarFragment
        extends
        BaseFragment<BuyCarFragmentPresenter, BuyCarFragmentPresenter.CallButtonUi>
        implements BuyCarFragmentPresenter.CallButtonUi {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        getPresenter().login(getActivity());
        return inflater.inflate(R.layout.buy_car_fragment_layout, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.buy_car_text);
    }

    @Override
    public void setEnabled(boolean on) {
        // TODO Auto-generated method stub

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
    public void login(String reponse) {
        Toast.makeText(getActivity(), reponse, Toast.LENGTH_LONG).show();

    }
}
