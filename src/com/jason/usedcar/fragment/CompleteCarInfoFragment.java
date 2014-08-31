package com.jason.usedcar.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.DatePicker;
import com.activeandroid.query.Select;
import com.jason.usedcar.Action;
import com.jason.usedcar.CarPickerActivity;
import com.jason.usedcar.DealPlaceActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.request.PublishUsedCarRequest;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * @author t77yq @14-7-1.
 */
public class CompleteCarInfoFragment extends BaseFragment implements Ui, DatePickerDialog.OnDateSetListener {

    private static final int PLACE = 10001;

    private static final int TYPE = 10002;

    @Required(order = 10, messageResId = R.string.err_car_info_address)
    private TextView tradeAddress;

    @Required(order = 20, messageResId = R.string.err_car_info_street)
    private TextView street;

    @Required(order = 30, messageResId = R.string.err_car_info_car_type)
    private TextView carType;

    @Required(order = 30, messageResId = R.string.err_car_info_time_bought)
    private TextView boughtTime;

    @Required(order = 40, messageResId = R.string.err_car_info_distance)
    private TextView distance;

    @Required(order = 50, messageResId = R.string.err_car_info_price)
    private TextView price;

    private CheckBox fixedPrice;

    private CheckBox payType;

    @Required(order = 60, messageResId = R.string.err_car_info_vin)
    private TextView vin;

    private PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_base_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        View v = getView();
        tradeAddress = getView(v, R.id.car_info_address);
        tradeAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), DealPlaceActivity.class), PLACE);
            }
        });
        street = getView(v, R.id.car_info_street);
        carType = getView(v, R.id.car_info_type);
        carType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), CarPickerActivity.class), TYPE);
            }
        });
        boughtTime = getView(v, R.id.car_info_time_bought);
        boughtTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                new com.jason.usedcar.DatePicker().setListener(CompleteCarInfoFragment.this).show(getFragmentManager(), "");
            }
        });
        distance = getView(v, R.id.car_info_distance);
        price = getView(v, R.id.car_info_price);
        fixedPrice = getView(v, R.id.price_check);
        payType = getView(v, R.id.type_check);
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
                getValidator().validate();
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
                        tradeAddress.setText(city.getCityName().concat(county.getCountyName()));
                    } else {
                        tradeAddress.setText(province.getProvinceName().concat(city.getCityName().concat(county.getCountyName())));
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
                    carType.setText(brandName.concat(seriesName).concat(model.getModelName()));
                    break;
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        publishUsedCarRequest.setStreet(String.valueOf(street.getText()));
        publishUsedCarRequest.setCarVin(String.valueOf(vin.getText()));
        publishUsedCarRequest.setPriceType(fixedPrice.isChecked() ? 0 : 1);
        publishUsedCarRequest.setPaymentMethod(payType.isChecked() ? 0 : 1);
        publishUsedCarRequest.setListPrice(Double.valueOf(String.valueOf(price.getText())));
        publishUsedCarRequest.setOdometer(Double.valueOf(String.valueOf(distance.getText())));
        publishUsedCarRequest.setCarVin(String.valueOf(vin.getText()));
        ((Action) getActivity()).action(this, publishUsedCarRequest);
    }

    @Override
    public void onValidationFailed(View view, Rule rule) {
        switch (view.getId()) {
            case R.id.car_info_address:
                // walk through
            case R.id.car_info_street:
                // walk through
            case R.id.car_info_type:
                // walk through
            case R.id.car_info_time_bought:
                // walk through
            case R.id.car_info_distance:
                // walk through
            case R.id.car_info_price:
                // walk through
            case R.id.car_info_vin:
                Toast.makeText(getActivity(), rule.getFailureMessage(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return this;
    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
        String date = year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日";
        boughtTime.setText(date);
        publishUsedCarRequest.setPurchaseDate(year + "-" + (monthOfYear+1));
    }
}
