package com.jason.usedcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import com.jason.usedcar.R;

/**
 * This is an extended EditText with a Prefix and Suffix.
 *
 * As used by "Allowance" on Google Play (v1.1)
 */
public class ExtendedEditText extends EditText {

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtendedTextView);
        TextDrawable drawable = new TextDrawable(context);
        drawable.setText(a.getString(R.styleable.ExtendedTextView_prefix));
        drawable.setTextColor(a.getColor(R.styleable.ExtendedTextView_prefixColor, 0));
        drawable.setRawTextSize(a.getDimensionPixelSize(R.styleable.ExtendedTextView_prefixSize, 15));
        int direction = a.getInt(R.styleable.ExtendedTextView_direction, 0);
        switch (direction) {
            case 0:
                setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case 1:
                setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            case 2:
                setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                break;
            default:
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                break;
        }
    }

}
