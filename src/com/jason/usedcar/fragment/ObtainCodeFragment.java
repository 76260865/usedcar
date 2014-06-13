package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.jason.usedcar.R;
import com.jason.usedcar.model.param.ObtainCodeParam;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter;
import com.jason.usedcar.presenter.ObtainCodeFragmentPresenter.ObtainCodeFragmentUi;
import com.jason.usedcar.view.ObtainCodeButton;
import com.jason.usedcar.view.ObtainCodeButton.OnClickLimitListener;
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

    private ObtainCodeButton buttonObtainCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_reset_password_obtain_code, container, false);
        editPhoneNumber = (EditText) contentView.findViewById(R.id.reset_password_account);
        buttonObtainCode = (ObtainCodeButton) contentView.findViewById(R.id.reset_password_obtain_code);
        buttonObtainCode.setOnClickListener(this);
        buttonObtainCode.setLimitListener(new OnClickLimitListener() {
            @Override
            public void onClickLimited() {
                Toast.makeText(getActivity(), "请不要重复获取短信验证码", Toast.LENGTH_SHORT).show();
            }
        });
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
                buttonObtainCode.reset();
                BaseDialogFragment.newInstance(getString(R.string.error_input_account)).show(getFragmentManager());
                break;
        }
    }

    private void obtainCode() {
        String phoneNumber = String.valueOf(editPhoneNumber.getText());
        ObtainCodeParam param = new ObtainCodeParam();
        param.setPhoneNumber(phoneNumber);
        getPresenter().obtainCode(getActivity(), param);
    }
}
