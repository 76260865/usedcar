package com.jason.usedcar.service;

import android.app.IntentService;
import android.content.Intent;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.util.CollectionUtils;
import java.util.List;

/**
 * @author t77yq @2014-11-01.
 */
public class InitService extends IntentService {

    public InitService() {
        super("init");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Province query = new Select().from(Province.class).executeSingle();
        if (query != null) {
            return;
        }

        List<Province> provinceList = new RestClient().getProvinces();
        if (CollectionUtils.isEmpty(provinceList)) {
            return;
        }

        ActiveAndroid.beginTransaction();
        for (Province province : provinceList) {
            province.save();

            List<City> cityList = new RestClient().getCities(province.getProvinceId());
            if (CollectionUtils.isEmpty(cityList)) {
                continue;
            }

            for (City city : cityList) {
                city.setProvinceId(province.getProvinceId());
                city.save();

                List<County> countyList = new RestClient().getCounties(city.getCityId());
                if (CollectionUtils.isEmpty(countyList)) {
                    continue;
                }

                for (County county : countyList) {
                    county.setCityId(city.getCityId());
                    county.save();
                }
            }
        }
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
    }
}
