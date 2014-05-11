package com.jason.usedcar.presenter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.Result;
import com.jason.usedcar.model.User;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class UserRegisterFragmentPresenter extends
        Presenter<UserRegisterFragmentPresenter.UserRegisterFragmentUi> {
    private static final String TAG = "UserRegisterFragmentPresenter";

    public void registerUser(final Context context, final User user) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d(TAG, " HttpUtil.SIGINON_URI:" + HttpUtil.SIGINON_URI);

        StringRequest postRequest = new StringRequest(Request.Method.POST, HttpUtil.SIGINON_URI,
                mSingnonResponseListener, mSingonErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", user.getPhone());
                params.put("phoneVerifyCode", user.getPhoneVerifyCode());
                params.put("nickname", user.getNickname());
                // params.put("email", user.getEmail());
                params.put("email", "76260865@qq.com");
                params.put("acceptTerm", Boolean.toString(user.getAcceptTerm()));
                params.put("password", user.getPassword());
                params.put("repassword", user.getRepassword());
                params.put("accountType", Integer.toString(user.getAccountType()));
                Log.d(TAG, "params is:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };

        queue.add(postRequest);
        Log.d(TAG, "queue.add(postRequest)");
    }

    private Response.Listener<String> mSingnonResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "mResponseListener onResponse:" + response);
            Gson gson = new Gson();
            SingOnResult result = gson.fromJson(response, SingOnResult.class);
            if (result.isExecutionResult()) {
                getUi().onUserRegistered();
            } else {
            }
        }
    };

    private Response.ErrorListener mSingonErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };

    public void obtainValidateCode(Context context, final String phoneNumber, final String deviceId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d(TAG, " obtainValidateCode URI:" + HttpUtil.OBTAIN_CODE_URI);

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                HttpUtil.OBTAIN_CODE_URI, mObtainCodeResponseListener, mObtainCodeErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", phoneNumber);
                params.put("deviceId", deviceId);
                Log.d(TAG, "obtainValidateCode params is:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };

        queue.add(postRequest);
    }

    private Response.Listener<String> mObtainCodeResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "mObtainCodeResponseListener:response" + response);
            Gson gson = new Gson();
            ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
            if (result.isExecutionResult()) {
                getUi().onVerifyCodeObtained(result.getCode());
            } else {
            }
        }
    };

    private Response.ErrorListener mObtainCodeErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };

    public interface UserRegisterFragmentUi extends Ui {
        void onVerifyCodeObtained(String code);

        void onUserRegistered();
    }

    private class ObtainCodeResult extends Result {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    private class SingOnResult extends Result {
        private String accessToken;
        private String userId;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
