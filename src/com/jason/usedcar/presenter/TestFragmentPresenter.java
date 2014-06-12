package com.jason.usedcar.presenter;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.http.StringPostRequest;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class TestFragmentPresenter extends
        Presenter<TestFragmentPresenter.CallButtonUi> {

    public void login(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = HttpUtil.LOGIN_URI;
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

        void login(String response);
    }
}
