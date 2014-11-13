package com.jason.usedcar.pm.view;

/**
 * @author t77yq @2014-09-18.
 */
public interface ViewFeedback extends ViewBase {

    void feedbackBegin();

    void feedbackEnd(boolean success);
}
