package com.jason.usedcar.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.jason.usedcar.*;
import com.jason.usedcar.interfaces.Ui;

/**
 * @author t77yq @2014.06.14
 */
public class PersonalCenterFragmentPresenter extends Presenter<Ui> {

    public void myInfo(Activity activity) {
        if (Application.fromActivity(activity).isLogin()) {
            activity.startActivity(new Intent(activity, InfoActivity.class));
        } else {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    public void identify(Context context) {
        Intent authorizeIntent = new Intent(context, AuthorizeActivity.class);
        authorizeIntent.putExtra("accountType", "reseller");
        context.startActivity(authorizeIntent);
    }

    public void myCarsToSale(Context context) {
        context.startActivity(new Intent(context, HistoryActivity.class));
    }

    public void myCollectCar(Context context) {
        context.startActivity(new Intent(context, CollectActivity.class));
    }

    public void tradeHistory(Context context) {
        context.startActivity(new Intent(context, HistoryActivity.class));
    }
}
