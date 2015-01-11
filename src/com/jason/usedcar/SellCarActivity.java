package com.jason.usedcar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.jason.usedcar.fragment.CarBaseInfoFragment;
import com.jason.usedcar.fragment.CompleteCarInfoFragment;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.fragment.ResellerInfoFragment;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.Response;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarActivity extends BaseActivity implements Action {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);
        CarResponse carResponse = (CarResponse) getIntent().getSerializableExtra("car_response");
        CarInfo carInfo = new CarInfo();
        if (carResponse != null) {
            CarInfo.convert(carResponse, carInfo);
        }
        CacheUtils.setCarInfo(carInfo);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_sell_car_container,
                new CompleteCarInfoFragment(), "").commit();
    }

    @Override
    public void action(final Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof CompleteCarInfoFragment) {
            ft.addToBackStack("CompleteCarInfoFragment").replace(R.id.activity_sell_car_container, new CarBaseInfoFragment()).commit();
        } else if (fragment instanceof CarBaseInfoFragment) {
            ft.addToBackStack("CarBaseInfoFragment").replace(R.id.activity_sell_car_container, new ResellerInfoFragment()).commit();
        } else if (fragment instanceof ResellerInfoFragment) {
            final LoadingFragment loading = LoadingFragment.newInstance("");
            loading.show(getSupportFragmentManager());
            new RestClient().publishUsedCar(CacheUtils.getCarInfo(), Application.fromActivity(this).getAccessToken(),
                    new Request().getDeviceId(), new Callback<Response>() {
                @Override
                public void success(final Response response, final retrofit.client.Response response2) {
                    if (response.isExecutionResult()) {
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

                @Override
                public void failure(final RetrofitError error) {
                    error.getCause();
                    Toast.makeText(getApplicationContext(), "出错了", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            });
        }
    }
}
