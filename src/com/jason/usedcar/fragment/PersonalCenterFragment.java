package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.PersonalCenterFragmentPresenter;
import com.jason.usedcar.util.ViewFinder;

public class PersonalCenterFragment extends
    BaseFragment<PersonalCenterFragmentPresenter, Ui> implements OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ViewFinder.findViewById(view, R.id.info_center_bought_cars_history).setOnClickListener(this);
        ViewFinder.findViewById(view, R.id.info_center_identify).setOnClickListener(this);
        ViewFinder.findViewById(view, R.id.info_center_my_collect_cars).setOnClickListener(this);
        ViewFinder.findViewById(view, R.id.info_center_my_info).setOnClickListener(this);
        ViewFinder.findViewById(view, R.id.info_center_sale_car).setOnClickListener(this);
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
                getPresenter().tradeHistory(getActivity());
                break;
            case R.id.info_center_identify:
                getPresenter().identify();
                break;
            case R.id.info_center_my_collect_cars:
                getPresenter().myCollectCar();
                break;
            case R.id.info_center_my_info:
                getPresenter().myInfo(getActivity());
                break;
            case R.id.info_center_sale_car:
                getPresenter().myCarsToSale(getActivity());
                break;
        }
    }
}
