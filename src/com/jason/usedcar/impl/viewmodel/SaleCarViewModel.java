package com.jason.usedcar.impl.viewmodel;

import com.jason.usedcar.interfaces.viewmodel.ISaleCarViewModel;
import com.jason.usedcar.request.PublishUsedCarRequest;

/**
 * @author t77yq @2014-07-13.
 */
public class SaleCarViewModel implements ISaleCarViewModel {

    private PublishUsedCarRequest publishUsedCarParam = new PublishUsedCarRequest();

    @Override
    public String getTradingAddress() {
        return null;
    }

    @Override
    public void setTradingAddress(String tradingAddress) {
    }

    @Override
    public String getTradingStreet() {
        return publishUsedCarParam.getStreet();
    }

    @Override
    public void setTradingStreet(String tradingStreet) {
        publishUsedCarParam.setStreet(tradingStreet);
    }

    @Override
    public String getCarType() {
        return null;
    }

    @Override
    public void setCarType(String carType) {

    }

    @Override
    public String getBoughtTime() {
        return publishUsedCarParam.getPurchaseDate();
    }

    @Override
    public void setBoughtTime(String time) {
        publishUsedCarParam.setPurchaseDate(time);
    }

    @Override
    public double getDistance() {
        return publishUsedCarParam.getOdometer();
    }

    @Override
    public void setDistance(double distance) {
        publishUsedCarParam.setOdometer(distance);
    }

    @Override
    public double getPrice() {
        return publishUsedCarParam.getListPrice();
    }

    @Override
    public void setPrice(double price) {
        publishUsedCarParam.setListPrice(price);
    }

    @Override
    public boolean isBargain() {
        return publishUsedCarParam.getPriceType() != 0;
    }

    @Override
    public void setBargain(boolean bargain) {
        publishUsedCarParam.setPriceType(bargain ? 0 : 1);
    }

    @Override
    public String getVin() {
        return publishUsedCarParam.getCarVin();
    }

    @Override
    public void setVin(String vin) {
        publishUsedCarParam.setCarVin(vin);
    }
}
