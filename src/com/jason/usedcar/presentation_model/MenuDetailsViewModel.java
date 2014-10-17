package com.jason.usedcar.presentation_model;

import com.jason.usedcar.MessageToast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.response.Response;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-10-03.
 */
@PresentationModel
public class MenuDetailsViewModel {

    private boolean added;

    private int type;

    private CarDetailsView carDetailsView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public MenuDetailsViewModel(final int type, final CarDetailsView carDetailsView) {
        this.type = type;
        this.carDetailsView = carDetailsView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public void setAdded(boolean added) {
        this.added = added;
        presentationModelChangeSupport.refreshPresentationModel();
    }

    public boolean getAdded() {
        return type == Constants.CarDetailsType.BUY && added;
    }

    public boolean getNotAdded() {
        return type == Constants.CarDetailsType.BUY && !added;
    }

    public boolean getEdit() {
        return type == Constants.CarDetailsType.SELL;
    }

    public void addToFavorite() {
        if (!carDetailsView.isLogin()) {
            carDetailsView.login();
            return;
        }
        carDetailsView.before();
        new RestClient().addToFavorite(carDetailsView.getProductId(), carDetailsView.getAccessToken(),
                android.os.Build.SERIAL, new Callback<Response>() {
                    @Override
                    public void success(Response response, retrofit.client.Response response2) {
                        carDetailsView.after();
                        if (response != null) {
                            if (response.isExecutionResult()) {
                                setAdded(!getAdded());
                                MessageToast.makeText(carDetailsView.getContext(), "收藏完毕").show();
                            } else {
                                setAdded(!getAdded());
                                MessageToast.makeText(carDetailsView.getContext(), response.getMessage()).show();
                            }
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        carDetailsView.after();
                        MessageToast.makeText(carDetailsView.getContext(), error.getMessage()).show();
                    }
                });
    }

    public void cancelFavorite() {
        if (!carDetailsView.isLogin()) {
            carDetailsView.login();
            return;
        }
        carDetailsView.before();
        new RestClient().deleteFavorite(carDetailsView.getProductId(), carDetailsView.getAccessToken(),
                android.os.Build.SERIAL, new Callback<Response>() {
                    @Override
                    public void success(Response response, retrofit.client.Response response2) {
                        carDetailsView.after();
                        if (response != null) {
                            if (response.isExecutionResult()) {
                                setAdded(!getAdded());
                                MessageToast.makeText(carDetailsView.getContext(), "已取消").show();
                            } else {
                                MessageToast.makeText(carDetailsView.getContext(), response.getMessage()).show();
                            }
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        carDetailsView.after();
                        MessageToast.makeText(carDetailsView.getContext(), error.getMessage()).show();
                    }
                });
    }

    public void editCar() {
        carDetailsView.editCar();
    }
}
