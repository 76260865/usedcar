package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants.ObtainCode;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter.ObtainCodeFragmentUi;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * @author t77yq @2014.06.08
 */
public class ObtainCodeFragment extends
        BaseFragment<ObtainCodeFragmentPresenter, ObtainCodeFragmentUi> implements
    OnClickListener, ObtainCodeFragmentUi, ValidationListener {

    @Required(order = 1)
    private EditText editPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_reset_password_obtain_code, container, false);
        editPhoneNumber = getView(contentView, R.id.reset_password_account);
        Button buttonObtainCode = getView(contentView, R.id.reset_password_obtain_code);
        buttonObtainCode.setOnClickListener(this);
        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_obtain_code:
                getValidator().validate();
                break;
        }
    }

    @Override
    public ObtainCodeFragmentPresenter createPresenter() {
        return new ObtainCodeFragmentPresenter();
    }

    @Override
    public ObtainCodeFragmentUi getUi() {
        return this;
    }

    @Override
    public void onCodeObtained(String code) {
        getFragmentManager().beginTransaction().add(R.id.fragment_content,
            ResetPasswordFragment.newInstance(String.valueOf(editPhoneNumber.getText()), code))
            .commit();
    }

    @Override
    public void onValidationSucceeded() {
        obtainCode();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.reset_password_account:
                MessageToast.makeText(getActivity(), R.string.error_input_account).show();
                break;
        }
    }

    private void obtainCode() {
        String phoneNumber = String.valueOf(editPhoneNumber.getText());
        ObtainCodeRequest param = new ObtainCodeRequest();
        param.setPhoneNumber(phoneNumber);
        param.setType(ObtainCode.TYPE_RESET_PASSWORD);
        getPresenter().obtainCode(this, param);
    }
}
