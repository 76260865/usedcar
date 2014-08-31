package com.jason.usedcar.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import com.jason.usedcar.*;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.ResetPasswordByPhoneRequest;
import com.jason.usedcar.response.*;
import com.jason.usedcar.presenter.ResetPasswordFragmentPresenter.ResetPasswordFragmentUi;
import retrofit.*;
import retrofit.client.*;
import retrofit.client.Response;

/**
 * @author t77yq @2014.06.08
 */
public class ResetPasswordFragmentPresenter extends BasePresenter<ResetPasswordFragmentUi> {

    public interface ResetPasswordFragmentUi extends Ui {

        public void onPasswordReset();
    }

    private static final String TAG = "ResetPasswordFragmentPresenter";

    public void resetPassword(Fragment fragment, final ResetPasswordByPhoneRequest param) {
        final LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().resetPasswordByPhone(param, new Callback<PasswordResponse>() {
            @Override
            public void success(final PasswordResponse response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response.isExecutionResult()) {
                    getUi().onPasswordReset();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    public void obtainCode(Fragment fragment, ObtainCodeRequest request) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("获取手机验证码&38230;");
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().obtainCode(request, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(final ObtainCodeResponse response, final Response response2) {
                loadingFragment.dismiss();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
