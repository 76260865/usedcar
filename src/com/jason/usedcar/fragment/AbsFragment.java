package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.Application;
import com.jason.usedcar.CheckedTextViewBinding;
import com.jason.usedcar.HtmlTextViewBinding;
import com.jason.usedcar.ImageViewBinding2;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.pm.view.ViewBase;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * @author t77yq @2014-09-17.
 */
public abstract class AbsFragment extends Fragment implements ViewBase {

    private LoadingFragment loadingFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initContentView(container);
    }

    public View initContentView(ViewGroup container) {
        ViewBinder viewBinder = createViewBinder();
        return viewBinder.inflateAndBind(layoutId(), presentationModel());
    }

    private ViewBinder createViewBinder() {
        return new BinderFactoryBuilder().mapView(ImageView.class, new ImageViewBinding2())
                .mapView(CheckedTextView.class, new CheckedTextViewBinding())
                .mapView(TextView.class, new HtmlTextViewBinding())
                .build().createViewBinder(getContext(), true);
    }

    protected abstract int layoutId();

    protected abstract Object presentationModel();

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        return (T) getView().findViewById(id);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public boolean isLogin() {
        return Application.fromActivity(getActivity()).isLogin();
    }

    @Override
    public void login() {
    }

    @Override
    public void login(final int requestCode) {
    }

    @Override
    public String getAccessToken() {
        return Application.fromActivity(getActivity()).getAccessToken();
    }

    @Override
    public void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void finish(int resultCode) {
        finish(resultCode, null);
    }

    @Override
    public void finish(int resultCode, Intent data) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.setResult(resultCode, data);
            activity.finish();
        }
    }

    @Override
    public void showProgress() {
        showMessage(0);
    }

    @Override
    public void showProgress(int messageId) {
        loadingFragment = LoadingFragment.newInstance(messageId);
        loadingFragment.show(getFragmentManager());
    }

    @Override
    public void showProgress(String message) {
        loadingFragment = LoadingFragment.newInstance(message);
        loadingFragment.show(getFragmentManager());
    }

    @Override
    public void dismissProgress() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public void showMessage(int messageId) {
        MessageToast.makeText(getActivity(), messageId).show();
    }

    @Override
    public void showMessage(String message) {
        MessageToast.makeText(getActivity(), message).show();
    }
}
