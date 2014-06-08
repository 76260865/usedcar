package com.jason.usedcar.presenter;

import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.ResetPasswordFragmentPresenter.ResetPasswordFragmentUi;

/**
 * @author t77yq @2014.06.08
 */
public class ResetPasswordFragmentPresenter extends Presenter<ResetPasswordFragmentUi> {

    public interface ResetPasswordFragmentUi extends Ui {

        public void onPasswordReset();
    }

    public void resetPassword() {
        getUi().onPasswordReset();
    }
}
