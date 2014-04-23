package com.jason.usedcar.presenter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.interfaces.Ui;

/**
 * Logic for call buttons.
 */
public class DealerRegisterFragmentPresenter extends
        Presenter<DealerRegisterFragmentPresenter.CallButtonUi> {

    public void login(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://112.124.62.114:8080/usedcar/login.json";
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

        void login(String reponse);
    }
}
