package com.jason.usedcar.pm;

import android.text.TextUtils;
import android.view.View;
import com.activeandroid.query.Select;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.pm.view.InfoView;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.request.UserInfoRequest;
import com.jason.usedcar.response.Response;
import com.jason.usedcar.response.UserInfoResponse;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class InfoPM extends BasePM {

    private InfoView infoView;

    private boolean editMode;

    private int provinceId;

    private int cityId;

    private int countyId;

    private UserInfoResponse userInfoResponse = new UserInfoResponse();

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public InfoPM(final InfoView infoView) {
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
        return infoView.isReseller() ? userInfoResponse.getResellerType() == 1 : userInfoResponse.getSex() == 0;
    }

    public boolean getFemale() {
        return !getMale();
    }

    public String getLeftType() {
        return infoView.isReseller() ? "二手车经纪公司" : "男";
    }

    public String getRightType() {
        return infoView.isReseller() ? "4S店" : "女";
    }

    public void setNickname(String nickname) {
        userInfoResponse.setNickname(nickname);
    }

    public String getNickname() {
        return infoView.isReseller() ? userInfoResponse.getResellerName() : userInfoResponse.getNickname();
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

    public void setProvince(Province province, City city, County county) {
        userInfoResponse.setProvince(province.getProvinceName());
        userInfoResponse.setCity(city.getCityName());
        userInfoResponse.setCounty(county.getCountyName());
        provinceId = province.getProvinceId();
        cityId = city.getCityId();
        countyId = county.getCountyId();
        presentationModelChangeSupport.firePropertyChange("province");
    }

    public String getProvince() {
        String province = userInfoResponse.getProvince();
        if (TextUtils.isEmpty(province)) {
            province = "";
        }
        String city = userInfoResponse.getCity();
        if (TextUtils.isEmpty(city)) {
            city = "";
        }
        String county = userInfoResponse.getCounty();
        if (TextUtils.isEmpty(county)) {
            county = "";
        }
        if (province.equals(city)) {
            return province + county;
        }
        else {
            return province + city + county;
        }
    }

    public void setCity(String city) {
    }

    public String getCity() {
        return "";
    }

    public void setCounty(String county) {
    }

    public String getCounty() {
        return "";
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
                    if (!TextUtils.isEmpty(response.getProvince())) {
                        Province province = new Select().from(Province.class).where("name = ?", response.getProvince()).executeSingle();
                        if (province == null) {
                            province = new Select().from(Province.class).where("name = ?", response.getProvince() + "省").executeSingle();
                        }
                        if (province != null) {
                            provinceId = province.getProvinceId();
                        }
                        City city = new Select().from(City.class).where("name = ?", response.getCity()).executeSingle();
                        if (city == null) {
                            city = new Select().from(City.class).where("name = ?", response.getCity() + "市").executeSingle();
                        }
                        if (city != null) {
                            cityId = city.getCityId();
                        }
                        County county = new Select().from(County.class).where("name = ?", response.getCounty()).executeSingle();
                        if (county != null) {
                            countyId = county.getCountyId();
                        }
                    }
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

    public void pickAddress() {
        infoView.pickAddress();
    }

    public void save() {
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setAccessToken(infoView.getAccessToken());
        userInfoRequest.setNickname(getNickname());
        userInfoRequest.setBirthday(getBirthday());
        userInfoRequest.setSex(userInfoResponse.getSex());
        userInfoRequest.setCity(userInfoResponse.getCity());
        userInfoRequest.setProvince(userInfoResponse.getProvince());
        userInfoRequest.setCounty(userInfoResponse.getCounty());
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
