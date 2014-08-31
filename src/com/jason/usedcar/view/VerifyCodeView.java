package com.jason.usedcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.jason.usedcar.R;

/**
 * @author t77yq @2014-06-23.
 */
public class VerifyCodeView extends LinearLayout {

    public VerifyCodeView(Context context) {
        super(context);
        inflate(context, R.layout.verify_code, this);
        setOrientation(HORIZONTAL);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.verify_code, this);
        setOrientation(HORIZONTAL);
    }
}
