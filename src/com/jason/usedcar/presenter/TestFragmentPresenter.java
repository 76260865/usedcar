package com.jason.usedcar.presenter;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.TestFragmentPresenter.CallButtonUi;
import com.jason.usedcar.request.MultipartRequest;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class TestFragmentPresenter extends BasePresenter<CallButtonUi> {
    private static final String TAG = "TestFragmentPresenter";

    public void login(final Context context) {
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bottombg);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bos);
        final byte[] bmp = bos.toByteArray();

        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> map = new HashMap<String, String>();
        MultipartRequest postRequest2 = new MultipartRequest(HttpUtil.IMAGE_UPLOAD_URI,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "error:" + error);
                        // error
                    }
                }, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "postRequest2 response:" + response);
                        getUi().login(response);
                    }
                }, bmp, map);
        queue.add(postRequest2);

    }

    public interface CallButtonUi extends Ui {

        void setEnabled(boolean on);

        void login(String response);
    }
}
