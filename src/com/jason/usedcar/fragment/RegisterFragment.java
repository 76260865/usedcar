package com.jason.usedcar.fragment;

import com.jason.usedcar.R;
import com.jason.usedcar.pm.RegisterPM;
import com.jason.usedcar.pm.view.ViewRegister;

public class RegisterFragment extends AbsFragment implements ViewRegister {

    @Override
    protected int layoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected Object presentationModel() {
        return new RegisterPM(this);
    }
}
