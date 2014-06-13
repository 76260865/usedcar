package com.jason.usedcar.presenter;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.param.ObtainCodeParam;
import com.jason.usedcar.model.param.SignOnParam;
import com.jason.usedcar.model.result.ObtainCodeResult;
import com.jason.usedcar.model.result.SignOnResult;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentUi> {

    public interface RegisterFragmentUi extends Ui {

        void onVerifyCodeRequested(String code);

        void onRegistered();
    }

    private static final String TAG = RegisterFragmentPresenter.class.getSimpleName();

    public void register(final Context context, final SignOnParam param) {
        Log.d(TAG, " HttpUtil.SIGN_ON_URI:" + HttpUtil.SIGN_ON_URI);
        Volley.newRequestQueue(context).add(createPostRequest(requestUrl(), param,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "mResponseListener onResponse:" + response);
                    Gson gson = new Gson();
                    SignOnResult result = gson.fromJson(response, SignOnResult.class);
                    if (result.isExecutionResult()) {
                        getUi().onRegistered();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            }));
        Log.d(TAG, "queue.add(postRequest)");
    }

    protected String requestUrl() {
        return HttpUtil.SIGN_ON_URI;
    }

    public void obtainCode(Context context, final ObtainCodeParam param) {
        Log.d(TAG, " obtainCode URI:" + HttpUtil.OBTAIN_CODE_URI);
        Volley.newRequestQueue(context).add(createPostRequest(HttpUtil.OBTAIN_CODE_URI, param,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "mObtainCodeResponseListener:response" + response);
                    Gson gson = new Gson();
                    ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
                    if (result.isExecutionResult()) {
                        getUi().onVerifyCodeRequested("0");
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            }));
    }
}
