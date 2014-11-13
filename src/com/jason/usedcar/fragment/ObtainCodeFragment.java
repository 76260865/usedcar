package com.jason.usedcar.fragment;

import com.jason.usedcar.R;
import com.jason.usedcar.pm.RequestSecurityCodePM;
import com.jason.usedcar.pm.view.ViewRequestSecurityCode;

/**
 * @author t77yq @2014.06.08
 */
public class ObtainCodeFragment extends AbsFragment implements ViewRequestSecurityCode {

    @Override
    protected int layoutId() {
        return R.layout.fragment_reset_password_obtain_code;
    }

    @Override
    protected Object presentationModel() {
        return new RequestSecurityCodePM(this);
    }

    @Override
    public void goToNext(String phoneNumber, String securityCode) {
        getFragmentManager().beginTransaction().add(R.id.fragment_content,
                ResetPasswordFragment.newInstance(phoneNumber, securityCode)).commit();
    }
}
