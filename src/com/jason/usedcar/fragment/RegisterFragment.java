package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.param.ObtainCodeParam;
import com.jason.usedcar.model.param.SignOnParam;
import com.jason.usedcar.presenter.RegisterFragmentPresenter;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.view.ObtainCodeButton;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Rules;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class RegisterFragment extends
    BaseFragment<RegisterFragmentPresenter, RegisterFragmentUi> implements
    RegisterFragmentUi, OnClickListener, ValidationListener {

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

    private ObtainCodeButton buttonObtainCode;

    @Checked(order = 5000)
    protected CheckBox checkAgreement;

    protected String verifyCode;

    private Validator verifyCodeValidator;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        verifyCodeValidator = new Validator(editAccount);
        verifyCodeValidator.put(editAccount, Rules.required("", true));
        verifyCodeValidator.setValidationListener(new ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                obtainCode();
            }

            @Override
            public void onValidationFailed(View view, Rule<?> rule) {
                switch (view.getId()) {
                    case R.id.register_account:
                        buttonObtainCode.reset();
                        BaseDialogFragment.newInstance(getString(R.string.error_input_account)).show(getFragmentManager());
                        break;
                }
            }
        });
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(layout(), container, false);
        bindViews(contentView);
        return contentView;
    }

    protected int layout() {
        return R.layout.fragment_user_register_layout;
    }

    protected void bindViews(View contentView) {
        editAccount = (EditText) contentView.findViewById(R.id.register_account);
        editVerifyCode = (EditText) contentView.findViewById(R.id.register_verify_code);
        editPassword = (EditText) contentView.findViewById(R.id.register_password);
        editConfirmPassword = (EditText) contentView.findViewById(R.id.register_password_confirm);
        checkAgreement = (CheckBox) contentView.findViewById(R.id.register_agreement_check);
        buttonObtainCode = (ObtainCodeButton) contentView.findViewById(R.id.register_obtain_code);
        buttonObtainCode.setOnClickListener(this);
        Button buttonRegister = (Button) contentView.findViewById(R.id.register_register);
        buttonRegister.setOnClickListener(this);
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
            case R.id.register_obtain_code:
                verifyCodeValidator.validate();
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
                BaseDialogFragment.newInstance(getString(R.string.error_input_account)).show(getFragmentManager());
                break;
            case R.id.register_password:
                if (Constants.Validator.MSG_PASSWORD_LENGTH.equals(rule.getFailureMessage())) {
                    BaseDialogFragment.newInstance(getString(R.string.error_password_length,
                        Constants.PASSWORD_LENGTH_MIN, Constants.PASSWORD_LENGTH_MAX)).show(getFragmentManager());
                } else {
                    BaseDialogFragment.newInstance(getString(R.string.error_input_password)).show(getFragmentManager());
                }
                break;
            case R.id.register_password_confirm:
                if (Constants.Validator.MSG_PASSWORD_NOT_MATCH.equals(rule.getFailureMessage())) {
                    BaseDialogFragment.newInstance(getString(R.string.error_password_not_equal)).show(getFragmentManager());
                } else {
                    BaseDialogFragment.newInstance(getString(R.string.error_input_confirm_password)).show(getFragmentManager());
                }
                break;
            case R.id.register_verify_code:
                BaseDialogFragment.newInstance(getString(R.string.error_input_verify_code)).show(getFragmentManager());
                break;
            case R.id.register_agreement_check:
                BaseDialogFragment.newInstance(getString(R.string.error_input_agreement)).show(getFragmentManager());
                break;
        }
    }

    protected void obtainCode() {
        String account = String.valueOf(editAccount.getText());
        ObtainCodeParam param = new ObtainCodeParam();
        param.setPhoneNumber(account);
        getPresenter().obtainCode(getActivity(), param);
    }

    protected void register() {
        String account = String.valueOf(editAccount.getText());
        String password = String.valueOf(editPassword.getText());
        String confirmPassword = String.valueOf(editConfirmPassword.getText());
        String verifyCode = String.valueOf(editVerifyCode.getText());
        if (!verifyCode.equals(this.verifyCode)) {
            BaseDialogFragment.newInstance(getString(R.string.error_verify_code_incorrect)).show(getFragmentManager());
            return;
        }
        SignOnParam param = new SignOnParam();
        param.setPhone(account);
        param.setPassword(password);
        param.setRepassword(confirmPassword);
        param.setPhoneVerifyCode(verifyCode);
        param.setAccountType(1);
        param.setAcceptTerm(true);
        getPresenter().register(getActivity(), param);
    }
}
