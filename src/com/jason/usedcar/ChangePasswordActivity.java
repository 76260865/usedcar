package com.jason.usedcar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jason.usedcar.util.ViewFinder;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * Created by t77yq on 14-6-22.
 */
public class ChangePasswordActivity extends ActionBarActivity implements ValidationListener, OnClickListener {

    @Required(order = 1)
    private EditText verifyCodeEdit;

    @Required(order = 2)
    private EditText oldPasswordEdit;

    @Password(order = 3)
    private EditText newPasswordEdit;

    @Required(order = 4, message = "empty")
    @ConfirmPassword(order = 5, message = "confirm")
    private EditText confirmNewPasswordEdit;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        TextView phoneText = ViewFinder.findViewById(this, R.id.change_password_bind_phone);
        phoneText.setText(getString(R.string.change_password_bind_phone, getIntent().getStringExtra("phone")));
        verifyCodeEdit = ViewFinder.findViewById(this, R.id.register_verify_code);
        ViewFinder.findViewById(this, R.id.register_obtain_code).setOnClickListener(this);
        oldPasswordEdit = ViewFinder.findViewById(this, R.id.change_password_old_password);
        newPasswordEdit = ViewFinder.findViewById(this, R.id.change_password_new_password);
        confirmNewPasswordEdit = ViewFinder.findViewById(this, R.id.change_password_new_password_confirm);
        ViewFinder.findViewById(this, R.id.register_register).setOnClickListener(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.register_verify_code:
                Toast.makeText(getApplicationContext(), "还没填写验证码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_password_old_password:
                Toast.makeText(getApplicationContext(), "还没填写旧密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_password_new_password:
                Toast.makeText(getApplicationContext(), "还没填写新密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_password_new_password_confirm:
                if (rule.getFailureMessage().equals("empty")) {
                    Toast.makeText(getApplicationContext(), "还没填写确认密码", Toast.LENGTH_SHORT).show();
                } else if (rule.getFailureMessage().equals("confirm")) {
                    Toast.makeText(getApplicationContext(), "确认密码与新密码不一致", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_obtain_code:
                break;
            case R.id.register_register:
                validator.validate();
                break;
        }
    }
}
