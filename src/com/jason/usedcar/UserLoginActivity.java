package com.jason.usedcar;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.interfaces.IJobListener;
import com.jason.usedcar.model.Result;
import com.jason.usedcar.util.HttpUtil;

public class UserLoginActivity extends Activity {

    private static final String TAG = "UserLoginActivity";
    private EditText mEditUserName;
    private EditText mEditPwd;
    private Button mBtnLogin;
    private TextView mTxtRegister;

    private boolean mResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mEditUserName = (EditText) findViewById(R.id.edit_user_name);
        mEditPwd = (EditText) findViewById(R.id.edit_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(mOnBtnLoginClickListener);
        mTxtRegister = (TextView) findViewById(R.id.txt_register);
        mTxtRegister.setOnClickListener(mOnTxtRegisterClickListener);
    }

    private OnClickListener mOnTxtRegisterClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };

    private OnClickListener mOnBtnLoginClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "xxxxxx", Toast.LENGTH_LONG).show();
            RequestQueue queue = Volley.newRequestQueue(UserLoginActivity.this);
            Log.d(TAG, " HttpUtil.LOGIN_URI:" + HttpUtil.LOGIN_URI);
            StringRequest postRequest = new StringRequest(Request.Method.POST, HttpUtil.LOGIN_URI,
                    mListener, mErrorListener) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("phoneOrEmail", mEditUserName.getText().toString());
                    params.put("password", mEditPwd.getText().toString());
                    params.put("deviceId", "1");
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
    };

    private Response.Listener<String> mListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, " response:" + response);
            mResult = true;
            Gson gson = new Gson();
            Result result = gson.fromJson(response, Result.class);
            if (result.isExecutionResult()) {

            } else {

            }

            if (mIJobListener != null) {
                mIJobListener.executionDone();
            }
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mResult = true;
            Log.d(TAG, error.toString());
            if (mIJobListener != null) {
                mIJobListener.executionDone();
            }
        }
    };

    // bellow code is for test:
    public boolean getResult() {
        return mResult;
    }

    private IJobListener mIJobListener;

    public void setListener(IJobListener listener) {
        mIJobListener = listener;
    }
}
