package com.jason.usedcar.presenter;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.http.StringPostRequest;
import com.jason.usedcar.interfaces.Ui;

/**
 * Logic for call buttons.
 */
public class BuyCarFragmentPresenter extends
        Presenter<BuyCarFragmentPresenter.CallButtonUi> {

    public void login(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = "http://112.124.62.114:8080/usedcar/login.json";
        StringRequest postRequest = new StringPostRequest(url,
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
            protected Object data() {
                return null;
            }
        };

        queue.add(postRequest);
    }

    public interface CallButtonUi extends Ui {
        void setEnabled(boolean on);

        void login(String reponse);
    }
}
