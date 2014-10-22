package com.jason.usedcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.interfaces.IJobListener;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.response.LoginResponse;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import retrofit.*;

public class LoginActivity extends BaseActivity {

    @Required(order = 1, messageResId = R.string.error_input_account)
    private EditText usernameEdit;

    @Password(order = 2, messageResId = R.string.error_input_password)
    private EditText passwordEdit;

    private RadioGroup radioGroup;

    private boolean mResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_login);
        bindViews();
    }

    private void bindViews() {
        usernameEdit = getView(R.id.login_edit_username);
        passwordEdit = getView(R.id.login_edit_password);
        radioGroup = getView(R.id.userTypeRadioGroup);
    }

    private void popupShortToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                validator.validate();
                break;
            case R.id.login_btn_register:
                register();
                break;
            case R.id.login_btn_reset_password:
                resetPassword();
                break;
        }
    }

    private void login(final String username, final String password) {
        final LoginRequest request = new LoginRequest();
        request.setPhoneOrEmail(username);
        request.setPassword(password);
        final LoadingFragment loadingFragment = new LoadingFragment();
        loadingFragment.show(getSupportFragmentManager());
        new RestClient().login(request, new Callback<LoginResponse>() {
            @Override
            public void success(final LoginResponse response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response != null && response.isExecutionResult()) {
                    if (mIJobListener != null) {
                        mIJobListener.executionDone();
                    }
                    setResult(Activity.RESULT_OK);
                    Application.fromContext(getApplicationContext()).setAccessToken(
                            Application.getEncryptedToken(response.getUserId(), response.getAccessToken()));
                    Application.fromContext(getApplicationContext()).username = username;
                    Application.fromContext(getApplicationContext()).password = password;
                    Application.fromContext(getApplicationContext()).userId = response.getUserId();
                    Application.fromContext(getApplicationContext()).isReseller
                            = radioGroup.getCheckedRadioButtonId() != R.id.radioPersonal;
                    finish();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
                mResult = true;
                if (mIJobListener != null) {
                    mIJobListener.executionDone();
                }
            }
        });
    }
    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
    private void resetPassword() {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    // bellow code is for test:
    public boolean getResult() {
        return mResult;
    }

    private IJobListener mIJobListener;

    public void setListener(IJobListener listener) {
        mIJobListener = listener;
    }

    @Override
    public void onValidationSucceeded() {
        login(String.valueOf(usernameEdit.getText()),
                String.valueOf(passwordEdit.getText()));
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.login_edit_username:
                // walk through
            case R.id.login_edit_password:
                popupShortToast(rule.getFailureMessage());
                break;
        }
    }
}
