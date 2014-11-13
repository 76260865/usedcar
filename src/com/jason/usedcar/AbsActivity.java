package com.jason.usedcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.pm.view.ViewBase;
import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

/**
 * @author t77yq @2014-09-18.
 */
public class AbsActivity extends ActionBarActivity implements ViewBase {

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
    }

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
        return new BinderFactoryBuilder().mapView(ImageView.class, new ImageViewBinding2())
                .mapView(CheckedTextView.class, new CheckedTextViewBinding())
                .mapView(TextView.class, new HtmlTextViewBinding())
                .build().createViewBinder(getContext(), true);
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
        return Application.fromActivity(this).isLogin();
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
    public void finish(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override
    public void finish(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }

    @Override
    public void showProgress() {
        loadingFragment = LoadingFragment.newInstance(0);
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void showProgress(int messageId) {
        loadingFragment = LoadingFragment.newInstance(messageId);
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void showProgress(String message) {
        loadingFragment = LoadingFragment.newInstance(message);
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void dismissProgress() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public void showMessage(int messageId) {
        MessageToast.makeText(this, messageId).show();
    }

    @Override
    public void showMessage(String message) {
        MessageToast.makeText(this, message).show();
    }

    @Override
    public String getAccessToken() {
        return Application.fromActivity(this).getAccessToken();
    }
}
