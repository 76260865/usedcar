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
import android.widget.TextView;
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
import com.jason.usedcar.response.CarResponse3;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * @author t77yq @14-7-1.
 */
public class CompleteCarInfoFragment extends AbsFragment implements DatePickerDialog.OnDateSetListener, Validator.ValidationListener {

    private static final int PLACE = 10001;

    private static final int TYPE = 10002;

    @Required(order = 10, messageResId = R.string.err_car_info_address)
    private TextView tradeAddress;

    @Required(order = 20, messageResId = R.string.err_car_info_street)
    private TextView street;

    @Required(order = 30, messageResId = R.string.err_car_info_car_type)
    private TextView carType;

    @Required(order = 40, messageResId = R.string.err_car_info_time_bought)
    private TextView boughtTime;

    @Required(order = 50, messageResId = R.string.err_car_info_distance)
    private TextView distance;

    @Required(order = 60, messageResId = R.string.err_car_info_price)
    private TextView price;

    @Required(order = 70, messageResId = R.string.err_car_info_vin)
    private TextView vin;

    protected Validator validator = new Validator(this);

    private PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();

    private CarBaseInfoViewModel carBaseInfoViewModel = new CarBaseInfoViewModel(publishUsedCarRequest);

    @Override
    protected int layoutId() {
        return R.layout.fragment_car_base_info;
    }

    @Override
    protected Object presentationModel() {
        CarResponse3 carResponse = ((SellCarActivity) getActivity()).carResponse;
        if (carResponse != null) {
            Province province = new Select().from(Province.class).where("name = ?", carResponse.getProvince()).executeSingle();
            City city = new Select().from(City.class).where("name = ?", carResponse.getCity()).executeSingle();
            County county = new Select().from(County.class).where("name = ?", carResponse.getCounty()).executeSingle();
            publishUsedCarRequest.setProvinceId(province.getProvinceId());
            publishUsedCarRequest.setCityId(city.getCityId());
            publishUsedCarRequest.setCountyId(county.getCityId());
            Brand brand = new Select().from(Brand.class).where("name = ?", carResponse.getBrandName()).executeSingle();
            Series series = new Select().from(Series.class).where("name = ?", carResponse.getSeriesName()).executeSingle();
            CarModel model = new Select().from(CarModel.class).where("name = ?", carResponse.getModelDisplayName()).executeSingle();
            publishUsedCarRequest.setBrandId(brand.getBrandId());
            publishUsedCarRequest.setSeriesId(series.getSeriesId());
            publishUsedCarRequest.setModelId(model.getModelId());

            carBaseInfoViewModel.setProvince(carResponse.getProvince());
            carBaseInfoViewModel.setCity(carResponse.getCity());
            carBaseInfoViewModel.setCounty(carResponse.getCounty());
            carBaseInfoViewModel.setBrand(carResponse.getBrandName());
            carBaseInfoViewModel.setSeries(carResponse.getSeriesName());
            carBaseInfoViewModel.setModel(carResponse.getModelDisplayName());
        }
        return carBaseInfoViewModel;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        validator.setValidationListener(this);
        View v = getView();
        tradeAddress = getView(v, R.id.car_info_address);
        street = getView(v, R.id.car_info_street);
        tradeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), DealPlaceActivity.class), PLACE);
            }
        });
        carType = getView(v, R.id.car_info_type);
        carType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), CarPickerActivity.class), TYPE);
            }
        });
        boughtTime = getView(v, R.id.car_info_time_bought);
        boughtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new com.jason.usedcar.DatePicker().setListener(CompleteCarInfoFragment.this).show(getFragmentManager(), "");
            }
        });
        distance = getView(v, R.id.car_info_distance);
        price = getView(v, R.id.car_info_price);
        vin = getView(v, R.id.car_info_vin);
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
                validator.validate();
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
                    carBaseInfoViewModel.setProvince(province.getProvinceName());
                    carBaseInfoViewModel.setCity(city.getCityName());
                    carBaseInfoViewModel.setCounty(county.getCountyName());
                    carBaseInfoViewModel.refreshAddress();
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
                    carBaseInfoViewModel.setBrand(brand.getBrandName());
                    carBaseInfoViewModel.setSeries(series.getSeriesName());
                    carBaseInfoViewModel.setModel(model.getModelName());
                    carBaseInfoViewModel.refreshCar();
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
        String date = String.format("%d-%02d", year, monthOfYear + 1);
        publishUsedCarRequest.setPurchaseDate(date);
        carBaseInfoViewModel.setTime(date);
    }

    protected static <V extends View> V getView(View view, int id) {
        return (V) view.findViewById(id);
    }

    @Override
    public void onValidationSucceeded() {
        ((Action) getActivity()).action(this, publishUsedCarRequest);
    }
}
