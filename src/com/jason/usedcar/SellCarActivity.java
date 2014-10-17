package com.jason.usedcar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.jason.usedcar.fragment.CarBaseInfoFragment;
import com.jason.usedcar.fragment.CompleteCarInfoFragment;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.fragment.ResellerInfoFragment;
import com.jason.usedcar.request.PublishUsedCarRequest;
import com.jason.usedcar.response.CarResponse3;
import com.jason.usedcar.response.Response;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarActivity extends BaseActivity implements Action {

    private PublishUsedCarRequest publishUsedCarParam = new PublishUsedCarRequest();

    public CarResponse3 carResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);
        carResponse = (CarResponse3) getIntent().getSerializableExtra("car_response");
        getSupportFragmentManager().beginTransaction().add(
                R.id.activity_sell_car_container,
                new CarBaseInfoFragment(),
                ""
        ).commit();
    }

    @Override
    public void action(final Fragment fragment, final Object... objects) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof CompleteCarInfoFragment) {
            publishUsedCarParam = (PublishUsedCarRequest) objects[0];
            ft.replace(R.id.activity_sell_car_container, new CarBaseInfoFragment()).commit();
        } else if (fragment instanceof CarBaseInfoFragment) {
            if (objects.length > 0) {
                int[] ids = (int[]) objects[0];
                String imageIds = "";
                for (int id : ids) {
                    if (id > 0) {
                        imageIds = imageIds.concat("," + id);
                    }
                }
                publishUsedCarParam.setImageIds(imageIds.substring(imageIds.indexOf(",") + 1));
            }
            ft.replace(R.id.activity_sell_car_container, new ResellerInfoFragment()).commit();
        } else if (fragment instanceof ResellerInfoFragment) {
            final LoadingFragment loading = LoadingFragment.newInstance("");
            loading.show(getSupportFragmentManager());
            int[] imageIds = (int[]) objects[0];
            if (imageIds.length > 0) {
                publishUsedCarParam.setCertificateImageId(imageIds[0] + "," + imageIds[1]);
                publishUsedCarParam.setLicenseImageIds(imageIds[2] + "," + imageIds[3]);
            }
            PublishUsedCarRequest r = (PublishUsedCarRequest)objects[1];
            publishUsedCarParam.setCarContact(r.getCarContact());
            publishUsedCarParam.setContactPhone(r.getContactPhone());
            publishUsedCarParam.setProvinceId(28);
            publishUsedCarParam.setCityId(225);
            publishUsedCarParam.setCountyId(1875);
            publishUsedCarParam.setAccessToken(Application.sampleAccessToken);
            publishUsedCarParam.setAcceptTerm(true);
            new RestClient().publishUsedCar(publishUsedCarParam, new Callback<Response>() {
                @Override
                public void success(final Response response, final retrofit.client.Response response2) {
                    if (response.isExecutionResult()) {
                        finish();
                    }
                    loading.dismiss();
                }

                @Override
                public void failure(final RetrofitError error) {
                    error.getCause();
                    loading.dismiss();
                }
            });
        }
    }
}
