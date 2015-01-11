package com.jason.usedcar.pm;

import com.jason.usedcar.RestClient;
import com.jason.usedcar.pm.view.ViewRegister;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.RegisterRequest;
import com.jason.usedcar.response.ObtainCodeResponse;
import java.util.Timer;
import java.util.TimerTask;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-11-11.
 */
@PresentationModel
public class RegisterPM implements HasPresentationModelChangeSupport {

    private String phoneNumber;

    private String password;

    private String passwordConfirm;

    private String securityCode;

    private boolean checked;

    private ViewRegister view;

    protected PresentationModelChangeSupport changeSupport;

    private Timer timer;

    private boolean isRequestingSecurityCode;

    public RegisterPM(ViewRegister view) {
        this.view = view;
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        validate();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        validate();
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        validate();
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        validate();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        validate();
    }

    public boolean isRegisterEnabled() {
        return isNotEmpty(phoneNumber) && isNotEmpty(password) && isChecked()
                && isNotEmpty(passwordConfirm) && isNotEmpty(securityCode);
    }

    public boolean isRequestSecurityCodeEnabled() {
        return !isRequestingSecurityCode && isNotEmpty(phoneNumber);
    }

    public void requestSecurityCode() {
        if (!isValidPhoneNumber(phoneNumber)) {
            view.showMessage("手机号长度不足11位");
            return;
        }
        view.showProgress("获取验证码...");
        final ObtainCodeRequest request = new ObtainCodeRequest();
        request.setPhoneNumber(phoneNumber);
        new RestClient().obtainCode(request, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(ObtainCodeResponse response, Response response2) {
                view.dismissProgress();
                if (response != null) {
                    view.showMessage(response.getMessage());
                    if (response.isExecutionResult()) {
                        view.showMessage("若未接收到短信验证码，请两分钟后重新获取");
                        isRequestingSecurityCode = true;
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //isRequestingSecurityCode = false;
                            }
                        }, 60 * 2);
                        changeSupport.firePropertyChange("requestSecurityCodeEnabled");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                view.dismissProgress();
                view.showMessage("出错了");
            }
        });
    }

    public void register() {
        if (!isValidPhoneNumber(phoneNumber)) {
            view.showMessage("手机号长度不足11位");
            return;
        }
        if (!isValidPassword(password)) {
            view.showMessage("密码长度限制在6-15位");
            return;
        }
        if (!passwordConfirm.equals(password)) {
            view.showMessage("两次输入的密码不一致");
            return;
        }
        view.showProgress("注册中...");
        RegisterRequest request = new RegisterRequest();
        request.setPhone(phoneNumber);
        request.setPassword(password);
        request.setConfirmPassword(passwordConfirm);
        request.setPhoneVerifyCode(securityCode);
        request.setAccountType(1);
        request.setAcceptTerm(checked);
        new RestClient().register(request, new Callback<com.jason.usedcar.response.Response>() {
            @Override
            public void success(com.jason.usedcar.response.Response response, Response response2) {
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
                view.showMessage("出错了^.^");
            }
        });
    }

    protected static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    protected static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    protected static boolean isValidPhoneNumber(String phoneNumber) {
        return isNotEmpty(phoneNumber) && phoneNumber.length() >= 11;
    }

    protected static boolean isValidPassword(String password) {
        return isNotEmpty(password) && password.length() >= 6 && password.length() <= 15;
    }

    protected void validate() {
        changeSupport.firePropertyChange("registerEnabled");
        changeSupport.firePropertyChange("requestSecurityCodeEnabled");
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
