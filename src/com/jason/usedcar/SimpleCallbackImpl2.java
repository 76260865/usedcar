package com.jason.usedcar;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.response.LoginResponse;
import org.apache.http.HttpStatus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-21.
 */
public abstract class SimpleCallbackImpl2<T> extends SimpleCallbackImpl<T> {

    /**
     * Login success.
     */
    abstract protected void onAuthorized();

    /**
     * Login failure during to username or password is incorrect, other in onFailure callback.
     */
    abstract protected void onUnauthorized();

    private static final int RETRY_LIMIT = 4;

    private int retryCount;

    public SimpleCallbackImpl2(Activity activity) {
        super(activity);
    }

    public SimpleCallbackImpl2(Fragment fragment) {
        super(fragment);
    }

    @Override
    public void failure(RetrofitError error) {
        if (ACTIVITY_VALIDATOR.call(activityRef) || FRAGMENT_VALIDATOR.call(fragmentRef)) {
            Response response = error == null ? null : error.getResponse();
            int status = response == null ? 0 : response.getStatus();
            switch (status) {
                case HttpStatus.SC_UNAUTHORIZED:
                    if (retryCount > RETRY_LIMIT) {
                        onFailure(error);
                        return;
                    }
                    retryCount++;

                    final Activity activity = activityRef == null ? null : activityRef.get();
                    Fragment fragment = fragmentRef == null ? null : fragmentRef.get();
                    if (activity != null || fragment != null) {
                        final Context context = (activity == null) ? fragment.getActivity() : activity;
                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setPhoneOrEmail(Application.fromContext(context).username);
                        loginRequest.setPassword(Application.fromContext(context).password);
                        final SimpleCallbackImpl2<T> callback = this;
                        new RestClient().login(loginRequest, new Callback<LoginResponse>() {
                            @Override
                            public void success(LoginResponse loginResponse, Response response) {
                                callback.onAuthorized();
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                if (!retrofitError.isNetworkError()) {
                                    if (retrofitError.getResponse().getStatus() == HttpStatus.SC_PRECONDITION_FAILED) {
                                        callback.onUnauthorized();
                                        return;
                                    }
                                }
                                callback.onFailure(retrofitError);
                            }
                        });
                        return;
                    }
                    onFailure(error);
                    break;
                default:
                    onFailure(error);
                    break;
            }
        }
    }
}
