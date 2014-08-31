package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.BuyCarHistoryAdapter;
import com.jason.usedcar.model.UsedCar;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.response.CarListResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-16.
 */
public class SellCarHistoryFragment extends ListFragment {

    public interface Data {

        public List<UsedCar> getData(Fragment fragment);

        public void setData(Fragment fragment, List<UsedCar> data);
    }

    private UsedCarModel<UsedCar> usedCarModel = new UsedCarModel<UsedCar>();

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new BuyCarHistoryAdapter(getActivity(), usedCarModel));
        //setListShown(false);
        List<UsedCar> data = ((Data) getActivity()).getData(this);
        if (data == null || data.isEmpty()) {
            new RestClient().saleUsedCarList(new PagedRequest(), new Callback<CarListResponse>() {
                @Override
                public void success(final CarListResponse response, final Response response2) {
                    if (response != null && response.isExecutionResult()) {
                        ((Data) getActivity()).setData(SellCarHistoryFragment.this, response.getUsedCars());
                        usedCarModel.add(response.getUsedCars());
                        usedCarModel.notifyDataSetInvalidated();
                        setListShown(true);
                    }
                }

                @Override
                public void failure(final RetrofitError error) {

                }
            });
        } else {
            usedCarModel.add(data);
            usedCarModel.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usedCarModel.unregisterAll();
    }
}
