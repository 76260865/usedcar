package com.jason.usedcar.pm;

import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.pm.view.ViewRequestSecurityCode;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.response.ObtainCodeResponse;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-11-12.
 */
@PresentationModel
public class RequestSecurityCodePM implements HasPresentationModelChangeSupport {

    private String phoneNumber;

    private ViewRequestSecurityCode view;

    private PresentationModelChangeSupport changeSupport;

    public RequestSecurityCodePM(ViewRequestSecurityCode view) {
        this.view = view;
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        changeSupport.firePropertyChange("requestSecurityCodeEnabled");
    }

    public boolean isRequestSecurityCodeEnabled() {
        return !isEmptyStr(phoneNumber);
    }

    public void requestSecurityCode() {
        if (!isValidPhoneNumber(phoneNumber)) {
            view.showMessage("手机号不足11位");
            return;
        }
        view.showProgress("获取验证码...");
        ObtainCodeRequest request = new ObtainCodeRequest();
        request.setPhoneNumber(phoneNumber);
        request.setType(Constants.ObtainCode.TYPE_RESET_PASSWORD);
        new RestClient().obtainCode(request, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(ObtainCodeResponse response, Response response2) {
                view.dismissProgress();
                if (response != null) {
                    view.showMessage(response.getMessage());
                    if (response.isExecutionResult()) {
                        view.goToNext(phoneNumber, response.getCode());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                view.dismissProgress();
                view.showMessage("出错了^.^");
            }
        });

    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }

    protected static boolean isEmptyStr(String str) {
        return str == null || str.trim().length() == 0;
    }

    protected static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.trim().length() >= 11;
    }
}
