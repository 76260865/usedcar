package com.jason.usedcar.pm;

import android.text.TextUtils;
import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.CarInfo;
import com.jason.usedcar.R;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.pm.view.CarBaseInfoView;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-10-14.
 */
@PresentationModel
public class CarBaseInfoPM {

    private CarBaseInfoView carBaseInfoView;

    private CarInfo carInfo;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public CarBaseInfoPM(CarBaseInfoView carBaseInfoView) {
        this.carInfo = CacheUtils.getCarInfo();
        this.carBaseInfoView = carBaseInfoView;
        presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public void setProvince(Province province) {
        carInfo.setProvince(province);
    }

    public void setCity(City city) {
        carInfo.setCity(city);
    }

    public void setCounty(County county) {
        carInfo.setCounty(county);
    }

    public void refreshAddress() {
        presentationModelChangeSupport.firePropertyChange("address");
    }

    public String getAddress() {
        if (carInfo.getProvinceName().equals(carInfo.getCityName())) {
            return carInfo.getProvinceName() + carInfo.getCountyName();
        }
        return carInfo.getProvinceName() + carInfo.getCityName() + carInfo.getCountyName();
    }

    public void setStreet(String street) {
        carInfo.street = street;
    }

    public String getStreet() {
        return carInfo.street;
    }

    public void setBrand(Brand brand) {
        carInfo.setBrand(brand);
    }

    public void setSeries(Series series) {
        carInfo.setSeries(series);
    }

    public void setModel(CarModel model) {
        carInfo.setModel(model);
    }

    public void refreshCar() {
        presentationModelChangeSupport.firePropertyChange("car");
    }

    public String getCar() {
        return carInfo.getBrandName() + carInfo.getSeriesName() + carInfo.getModelName();
    }

    public void setTime(String time) {
        carInfo.purchaseDate = time;
        presentationModelChangeSupport.firePropertyChange("time");
    }

    public String getTime() {
        return carInfo.purchaseDate;
    }

    public void setDistance(String distance) {
        distance = TextUtils.isEmpty(distance) ? "0" : distance;
        carInfo.odometer = Double.parseDouble(distance);
    }

    public String getDistance() {
        return String.valueOf(carInfo.odometer);
    }

    public void setPrice(String price) {
        if (TextUtils.isEmpty(price)) {
            return;
        }
        carInfo.listPrice = Double.valueOf(price);
    }

    public String getPrice() {
        return String.valueOf(carInfo.listPrice);
    }

    public boolean isPriceCheck() {
        return carInfo.priceType == 1;
    }

    public boolean isTypeCheck() {
        return carInfo.paymentMethod == 1;
    }

    public void setVin(String vin) {
        carInfo.carVin = vin;
    }

    public String getVin() {
        return carInfo.carVin;
    }

    public void pickAddress() {
        carBaseInfoView.pickPlace();
    }

    public void pickCar() {
        carBaseInfoView.pickCar();
    }

    public void pickDate() {
        carBaseInfoView.pickDate();
    }

    public boolean validate() {
        if (TextUtils.isEmpty(getAddress())) {
            carBaseInfoView.message(R.string.err_car_info_address);
            return false;
        }
//        if (TextUtils.isEmpty(getStreet())) {
//            carBaseInfoView.message(R.string.err_car_info_street);
//            return false;
//        }
        if (TextUtils.isEmpty(getCar())) {
            carBaseInfoView.message(R.string.err_car_info_car_type);
            return false;
        }
        if (TextUtils.isEmpty(getTime())) {
            carBaseInfoView.message(R.string.err_car_info_time_bought);
            return false;
        }
        if (TextUtils.isEmpty(getDistance())) {
            carBaseInfoView.message(R.string.err_car_info_distance);
            return false;
        }
        else if (Double.valueOf(getDistance()) > 100) {
            carBaseInfoView.message("亲，您的车还跑得动？请输入合理的行车里程");
            return false;
        }
        if (TextUtils.isEmpty(getPrice())) {
            carBaseInfoView.message(R.string.err_car_info_price);
            return false;
        }
        else if (Double.valueOf(getPrice()) > 1000) {
            carBaseInfoView.message("亲，您的车真这么贵？请输入合理的价格");
            return false;
        }
        if (TextUtils.isEmpty(getVin())) {
            carBaseInfoView.message(R.string.err_car_info_vin);
            return false;
        }
        else if (getVin().length() != 17) {
            carBaseInfoView.message("亲，车辆识别码不足17位，请重新输入");
            return false;
        }
        return true;
    }

}
