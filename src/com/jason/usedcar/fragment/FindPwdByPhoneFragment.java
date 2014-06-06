package com.jason.usedcar.fragment;

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
import com.jason.usedcar.R;
import com.jason.usedcar.model.Result;
import com.jason.usedcar.util.HttpUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FindPwdByPhoneFragment extends Fragment {

    private static final String TAG = "FindPwdByPhoneFragment";
    RequestQueue queue;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        Log.d(TAG, " HttpUtil.SIGINON_URI:" + HttpUtil.SIGINON_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_pwd_by_phone, container, false);
        return rootView;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            StringRequest postRequest = new StringRequest(Request.Method.POST,
                    HttpUtil.UPDATE_PWD_URI, mUpdateResponseListener, mUpdateErrorListener) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //TODO 添加更新密码信息
//                    params.put("oldPWD", user.getPhone());
//                    params.put("newPWD", user.getPhoneVerifyCode());
//                    params.put("confirmPWD", user.getNickname());
//                    params.put("accessToken", "76260865@qq.com");
//                    params.put("deviceId", Boolean.toString(user.getAcceptTerm()));
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
    };
    private Response.Listener<String> mUpdateResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "mResponseListener onResponse:" + response);
            Gson gson = new Gson();
            Result result = gson.fromJson(response, Result.class);
            if (result.isExecutionResult()) {
                //TODO: 更新成功
            } else {
            }
        }
    };

    private Response.ErrorListener mUpdateErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.toString());
        }
    };
}
