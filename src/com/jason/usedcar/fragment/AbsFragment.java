package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jason.usedcar.Application;
import org.robobinding.ViewBinder;

/**
 * @author t77yq @2014-09-17.
 */
public abstract class AbsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initContentView(container);
    }

    public View initContentView(ViewGroup container) {
        ViewBinder viewBinder = createViewBinder();
        return viewBinder.inflateAndBind(layoutId(), presentationModel());
    }

    private ViewBinder createViewBinder() {
        return Application.fromActivity(getActivity())
                .getReusableBinderFactory().createViewBinder(getActivity());
    }

    protected abstract int layoutId();

    protected abstract Object presentationModel();

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        return (T) getView().findViewById(id);
    }
}
