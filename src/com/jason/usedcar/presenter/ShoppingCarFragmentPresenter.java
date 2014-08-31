package com.jason.usedcar.presenter;

import android.content.Context;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter.CallButtonUi;

/**
 * Logic for call buttons.
 */
public class ShoppingCarFragmentPresenter extends BasePresenter<CallButtonUi> {

    public void login(final Context context) {
    }

    public interface CallButtonUi extends Ui {

        void setEnabled(boolean on);

        void login(String response);
    }
}
