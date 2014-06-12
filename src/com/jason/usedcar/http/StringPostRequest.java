package com.jason.usedcar.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author t77yq @2014.06.12
 */

public class StringPostRequest extends StringRequest {

    public StringPostRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() {
        return object2Map(data());
    }

    protected Object data() {
        return null;
    }

    private Map<String, String> object2Map(Object obj) {
        return object2Map(obj, false);
    }

    private Map<String, String> object2Map(Object obj, boolean nullable) {
        Map<String, String> result = new HashMap<String, String>();
        java.lang.reflect.Method[] methods = obj.getClass().getMethods();
        try {
            for (java.lang.reflect.Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
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
}
