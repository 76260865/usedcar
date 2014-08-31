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
import com.jason.usedcar.Application;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.BuyCarAdapter;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.request.TokenGenerateRequest;
import com.jason.usedcar.response.CarListResponse;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.LoginResponse;
import com.jason.usedcar.response.TokenGenerateResponse;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BuyCarFragment extends
    BaseFragment<BuyCarFragmentPresenter, CallButtonUi> implements
    CallButtonUi, OnClickListener {

    private View mFooterLoadingView;

    @Required(order = 1)
    private TextView filterText;

    private SaleCarModel saleCarModel = new SaleCarModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getPresenter().login(getActivity());
        View view = inflater.inflate(R.layout.fragment_buy_car, container, false);
        filterText = getView(view, R.id.textSaleCarFilter);
        filterText.setOnClickListener(this);
        getView(view, R.id.saleCarFilterButton).setOnClickListener(this);
        ListView saleCarlList = getView(view, R.id.usedCarList);
        mFooterLoadingView = inflater.inflate(R.layout.footer_loading_layout, null);
        saleCarlList.addFooterView(mFooterLoadingView);
        saleCarlList.setAdapter(new BuyCarAdapter(getActivity(), saleCarModel));
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
        PagedRequest pagedRequest = new PagedRequest();
        pagedRequest.setPageIndex(saleCarModel.getPageSize() + 1);
        new RestClient().getUsedCarList(pagedRequest, new Callback<CarListResponse>() {
            @Override
            public void success(final CarListResponse response, final Response response2) {
                if (response != null && response.isExecutionResult()) {
                    saleCarModel.add(response.getUsedCars());
                    saleCarModel.notifyDataSetInvalidated();
                }
            }

            @Override
            public void failure(final RetrofitError error) {

            }
        });
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhoneOrEmail("15008488463");
        loginRequest.setPassword("111111");
        new RestClient().login(loginRequest, new Callback<LoginResponse>() {
            @Override
            public void success(final LoginResponse response, final Response response2) {
                TokenGenerateRequest tokenGenerateRequest = new TokenGenerateRequest();
                tokenGenerateRequest.setUserId(String.valueOf(response.getUserId()));
                tokenGenerateRequest.setAccessToken(response.getAccessToken());
                final int userId = response.getUserId();
                final String accessToken = response.getAccessToken();
                new RestClient().generateAccessToken(tokenGenerateRequest, new Callback<TokenGenerateResponse>() {
                    @Override
                    public void success(final TokenGenerateResponse response, final Response response2) {
                        CarRequest carRequest = new CarRequest();
                        carRequest.setProductId("prod10032");
                        Application.sampleAccessToken = response.getSampleAccessToken();
                        Application.fromContext(getActivity()).setAccessToken(response.getSampleAccessToken());
                        carRequest.setAccessToken(Application.getEncryptedToken(userId, accessToken));
                        new RestClient().getUsedCar(carRequest, new Callback<CarResponse>() {
                            @Override
                            public void success(final CarResponse response, final Response response2) {

                            }

                            @Override
                            public void failure(final RetrofitError error) {

                            }
                        });
                        PagedRequest pagedRequest = new PagedRequest();
                        pagedRequest.setAccessToken(Application.sampleAccessToken);
                        pagedRequest.setPageSize(SaleCarModel.PAGE_SIZE);
                        pagedRequest.setPageIndex(saleCarModel.getPageSize() + 1);
                        new RestClient().getUsedCarList(pagedRequest, new Callback<CarListResponse>() {
                            @Override
                            public void success(final CarListResponse response, final Response response2) {
                                if (response != null && response.isExecutionResult()) {
                                    if (response.getUsedCars().isEmpty()) {
                                        saleCarModel.setFull(true);
                                    }
                                    saleCarModel.setLoading(false);
                                    saleCarModel.add(response.getUsedCars());
                                    saleCarModel.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void failure(final RetrofitError error) {
                            }
                        });
                    }

                    @Override
                    public void failure(final RetrofitError error) {

                    }
                });
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
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
            if (saleCarModel.isFull()) {
                mFooterLoadingView.findViewById(R.id.loading_more_no_data).setVisibility(View.VISIBLE);
                mFooterLoadingView.findViewById(R.id.loading_more_progress).setVisibility(View.GONE);
            } else if (!saleCarModel.isLoading() && firstVisibleItem + visibleItemCount == totalItemCount ) {
                saleCarModel.setLoading(true);
                PagedRequest pagedRequest = new PagedRequest();
                pagedRequest.setAccessToken(Application.sampleAccessToken);
                pagedRequest.setPageSize(SaleCarModel.PAGE_SIZE);
                pagedRequest.setPageIndex(saleCarModel.getPageSize() + 1);
                new RestClient().getUsedCarList(pagedRequest, new Callback<CarListResponse>() {
                    @Override
                    public void success(final CarListResponse response, final Response response2) {
                        if (response != null && response.isExecutionResult()) {
                            if (response.getUsedCars().isEmpty()) {
                                saleCarModel.setFull(true);
                            }
                            saleCarModel.setLoading(false);
                            saleCarModel.add(response.getUsedCars());
                            saleCarModel.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                    }
                });
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSaleCarFilter:
                break;
            case R.id.saleCarFilterButton:
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
            case R.id.textSaleCarFilter:
                Toast.makeText(getActivity(), "请选择筛选条件", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
