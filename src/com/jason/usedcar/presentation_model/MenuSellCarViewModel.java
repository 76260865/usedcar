package com.jason.usedcar.presentation_model;

import android.widget.Toast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.UserAuthInfo;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-10-03.
 */
@PresentationModel
public class MenuSellCarViewModel {

    private ViewSellingCarView view;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public MenuSellCarViewModel(ViewSellingCarView view) {
        this.view = view;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public boolean isLogin() {
        return view.isLogin();
    }

    public void addNewCar() {
        if (!view.isLogin()) {
            view.login();
            return;
        }
        new RestClient().getUserAuthInfo(view.getAccessToken(), new Request().getDeviceId(),
                new Callback<UserAuthInfo>() {
                    @Override
                    public void success(final UserAuthInfo info, final Response response) {
                        if (info != null && info.isExecutionResult()) {
                            view.addNewCar();
                            return;
                        }
                        Toast.makeText(view.getContext(), "请先完成用户认证", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        Toast.makeText(view.getContext(), "请先完成用户认证", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void editCar() {
        //
    }

    public void refresh() {
        presentationModelChangeSupport.refreshPresentationModel();
    }
}
