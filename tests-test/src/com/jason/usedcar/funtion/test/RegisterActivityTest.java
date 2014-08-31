package com.jason.usedcar.funtion.test;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.jason.usedcar.R;
import com.jason.usedcar.RegisterActivity;
import com.robotium.solo.Solo;
import java.util.concurrent.CountDownLatch;

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
        //mBtnObtainCode = (Button) mRegisterActivity
        //        .getView(R.id.register_obtain_code);
        mEditPhone = (EditText) mRegisterActivity.findViewById(R.id.register_account);
        mEditValidateCode = (EditText) mRegisterActivity.findViewById(R.id.register_verify_code);
        mEditPwd = (EditText) mRegisterActivity.findViewById(R.id.register_password);
        mEditRePwd = (EditText) mRegisterActivity.findViewById(R.id.register_password_confirm);
        mChkAcceptRule = (CheckBox) mRegisterActivity.findViewById(R.id.register_agreement_check);
        mBtnRegister = (Button) mRegisterActivity.findViewById(R.id.register_register);
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
//        assertTrue("onRegistered", mSolo.waitForText("onRegistered"));
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
//            assertTrue("onRegistered", mSolo.waitForText("onRegistered"));
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
