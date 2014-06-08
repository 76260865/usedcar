package com.jason.usedcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author t77yq @2014.06.08
 */
public class ObtainCodeButton extends Button {

    public interface OnClickLimitListener {

        public void onClickLimited();
    }

    private static final int CLICK_TIME_LIMIT = 2 * 60 * 1000;

    private long lastClickTime;

    private OnClickLimitListener limitListener;

    public ObtainCodeButton(Context context) {
        super(context);
    }

    public ObtainCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObtainCodeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setLimitListener(OnClickLimitListener limitListener) {
        this.limitListener = limitListener;
    }

    public void reset() {
        lastClickTime = 0;
    }

    @Override
    public boolean performClick() {
        boolean clicked = true;
        if (canClick()) {
            lastClickTime = System.currentTimeMillis();
            clicked = super.performClick();
        } else if (limitListener != null) {
            limitListener.onClickLimited();
        }
        return clicked;
    }

    private boolean canClick() {
        return System.currentTimeMillis() - lastClickTime > CLICK_TIME_LIMIT;
    }
}
