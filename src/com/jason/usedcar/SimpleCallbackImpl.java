package com.jason.usedcar;

import android.app.Activity;
import android.support.v4.app.Fragment;
import java.lang.ref.WeakReference;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-21.
 */
public abstract class SimpleCallbackImpl<T> implements Callback<T> {

    interface Func1<T1, R> {
        public R call(T1 t1);
    }

    /**
     * @see retrofit.Callback#success(Object, retrofit.client.Response)
     */
    abstract protected void onSuccess(T t, Response response);

    /**
     * @see retrofit.Callback#failure(retrofit.RetrofitError)
     */
    abstract protected void onFailure(RetrofitError error);

    protected static final Func1<WeakReference<Activity>, Boolean> ACTIVITY_VALIDATOR
            = new Func1<WeakReference<Activity>, Boolean>() {
        @Override
        public Boolean call(WeakReference<Activity> activityRef) {
            if (activityRef != null
                    && activityRef.get() != null
                    && !activityRef.get().isFinishing()) {
                Activity activity = activityRef.get();
                if (activity instanceof Cancellable) {
                    return !((Cancellable) activity).isCancelled();
                }
                return true;
            }
            return false;
        }
    };

    protected static final Func1<WeakReference<Fragment>, Boolean> FRAGMENT_VALIDATOR
            = new Func1<WeakReference<Fragment>, Boolean>() {
        @Override
        public Boolean call(WeakReference<Fragment> fragmentRef) {
            if(fragmentRef != null
                    && fragmentRef.get() != null
                    && fragmentRef.get().isAdded()
                    && !fragmentRef.get().getActivity().isFinishing()) {
                Activity activity = fragmentRef.get().getActivity();
                if (activity instanceof Cancellable) {
                    return !((Cancellable) activity).isCancelled();
                }
                return true;
            }
            return false;
        }
    };

    protected final WeakReference<Activity> activityRef;

    protected final WeakReference<Fragment> fragmentRef;

    public SimpleCallbackImpl(Activity activity) {
        this.activityRef = new WeakReference<Activity>(activity);
        this.fragmentRef = null;
    }

    public SimpleCallbackImpl(Fragment fragment) {
        this.activityRef = null;
        this.fragmentRef = new WeakReference<Fragment>(fragment);
    }

    @Override
    public void success(T t, Response response) {
        if (ACTIVITY_VALIDATOR.call(activityRef) || FRAGMENT_VALIDATOR.call(fragmentRef)) {
            onSuccess(t, response);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (ACTIVITY_VALIDATOR.call(activityRef) || FRAGMENT_VALIDATOR.call(fragmentRef)) {
            onFailure(error);
        }
    }
}
