package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.request.UserInfoRequest;
import com.jason.usedcar.response.Response;
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
        return userInfoResponse.getSex() == 0;
    }

    public boolean getFemale() {
        return !getMale();
    }

    public void setNickname(String nickname) {
        userInfoResponse.setNickname(nickname);
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

    public void setBirthday(String birthday) {
        userInfoResponse.setBirthdate(birthday);
        presentationModelChangeSupport.firePropertyChange("birthday");
    }

    public String getBirthday() {
        return userInfoResponse.getBirthdate();
    }

    public void setProvince(String province) {
        userInfoResponse.setProvince(province);
    }

    public String getProvince() {
        return userInfoResponse.getProvince();
    }

    public void setCity(String city) {
        userInfoResponse.setCity(city);
    }

    public String getCity() {
        return userInfoResponse.getCity();
    }

    public void setCounty(String county) {
        userInfoResponse.setCounty(county);
    }

    public String getCounty() {
        return userInfoResponse.getCounty();
    }

    public void setStreet(String street) {
        userInfoResponse.setStreet(street);
    }

    public String getStreet() {
        return userInfoResponse.getStreet();
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

    public void pickTime() {
        infoView.pickTime();
    }

    public void save() {
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setAccessToken(infoView.getAccessToken());
        userInfoRequest.setNickname(getNickname());
        userInfoRequest.setBirthday(getBirthday());
        userInfoRequest.setSex(userInfoResponse.getSex());
        userInfoRequest.setCity(getCity());
        userInfoRequest.setProvince(getProvince());
        userInfoRequest.setCounty(getCounty());
        userInfoRequest.setStreet(getStreet());
        infoView.start();
        new RestClient().updateUserInfo(userInfoRequest, new Callback<Response>() {
            @Override
            public void success(final Response response, final retrofit.client.Response response2) {
                infoView.stop();
                if (response != null) {
                    infoView.tell(response.getMessage());
                } else {
                    infoView.tell("出错");
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                infoView.stop();
                infoView.tell("出错");
            }
        });
    }
}
