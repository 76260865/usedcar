package com.jason.usedcar.presenter;

import android.support.v4.app.Fragment;
import android.content.Context;
import com.jason.usedcar.*;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.RegisterRequest;
import com.jason.usedcar.response.ObtainCodeResponse;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import retrofit.*;

/**
 * Logic for call buttons.
 */
public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentUi> {

    public interface RegisterFragmentUi extends Ui {

        void onVerifyCodeRequested(String code);

        void onRegistered();
    }

    private static final String TAG = RegisterFragmentPresenter.class.getSimpleName();

    public void register(final Fragment fragment, final RegisterRequest param) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().register(param, new Callback<com.jason.usedcar.response.Response>() {
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

    protected String requestUrl() {
        return null;
    }

    public void obtainCode(Fragment fragment, final ObtainCodeRequest param) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("获取验证码&#8230;");
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().obtainCode(param, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(final ObtainCodeResponse response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response.isExecutionResult()) {
                    getUi().onVerifyCodeRequested(response.getCode());
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
