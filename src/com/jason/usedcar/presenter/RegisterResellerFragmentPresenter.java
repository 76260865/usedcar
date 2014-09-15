package com.jason.usedcar.presenter;

import android.support.v4.app.Fragment;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.request.RegisterResellerRequest;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Logic for call buttons.
 */
public class RegisterResellerFragmentPresenter extends RegisterFragmentPresenter {

    private static final String TAG = RegisterResellerFragmentPresenter.class.getSimpleName();

    public void registerReseller(final Fragment fragment, final RegisterResellerRequest param) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().registerReseller(param, new Callback<com.jason.usedcar.response.Response>() {
            @Override
            public void success(final com.jason.usedcar.response.Response response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
