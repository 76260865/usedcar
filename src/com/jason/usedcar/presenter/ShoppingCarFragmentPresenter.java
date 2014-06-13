package com.jason.usedcar.presenter;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class ShoppingCarFragmentPresenter extends BasePresenter<CallButtonUi> {

    public void login(final Context context) {
        Volley.newRequestQueue(context).add(createPostRequest(HttpUtil.LOGIN_URI, null,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getUi().login(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                }
            }));
    }

    public interface CallButtonUi extends Ui {

        void setEnabled(boolean on);

        void login(String response);
    }
}
