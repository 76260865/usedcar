package com.jason.usedcar;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.presentation_model.ResellerAuthView;
import com.jason.usedcar.presentation_model.ResellerAuthViewModel;

/**
 * @author t77yq @2014-09-27.
 */
public class AuthorizeActivity extends AbsActivity implements ResellerAuthView {

    private ResellerAuthViewModel resellerAuthViewModel;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        setTitle("认证");
        resellerAuthViewModel = new ResellerAuthViewModel(this);
        initContentView(R.layout.activity_authorize_1, resellerAuthViewModel);

        final String[] spinnerData = new String[] {
                "身份证", "护照", "军人证"
        };
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerAdapter() {
            @Override
            public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setText(getItem(position));
                return textView;
            }

            @Override
            public void registerDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return spinnerData.length;
            }

            @Override
            public String getItem(final int position) {
                return spinnerData[position];
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setText(getItem(position));
                return textView;
            }

            @Override
            public int getItemViewType(final int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                resellerAuthViewModel.setCertificateType(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
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
    public void before() {
        loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void after() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public boolean isReseller() {
        return Application.fromActivity(this).isReseller;
    }
}
