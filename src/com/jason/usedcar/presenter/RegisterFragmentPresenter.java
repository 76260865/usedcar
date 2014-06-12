package com.jason.usedcar.presenter;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.http.StringPostRequest;
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
public class RegisterFragmentPresenter extends Presenter<RegisterFragmentUi> {

    public interface RegisterFragmentUi extends Ui {

        void onVerifyCodeRequested(String code);

        void onRegistered();
    }

    private static final String TAG = RegisterFragmentPresenter.class.getSimpleName();

    public void register(final Context context, final SignOnParam param) {
        Log.d(TAG, " HttpUtil.SIGN_ON_URI:" + HttpUtil.SIGN_ON_URI);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "mResponseListener onResponse:" + response);
                Gson gson = new Gson();
                SignOnResult result = gson.fromJson(response, SignOnResult.class);
                if (result.isExecutionResult()) {
                    getUi().onRegistered();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        };
        StringRequest postRequest = new StringPostRequest(requestUrl(), responseListener, errorListener) {
            @Override
            protected Object data() {
                return param;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
        Log.d(TAG, "queue.add(postRequest)");
    }

    protected String requestUrl() {
        return HttpUtil.SIGN_ON_URI;
    }

    public void obtainCode(Context context, final ObtainCodeParam param) {
        Log.d(TAG, " obtainCode URI:" + HttpUtil.OBTAIN_CODE_URI);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "mObtainCodeResponseListener:response" + response);
                Gson gson = new Gson();
                ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
                if (result.isExecutionResult()) {
                    getUi().onVerifyCodeRequested("0");
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        };
        StringRequest postRequest = new StringPostRequest(HttpUtil.OBTAIN_CODE_URI,
            responseListener, errorListener) {
            @Override
            protected Object data() {
                return param;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
