package com.jason.usedcar.presenter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.jason.usedcar.config.DeviceInfo;
import com.jason.usedcar.interfaces.Ui;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author t77yq @2014.06.13
 */
public class BasePresenter<U extends Ui> extends Presenter<U> {

    protected StringRequest createPostRequest(String url, final Object data,
        Listener<String> listener, ErrorListener errorListener) {
        return new StringRequest(Method.POST, url, listener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("User-Agent", DeviceInfo.USER_AGENT);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return object2Map(data);
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
    }
}
