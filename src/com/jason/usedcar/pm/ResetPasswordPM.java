package com.jason.usedcar.pm;

import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.pm.view.ViewBase;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.ResetPasswordByPhoneRequest;
import com.jason.usedcar.response.ObtainCodeResponse;
import com.jason.usedcar.response.PasswordResponse;
import java.util.Timer;
import java.util.TimerTask;
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
public class ResetPasswordPM implements HasPresentationModelChangeSupport {

    private String phoneNumber;

    private String securityCode;

    private String securityCodeConfirm;

    private String password;

    private String passwordConfirm;

    private boolean timeout;

    private ViewBase view;

    private PresentationModelChangeSupport changeSupport;

    private Timer timer;

    public ResetPasswordPM(ViewBase view, String phoneNumber, String securityCode) {
        this.view = view;
        this.phoneNumber = phoneNumber;
        this.securityCode = securityCode;
        this.changeSupport = new PresentationModelChangeSupport(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeout = true;
                //changeSupport.firePropertyChange("timeout");
            }
        }, 2 * 60);
        timeout = false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSecurityCode() {
        return securityCodeConfirm;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCodeConfirm = securityCode;
        changeSupport.firePropertyChange("resetPasswordEnabled");
    }

    public boolean isTimeout() {
        return timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        changeSupport.firePropertyChange("resetPasswordEnabled");
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        changeSupport.firePropertyChange("resetPasswordEnabled");
    }

    public boolean isResetPasswordEnabled() {
        return !isEmptyStr(securityCode) && !isEmptyStr(password)
                && !isEmptyStr(passwordConfirm);
    }

    public void requestSecurityCode() {
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
                        view.showMessage("若未收到短信验证码，请两分钟后重试");
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //timeout = true;
                                changeSupport.firePropertyChange("timeout");
                            }
                        }, 2 * 60);
                        timeout = false;
                        changeSupport.firePropertyChange("timeout");
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

    public void resetPassword() {
        if (!securityCodeConfirm.equals(securityCode)) {
            view.showMessage("验证码不正确");
            return;
        }
        if (!passwordConfirm.equals(password)) {
            view.showMessage("两次输入的密码不一致");
            return;
        }
        view.showProgress("重置密码...");
        ResetPasswordByPhoneRequest request = new ResetPasswordByPhoneRequest();
        request.setNewPassword(password);
        request.setConfirmPassword(passwordConfirm);
        request.setPhone(phoneNumber);
        request.setCode(securityCode);
        new RestClient().resetPasswordByPhone(request, new Callback<PasswordResponse>() {
            @Override
            public void success(PasswordResponse response, Response response2) {
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

    protected static boolean isEmptyStr(String str) {
        return str == null || str.trim().length() == 0;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
