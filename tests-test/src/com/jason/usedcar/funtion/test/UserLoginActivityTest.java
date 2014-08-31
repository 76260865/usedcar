package com.jason.usedcar.funtion.test;

import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jason.usedcar.LoginActivity;
import com.jason.usedcar.RegisterActivity;
import com.jason.usedcar.interfaces.IJobListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UserLoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mLoginActivity;
    private EditText mEditUserName;
    private EditText mEditUserPwd;
    private Button mBtnLogin;
    private TextView mTxtRegister;

    public UserLoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mLoginActivity = getActivity();
        mEditUserName = (EditText) mLoginActivity
                .findViewById(com.jason.usedcar.R.id.login_edit_username);
        mEditUserPwd = (EditText) mLoginActivity.findViewById(com.jason.usedcar.R.id.login_edit_password);
        mBtnLogin = (Button) mLoginActivity.findViewById(com.jason.usedcar.R.id.login_btn_login);
        mTxtRegister = (TextView) mLoginActivity
                .findViewById(com.jason.usedcar.R.id.login_btn_register);
    }

    public void testPreconditions() {
        assertNotNull("mLoginActivity is null", mLoginActivity);
        assertNotNull("mEditUserName is null", mEditUserName);
        assertNotNull("mEditUserPwd is null", mEditUserPwd);
        assertNotNull("mBtnLogin is null", mBtnLogin);
    }

    public void testLogin() throws InterruptedException {
        // create CountDownLatch for which the test can wait.
        final CountDownLatch latch = new CountDownLatch(1);
        mLoginActivity.setListener(new IJobListener() {

            @Override
            public void executionDone() {
                latch.countDown();
            }
        });
        getInstrumentation().runOnMainSync(new Runnable() {

            @Override
            public void run() {
                mEditUserName.setText("15008488463");
                mEditUserPwd.setText("admin123");
            }
        });
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, mBtnLogin);
        getInstrumentation().waitForIdleSync();
        boolean await = latch.await(30, TimeUnit.SECONDS);
        assertTrue(await);
        assertTrue("Login Failed", mLoginActivity.getResult());
    }

    public void testRegister() {
        // Set up an ActivityMonitor
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(
                RegisterActivity.class.getName(), null, false);
        // Validate that registerActivity is started
        TouchUtils.clickView(this, mTxtRegister);
        RegisterActivity registerActivity = (RegisterActivity) activityMonitor
                .waitForActivityWithTimeout(1000);
        assertNotNull("RegisterActivity is null", registerActivity);
        assertEquals("Monitor for RegisterActivity has not been called", 1,
                activityMonitor.getHits());
        assertEquals("Activity is of wrong type", RegisterActivity.class,
            ((FragmentActivity) registerActivity).getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(activityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
