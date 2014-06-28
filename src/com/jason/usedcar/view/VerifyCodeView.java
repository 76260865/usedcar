package com.jason.usedcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.jason.usedcar.R;

/**
 * @author t77yq @14-6-23.
 */
public class VerifyCodeView extends RelativeLayout {

    public VerifyCodeView(Context context) {
        super(context);
        inflate(context, R.layout.verify_code, this);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.verify_code, this);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.verify_code, this);
    }
}
