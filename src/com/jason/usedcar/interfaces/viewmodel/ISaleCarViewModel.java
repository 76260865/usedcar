package com.jason.usedcar.interfaces.viewmodel;

/**
 * @author t77yq @2014-07-13.
 */
public interface ISaleCarViewModel {

    public String getTradingAddress();

    public void setTradingAddress(String tradingAddress);

    public String getTradingStreet();

    public void setTradingStreet(String tradingStreet);

    public String getCarType();

    public void setCarType(String carType);

    public String getBoughtTime();

    public void setBoughtTime(String time);

    public double getDistance();

    public void setDistance(double distance);

    public double getPrice();

    public void setPrice(double price);

    public boolean isBargain();

    public void setBargain(boolean bargain);

    public String getVin();

    public void setVin(String vin);
}
