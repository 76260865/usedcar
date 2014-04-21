package com.jason.usedcar;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.model.Result;
import com.jason.usedcar.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class UserLoginActivity extends Activity {

    private static final String TAG = "UserLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        url = HttpUtil.SIGINON_URI;
        RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, " HttpUtil.SIGINON_URI:" + HttpUtil.SIGINON_URI);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                mListener, mErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", "15008488463");
                params.put("phoneVerifyCode", "15008488463");
                params.put("nickname", "nickname");
                // params.put("email", user.getEmail());
                params.put("email", "76260865@qq.com");
                params.put("acceptTerm", "true");
                params.put("password", "admin123");
                params.put("repassword", "admin123");
                params.put("accountType", "1");
                Log.d(TAG, "params is:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                Log.d(TAG, " getHeaders:" + params);
                return params;
            }
        };

        queue.add(postRequest);
    }

    Response.Listener<String> mListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            Result result = gson.fromJson(response, Result.class);
            if (result.isExecutionResult()) {

            } else {

            }
        }
    };
    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };
}
