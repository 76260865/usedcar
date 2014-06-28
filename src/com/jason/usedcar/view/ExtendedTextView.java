package com.jason.usedcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * This is an extended EditText with a Prefix and Suffix.
 *
 * As used by "Allowance" on Google Play (v1.1)
 */
public class ExtendedTextView extends TextView {

    public ExtendedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextDrawable leftDrawable = new TextDrawable(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExtendedTextView);
        leftDrawable.setText(a.getString(R.styleable.ExtendedTextView_prefix));
        leftDrawable.setTextColor(a.getColor(R.styleable.ExtendedTextView_prefixColor, 0));
        leftDrawable.setRawTextSize(a.getDimensionPixelSize(R.styleable.ExtendedTextView_prefixSize, 15));
        setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
    }

}
