package com.jason.usedcar;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TestFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RequestQueue queue = Volley.newRequestQueue(getActivity());
		String url = "http://112.124.62.114:8080/usedcar/login.json";
		final JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest.put("phoneOrEmail", "d");
			jsonRequest.put("password", "d");
			jsonRequest.put("rememberUser", "1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringRequest postRequest = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Toast.makeText(getActivity(), response.toString(),
								Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("phoneOrEmail", "d");
				params.put("password", "d");
				params.put("rememberUser", "1");
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
		return inflater.inflate(R.layout.fragment_1, null);
	}
}
