package com.jason.usedcar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.request.PhoneRequest;
import com.jason.usedcar.response.Response;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.Required;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-06-22.
 */
public class BindPhoneActivity extends BaseActivity {

    @Required(order = 1)
    private EditText phoneEditText;

    @Required(order = 2)
    private EditText verifyCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        bindViews();
    }

    private void bindViews() {
        phoneEditText = getView(R.id.bind_phone_edit_new_phone);
        verifyCodeEditText = getView(R.id.edit_verify_code);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_obtain_code:
                if (TextUtils.isEmpty(phoneEditText.getText())) {
                    MessageToast.makeText(getApplicationContext(), "请输入绑定的手机号").show();
                }
                break;
            case R.id.bind_phone_btn_ok:
                validator.validate();
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        changePhone();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        switch (view.getId()) {
            case R.id.bind_phone_edit_new_phone:
                MessageToast.makeText(getApplicationContext(), "请输入绑定的手机号").show();
                break;
            case R.id.edit_verify_code:
                MessageToast.makeText(getApplicationContext(), "请输入收到的短信验证码").show();
                break;
        }
    }

    private void changePhone() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        PhoneRequest phoneRequest = new PhoneRequest();
        phoneRequest.setAccessToken(Application.fromActivity(this).getAccessToken());
        phoneRequest.setPhoneNumber(String.valueOf(phoneEditText.getText()));
        new RestClient().bindNewPhone(phoneRequest, new Callback<Response>() {
            @Override
            public void success(final Response response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
