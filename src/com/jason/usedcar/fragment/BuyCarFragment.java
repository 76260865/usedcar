package com.jason.usedcar.fragment;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.SaleCarAdapter;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.param.PublishUsedCarParam;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.util.ViewFinder;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import java.util.ArrayList;
import java.util.List;

public class BuyCarFragment extends
    BaseFragment<BuyCarFragmentPresenter, CallButtonUi> implements
    CallButtonUi, OnClickListener {

    private View mFooterLoadingView;

    private int mVisibleLastIndex = 0;

    private SaleCarModel saleCarModel = new SaleCarModel();

    private int mCount = 41;

    @Required(order = 1)
    private TextView filterText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getPresenter().login(getActivity());
        View view = inflater.inflate(R.layout.fragment_buy_car, container, false);
        filterText = ViewFinder.findViewById(view, R.id.sale_car_filter_text);
        filterText.setOnClickListener(this);
        ViewFinder.findViewById(view, R.id.sale_car_filter_button).setOnClickListener(this);
        ListView saleCarlList = ViewFinder.findViewById(view, R.id.listCar);
        mFooterLoadingView = inflater.inflate(R.layout.footer_loading_layout, null);
        saleCarlList.addFooterView(mFooterLoadingView);
        saleCarlList.setAdapter(new SaleCarAdapter(getActivity(), saleCarModel));
        saleCarlList.setOnScrollListener(mOnScrollListener);
        saleCarlList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().clickItem(getActivity(), saleCarModel.get(position));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.buy_car_text);
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
        inflater.inflate(R.menu.menu_buy_car, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        getPresenter().filterCar(getActivity(), s);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int mScrollState) {
            int itemsLastIndex = saleCarModel.size() - 1;
            int lastIndex = itemsLastIndex + 1;
            if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE
                && !saleCarModel.isLoading()
                && mVisibleLastIndex == lastIndex
                && saleCarModel.size() <= mCount) {
                saleCarModel.setLoading(true);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<PublishUsedCarParam> data = new ArrayList<PublishUsedCarParam>(20);
                        for (int i = 0; i < 20; i++) {
                            PublishUsedCarParam param = new PublishUsedCarParam();
                            param.setListPrice(1000 + i);
                            param.setOdometer(2032 + 10 * i);
                            param.setPurchaseDate(String.valueOf(System.currentTimeMillis()));
                            data.add(param);
                        }
                        saleCarModel.add(data);
                        saleCarModel.setLoading(false);
                        saleCarModel.notifyDataSetInvalidated();
                    }
                }, 1000);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
            mVisibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            if (saleCarModel.size() > mCount) {
                mFooterLoadingView.findViewById(R.id.loading_more_no_data).setVisibility(View.VISIBLE);
                mFooterLoadingView.findViewById(R.id.loading_more_progress).setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sale_car_filter_text:
                break;
            case R.id.sale_car_filter_button:
                getValidator().validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        getPresenter().filterCar(getActivity(), String.valueOf(filterText.getText()));
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.sale_car_filter_text:
                Toast.makeText(getActivity(), "请选择筛选条件", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
