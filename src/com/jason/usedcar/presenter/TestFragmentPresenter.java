package com.jason.usedcar.presenter;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Logic for call buttons.
 */
public class TestFragmentPresenter extends
        Presenter<TestFragmentPresenter.CallButtonUi> {

    public void login(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HttpUtil.LOGIN_URI;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getUi().login(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneOrEmail", "d");
                params.put("password", "d");
                params.put("rememberUser", "1");
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

        void setEnabled(boolean on);

        void login(String response);
    }
}
