package com.jason.usedcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.internal.widget.LinearLayoutICS;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.jason.usedcar.R;

/**
 * @author t77yq @14-6-23.
 */
public class IconEditText extends LinearLayoutICS {

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_icon_edit_text, this);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.IconEditText);
        ImageView icon = findById(R.id.icon);
        icon.setImageResource(arr.getResourceId(R.styleable.IconEditText_src, 0));
        EditText editText = findById(R.id.edit);
        editText.setId(arr.getResourceId(R.styleable.IconEditText_editId, 0));
        editText.getPaint().setTextSize(arr.getDimensionPixelSize(R.styleable.IconEditText_textSize, 15));
        editText.setTextColor(arr.getColor(R.styleable.IconEditText_textColor, 0));
        editText.setHintTextColor(arr.getColor(R.styleable.IconEditText_textColorHint, 0));
        editText.setHint(arr.getString(R.styleable.IconEditText_hint));
        setAddStatesFromChildren(true);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findById(int resId) {
        return (T) findViewById(resId);
    }
}
