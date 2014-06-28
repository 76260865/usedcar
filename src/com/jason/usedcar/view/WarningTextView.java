package com.jason.usedcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * @author t77yq @14-6-24.
 */
public class WarningTextView extends TextView {

    public WarningTextView(Context context) {
        super(context);
    }

    public WarningTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WarningTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setText("所有注册信息均按照相关服务条款受到法律保护");
        setTextColor(getResources().getColor(R.color.register_button_color_normal));
    }
}
