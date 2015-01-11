package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.activeandroid.query.Select;
import com.jason.usedcar.DealPlaceActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.pm.RegisterResellerPM;
import com.jason.usedcar.pm.view.ViewRegisterReseller;

public class RegisterResellerFragment extends RegisterFragment implements ViewRegisterReseller {

    private static final int PICK = 10001;

    private RegisterResellerPM pm;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((RadioGroup) findViewById(R.id.register_reseller_type)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                if (checkedId == R.id.register_used_car_company) {
                    pm.s1etResellerType(1);
                } else if (checkedId == R.id.register_4s_shop) {
                    pm.s1etResellerType(2);
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_reseller;
    }

    @Override
    protected Object presentationModel() {
        return pm = new RegisterResellerPM(this);
    }

    @Override
    public void pickResellerAddress() {
        Intent pick = new Intent(getContext(), DealPlaceActivity.class);
        pick.putExtra("isResellerRegister", true);
        startActivityForResult(pick, PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK) {
                String area = data.getStringExtra("data");
                String[] areaList = area.split(";");
                Province province = new Select().from(Province.class).where("remote_id = ?", areaList[0]).executeSingle();
                City city = new Select().from(City.class).where("remote_id = ?", areaList[1]).executeSingle();
                County county = new Select().from(County.class).where("remote_id = ?", areaList[2]).executeSingle();
                String provinceName = province.getProvinceName();
                String cityName = city.getCityName();
                String countyName = county.getCountyName();
                if (provinceName == null) {
                    provinceName = "";
                }
                if (cityName == null) {
                    cityName = "";
                }
                if (countyName == null) {
                    countyName = "";
                }
                if (provinceName.equals(cityName)) {
                    pm.setResellerAddress(cityName + countyName);
                } else {
                    pm.setResellerAddress(provinceName + cityName + countyName);
                }
            }
        }
    }
}
