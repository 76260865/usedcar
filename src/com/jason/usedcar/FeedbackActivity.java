package com.jason.usedcar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * @author t77yq @2014-08-17.
 */
public class FeedbackActivity extends BaseActivity {

    private EditText contentEditText;

    private EditText contactEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("意见反馈");
        contactEditText = (EditText) findViewById(R.id.editFeedback);
        contactEditText = (EditText) findViewById(R.id.editContact);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnFeedback:
                finish();
                break;
        }
    }
}
