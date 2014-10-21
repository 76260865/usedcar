package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jason.usedcar.Application;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.ResetPasswordByPhoneRequest;
import com.jason.usedcar.presenter.ResetPasswordFragmentPresenter;
import com.jason.usedcar.presenter.ResetPasswordFragmentPresenter.ResetPasswordFragmentUi;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

/**
 * @author t77yq @2014.06.08
 */
public class ResetPasswordFragment extends
        BaseFragment<ResetPasswordFragmentPresenter, ResetPasswordFragmentUi> implements
        ResetPasswordFragmentUi, OnClickListener {

    private static final String PHONE_NUMBER = "phoneNumber";

    private static final String VERIFY_CODE = "verifyCode";

    @Required(order = 1)
    private EditText editVerifyCode;

    @Password(order = 2)
    @TextRule(order = 3, minLength = 6, maxLength = 15, message = Constants.Validator.MSG_PASSWORD_LENGTH)
    private EditText editNewPassword;

    @Required(order = 4)
    @ConfirmPassword(order = 5, message = Constants.Validator.MSG_PASSWORD_NOT_MATCH)
    private EditText editConfirmNewPassword;

    private String verifyCode;

    public static ResetPasswordFragment newInstance(String phoneNumber, String verifyCode) {
        Bundle args = new Bundle();
        args.putString(PHONE_NUMBER, phoneNumber);
        args.putString(VERIFY_CODE, verifyCode);
        ResetPasswordFragment instance = new ResetPasswordFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_reset_password_set_new_password,
                container, false);
        TextView textAccount = getView(contentView, R.id.reset_password_account);
        Button buttonObtainCode = getView(contentView, R.id.reset_password_obtain_code);
        buttonObtainCode.setOnClickListener(this);
        editVerifyCode = getView(contentView, R.id.reset_password_verify_code);
        editNewPassword = getView(contentView, R.id.reset_password_new_password);
        editConfirmNewPassword = getView(contentView, R.id.reset_password_confirm_password);
        Button buttonResetPassword = getView(contentView, R.id.reset_password_confirm);
        buttonResetPassword.setOnClickListener(this);
        Bundle args = getArguments();
        if (args != null) {
            textAccount.setText(args.getString(PHONE_NUMBER));
            verifyCode = args.getString(VERIFY_CODE);
        }
        return contentView;
    }

    @Override
    public ResetPasswordFragmentPresenter createPresenter() {
        return new ResetPasswordFragmentPresenter();
    }

    @Override
    public ResetPasswordFragmentUi getUi() {
        return this;
    }

    @Override
    public void onPasswordReset() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_confirm:
                getValidator().validate();
                break;
            case R.id.reset_password_obtain_code:
                obtainCode();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        resetPassword();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        super.onValidationFailed(view, rule);
        switch (view.getId()) {
            case R.id.reset_password_verify_code:
                MessageToast.makeText(getActivity(), R.string.error_input_verify_code).show();
                break;
            case R.id.reset_password_new_password:
                if (Constants.Validator.MSG_PASSWORD_LENGTH.equals(rule.getFailureMessage())) {
                    MessageToast.makeText(getActivity(),
                            getString(R.string.error_password_length, Constants.PASSWORD_LENGTH_MIN,
                                    Constants.PASSWORD_LENGTH_MAX)).show();
                } else {
                    MessageToast.makeText(getActivity(), R.string.error_input_password).show();
                }
                break;
            case R.id.reset_password_confirm_password:
                if (Constants.Validator.MSG_PASSWORD_NOT_MATCH.equals(rule.getFailureMessage())) {
                    MessageToast.makeText(getActivity(), R.string.error_password_not_equal).show();
                } else {
                    MessageToast.makeText(getActivity(), R.string.error_input_confirm_password).show();
                }
                break;
        }
    }

    private void resetPassword() {
        String verifyCode = String.valueOf(editVerifyCode.getText());
//        if (!verifyCode.equals(this.verifyCode)) {
//            MessageToast.makeText(getActivity(), R.string.error_verify_code_incorrect).show();
//            return;
//        }
        String newPassword = String.valueOf(editNewPassword.getText());
        String confirmPassword = String.valueOf(editConfirmNewPassword.getText());
        String phoneNum = getArguments().getString(PHONE_NUMBER);
        ResetPasswordByPhoneRequest param = new ResetPasswordByPhoneRequest();
        param.setNewPassword(newPassword);
        param.setConfirmPassword(confirmPassword);
        param.setPhone(phoneNum);
        param.setCode(verifyCode);
        param.setAccessToken(Application.fromActivity(getActivity()).getAccessToken());
        getPresenter().resetPassword(this, param);
    }

    private void obtainCode() {
        ObtainCodeRequest request = new ObtainCodeRequest();
        request.setPhoneNumber(getArguments().getString(PHONE_NUMBER));
        request.setType(Constants.ObtainCode.TYPE_RESET_PASSWORD);
        getPresenter().obtainCode(this, request);
    }
}
