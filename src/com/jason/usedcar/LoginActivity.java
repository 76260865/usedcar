package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import com.jason.usedcar.pm.LoginPM;
import com.jason.usedcar.pm.view.ViewLogin;

public class LoginActivity extends AbsActivity implements ViewLogin {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_login, new LoginPM(this));
    }

    @Override
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void resetPassword() {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }
}
