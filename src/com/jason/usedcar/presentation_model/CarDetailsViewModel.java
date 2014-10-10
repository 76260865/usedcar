package com.jason.usedcar.presentation_model;

import android.os.Build;
import android.view.View;
import android.widget.Toast;
import com.jason.usedcar.Application;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.CarResponse2;
import com.jason.usedcar.response.CarResponse3;
import com.jason.usedcar.response.Response;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class CarDetailsViewModel {

    private static final String[] METHODS = new String[] {
            "贷款或全款",
            "全款购买"
    };

    private int contentVisibility;

    private int progressVisibility;

    private int nothingVisibility;

    private Product product;

    private CarResponse3 carResponse = new CarResponse3();

    private CarDetailsView carDetailsView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public CarDetailsViewModel(Product product, CarDetailsView carDetailsView) {
        this.product = product;
        this.carDetailsView = carDetailsView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getCarName() {
        return carResponse.getProductName();
    }

    public String getSalePrice() {
        return carResponse.getListPrice();
    }

    public String getPriceType() {
        return carResponse.getPriceType() == null || carResponse.getPriceType() == 0 ? "一口价" : "可议价";
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
        return String.format("%1$d万公里", carResponse.getOdometer());
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
        carDetailsView.openCalculator(Double.parseDouble(carResponse.getListPrice()));
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
        new RestClient().addToCart(product.getProductId(), carDetailsView.getAccessToken(),
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
        carRequest.setProductId(product.getProductId());
        carRequest.setAccessToken(Application.sampleAccessToken);
        new RestClient().getUsedCar(carRequest, new Callback<CarResponse3>() {
            @Override
            public void success(final CarResponse3 response, final retrofit.client.Response response2) {
                contentVisibility = View.VISIBLE;
                progressVisibility = View.GONE;
                if (response != null && response.isExecutionResult()) {
                    carResponse = response;
                    presentationModelChangeSupport.refreshPresentationModel();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                progressVisibility = View.GONE;
                nothingVisibility = View.VISIBLE;
                presentationModelChangeSupport.firePropertyChange("progressVisibility");
                presentationModelChangeSupport.firePropertyChange("nothingVisibility");
            }
        });
    }

    public Product getProduct() {
        return product;
    }
}
