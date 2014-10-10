package com.jason.usedcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.jason.usedcar.Application;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.PersonalCenterFragmentPresenter;

public class PersonalCenterFragment extends
        BaseFragment<PersonalCenterFragmentPresenter, Ui> implements OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        getView(view, R.id.info_center_bought_cars_history).setOnClickListener(this);
        getView(view, R.id.info_center_identify).setOnClickListener(this);
        getView(view, R.id.info_center_my_collect_cars).setOnClickListener(this);
        getView(view, R.id.info_center_my_info).setOnClickListener(this);
        getView(view, R.id.info_center_sale_car).setOnClickListener(this);
        return view;
    }

    @Override
    public PersonalCenterFragmentPresenter createPresenter() {
        return new PersonalCenterFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.title_personal_center);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_center_bought_cars_history:
                if (Application.fromContext(getActivity()).getAccessToken() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                getPresenter().tradeHistory(getActivity());
                break;
            case R.id.info_center_identify:
                getPresenter().identify(getActivity());
                break;
            case R.id.info_center_my_collect_cars:
                if (Application.fromContext(getActivity()).getAccessToken() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                getPresenter().myCollectCar(getActivity());
                break;
            case R.id.info_center_my_info:
                getPresenter().myInfo(getActivity());
                break;
            case R.id.info_center_sale_car:
                if (Application.fromContext(getActivity()).getAccessToken() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                getPresenter().myCarsToSale(getActivity());
                break;
        }
    }
}
