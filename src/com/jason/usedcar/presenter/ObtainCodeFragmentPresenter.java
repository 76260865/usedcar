package com.jason.usedcar.presenter;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.param.ObtainCodeParam;
import com.jason.usedcar.model.result.ObtainCodeResult;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter.ObtainCodeFragmentUi;
import com.jason.usedcar.util.HttpUtil;

/**
 * @author t77yq @2014.06.08
 */
public class ObtainCodeFragmentPresenter extends BasePresenter<ObtainCodeFragmentUi> {

    public interface ObtainCodeFragmentUi extends Ui {

        public void onCodeObtained(String code);
    }

    private static final String TAG = ObtainCodeFragmentPresenter.class.getSimpleName();

    public void obtainCode(Context context, final ObtainCodeParam param) {
        Log.d(TAG, " obtainCode URI:" + HttpUtil.OBTAIN_CODE_URI);
        Volley.newRequestQueue(context).add(createPostRequest(HttpUtil.OBTAIN_CODE_URI, param,
            new Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "mObtainCodeResponseListener:response" + response);
                    Gson gson = new Gson();
                    ObtainCodeResult result = gson.fromJson(response, ObtainCodeResult.class);
                    if (result.isExecutionResult()) {
                        getUi().onCodeObtained("0");
                    }
                }
            },
            new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            }));
    }
}
