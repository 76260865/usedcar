package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.UserInfoResponse;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class InfoViewModel extends ViewModelBase {

    private InfoView infoView;

    private boolean editMode;

    private UserInfoResponse userInfoResponse = new UserInfoResponse();

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public InfoViewModel(final InfoView infoView) {
        this.infoView = infoView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        presentationModelChangeSupport.refreshPresentationModel();
    }

    public boolean getMale() {
        return userInfoResponse.isSex();
    }

    public boolean getFemale() {
        return !getMale();
    }

    public String getNickname() {
        return userInfoResponse.getNickname();
    }

    public String getPhoneNumber() {
        return userInfoResponse.getPhone();
    }

    public String getEmail() {
        return userInfoResponse.getEmail();
    }

    public String getRealName() {
        return userInfoResponse.getRealName();
    }

    public String getBirthday() {
        return userInfoResponse.getBirthyear() + userInfoResponse.getBirthmonth() + userInfoResponse.getBirthday();
    }

    public String getCertificateNumber() {
        return userInfoResponse.getCertificateNumber();
    }

    public String getAddress() {
        return userInfoResponse.getProvince() + userInfoResponse.getCity() + userInfoResponse.getCounty() + userInfoResponse.getStreet();
    }

    public void changePassword() {
        infoView.changePassword(userInfoResponse.getPhone());
    }

    public void changePhone() {
        infoView.changePhone("");
    }

    public void loadData() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        presentationModelChangeSupport.firePropertyChange("contentVisibility");
        presentationModelChangeSupport.firePropertyChange("progressVisibility");
        presentationModelChangeSupport.firePropertyChange("nothingVisibility");
        Request viewUserInfoRequest = new Request();
        viewUserInfoRequest.setAccessToken(infoView.getAccessToken());
        new RestClient().viewUserInfo(viewUserInfoRequest, new Callback<UserInfoResponse>() {
            @Override
            public void success(final UserInfoResponse response, final retrofit.client.Response response2) {
                contentVisibility = View.VISIBLE;
                progressVisibility = View.GONE;
                if (response.isExecutionResult()) {
                    userInfoResponse = response;
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
}
