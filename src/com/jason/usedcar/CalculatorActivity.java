package com.jason.usedcar;

import android.os.*;
import android.view.*;
import android.widget.*;
import com.jason.usedcar.fragment.RatioDialogFragment;
import com.jason.usedcar.fragment.RentDialogFragment;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * @author t77yq @2014-08-03.
 */
public class CalculatorActivity extends BaseActivity
        implements RatioDialogFragment.Callback, RentDialogFragment.Callback {

    @Required(order = 1)
    private EditText priceEdit;

    private TextView ratioText;

    private TextView rentText;

    private TextView monthlyPayText;

    private TextView dailyPayText;

    private int ratioPercent = 30;

    private int rent = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calculator);
        priceEdit = getView(R.id.editCarPrice);
        ratioText = getView(R.id.textRadio);
        rentText = getView(R.id.textRent);
        monthlyPayText = getView(R.id.textMonthlyPay);
        dailyPayText = getView(R.id.textDailyPay);
        double carPrice = getIntent().getDoubleExtra("car_price", 0);
        if (carPrice != 0) {
            priceEdit.setText(String.valueOf(carPrice));
        }
    }

    public void onClick(View view) {
        if (getCurrentFocus() != null) {
            getCurrentFocus().clearFocus();
        }

        switch (view.getId()) {
            case R.id.textRadio:
                new RatioDialogFragment().show(getSupportFragmentManager(), "");
                break;
            case R.id.textRent:
                new RentDialogFragment().show(getSupportFragmentManager(), "");
                break;
            case R.id.buttonCalculate:
                validator.validate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        calculatePay();
    }

    @Override
    public void onValidationFailed(final View view, final Rule<?> rule) {
        switch (view.getId()) {
            case R.id.editCarPrice:
                Toast.makeText(this, R.string.validation_failure_car_price, Toast.LENGTH_SHORT).show();
                break;
        }
        super.onValidationFailed(view, rule);
    }

    private void calculatePay() {
        float carPrice = Float.valueOf(String.valueOf(priceEdit.getText()));
        monthlyPayText.setText(String.valueOf(ratioPercent));
        dailyPayText.setText(String.valueOf(rent));
    }

    @Override
    public void itemClicked(int type, final int percentage) {
        switch (type) {
            case RatioDialogFragment.RATIO:
                ratioPercent = percentage;
                ratioText.setTag(percentage);
                ratioText.setText(String.valueOf(percentage).concat("%"));
                break;
            case RentDialogFragment.RENT:
                rent = percentage;
                rentText.setTag(percentage);
                rentText.setText(String.valueOf(percentage).concat("个月"));
                break;
        }
    }
}
