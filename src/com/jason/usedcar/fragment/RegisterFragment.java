package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.constants.Constants.ObtainCode;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.RegisterRequest;
import com.jason.usedcar.presenter.RegisterFragmentPresenter;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class RegisterFragment extends
        BaseFragment<RegisterFragmentPresenter, RegisterFragmentUi> implements
        RegisterFragmentUi, OnClickListener {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    @Required(order = 1000)
    protected EditText editAccount;

    @Password(order = 2000)
    @TextRule(order = 2001, minLength = 6, maxLength = 15, message = Constants.Validator.MSG_PASSWORD_LENGTH)
    protected EditText editPassword;

    @Required(order = 3000)
    @ConfirmPassword(order = 3001, message = Constants.Validator.MSG_PASSWORD_NOT_MATCH)
    protected EditText editConfirmPassword;

    @Required(order = 4000)
    protected EditText editVerifyCode;

    @Checked(order = 5000)
    protected CheckBox checkAgreement;

    protected String verifyCode;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout(), container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    protected int layout() {
        return R.layout.fragment_register;
    }

    protected void bindViews(View view) {
        editAccount = getView(view, R.id.register_account);
        editVerifyCode = getView(view, R.id.edit_verify_code);
        editPassword = getView(view, R.id.register_password);
        editConfirmPassword = getView(view, R.id.register_password_confirm);
        checkAgreement = getView(view, R.id.register_agreement_check);
        getView(view, R.id.btn_obtain_code).setOnClickListener(this);
        getView(view, R.id.register_register).setOnClickListener(this);
    }

    @Override
    public RegisterFragmentPresenter createPresenter() {
        return new RegisterFragmentPresenter();
    }

    @Override
    public RegisterFragmentPresenter.RegisterFragmentUi getUi() {
        return this;
    }

    @Override
    public void onRegistered() {
        Log.d(TAG, "onRegistered");
        Toast.makeText(getActivity(), "onRegistered", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVerifyCodeRequested(String code) {
        Log.d(TAG, "onVerifyCodeRequested code is : " + code);
        editVerifyCode.setText(code);
        verifyCode = code;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_obtain_code:
                if (TextUtils.isEmpty(editAccount.getText())) {
                    MessageToast.makeText(getActivity(), R.string.error_input_account).show();
                    return;
                }
                obtainCode();
                break;
            case R.id.register_register:
                getValidator().validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        register();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.register_account:
                MessageToast.makeText(getActivity(), R.string.error_input_account).show();
                break;
            case R.id.register_password:
                if (Constants.Validator.MSG_PASSWORD_LENGTH.equals(rule.getFailureMessage())) {
                    MessageToast.makeText(getActivity(), getString(R.string.error_password_length,
                            Constants.PASSWORD_LENGTH_MIN, Constants.PASSWORD_LENGTH_MAX)).show();
                } else {
                    MessageToast.makeText(getActivity(), R.string.error_input_password).show();
                }
                break;
            case R.id.register_password_confirm:
                if (Constants.Validator.MSG_PASSWORD_NOT_MATCH.equals(rule.getFailureMessage())) {
                    MessageToast.makeText(getActivity(), R.string.error_password_not_equal).show();
                } else {
                    MessageToast.makeText(getActivity(), R.string.error_input_confirm_password).show();
                }
                break;
            case R.id.edit_verify_code:
                MessageToast.makeText(getActivity(), R.string.error_input_verify_code).show();
                break;
            case R.id.register_agreement_check:
                MessageToast.makeText(getActivity(), R.string.error_input_agreement).show();
                break;
        }
    }

    protected void obtainCode() {
        String account = String.valueOf(editAccount.getText());
        ObtainCodeRequest param = new ObtainCodeRequest();
        param.setPhoneNumber(account);
        param.setType(ObtainCode.TYPE_REGISTER);
        getPresenter().obtainCode(this, param);
    }

    protected void register() {
        String account = String.valueOf(editAccount.getText());
        String password = String.valueOf(editPassword.getText());
        String confirmPassword = String.valueOf(editConfirmPassword.getText());
        String verifyCode = String.valueOf(editVerifyCode.getText());
        if (!verifyCode.equals(this.verifyCode)) {
            MessageToast.makeText(getActivity(), R.string.error_verify_code_incorrect).show();
            return;
        }
        RegisterRequest param = new RegisterRequest();
        param.setPhone(account);
        param.setPassword(password);
        param.setConfirmPassword(confirmPassword);
        param.setPhoneVerifyCode(verifyCode);
        param.setAccountType(1);
        param.setAcceptTerm(true);
        getPresenter().register(this, param);
    }
}
