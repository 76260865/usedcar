package com.jason.usedcar.presenter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
        Presenter<UserRegisterFragmentPresenter.CallButtonUi> {
    private static final String TAG = "UserRegisterFragmentPresenter";

    public void registerUser(final Context context, final User user) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d(TAG, " HttpUtil.SIGINON_URI:" + HttpUtil.SIGINON_URI);
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                HttpUtil.SIGINON_URI, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Result result = gson.fromJson(response, Result.class);
                        if (result.isExecutionResult()) {
                            getUi().onUserRegistered();
                        } else {
                            Toast.makeText(context, result.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }) {
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
                params.put("accountType",
                        Integer.toString(user.getAccountType()));
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
    }

    public interface CallButtonUi extends Ui {
        void onUserRegistered();
    }
}
