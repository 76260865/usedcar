package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jason.usedcar.R;
import com.jason.usedcar.model.Dealer;
import com.jason.usedcar.presenter.DealerRegisterFragmentPresenter;
import com.jason.usedcar.presenter.DealerRegisterFragmentPresenter.CallButtonUi;

public class DealerRegisterFragment
        extends
        BaseFragment<DealerRegisterFragmentPresenter, DealerRegisterFragmentPresenter.CallButtonUi>
        implements DealerRegisterFragmentPresenter.CallButtonUi {
    private static final String TAG = "DealerRegisterFragment";

    private EditText mEditPhone;
    private EditText mEditValidateCode;

    private EditText mEditDealerName;
    private EditText mEditDealerAddr;
    private RadioGroup mChkDealerType;

    private EditText mEditPwd;
    private EditText mEditRePwd;
    private CheckBox mChkAcceptRule;
    private Button mBtnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_dealer_register_layout, container, false);
        mEditPhone = (EditText) rootView.findViewById(R.id.edit_register_phone);
        mEditValidateCode = (EditText) rootView
                .findViewById(R.id.edit_validate_code);
        mEditDealerName = (EditText) rootView
                .findViewById(R.id.edit_dealer_name);
        mEditDealerAddr = (EditText) rootView
                .findViewById(R.id.edit_dealer_addr);
        mChkDealerType = (RadioGroup) rootView
                .findViewById(R.id.rdgp_dealer_type);
        mEditPwd = (EditText) rootView.findViewById(R.id.edit_pwd);
        mEditRePwd = (EditText) rootView.findViewById(R.id.edit_repwd);
        mChkAcceptRule = (CheckBox) rootView.findViewById(R.id.chk_accept);
        mBtnRegister = (Button) rootView.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(mOnClickListener);
        return rootView;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Dealer dealer = new Dealer();
            dealer.setPhone(mEditPhone.getText().toString());
            dealer.setPhoneVerifyCode(mEditValidateCode.getText().toString());

            dealer.setPhoneVerifyCode(mEditDealerName.getText().toString());
            dealer.setPhoneVerifyCode(mEditDealerAddr.getText().toString());
            // dealer.setPhoneVerifyCode(mChkDealerType.getCheckedRadioButtonId());

            dealer.setPhoneVerifyCode(mEditValidateCode.getText().toString());
            dealer.setPhoneVerifyCode(mEditValidateCode.getText().toString());

            dealer.setPassword(mEditPwd.getText().toString());
            dealer.setRepassword(mEditRePwd.getText().toString());
            dealer.setAcceptTerm(mChkAcceptRule.isChecked());
            dealer.setNickname("nickname");
            dealer.setAccountType(2);
        }
    };

    @Override
    public void setEnabled(boolean on) {
        // TODO Auto-generated method stub

    }

    @Override
    public void login(String reponse) {
        // TODO Auto-generated method stub

    }

    @Override
    public DealerRegisterFragmentPresenter createPresenter() {
        return new DealerRegisterFragmentPresenter();
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }
}
