package com.jason.usedcar.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.jason.usedcar.R;
import com.jason.usedcar.model.param.ResellerSignOnParam;
import com.jason.usedcar.presenter.RegisterFragmentPresenter;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.util.HttpUtil;
import com.jason.usedcar.util.ViewFinder;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;

public class ResellerRegisterFragment extends RegisterFragment {

    @Required(order = 4100)
    private EditText editResellerName;

    @Required(order = 4200)
    private EditText editResellerAddress;

    private RadioGroup radioResellerType;

    @Override
    protected void bindViews(View contentView) {
        super.bindViews(contentView);
        ViewStub viewStub = ViewFinder.findViewById(contentView, R.id.reseller_info);
        viewStub.inflate();
        editResellerName = ViewFinder.findViewById(contentView, R.id.register_reseller_name);
        editResellerAddress = ViewFinder.findViewById(contentView, R.id.register_reseller_address);
        radioResellerType = ViewFinder.findViewById(contentView, R.id.register_reseller_type);
    }

    @Override
    protected int layout() {
        return R.layout.fragment_register;
    }

    @Override
    public RegisterFragmentPresenter createPresenter() {
        return new RegisterFragmentPresenter() {
            @Override
            protected String requestUrl() {
                return HttpUtil.SIGN_ON_RESELLER_URI;
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
        ResellerSignOnParam param = new ResellerSignOnParam();
        param.setPhone(account);
        param.setPassword(password);
        param.setRepassword(confirmPassword);
        param.setPhoneVerifyCode(verifyCode);
        param.setResellerName(resellerName);
        param.setAdress(resellerAddress);
        param.setResellerType(checkedRadioId == R.id.register_used_car_company ? 1 : 2);
        param.setAcceptTerm(true);
        param.setAccountType(2);
        getPresenter().register(getActivity(), param);
    }

    @Override
    public RegisterFragmentUi getUi() {
        return this;
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
