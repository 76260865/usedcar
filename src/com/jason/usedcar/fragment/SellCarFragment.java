package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.SaleCarAdapter;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.param.PublishUsedCarParam;
import com.jason.usedcar.presenter.SellCarFragmentPresenter;
import com.jason.usedcar.util.ViewFinder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarFragment extends BaseFragment<SellCarFragmentPresenter, Ui> {


    private SaleCarModel saleCarModel = new SaleCarModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_car, container, false);
        ListView listView = ViewFinder.findViewById(view, R.id.selling_car_list);
        listView.setAdapter(new SaleCarAdapter(getActivity(), saleCarModel));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.sell_car_text);
        List<PublishUsedCarParam> data = new ArrayList<PublishUsedCarParam>(20);
        for (int i = 0; i < 20; i++) {
            PublishUsedCarParam param = new PublishUsedCarParam();
            param.setListPrice(1000 + i);
            param.setOdometer(2032 + 10 * i);
            param.setPurchaseDate(String.valueOf(System.currentTimeMillis()));
            data.add(param);
        }
        saleCarModel.setData(data);
        saleCarModel.notifyDataSetInvalidated();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
    public SellCarFragmentPresenter createPresenter() {
        return new SellCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return null;
    }
}
