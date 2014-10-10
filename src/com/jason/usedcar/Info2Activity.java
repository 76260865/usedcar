package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jason.usedcar.presentation_model.InfoView;
import com.jason.usedcar.presentation_model.InfoViewModel;

/**
 * @author t77yq @2014-09-29.
 */
public class Info2Activity extends AbsActivity implements InfoView {

    private InfoViewModel infoViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoViewModel = new InfoViewModel(this);
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
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
