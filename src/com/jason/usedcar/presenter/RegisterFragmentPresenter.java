package com.jason.usedcar.presenter;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.constants.Parameters;
import com.jason.usedcar.constants.Parameters.Register;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.ObtainCodeResult;
import com.jason.usedcar.model.SignOnResult;
import com.jason.usedcar.model.User;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Logic for call buttons.
 */
public class RegisterFragmentPresenter extends Presenter<RegisterFragmentUi> {

    public interface RegisterFragmentUi extends Ui {

        void onVerifyCodeRequested(String code);

        void onRegistered();
    }

    private static final String TAG = RegisterFragmentPresenter.class.getSimpleName();

    public void register(final Context context, final User user) {
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
        StringRequest postRequest = new StringRequest(Request.Method.POST,
            requestUrl(), responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = buildParams(user);
                Log.d(TAG, "params is:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
        Log.d(TAG, "queue.add(postRequest)");
    }

    protected String requestUrl() {
        return HttpUtil.SIGN_ON_URI;
    }

    protected Map<String, String> buildParams(User user) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Register.ACCOUNT, user.getAccount());
        params.put(Register.ACCOUNT_TYPE, Integer.toString(user.getAccountType()));
        params.put(Register.VERIFY_CODE, user.getVerifyCode());
        params.put(Register.AGREEMENT, Boolean.toString(user.isAcceptTerm()));
        params.put(Register.PASSWORD, user.getPassword());
        params.put(Register.CONFIRM_PASSWORD, user.getConfirmPassword());
        return params;
    }

    public void obtainCode(Context context, final String phoneNumber, final String deviceId) {
        Log.d(TAG, " obtainCode URI:" + HttpUtil.OBTAIN_CODE_URI);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "mObtainCodeResponseListener:response" + response);
                Gson gson = new Gson();
                ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
                if (result.isExecutionResult()) {
                    getUi().onVerifyCodeRequested(result.getCode());
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        };
        StringRequest postRequest = new StringRequest(Request.Method.POST,
            HttpUtil.OBTAIN_CODE_URI, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Parameters.Register.PHONE, phoneNumber);
                params.put(Parameters.Register.DEVICE_ID, deviceId);
                Log.d(TAG, "obtainCode params is:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(postRequest);
    }
}
