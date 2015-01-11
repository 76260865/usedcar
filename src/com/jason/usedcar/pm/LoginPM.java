package com.jason.usedcar.pm;

import android.app.Activity;

import com.jason.usedcar.Application;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.pm.view.ViewLogin;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.response.LoginResponse;

import org.robobinding.annotation.PresentationModel;

import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-11-10.
 */
@PresentationModel
public class LoginPM implements HasPresentationModelChangeSupport{

    private String username;

    private String password;

    private ViewLogin view;

    private PresentationModelChangeSupport changeSupport;

    public LoginPM(ViewLogin view) {
        this.view = view;
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    public boolean isValid() {
        return isNotEmpty(username) && isNotEmpty(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        changeSupport.firePropertyChange("valid");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        changeSupport.firePropertyChange("valid");
    }

    public void login() {
        view.showProgress();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccessToken(view.getAccessToken());
        loginRequest.setPhoneOrEmail(username);
        loginRequest.setPassword(password);
        new RestClient().login(loginRequest, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse response, Response response2) {
                view.dismissProgress();
                if (response != null) {
                    view.showMessage(response.getMessage());
                    if (response.isExecutionResult()) {
                        response.setUsername(username);
                        response.setPassword(password);
                        response.setAccessToken(Application.getEncryptedToken(response.getUserId(), response.getAccessToken()));
                        Application.fromContext(view.getContext()).setLoginResponse(response);
                        view.finish(Activity.RESULT_OK);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                view.dismissProgress();
                view.showMessage(R.string.error);
            }
        });
    }

    public void register() {
        view.register();
    }

    public void resetPassword() {
        view.resetPassword();
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
