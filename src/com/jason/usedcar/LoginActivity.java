package com.jason.usedcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.config.DeviceInfo;
import com.jason.usedcar.fragment.BaseDialogFragment;
import com.jason.usedcar.interfaces.IJobListener;
import com.jason.usedcar.model.param.LoginParam;
import com.jason.usedcar.model.param.UpdateUserInfoParam;
import com.jason.usedcar.model.result.LoginResult;
import com.jason.usedcar.util.AccessTokenUtil;
import com.jason.usedcar.util.HttpUtil;
import com.jason.usedcar.util.ViewFinder;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity implements OnClickListener, ValidationListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Required(order = 1)
    private EditText editAccount;

    @Password(order = 2)
    private EditText editPassword;

    private boolean mResult = false;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_login);

        editAccount = ViewFinder.findViewById(this, R.id.login_account);
        editPassword = ViewFinder.findViewById(this, R.id.login_password);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                validator.validate();
                break;
            case R.id.login_register:
                register();
                break;
            case R.id.login_reset_password:
                resetPassword();
                break;
        }
    }

    private void login() {
        final String account = String.valueOf(editAccount.getText());
        final String password = String.valueOf(editPassword.getText());
        Log.d(TAG, " HttpUtil.LOGIN_URI:" + HttpUtil.LOGIN_URI);
        final LoginParam param = new LoginParam();
        Toast.makeText(getApplicationContext(), "" + param.getDeviceId() + " - " + Build.MANUFACTURER + " "
            + Build.DEVICE + " - " + "android " + VERSION.RELEASE, Toast.LENGTH_SHORT).show();
        param.setPhoneOrEmail(account);
        param.setPassword(password);
        StringRequest request = new StringRequest(Method.POST, HttpUtil.LOGIN_URI,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "response: " + response);
                    mResult = true;
                    LoginResult result = new Gson().fromJson(response, LoginResult.class);
                    try {
                        String md5 = AccessTokenUtil.MD5Encode("dd4nfxp8uja6snx21b1396849877041");

                        String token = Base64.encodeToString((md5 + "10001&1396849877041").getBytes(), Base64.DEFAULT);
                        token.length();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    if (result.isExecutionResult()) {
                        UsedCarApplication application = (UsedCarApplication) getApplication();
                        application.userId = result.getUserId();
                        application.accessToken = application.getEncryptedToken(result.getUserId(),
                            result.getAccessToken());
                        application.accessToken = application.accessToken.replaceAll("\n", "");
                        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Method.POST, HttpUtil.UPDATE_USER_INFO_URI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    int i ;
                                    i = 0;
                                }
                            },
                            new ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    int i ;
                                    i = 0;
                                }
                            }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Accept", "application/json");
                                headers.put("User-Agent", DeviceInfo.USER_AGENT);
                                return headers;
                            }

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                UpdateUserInfoParam param = new UpdateUserInfoParam();
                                param.setAccessToken(((UsedCarApplication) getApplication()).accessToken);
                                param.setNickname("123");
                                param.setRealName("321");
                                param.setSex(true);
                                param.setBirthyear("2014");
                                param.setBirthmonth("12");
                                param.setBirthday("25");
                                param.setCertificateNumber("123456");
                                param.setProvince("sc");
                                param.setCity("cd");
                                param.setCounty("ht");
                                param.setStreet("NO. 25");
                                return object2Map(param);
                            }

                            private Map<String, String> object2Map(Object obj) {
                                return object2Map(obj, false);
                            }

                            private Map<String, String> object2Map(Object obj, boolean nullable) {
                                Map<String, String> result = new HashMap<String, String>();
                                if (obj == null) {
                                    return result;
                                }
                                java.lang.reflect.Method[] methods = obj.getClass().getMethods();
                                if (methods == null || methods.length == 0) {
                                    return result;
                                }
                                try {
                                    for (java.lang.reflect.Method method : methods) {
                                        String methodName = method.getName();
                                        if (methodName != null && methodName.length() > 3
                                            && methodName.startsWith("get") && !methodName.equals("getClass")) {
                                            Object value = method.invoke(obj, null);
                                            if (!nullable && value == null) {
                                                continue;
                                            }
                                            char[] fieldNameArray = methodName.substring(3).toCharArray();
                                            fieldNameArray[0] = Character.toLowerCase(fieldNameArray[0]);
                                            result.put(String.valueOf(fieldNameArray),
                                                (value == null) ? "" : String.valueOf(value));
                                        }
                                    }
                                } catch (InvocationTargetException e) {
                                    result.clear();
                                } catch (IllegalAccessException e) {
                                    result.clear();
                                }
                                return result;
                            }
                        });
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                    if (mIJobListener != null) {
                        mIJobListener.executionDone();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mResult = true;
                    Log.d(TAG, error.toString());
                    if (mIJobListener != null) {
                        mIJobListener.executionDone();
                    }
                }
            }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("User-Agent", DeviceInfo.USER_AGENT);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return object2Map(param);
            }

            private Map<String, String> object2Map(Object obj) {
                return object2Map(obj, false);
            }

            private Map<String, String> object2Map(Object obj, boolean nullable) {
                Map<String, String> result = new HashMap<String, String>();
                if (obj == null) {
                    return result;
                }
                java.lang.reflect.Method[] methods = obj.getClass().getMethods();
                if (methods == null || methods.length == 0) {
                    return result;
                }
                try {
                    for (java.lang.reflect.Method method : methods) {
                        String methodName = method.getName();
                        if (methodName != null && methodName.length() > 3
                            && methodName.startsWith("get") && !methodName.equals("getClass")) {
                            Object value = method.invoke(obj, null);
                            if (!nullable && value == null) {
                                continue;
                            }
                            char[] fieldNameArray = methodName.substring(3).toCharArray();
                            fieldNameArray[0] = Character.toLowerCase(fieldNameArray[0]);
                            result.put(String.valueOf(fieldNameArray),
                                (value == null) ? "" : String.valueOf(value));
                        }
                    }
                } catch (InvocationTargetException e) {
                    result.clear();
                } catch (IllegalAccessException e) {
                    result.clear();
                }
                return result;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
    private void resetPassword() {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    // bellow code is for test:
    public boolean getResult() {
        return mResult;
    }

    private IJobListener mIJobListener;

    public void setListener(IJobListener listener) {
        mIJobListener = listener;
    }

    @Override
    public void onValidationSucceeded() {
        login();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.login_account:
                BaseDialogFragment.newInstance(getString(R.string.error_input_account)).show(getSupportFragmentManager());
                break;
            case R.id.login_password:
                BaseDialogFragment.newInstance(getString(R.string.error_input_password)).show(getSupportFragmentManager());
                break;
        }
    }
}
