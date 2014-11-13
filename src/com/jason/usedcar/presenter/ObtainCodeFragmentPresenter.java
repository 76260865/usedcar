package com.jason.usedcar.presenter;

import android.support.v4.app.Fragment;
import com.jason.usedcar.*;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.response.ObtainCodeResponse;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter.ObtainCodeFragmentUi;
import retrofit.*;
import retrofit.client.*;

/**
 * @author t77yq @2014.06.08
 */
public class ObtainCodeFragmentPresenter extends BasePresenter<ObtainCodeFragmentUi> {

    public interface ObtainCodeFragmentUi extends Ui {

        public void onCodeObtained(String code);
    }

    private static final String TAG = ObtainCodeFragmentPresenter.class.getSimpleName();

    public void obtainCode(final Fragment fragment, final ObtainCodeRequest param) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("获取短信验证码...");
        loadingFragment.show(fragment.getFragmentManager());
        new RestClient().obtainCode(param, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(final ObtainCodeResponse response, final Response response2) {
                loadingFragment.dismiss();
                if (response.isExecutionResult()) {
                    getUi().onCodeObtained(response.getCode());
                }
                MessageToast.makeText(fragment.getActivity(), response.getMessage()).show();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
