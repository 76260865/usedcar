package com.jason.usedcar.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.presenter.RegisterFragmentPresenter;
import com.jason.usedcar.presenter.RegisterFragmentPresenter.RegisterFragmentUi;
import com.jason.usedcar.presenter.RegisterResellerFragmentPresenter;
import com.jason.usedcar.request.RegisterResellerRequest;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;

public class ResellerRegisterFragment extends RegisterFragment {

    @Required(order = 4100)
    private EditText editResellerName;

    @Required(order = 4200)
    private EditText editResellerAddress;

    private RadioGroup radioResellerType;

    @Override
    protected void bindViews(View view) {
        super.bindViews(view);
        ViewStub viewStub = getView(view, R.id.reseller_info);
        viewStub.inflate();
        editResellerName = getView(view, R.id.register_reseller_name);
        editResellerAddress = getView(view, R.id.register_reseller_address);
        radioResellerType = getView(view, R.id.register_reseller_type);
    }

    @Override
    protected int layout() {
        return R.layout.fragment_register;
    }

    @Override
    public RegisterFragmentPresenter createPresenter() {
        return new RegisterResellerFragmentPresenter() {
        };
    }

    @Override
    protected void register() {
        String account = String.valueOf(editAccount.getText());
        String password = String.valueOf(editPassword.getText());
        String confirmPassword = String.valueOf(editConfirmPassword.getText());
        String verifyCode = String.valueOf(editVerifyCode.getText());
//        if (!verifyCode.equals(this.verifyCode)) {
//            MessageToast.makeText(getActivity(), R.string.error_verify_code_incorrect).show();
//            return;
//        }
        String resellerName = String.valueOf(editResellerName.getText());
        String resellerAddress = String.valueOf(editResellerAddress.getText());
        int checkedRadioId = radioResellerType.getCheckedRadioButtonId();
        RegisterResellerRequest param = new RegisterResellerRequest();
        param.setPhone(account);
        param.setPassword(password);
        param.setConfirmPassword(confirmPassword);
        param.setPhoneVerifyCode(verifyCode);
        param.setAccountType(2);
        param.setAcceptTerm(true);
        param.setResellerType(checkedRadioId == R.id.register_used_car_company ? 1 : 2);
        param.setResellerName(resellerName);
        param.setAddress(resellerAddress);
        ((RegisterResellerFragmentPresenter) getPresenter()).registerReseller(this, param);
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
                MessageToast.makeText(getActivity(), R.string.error_input_reseller_name).show();
                break;
            case R.id.register_reseller_address:
                MessageToast.makeText(getActivity(), R.string.error_input_reseller_address).show();
                break;
        }
    }
}
