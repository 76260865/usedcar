package com.jason.usedcar.funtion.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jason.usedcar.RegisterActivity;
import com.jason.usedcar.UserLoginActivity;
import com.jason.usedcar.interfaces.IJobListener;

public class UserLoginActivityTest extends ActivityInstrumentationTestCase2<UserLoginActivity> {

    private UserLoginActivity mUserLoginActivity;
    private EditText mEditUserName;
    private EditText mEditUserPwd;
    private Button mBtnLogin;
    private TextView mTxtRegister;

    public UserLoginActivityTest() {
        super(UserLoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mUserLoginActivity = getActivity();
        mEditUserName = (EditText) mUserLoginActivity
                .findViewById(com.jason.usedcar.R.id.edit_user_name);
        mEditUserPwd = (EditText) mUserLoginActivity.findViewById(com.jason.usedcar.R.id.edit_pwd);
        mBtnLogin = (Button) mUserLoginActivity.findViewById(com.jason.usedcar.R.id.btn_login);
        mTxtRegister = (TextView) mUserLoginActivity
                .findViewById(com.jason.usedcar.R.id.txt_register);
    }

    public void testPreconditions() {
        assertNotNull("mUserLoginActivity is null", mUserLoginActivity);
        assertNotNull("mEditUserName is null", mEditUserName);
        assertNotNull("mEditUserPwd is null", mEditUserPwd);
        assertNotNull("mBtnLogin is null", mBtnLogin);
    }

    public void testLogin() throws InterruptedException {
        // create CountDownLatch for which the test can wait.
        final CountDownLatch latch = new CountDownLatch(1);
        mUserLoginActivity.setListener(new IJobListener() {

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
        assertTrue("Login Failed", mUserLoginActivity.getResult());
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
                registerActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(activityMonitor);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
