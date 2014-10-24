package com.jason.usedcar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.jason.usedcar.presentation_model.ViewBase;
import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;

/**
 * @author t77yq @2014-09-18.
 */
public class AbsActivity extends ActionBarActivity implements ViewBase {

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initContentView(int layoutId, Object presentationModel) {
        ViewBinder viewBinder = createViewBinder();
        View rootView = viewBinder.inflateAndBind(layoutId, presentationModel);
        setContentView(rootView);
    }

    protected ViewBinder createViewBinder() {
        return Application.fromActivity(this)
                .getReusableBinderFactory().createViewBinder(this);
    }

    protected MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater) {
        BinderFactory binderFactory = Application.fromActivity(this).getReusableBinderFactory();
        return binderFactory.createMenuBinder(menu, menuInflater, this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isLogin() {
        return getAccessToken() != null;
    }

    @Override
    public void login() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void login(final int requestCode) {
        startActivityForResult(new Intent(this, LoginActivity.class), requestCode);
    }

    @Override
    public String getAccessToken() {
        return Application.fromActivity(this).getAccessToken();
    }
}
