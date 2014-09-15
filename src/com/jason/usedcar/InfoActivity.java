package com.jason.usedcar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.DatePicker;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.request.*;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.*;
import com.jason.usedcar.response.Response;
import com.jason.usedcar.view.ExtendedEditText;
import retrofit.*;

/**
 * @author t77yq @2014.06.14
 */
public class InfoActivity extends BaseActivity implements OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final int LOGIN = 10001;

    private boolean editMode = false;

    private InfoActivityHolder activityHolder;

    private UserInfoRequest userInfoParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        userInfoParam = new UserInfoRequest();
        userInfoParam.setAccessToken(Application.fromActivity(this).getAccessToken());
        activityHolder = new InfoActivityHolder(this);
        activityHolder.changePasswordText.setOnClickListener(this);
        activityHolder.changePhoneButton.setOnClickListener(this);
        //activityHolder.bindEmailText.setOnClickListener(this);
        activityHolder.birthdayText.setOnClickListener(this);
        viewUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_info_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(editMode ? R.menu.menu_my_info_save : R.menu.menu_my_info_edit, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                editMode = true;
                supportInvalidateOptionsMenu();
                edit();
                break;
            case R.id.action_save:
                if (validate()) {
                    updateUserInfo();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewUserInfo() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("获取个人资料&#8230;");
        loadingFragment.show(getSupportFragmentManager());
        Request viewUserInfoRequest = new Request();
//        viewUserInfoRequest.setAccessToken(Application.fromActivity(this).getAccessToken());
//        viewUserInfoRequest.setAccessToken(Application.getEncryptedToken(Application.fromActivity(this).userId,
//                Application.fromActivity(this).getAccessToken()));
        viewUserInfoRequest.setAccessToken(Application.sampleAccessToken);
        new RestClient().viewUserInfo(viewUserInfoRequest, new Callback<UserInfoResponse>() {
            @Override
            public void success(final UserInfoResponse response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response.isExecutionResult()) {
                    userInfoParam.setNickname(response.getNickname());
                    userInfoParam.setRealName(response.getRealName());
                    userInfoParam.setSex(response.isSex());
                    userInfoParam.setBirthyear(response.getBirthyear());
                    userInfoParam.setBirthmonth(response.getBirthmonth());
                    userInfoParam.setBirthday(response.getBirthday());
                    userInfoParam.setProvince(response.getProvince());
                    userInfoParam.setCity(response.getCity());
                    userInfoParam.setCounty(response.getCounty());
                    userInfoParam.setStreet(response.getStreet());
                    activityHolder.nicknameText.setText(response.getNickname());
                    activityHolder.changePasswordText.setText("");
                    activityHolder.bindPhoneText.setText(response.getPhone());
                    activityHolder.bindEmailText.setText(response.getEmail());
                    activityHolder.nameText.setText(response.getRealName());
                    activityHolder.birthdayText.setText(new StringBuilder()
                            .append(response.getBirthyear()).append("-")
                            .append(response.getBirthmonth()).append("-")
                            .append(response.getBirthday()));
                    activityHolder.birthdayText.setText(response.getCertificateNumber());
                    activityHolder.addressText.setText(new StringBuffer()
                            .append(response.getProvince()).append(response.getCity())
                            .append(response.getCounty()).append(response.getStreet()));
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    private void updateUserInfo() {
        userInfoParam.setNickname(String.valueOf(activityHolder.nicknameText.getText()));
        userInfoParam.setRealName(String.valueOf(activityHolder.nameText.getText()));
        userInfoParam.setCertificateNumber(String.valueOf(activityHolder.identifyText.getText()));
        userInfoParam.setStreet(String.valueOf(activityHolder.addressText.getText()));
        userInfoParam.setSex(activityHolder.radioTypeMale.isChecked());
        userInfoParam.setProvince("xxx");
        userInfoParam.setCity("yyy");
        userInfoParam.setStreet("zzz");
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("更新个人资料&#8230;");
        loadingFragment.show(getSupportFragmentManager());
        new RestClient().updateUserInfo(userInfoParam, new Callback<Response>() {
            @Override
            public void success(final Response response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                editMode = false;
                supportInvalidateOptionsMenu();
                save();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(activityHolder.nicknameText.getText())) {
            MessageToast.makeText(this, "请设置用户名").show();
            return false;
        }
        if (TextUtils.isEmpty(activityHolder.nameText.getText())) {
            MessageToast.makeText(this, "请设置姓名").show();
            return false;
        }
        if (TextUtils.isEmpty(activityHolder.birthdayText.getText())) {
            MessageToast.makeText(this, "请设置出生年月").show();
            return false;
        }
        if (TextUtils.isEmpty(activityHolder.identifyText.getText())) {
            MessageToast.makeText(this, "请设置证件号码").show();
            return false;
        }
        if (TextUtils.isEmpty(activityHolder.addressText.getText())) {
            MessageToast.makeText(this, "请设置所在地").show();
            return false;
        }
        return true;
    }

    private void edit() {
        activityHolder.nicknameText.setEnabled(true);
        activityHolder.changePasswordText.setEnabled(true);
        activityHolder.bindPhoneText.setEnabled(true);
        activityHolder.changePhoneButton.setEnabled(true);
        activityHolder.bindEmailText.setEnabled(true);
        activityHolder.nameText.setEnabled(true);
        activityHolder.birthdayText.setEnabled(true);
        activityHolder.identifyText.setEnabled(true);
        activityHolder.addressText.setEnabled(true);
    }

    private void save() {
        activityHolder.nicknameText.setEnabled(false);
        activityHolder.changePasswordText.setEnabled(false);
        activityHolder.bindPhoneText.setEnabled(false);
        activityHolder.changePhoneButton.setEnabled(false);
        activityHolder.bindEmailText.setEnabled(false);
        activityHolder.nameText.setEnabled(false);
        activityHolder.birthdayText.setEnabled(false);
        activityHolder.identifyText.setEnabled(false);
        activityHolder.addressText.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LOGIN:
                    viewUserInfo();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_password:
                Intent changePassword = new Intent(this, ChangePasswordActivity.class);
                changePassword.putExtra("phone", activityHolder.bindPhoneText.getText());
                startActivity(changePassword);
                break;
            case R.id.info_change_phone:
                startActivity(new Intent(this, BindPhoneActivity.class));
                break;
            case R.id.info_bind_email:
                startActivity(new Intent(this, BindEmailActivity.class));
                break;
            case R.id.info_birthday:
                new com.jason.usedcar.DatePicker().setListener(this).show(getSupportFragmentManager(), "");
                break;
        }
    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
        String date = year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日";
        userInfoParam.setBirthyear(String.valueOf(year));
        userInfoParam.setBirthmonth(String.valueOf(monthOfYear + 1));
        userInfoParam.setBirthday(String.valueOf(dayOfMonth));
        activityHolder.birthdayText.setText(date);
    }

    private static class InfoActivityHolder {

        public final ExtendedEditText nicknameText;

        public final Button changePasswordText;

        public final TextView bindPhoneText;

        public final Button changePhoneButton;

        public final TextView bindEmailText;

        public final ExtendedEditText nameText;

        public final TextView birthdayText;

        public final ExtendedEditText identifyText;

        public final ExtendedEditText addressText;

        public final RadioButton radioTypeMale;

        public InfoActivityHolder(Activity activity) {
            nicknameText = (ExtendedEditText) activity.findViewById(R.id.info_nickname);
            changePasswordText = (Button) activity.findViewById(R.id.info_password);
            bindPhoneText = (TextView) activity.findViewById(R.id.info_bind_phone);
            changePhoneButton = (Button) activity.findViewById(R.id.info_change_phone);
            bindEmailText = (TextView) activity.findViewById(R.id.info_bind_email);
            nameText = (ExtendedEditText) activity.findViewById(R.id.info_name);
            birthdayText = (TextView) activity.findViewById(R.id.info_birthday);
            identifyText = (ExtendedEditText) activity.findViewById(R.id.info_id);
            addressText = (ExtendedEditText) activity.findViewById(R.id.info_address);
            radioTypeMale = (RadioButton) activity.findViewById(R.id.radioTypeMale);
        }
    }
}
