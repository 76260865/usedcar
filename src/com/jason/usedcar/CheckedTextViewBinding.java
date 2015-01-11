package com.jason.usedcar;

import android.widget.CheckedTextView;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

/**
 * @author t77yq @2014-11-06.
 */
public class CheckedTextViewBinding implements ViewBinding<CheckedTextView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<CheckedTextView> mappings) {
        mappings.mapProperty(CheckedTextViewAttribute.class, "checked");
    }
}
