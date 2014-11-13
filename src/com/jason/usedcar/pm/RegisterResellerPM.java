package com.jason.usedcar.pm;

import com.jason.usedcar.RestClient;
import com.jason.usedcar.pm.view.ViewRegisterReseller;
import com.jason.usedcar.request.RegisterResellerRequest;
import com.jason.usedcar.response.Response;
import org.robobinding.annotation.PresentationModel;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-11-11.
 */
@PresentationModel
public class RegisterResellerPM extends RegisterPM {

    private String resellerName;

    private String resellerAddress;

    private int resellerType;

    private ViewRegisterReseller view;

    public RegisterResellerPM(ViewRegisterReseller view) {
        super(view);
        this.view = view;
    }

    public String getResellerName() {
        return resellerName;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
        validate();
    }

    public String getResellerAddress() {
        return resellerAddress;
    }

    public void s1etResellerType(int resellerType) {
        this.resellerType = resellerType;
    }

    public void setResellerAddress(String resellerAddress) {
        this.resellerAddress = resellerAddress;
        changeSupport.firePropertyChange("resellerAddress");
        validate();
    }

    public void pickResellerAddress() {
        view.pickResellerAddress();
    }

    @Override
    public boolean isRegisterEnabled() {
        return super.isRegisterEnabled() && isNotEmpty(resellerAddress) && isNotEmpty(resellerAddress);
    }

    public void register() {
        if (!isValidPhoneNumber(getPhoneNumber())) {
            view.showMessage("手机号长度不足11位");
            return;
        }
        if (!isValidPassword(getPassword())) {
            view.showMessage("密码长度限制在6-15位");
            return;
        }
        if (!getPasswordConfirm().equals(getPassword())) {
            view.showMessage("两次输入的密码不一致");
            return;
        }
        view.showProgress("注册中...");
        RegisterResellerRequest request = new RegisterResellerRequest();
        request.setResellerName(resellerName);
        request.setAddress(resellerAddress);
        request.setResellerType(resellerType);
        request.setAccountType(2);
        request.setPhone(getPhoneNumber());
        request.setPassword(getPassword());
        request.setConfirmPassword(getPasswordConfirm());
        request.setPhoneVerifyCode(getSecurityCode());
        request.setAcceptTerm(isChecked());
        new RestClient().registerReseller(request, new Callback<Response>() {
            @Override
            public void success(Response response, retrofit.client.Response response2) {
                view.dismissProgress();
                if (response != null) {
                    view.showMessage(response.getMessage());
                    if (response.isExecutionResult()) {
                        view.finish();
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
}
