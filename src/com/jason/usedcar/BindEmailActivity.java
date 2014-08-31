package com.jason.usedcar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.jason.usedcar.fragment.LoadingFragment;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;

/**
 * @author t77yq @2014-06-22.
 */
public class BindEmailActivity extends BaseActivity {

    @Required(order = 1)
    private EditText emailEditText;

    @Required(order = 2)
    private EditText verifyCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        bindViews();
    }

    private void bindViews() {
        emailEditText = getView(R.id.bind_email_edit_email);
        verifyCodeEditText = getView(R.id.edit_verify_code);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_obtain_code:
                if (TextUtils.isEmpty(emailEditText.getText())) {
                    MessageToast.makeText(getApplicationContext(), "请输入绑定的邮箱地址").show();
                }
                break;
            case R.id.bind_email_btn_ok:
                validator.validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        changeEmail();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.bind_email_edit_email:
                MessageToast.makeText(getApplicationContext(), "请输入绑定的邮箱地址").show();
                break;
            case R.id.edit_verify_code:
                MessageToast.makeText(getApplicationContext(), "请输入收到的短信验证码").show();
                break;
        }
    }

    private void changeEmail() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        // TODO
    }
}
