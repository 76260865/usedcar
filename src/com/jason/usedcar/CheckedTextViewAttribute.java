package com.jason.usedcar;

import android.widget.CheckedTextView;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-11-06.
 */
public class CheckedTextViewAttribute implements PropertyViewAttribute<CheckedTextView, Boolean> {
    @Override
    public void updateView(CheckedTextView view, Boolean newValue) {
        view.setChecked(newValue);
    }
}
