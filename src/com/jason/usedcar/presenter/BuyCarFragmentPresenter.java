package com.jason.usedcar.presenter;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jason.usedcar.db.DBHelper;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.db.Brand;
import com.jason.usedcar.model.db.Province;
import com.jason.usedcar.model.param.PublishUsedCarParam;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.util.HttpUtil;

/**
 * Logic for call buttons.
 */
public class BuyCarFragmentPresenter extends BasePresenter<CallButtonUi> {

    public void login(final Context context) {
        Volley.newRequestQueue(context).add(
                createPostRequest(HttpUtil.LOGIN_URI, null, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getUi().login(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }));
    }

    public void clickItem(Context context, PublishUsedCarParam param) {
        if (param != null) {
            context.startActivity(new Intent());
        }
    }

    public void filterCar(Context context, String filter) {
        Toast.makeText(context, "filter " + filter, Toast.LENGTH_SHORT).show();
    }

    public interface CallButtonUi extends Ui {

        void setEnabled(boolean on);

        void login(String response);
    }

    // 在第一次启动程序后需要在后台检查是否有汽车和城市信息，如果没有需要再后台差入
    public void updateCarAndCityInfos(Context context, JSONObject object) {
        updateCarInfos(context, object);
        updateProvinceInfos(context, object);
    }

    private void updateCarInfos(Context context, JSONObject object) {
        ArrayList<Brand> brands = new ArrayList<Brand>();
        Iterator<String> iterator = object.keys();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String name = object.getString(key);
                Brand brand = new Brand();
                brand.setBrandId(Integer.valueOf(key));
                brand.setBrandName(name);
                brands.add(brand);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper.getInstance(context).insertBands(brands);
    }

    private void updateProvinceInfos(Context context, JSONObject object) {
        ArrayList<Province> provinces = new ArrayList<Province>();
        Iterator<String> iterator = object.keys();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String name = object.getString(key);
                Province province = new Province();
                province.setProvinceId(Integer.valueOf(key));
                province.setProvinceName(name);
                provinces.add(province);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper.getInstance(context).insertProvinces(provinces);
    }
}
