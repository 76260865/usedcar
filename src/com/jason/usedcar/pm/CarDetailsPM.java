package com.jason.usedcar.pm;

import android.os.Build;
import android.view.View;
import com.jason.usedcar.Application;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.pm.view.CarDetailsView;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.Response;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class CarDetailsPM {

    private static final String[] METHODS = new String[] {
            "可议价",
            "一口价"
    };

    private int contentVisibility;

    private int progressVisibility;

    private int nothingVisibility;

    private String productId;

    private int type;

    private CarResponse carResponse = new CarResponse();

    private CarDetailsView carDetailsView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public CarDetailsPM(String productId, int type, CarDetailsView carDetailsView) {
        this.productId = productId;
        this.type = type;
        this.carDetailsView = carDetailsView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getCarName() {
        return carResponse.getProductName();
    }

    public String getSalePrice() {
        return String.format("%.2f万元", carResponse.getListPrice());
    }

    public String getPriceType() {
        return carResponse.getPriceType() == null || carResponse.getPriceType() == 0 ? "可议价" : "一口价";
    }

    public String getPayType() {
        Integer i = carResponse.getPaymentMethod();
        if (i == null) {
            i = 0;
        }
        return METHODS[i];
    }

    public String getPurchaseTime() {
        return carResponse.getPurchaseDate();
    }

    public String getMileage() {
        return String.format("%.1f万公里", carResponse.getOdometer());
    }

    public String getDailyPay() {
        return "";
    }

    public String getContactPhone() {
        return carResponse.getContactPhone();
    }

    public String getDealPlace() {
        return carResponse.getProvince() + carResponse.getCity()
                + carResponse.getCounty() + carResponse.getStreet();
    }

    public String getUrl() {
        return "http://112.124.62.114:80" + carResponse.getCarBasicInfoUrl();
    }

    public int getContentVisibility() {
        return contentVisibility;
    }

    public int getProgressVisibility() {
        return progressVisibility;
    }

    public int getNothingVisibility() {
        return nothingVisibility;
    }

    public void openCalculator() {
        carDetailsView.openCalculator(carResponse.getListPrice());
    }

    public void callCarOwner() {
        carDetailsView.callCarOwner(carResponse.getContactPhone());
    }

    public void addToCart() {
        if (!carDetailsView.isLogin()) {
            carDetailsView.login();
            return;
        }
        carDetailsView.before();
        new RestClient().addToCart(productId, carDetailsView.getAccessToken(),
                Build.SERIAL, new Callback<Response>() {
                    @Override
                    public void success(final Response response, final retrofit.client.Response response2) {
                        carDetailsView.after();
                        if (response != null) {
                            if (response.isExecutionResult()) {
                                MessageToast.makeText(carDetailsView.getContext(), "已添加到购物车").show();
                            } else {
                                MessageToast.makeText(carDetailsView.getContext(), response.getMessage()).show();
                            }
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        carDetailsView.after();
                    }
                });
    }

    public void loadData() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        presentationModelChangeSupport.firePropertyChange("contentVisibility");
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
        CarRequest carRequest = new CarRequest();
        carRequest.setProductId(productId);
        carRequest.setAccessToken(Application.sampleAccessToken);
        new RestClient().getUsedCar(carRequest, new Callback<CarResponse>() {
            @Override
            public void success(final CarResponse response, final retrofit.client.Response response2) {
                contentVisibility = View.VISIBLE;
                progressVisibility = View.GONE;
                if (response != null && response.isExecutionResult()) {
                    carResponse = response;
                    carResponse.setProductId(productId);
                    presentationModelChangeSupport.refreshPresentationModel();
                }
                carDetailsView.after();
            }

            @Override
            public void failure(final RetrofitError error) {
                carDetailsView.after();
                progressVisibility = View.GONE;
                nothingVisibility = View.VISIBLE;
                presentationModelChangeSupport.firePropertyChange("progressVisibility");
                presentationModelChangeSupport.firePropertyChange("nothingVisibility");
            }
        });
    }

    public String getProductId() {
        return productId;
    }

    public CarResponse getCarResponse() {
        return carResponse;
    }

    public int getBuyVisibility() {
        return type == Constants.CarDetailsType.BUY ? View.VISIBLE : View.GONE;
    }

    public boolean isOverviewMode() {
        return true;
    }

    public boolean isUseWideViewPort() {
        return true;
    }

}
