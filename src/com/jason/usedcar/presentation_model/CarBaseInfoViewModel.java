package com.jason.usedcar.presentation_model;

import android.text.TextUtils;
import com.jason.usedcar.response.CarResponse3;
import org.robobinding.aspects.PresentationModel;

/**
 * @author t77yq @2014-10-14.
 */
@PresentationModel
public class CarBaseInfoViewModel {

    private CarResponse3 carResponse;

    public CarBaseInfoViewModel(CarResponse3 carResponse) {
        if (carResponse == null) {
            carResponse = new CarResponse3();
        }
        this.carResponse = carResponse;
    }

    public void setAddress(String address) {
        //
    }

    public String getAddress() {
        String province = carResponse.getProvince();
        String city = carResponse.getCity();
        String county = carResponse.getCounty();
        if (province == null) {
            return "";
        }
        return province + city + county;
    }

    public void setStreet(String street) {
        carResponse.setStreet(street);
    }

    public String getStreet() {
        return carResponse.getStreet();
    }

    public void setCar(String car) {
        //
    }

    public String getCar() {
        return carResponse.getBrandName();
    }

    public void setTime(String time) {
        carResponse.setPurchaseDate(time);
    }

    public String getTime() {
        return carResponse.getPurchaseDate();
    }

    public void setDistance(String distance) {
        int odometer = TextUtils.isEmpty(distance) ? 0 : Integer.valueOf(distance);
        carResponse.setOdometer(odometer);
    }

    public String getDistance() {
        Integer odometer = carResponse.getOdometer();
        if (odometer == null) {
            return null;
        }
        return String.valueOf(odometer);
    }

    public void setPrice(String price) {
        carResponse.setListPrice(price);
    }

    public String getPrice() {
        return carResponse.getListPrice();
    }

    public boolean isPriceCheck() {
        Integer priceType = carResponse.getPriceType();
        if (priceType == null) {
            return true;
        }
        return priceType == 1;
    }

    public boolean isTypeCheck() {
        Integer paymentMethod = carResponse.getPaymentMethod();
        if (paymentMethod == null) {
            return true;
        }
        return paymentMethod == 1;
    }

    public void setVin(String vin) {
        carResponse.setCarVin(vin);
    }

    public String getVin() {
        return carResponse.getCarVin();
    }
}
