package com.jason.usedcar.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Parameters.Register;
import com.jason.usedcar.model.Reseller;
import com.jason.usedcar.model.User;
import com.jason.usedcar.presenter.RegisterFragmentPresenter;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.util.HttpUtil;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import java.util.HashMap;
import java.util.Map;

public class ResellerRegisterFragment extends RegisterFragment {

    @Required(order = 4100)
    private EditText editResellerName;

    @Required(order = 4200)
    private EditText editResellerAddress;

    private RadioGroup radioResellerType;

    @Override
    protected void bindViews(View contentView) {
        super.bindViews(contentView);
        editResellerName = (EditText) contentView.findViewById(R.id.register_reseller_name);
        editResellerAddress = (EditText) contentView.findViewById(R.id.register_reseller_address);
        radioResellerType = (RadioGroup) contentView.findViewById(R.id.register_reseller_type);
    }

    @Override
    protected int layout() {
        return R.layout.fragment_reseller_register_layout;
    }

    @Override
    public RegisterFragmentPresenter createPresenter() {
        return new RegisterFragmentPresenter() {
            @Override
            protected String requestUrl() {
                return HttpUtil.SIGN_ON_RESELLER_URI;
            }

            @Override
            protected Map<String, String> buildParams(User user) {
                Map<String, String> params = new HashMap<String, String>();
                if (user instanceof Reseller) {
                    Reseller reseller = (Reseller) user;
                    params.put(Register.ACCOUNT, reseller.getAccount());
                    params.put(Register.ACCOUNT_TYPE, Integer.toString(reseller.getAccountType()));
                    params.put(Register.AGREEMENT, Boolean.toString(reseller.isAcceptTerm()));
                    params.put(Register.PASSWORD, reseller.getPassword());
                    params.put(Register.CONFIRM_PASSWORD, reseller.getConfirmPassword());
                    params.put(Register.VERIFY_CODE, reseller.getVerifyCode());
                    params.put(Register.RESELLER_NAME, reseller.getResellerName());
                    params.put(Register.RESELLER_TYPE, String.valueOf(reseller.getResellerType()));
                    params.put(Register.ADDRESS, reseller.getAddress());
                }
                return params;
            }
        };
    }

    @Override
    protected void register() {
        String account = String.valueOf(editAccount.getText());
        String password = String.valueOf(editPassword.getText());
        String confirmPassword = String.valueOf(editConfirmPassword.getText());
        String verifyCode = String.valueOf(editVerifyCode.getText());
        if (!verifyCode.equals(this.verifyCode)) {
            BaseDialogFragment.newInstance(getString(R.string.error_verify_code_incorrect)).show(getFragmentManager());
            return;
        }
        String resellerName = String.valueOf(editResellerName.getText());
        String resellerAddress = String.valueOf(editResellerAddress.getText());
        int checkedRadioId = radioResellerType.getCheckedRadioButtonId();
        getPresenter().register(getActivity(), new Reseller.Builder()
            .resellerName(resellerName).address(resellerAddress)
            .resellerType(checkedRadioId == R.id.register_used_car_company ? 1 : 2)
            .account(account).verifyCode(verifyCode).password(password)
            .confirmPassword(confirmPassword).acceptTerm(checkAgreement.isChecked())
            .nickname("nickname").accountType(2).build());
    }

    @Override
    public RegisterFragmentUi getUi() {
        return this;
    }

    @Override
    public void onVerifyCodeRequested(String code) {
    }

    @Override
    public void onRegistered() {
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        super.onValidationFailed(view, rule);
        switch (view.getId()) {
            case R.id.register_reseller_name:
                BaseDialogFragment.newInstance(getString(R.string.error_input_reseller_name)).show(getFragmentManager());
                break;
            case R.id.register_reseller_address:
                BaseDialogFragment.newInstance(getString(R.string.error_input_reseller_address)).show(getFragmentManager());
                break;
        }
    }
}
