package com.jason.usedcar.presenter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.ObtainCodeResult;
import com.jason.usedcar.presenter.ResetPasswordFragmentPresenter.ResetPasswordFragmentUi;
import com.jason.usedcar.util.HttpUtil;

/**
 * @author t77yq @2014.06.08
 */
public class ResetPasswordFragmentPresenter extends Presenter<ResetPasswordFragmentUi> {

    public interface ResetPasswordFragmentUi extends Ui {

        public void onPasswordReset();
    }

    private static final String TAG = "ResetPasswordFragmentPresenter";

    public void resetPassword(Context context, final String principle, final String activeCode,
            final String newPassword, final String confirmPassword) {
        Log.d(TAG, " resetPasswordByPhone:" + HttpUtil.RESET_PWD_BY_PHONE_URI);

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                HttpUtil.RESET_PWD_BY_PHONE_URI, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("principle", principle);
                params.put("activeCode", activeCode);
                params.put("newPassword", newPassword);
                params.put("confirmPassword", confirmPassword);
                Log.d(TAG, "RESET_PWD_BY_PHONE_URI params is:" + params);
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

    Listener<String> responseListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "mObtainCodeResponseListener:response" + response);
            Gson gson = new Gson();
            ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
            if (result.isExecutionResult()) {
                getUi().onPasswordReset();
            }
        }
    };
    ErrorListener errorListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };
}
