package com.jason.usedcar;

import android.os.Bundle;

/**
 * @author t77yq @14-08-17.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
        setContentView(R.layout.activity_about);
    }
}
