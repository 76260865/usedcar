package com.jason.usedcar.funtion.test;

import java.util.concurrent.CountDownLatch;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jason.usedcar.R;
import com.jason.usedcar.RegisterActivity;
import com.jason.usedcar.interfaces.IJobListener;
import com.robotium.solo.Solo;

public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
    private Solo mSolo;
    private RegisterActivity mRegisterActivity;
    private Button mBtnObtainCode;
    private EditText mEditPhone;
    private EditText mEditValidateCode;
    private EditText mEditPwd;
    private EditText mEditRePwd;
    private CheckBox mChkAcceptRule;
    private Button mBtnRegister;

    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mRegisterActivity = getActivity();
        mSolo = new Solo(getInstrumentation(), mRegisterActivity);
        mBtnObtainCode = (Button) mRegisterActivity
                .findViewById(com.jason.usedcar.R.id.btn_obtain_code);
        mEditPhone = (EditText) mRegisterActivity.findViewById(R.id.edit_register_phone);
        mEditValidateCode = (EditText) mRegisterActivity.findViewById(R.id.edit_validate_code);
        mEditPwd = (EditText) mRegisterActivity.findViewById(R.id.edit_pwd);
        mEditRePwd = (EditText) mRegisterActivity.findViewById(R.id.edit_repwd);
        mChkAcceptRule = (CheckBox) mRegisterActivity.findViewById(R.id.chk_accept);
        mBtnRegister = (Button) mRegisterActivity.findViewById(R.id.btn_register);
    }

    public void testPreconditions() {
        assertNotNull("mUserLoginActivity is null", mRegisterActivity);
        assertNotNull("mBtnObtainCode is null", mBtnObtainCode);
    }

    public void testObtainVerifyCode() throws InterruptedException {
        // create CountDownLatch for which the test can wait.
        final CountDownLatch latch = new CountDownLatch(1);
        TouchUtils.clickView(this, mBtnObtainCode);
        getInstrumentation().waitForIdleSync();
        // boolean await = latch.await(30, TimeUnit.SECONDS);
        // assertTrue(await);
        getInstrumentation().runOnMainSync(new Runnable() {

            @Override
            public void run() {
                mEditPhone.setText("15008488463");
                mEditPwd.setText("admin123");
                mChkAcceptRule.setChecked(true);
            }
        });
        getInstrumentation().waitForIdleSync();
//        TouchUtils.clickView(this, mBtnRegister);
//        assertTrue("onUserRegistered", mSolo.waitForText("onUserRegistered"));
    }

    public void testRegisterUser() throws InterruptedException {
        TouchUtils.clickView(this, mBtnObtainCode);
        getInstrumentation().waitForIdleSync();
        getInstrumentation().runOnMainSync(new Runnable() {

            @Override
            public void run() {
                mEditPhone.setText("15008488463");
                mEditPwd.setText("admin123");
                mChkAcceptRule.setChecked(true);
            }
        });
        boolean ret = waitFor(10000, new ICallBack() {

            @Override
            public boolean callBack() {
                return !TextUtils.isEmpty(mEditValidateCode.getText().toString());
            }

        });
        assertTrue("get validate code failed", ret);
        if (ret) {
            TouchUtils.clickView(this, mBtnRegister);
//            assertTrue("onUserRegistered", mSolo.waitForText("onUserRegistered"));
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private interface ICallBack {
        boolean callBack();
    }

    private boolean waitFor(int timeout, ICallBack callback) throws InterruptedException {

        long endTime = SystemClock.uptimeMillis() + timeout;

        while (SystemClock.uptimeMillis() <= endTime) {
            Thread.sleep(100);

            if (callback != null && callback.callBack()) {
                return true;
            }
        }
        return false;
    }
}
