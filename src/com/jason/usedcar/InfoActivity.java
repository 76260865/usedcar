package com.jason.usedcar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.activeandroid.query.Select;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.pm.InfoPM;
import com.jason.usedcar.pm.view.InfoView;

/**
 * @author t77yq @2014-09-29.
 */
public class InfoActivity extends AbsActivity implements InfoView, DatePickerDialog.OnDateSetListener {

    private static final int ADDRESS = 100;

    private InfoPM infoViewModel;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        infoViewModel = new InfoPM(this);
        initContentView(R.layout.activity_info2, infoViewModel);
        infoViewModel.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_info_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(infoViewModel.getEditMode() ? R.menu.menu_my_info_save : R.menu.menu_my_info_edit, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                supportInvalidateOptionsMenu();
                infoViewModel.setEditMode(true);
                break;
            case R.id.action_save:
                infoViewModel.save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ADDRESS:
                    String area = data.getStringExtra("data");
                    String[] areaList = area.split(";");
                    Province province = new Select().from(Province.class).where("remote_id = ?", areaList[0]).executeSingle();
                    City city = new Select().from(City.class).where("remote_id = ?", areaList[1]).executeSingle();
                    County county = new Select().from(County.class).where("remote_id = ?", areaList[2]).executeSingle();
                    infoViewModel.setProvince(province, city, county);
                    break;
            }
        }
    }

    @Override
    public void changePassword(String phoneNumber) {
        Intent changePassword = new Intent(this, ChangePasswordActivity.class);
        changePassword.putExtra("phone", phoneNumber);
        startActivity(changePassword);
    }

    @Override
    public void changePhone(String phoneNumber) {
        startActivity(new Intent(this, BindPhoneActivity.class));
    }

    @Override
    public void pickTime() {
        new com.jason.usedcar.DatePicker().setListener(this).show(getSupportFragmentManager(), "");
    }

    @Override
    public void pickAddress() {
        startActivityForResult(new Intent(this, DealPlaceActivity.class), ADDRESS);
    }

    @Override
    public void start() {
        loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void stop() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public void tell(final String msg) {
        MessageToast.makeText(this, msg).show();
    }

    @Override
    public boolean isReseller() {
        return Application.fromActivity(this).isReseller();
    }

    @Override
    public void onDateSet(final android.widget.DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
        infoViewModel.setBirthday(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
    }
}
