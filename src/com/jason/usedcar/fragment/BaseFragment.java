package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.View;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.Presenter;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;

/**
 * Parent for all fragments that use Presenters and Ui design.
 */
public abstract class BaseFragment<T extends Presenter<U>, U extends Ui> extends Fragment implements ValidationListener {

    private Validator validator;

    private T mPresenter;

    /**
     * M: change to public
     */
    public abstract T createPresenter();

    /**
     * M: change to public
     */
    public abstract U getUi();

    protected BaseFragment() {
        mPresenter = createPresenter();
    }

    /**
     * Presenter will be available after onActivityCreated().
     *
     * @return The presenter associated with this fragment.
     */
    public T getPresenter() {
        return mPresenter;
    }

    public Validator getValidator() {
        return validator;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUiDestroy(getUi());
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
    }
}
