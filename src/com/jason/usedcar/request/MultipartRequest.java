package com.jason.usedcar.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

public class MultipartRequest extends Request<String> {
    private static final String TAG = "MultipartRequest";

    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;
    private static final String FILE_PART_NAME = "image";

    private final Response.Listener<String> mListener;
    private final byte[] mFilePart;
    private final Map<String, String> mStringPart;

    public MultipartRequest(String url, Response.ErrorListener errorListener,
            Response.Listener<String> listener, byte[] file, Map<String, String> mStringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        this.mStringPart = mStringPart;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        buildMultipartEntity();
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        entity.addPart(FILE_PART_NAME,
                new ByteArrayBody(mFilePart, ContentType.create("image/jpeg"), "bottombg.jpg"));
        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getBodyContentType() {
        // return "image/jpeg";
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            // 将data字节数据转化为String对象
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Log.d(TAG, "parseNetworkResponse:" + parsed);
        // 返回Response对象，其中该对象包含访问相关数据
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        // return
        // Response.success(parseNetworkResponse(response).toString(),
        // getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}