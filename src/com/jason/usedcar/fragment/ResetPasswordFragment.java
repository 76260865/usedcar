package com.jason.usedcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import com.jason.usedcar.R;
import com.jason.usedcar.pm.ResetPasswordPM;

/**
 * @author t77yq @2014.06.08
 */
public class ResetPasswordFragment extends AbsFragment {

    private static final String PHONE_NUMBER = "phoneNumber";

    private static final String VERIFY_CODE = "verifyCode";

    public static ResetPasswordFragment newInstance(String phoneNumber, String verifyCode) {
        Bundle args = new Bundle();
        args.putString(PHONE_NUMBER, phoneNumber);
        args.putString(VERIFY_CODE, verifyCode);
        ResetPasswordFragment instance = new ResetPasswordFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_reset_password_set_new_password;
    }

    @Override
    protected Object presentationModel() {
        Intent intent = getActivity().getIntent();
        return new ResetPasswordPM(this, intent.getStringExtra(PHONE_NUMBER), intent.getStringExtra(VERIFY_CODE));
    }
}
