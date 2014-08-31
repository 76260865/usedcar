package com.jason.usedcar;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.constants.Constants.ObtainCode;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.UpdatePasswordRequest;
import com.jason.usedcar.response.*;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import retrofit.*;
import retrofit.client.Response;

/**
 * @author t77yq @2014-06-22.
 */
public class ChangePasswordActivity extends BaseActivity {

    @Required(order = 1000)
    private EditText verifyCodeEdit;

    @Required(order = 2000)
    private EditText oldPasswordEdit;

    @Password(order = 3000)
    @TextRule(order = 3001, minLength = 6, maxLength = 15, message = Constants.Validator.MSG_PASSWORD_LENGTH)
    private EditText newPasswordEdit;

    @Required(order = 4000, message = "empty")
    @ConfirmPassword(order = 4001, message = "confirm")
    private EditText confirmNewPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bindViews();
    }

    private void bindViews() {
        final TextView phoneText = getView(R.id.change_password_text_phone);
        phoneText.setText(getString(R.string.change_password_bind_phone, getIntent().getStringExtra("phone")));
        verifyCodeEdit = getView(R.id.edit_verify_code);
        oldPasswordEdit = getView(R.id.change_password_edit_old_password);
        newPasswordEdit = getView(R.id.change_password_edit_new_password);
        confirmNewPasswordEdit = getView(R.id.change_password_edit_new_password_confirm);
        getView(R.id.btn_obtain_code).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                obtainCode();
            }
        });
        getView(R.id.change_password_btn_ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        changePassword();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.edit_verify_code:
                MessageToast.makeText(getApplicationContext(), "还没填写验证码").show();
                break;
            case R.id.change_password_edit_old_password:
                MessageToast.makeText(getApplicationContext(), "还没填写旧密码").show();
                break;
            case R.id.change_password_edit_new_password:
                MessageToast.makeText(getApplicationContext(), "还没填写新密码").show();
                break;
            case R.id.change_password_edit_new_password_confirm:
                if (rule.getFailureMessage().equals("empty")) {
                    MessageToast.makeText(getApplicationContext(), "还没填写确认密码").show();
                } else if (rule.getFailureMessage().equals("confirm")) {
                    MessageToast.makeText(getApplicationContext(), "确认密码与新密码不一致").show();
                }
                break;
        }
    }

    private void obtainCode() {
        String phoneNumber = String.valueOf(getIntent().getStringExtra("phone"));
        ObtainCodeRequest param = new ObtainCodeRequest();
        param.setPhoneNumber(phoneNumber);
        param.setType(ObtainCode.TYPE_VERIFY_ID);
        obtainCode(param);
    }

    public void obtainCode(final ObtainCodeRequest param) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("获取手机验证码&#8230;");
        loadingFragment.show(getSupportFragmentManager());
        new RestClient().obtainCode(param, new Callback<ObtainCodeResponse>() {
            @Override
            public void success(final ObtainCodeResponse response, final Response response2) {
                loadingFragment.dismiss();
                if (response.isExecutionResult()) {
                    verifyCodeEdit.setTag(response.getCode());
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    private void changePassword() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setAccessToken(Application.fromActivity(this).getAccessToken());
        updatePasswordRequest.setOldPassword(String.valueOf(oldPasswordEdit.getText()));
        updatePasswordRequest.setNewPassword(String.valueOf(newPasswordEdit.getText()));
        updatePasswordRequest.setConfirmPassword(String.valueOf(confirmNewPasswordEdit.getText()));
        new RestClient().updatePassword(updatePasswordRequest, new Callback<com.jason.usedcar.response.Response>() {
            @Override
            public void success(final com.jason.usedcar.response.Response response, final Response response2) {
                loadingFragment.dismiss();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
