package com.jason.usedcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jason.usedcar.config.DeviceInfo;
import com.jason.usedcar.model.param.UpdateUserInfoParam;
import com.jason.usedcar.model.param.ViewUserInfoParam;
import com.jason.usedcar.model.result.ViewUserInfoResult;
import com.jason.usedcar.util.HttpUtil;
import com.jason.usedcar.util.ViewFinder;
import com.jason.usedcar.view.ExtendedEditText;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author t77yq @2014.06.14
 */
public class InfoActivity extends ActionBarActivity implements OnClickListener {

    private static final int LOGIN = 10001;

    private boolean editMode = false;

    private InfoActivityHolder activityHolder;

    private UpdateUserInfoParam userInfoParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        viewUserInfo();
        userInfoParam = new UpdateUserInfoParam();
        userInfoParam.setAccessToken(((UsedCarApplication) getApplication()).accessToken);
        activityHolder = new InfoActivityHolder(this);
        activityHolder.changePasswordText.setOnClickListener(this);
        activityHolder.bindPhoneText.setOnClickListener(this);
        activityHolder.bindEmailText.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_info_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(editMode ? R.menu.menu_my_info_save : R.menu.menu_my_info_edit, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                editMode = true;
                supportInvalidateOptionsMenu();
                edit();
                break;
            case R.id.action_save:
                editMode = false;
                supportInvalidateOptionsMenu();
                updateUserInfo();
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewUserInfo() {
        Volley.newRequestQueue(this).add(
            new StringRequest(HttpUtil.VIEW_USER_INFO_URI,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ViewUserInfoResult result = new Gson().fromJson(response, ViewUserInfoResult.class);
                        if (result == null) {
                            return;
                        }
                        userInfoParam.setNickname(result.getNickname());
                        userInfoParam.setRealName(result.getRealName());
                        userInfoParam.setSex(result.isSex());
                        userInfoParam.setPhone(result.getPhone());
                        userInfoParam.setBindPhone(result.isBindPhone());
                        userInfoParam.setEmail(result.getEmail());
                        userInfoParam.setBindEmail(result.isBindEmail());
                        userInfoParam.setBirthyear(result.getBirthyear());
                        userInfoParam.setBirthmonth(result.getBirthmonth());
                        userInfoParam.setBirthday(result.getBirthday());
                        userInfoParam.setProvince(result.getProvince());
                        userInfoParam.setCity(result.getCity());
                        userInfoParam.setCounty(result.getCounty());
                        userInfoParam.setStreet(result.getStreet());
                        activityHolder.nicknameText.setText(result.getNickname());
                        activityHolder.changePasswordText.setText("");
                        activityHolder.bindPhoneText.setText(result.getPhone());
                        activityHolder.bindEmailText.setText(result.getEmail());
                        activityHolder.nameText.setText(result.getRealName());
                        activityHolder.birthdayText.setText(new StringBuilder()
                            .append(result.getBirthyear()).append("-")
                            .append(result.getBirthmonth()).append("-")
                            .append(result.getBirthday()));
                        activityHolder.birthdayText.setText(result.getCertificateNumber());
                        activityHolder.addressText.setText(new StringBuffer()
                            .append(result.getProvince()).append(result.getCity())
                            .append(result.getCounty()).append(result.getStreet()));
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getCause();
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
                    ViewUserInfoParam param = new ViewUserInfoParam();
                    param.setAccessToken(((UsedCarApplication) getApplication()).accessToken);
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
    }

    private void updateUserInfo() {
        Volley.newRequestQueue(this).add(
            new StringRequest(Method.POST, HttpUtil.UPDATE_USER_INFO_URI,
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("User-Agent", DeviceInfo.USER_AGENT);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return object2Map(userInfoParam);
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
    }

    private void edit() {
        activityHolder.nicknameText.setEnabled(true);
        activityHolder.changePasswordText.setEnabled(true);
        activityHolder.bindPhoneText.setEnabled(true);
        activityHolder.bindEmailText.setEnabled(true);
        activityHolder.nameText.setEnabled(true);
        activityHolder.birthdayText.setEnabled(true);
        activityHolder.identifyText.setEnabled(true);
        activityHolder.addressText.setEnabled(true);
    }

    private void save() {
        activityHolder.nicknameText.setEnabled(false);
        activityHolder.changePasswordText.setEnabled(false);
        activityHolder.bindPhoneText.setEnabled(false);
        activityHolder.bindEmailText.setEnabled(false);
        activityHolder.nameText.setEnabled(false);
        activityHolder.birthdayText.setEnabled(false);
        activityHolder.identifyText.setEnabled(false);
        activityHolder.addressText.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOGIN:
                    viewUserInfo();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_password:
                Intent changePassword = new Intent(this, ChangePasswordActivity.class);
                changePassword.putExtra("phone", activityHolder.bindPhoneText.getText());
                startActivity(changePassword);
                break;
            case R.id.info_bind_phone:
                startActivity(new Intent(this, BindPhoneActivity.class));
                break;
            case R.id.info_bind_email:
                startActivity(new Intent(this, BindEmailActivity.class));
                break;
        }
    }

    private static class InfoActivityHolder {

        public final ExtendedEditText nicknameText;

        public final TextView changePasswordText;

        public final TextView bindPhoneText;

        public final TextView bindEmailText;

        public final ExtendedEditText nameText;

        public final TextView birthdayText;

        public final ExtendedEditText identifyText;

        public final ExtendedEditText addressText;

        public InfoActivityHolder(Activity activity) {
            nicknameText = ViewFinder.findViewById(activity, R.id.info_nickname);
            changePasswordText = ViewFinder.findViewById(activity, R.id.info_password);
            bindPhoneText = ViewFinder.findViewById(activity, R.id.info_bind_phone);
            bindEmailText = ViewFinder.findViewById(activity, R.id.info_bind_email);
            nameText = ViewFinder.findViewById(activity, R.id.info_name);
            birthdayText = ViewFinder.findViewById(activity, R.id.info_birthday);
            identifyText = ViewFinder.findViewById(activity, R.id.info_id);
            addressText = ViewFinder.findViewById(activity, R.id.info_address);
        }
    }
}
