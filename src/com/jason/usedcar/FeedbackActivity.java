package com.jason.usedcar;

import android.os.Bundle;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.presentation_model.FeedbackViewModel;
import com.jason.usedcar.presentation_model.ViewFeedback;

/**
 * @author t77yq @2014-08-17.
 */
public class FeedbackActivity extends AbsActivity implements ViewFeedback {

    private LoadingFragment loadingFragment;

    private FeedbackViewModel feedbackViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackViewModel = new FeedbackViewModel(this);
        initContentView(R.layout.activity_feedback, feedbackViewModel);
        setTitle("意见反馈");
    }

    @Override
    public void feedbackBegin() {
        loadingFragment = LoadingFragment.newInstance("请等待...");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void feedbackEnd(final boolean success) {
        loadingFragment.dismiss();
        loadingFragment = null;
    }
}
