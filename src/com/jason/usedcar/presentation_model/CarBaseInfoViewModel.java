package com.jason.usedcar.presentation_model;

import android.text.TextUtils;
import com.jason.usedcar.request.PublishUsedCarRequest;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-10-14.
 */
@PresentationModel
public class CarBaseInfoViewModel {

    private PublishUsedCarRequest publishUsedCarRequest;

    private String province;

    private String city;

    private String county;

    private String brand;

    private String series;

    private String model;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public CarBaseInfoViewModel(PublishUsedCarRequest publishUsedCarRequest) {
        this.publishUsedCarRequest = publishUsedCarRequest;
        presentationModelChangeSupport = new PresentationModelChangeSupport(this);

    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String cityId) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void refreshAddress() {
        presentationModelChangeSupport.firePropertyChange("address");
    }

    public String getAddress() {
        province = province == null ? "" : province;
        city = city == null ? "" : city;
        county = county == null ? "" : county;
        if (province.equals(city)) {
            return city + county;
        }
        return province + city + county;
    }

    public void setStreet(String street) {
        publishUsedCarRequest.setStreet(street);
    }

    public String getStreet() {
        return publishUsedCarRequest.getStreet();
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void refreshCar() {
        presentationModelChangeSupport.firePropertyChange("car");
    }

    public String getCar() {
        brand = brand == null ? "" : brand;
        series = series == null ? "" : series;
        model = model == null ? "" : model;
        return brand + series + model;
    }

    public void setTime(String time) {
        publishUsedCarRequest.setPurchaseDate(time);
        presentationModelChangeSupport.firePropertyChange("time");
    }

    public String getTime() {
        return publishUsedCarRequest.getPurchaseDate();
    }

    public void setDistance(String distance) {
        int odometer = TextUtils.isEmpty(distance) ? 0 : Integer.valueOf(distance);
        publishUsedCarRequest.setOdometer((double) odometer);
    }

    public String getDistance() {
        Double odometer = publishUsedCarRequest.getOdometer();
        if (odometer == null) {
            return null;
        }
        return String.valueOf(odometer);
    }

    public void setPrice(String price) {
        if (TextUtils.isEmpty(price)) {
            return;
        }
        publishUsedCarRequest.setListPrice(Double.valueOf(price));
    }

    public String getPrice() {
        Double price = publishUsedCarRequest.getListPrice();
        if (price == null) {
            return "";
        }
        return String.valueOf(price);
    }

    public boolean isPriceCheck() {
        Integer priceType = publishUsedCarRequest.getPriceType();
        if (priceType == null) {
            publishUsedCarRequest.setPriceType(1);
            return true;
        }
        return priceType == 1;
    }

    public boolean isTypeCheck() {
        Integer paymentMethod = publishUsedCarRequest.getPaymentMethod();
        if (paymentMethod == null) {
            publishUsedCarRequest.setPaymentMethod(1);
            return true;
        }
        return paymentMethod == 1;
    }

    public void setVin(String vin) {
        publishUsedCarRequest.setCarVin(vin);
    }

    public String getVin() {
        return publishUsedCarRequest.getCarVin();
    }
}
