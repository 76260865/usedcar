package com.jason.usedcar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import com.jason.usedcar.presentation_model.ResellerAuthView;
import com.jason.usedcar.presentation_model.ResellerAuthViewModel;

/**
 * @author t77yq @2014-09-27.
 */
public class AuthorizeActivity extends AbsActivity implements ResellerAuthView, DatePickerDialog.OnDateSetListener {

    private ResellerAuthViewModel resellerAuthViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String accountType = getIntent().getStringExtra("accountType");
        if (accountType == null) {
            throw new NullPointerException("请传入账户类别");
        }
        if (accountType.equals("normal")) {
            //
        } else if (accountType.equals("reseller")) {
            resellerAuthViewModel = new ResellerAuthViewModel(this);
            initContentView(R.layout.activity_authorize_1, resellerAuthViewModel);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    break;
            }
        }
    }

    @Override
    public void launchCamera() {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 100);
    }

    @Override
    public void openDatePicker() {
        new DatePicker().setListener(this).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDateSet(final android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        resellerAuthViewModel.setYear(year);
        resellerAuthViewModel.setMonth(monthOfYear + 1);
        resellerAuthViewModel.setDay(dayOfMonth);
        resellerAuthViewModel.updateFoundationTime();
    }
}
