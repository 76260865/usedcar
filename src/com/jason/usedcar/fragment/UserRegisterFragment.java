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

import com.jason.usedcar.R;
import com.jason.usedcar.model.User;
import com.jason.usedcar.presenter.UserRegisterFragmentPresenter;
import com.jason.usedcar.presenter.UserRegisterFragmentPresenter.CallButtonUi;

public class UserRegisterFragment
        extends
        BaseFragment<UserRegisterFragmentPresenter, UserRegisterFragmentPresenter.CallButtonUi>
        implements UserRegisterFragmentPresenter.CallButtonUi {
    private static final String TAG = "UserRegisterFragment";

    private EditText mEditPhone;
    private EditText mEditValidateCode;
    private EditText mEditPwd;
    private EditText mEditRePwd;
    private CheckBox mChkAcceptRule;
    private Button mBtnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_user_register_layout, container, false);
        mEditPhone = (EditText) rootView.findViewById(R.id.edit_register_phone);
        mEditValidateCode = (EditText) rootView
                .findViewById(R.id.edit_validate_code);
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
            User user = new User();
            user.setPhone(mEditPhone.getText().toString());
            user.setPhoneVerifyCode(mEditValidateCode.getText().toString());
            user.setPassword(mEditPwd.getText().toString());
            user.setRepassword(mEditRePwd.getText().toString());
            user.setAcceptTerm(mChkAcceptRule.isChecked());
            user.setNickname("nickname");
            user.setAccountType(1);
            getPresenter().registerUser(getActivity().getApplicationContext(),
                    user);
        }
    };

    @Override
    public UserRegisterFragmentPresenter createPresenter() {
        return new UserRegisterFragmentPresenter();
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }

    @Override
    public void onUserRegistered() {
        Log.d(TAG, "onUserRegistered");
    }
}
