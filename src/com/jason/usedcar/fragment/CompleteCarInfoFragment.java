package com.jason.usedcar.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import com.activeandroid.query.Select;
import com.jason.usedcar.*;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.presentation_model.CarBaseInfoViewModel;
import com.jason.usedcar.request.PublishUsedCarRequest;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.mobsandgeeks.saripaar.Rule;

/**
 * @author t77yq @14-7-1.
 */
public class CompleteCarInfoFragment extends AbsFragment implements DatePickerDialog.OnDateSetListener {

    private static final int PLACE = 10001;

    private static final int TYPE = 10002;

    private PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();

    @Override
    protected int layoutId() {
        return R.layout.fragment_car_base_info;
    }

    @Override
    protected Object presentationModel() {
        return new CarBaseInfoViewModel(((SellCarActivity) getActivity()).carResponse);
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
                //getValidator().validate();
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
                    publishUsedCarRequest.setProvinceId(28);
                    publishUsedCarRequest.setCityId(225);
                    publishUsedCarRequest.setCountyId(1875);
                    if (province.getProvinceName().equalsIgnoreCase(city.getCityName())) {
                        //tradeAddress.setText(city.getCityName().concat(county.getCountyName()));
                    } else {
                        //tradeAddress.setText(province.getProvinceName().concat(city.getCityName().concat(county.getCountyName())));
                    }
                    break;
                case TYPE:
                    String type = data.getStringExtra("data");
                    String[] typeList = type.split(";");
                    Brand brand = new Select().from(Brand.class).where("remote_id = ?", typeList[0]).executeSingle();
                    Series series = new Select().from(Series.class).where("remote_id = ?", typeList[1]).executeSingle();
                    CarModel model = new Select().from(CarModel.class).where("remote_id = ?", typeList[2]).executeSingle();
                    publishUsedCarRequest.setBrandId(brand.getBrandId());
                    publishUsedCarRequest.setSeriesId(series.getSeriesId());
                    publishUsedCarRequest.setModelId(model.getModelId());
                    String brandName =brand.getName();
                    String seriesName = series.getSeriesName().replace(brandName, "");
                    //carType.setText(brandName.concat(seriesName).concat(model.getModelName()));
                    break;
            }
        }
    }

    public void onValidationFailed(View view, Rule rule) {
        switch (view.getId()) {
            case R.id.car_info_address:
            case R.id.car_info_street:
            case R.id.car_info_type:
            case R.id.car_info_time_bought:
            case R.id.car_info_distance:
            case R.id.car_info_price:
            case R.id.car_info_vin:
                MessageToast.makeText(getActivity(), rule.getFailureMessage()).show();
                break;
        }
    }

    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
        //boughtTime.setText(String.format("%d年%02d月", year, monthOfYear + 1));
        publishUsedCarRequest.setPurchaseDate(String.format("%d-%02d", year, monthOfYear + 1));
    }

    protected static <V extends View> V getView(View view, int id) {
        return (V) view.findViewById(id);
    }
}
