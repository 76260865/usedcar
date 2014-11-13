package com.jason.usedcar.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import com.activeandroid.query.Select;
import com.jason.usedcar.*;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.pm.CarBaseInfoPM;
import com.jason.usedcar.pm.view.CarBaseInfoView;

/**
 * @author t77yq @14-7-1.
 */
public class CompleteCarInfoFragment extends AbsFragment
        implements DatePickerDialog.OnDateSetListener, CarBaseInfoView {

    private static final int PLACE = 10001;

    private static final int TYPE = 10002;

    private CarBaseInfoPM carBaseInfoViewModel;

    @Override
    protected int layoutId() {
        return R.layout.fragment_car_base_info;
    }

    @Override
    protected Object presentationModel() {
        carBaseInfoViewModel = new CarBaseInfoPM(this);
        return carBaseInfoViewModel;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_framgent_sell_car_1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                if (carBaseInfoViewModel.validate()) {
                    ((Action) getActivity()).action(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PLACE:
                    String area = data.getStringExtra("data");
                    String[] areaList = area.split(";");
                    Province province = new Select().from(Province.class).where("remote_id = ?", areaList[0]).executeSingle();
                    City city = new Select().from(City.class).where("remote_id = ?", areaList[1]).executeSingle();
                    County county = new Select().from(County.class).where("remote_id = ?", areaList[2]).executeSingle();
                    carBaseInfoViewModel.setProvince(province);
                    carBaseInfoViewModel.setCity(city);
                    carBaseInfoViewModel.setCounty(county);
                    carBaseInfoViewModel.refreshAddress();
                    break;
                case TYPE:
                    String type = data.getStringExtra("data");
                    String[] typeList = type.split(";");
                    Brand brand = new Select().from(Brand.class).where("remote_id = ?", typeList[0]).executeSingle();
                    Series series = new Select().from(Series.class).where("remote_id = ?", typeList[1]).executeSingle();
                    CarModel model = new Select().from(CarModel.class).where("remote_id = ?", typeList[2]).executeSingle();
                    carBaseInfoViewModel.setBrand(brand);
                    carBaseInfoViewModel.setSeries(series);
                    carBaseInfoViewModel.setModel(model);
                    carBaseInfoViewModel.refreshCar();
                    break;
            }
        }
    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
        //boughtTime.setText(String.format("%d年%02d月", year, monthOfYear + 1));
        carBaseInfoViewModel.setTime(String.format("%4d-%02d", year, monthOfYear + 1));
    }

    @Override
    public void message(final int messageId) {
        MessageToast.makeText(getActivity(), messageId).show();
    }

    @Override
    public void message(final String message) {
        MessageToast.makeText(getActivity(), message).show();
    }

    @Override
    public void pickPlace() {
        Intent pickPlace = new Intent(getActivity(), DealPlaceActivity.class);
        pickPlace.putExtra("isSellingCar", true);
        startActivityForResult(pickPlace, PLACE);
    }

    @Override
    public void pickDate() {
        new com.jason.usedcar.DatePicker().setListener(CompleteCarInfoFragment.this).show(getFragmentManager(), "");
    }

    @Override
    public void pickCar() {
        startActivityForResult(new Intent(getActivity(), CarPickerActivity.class), TYPE);
    }
}
