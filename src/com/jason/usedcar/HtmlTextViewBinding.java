package com.jason.usedcar;

import android.widget.TextView;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.widget.textview.TextViewBinding;

/**
 * @author t77yq @2014-11-06.
 */
public class HtmlTextViewBinding extends TextViewBinding {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<TextView> mappings) {
        super.mapBindingAttributes(mappings);
        mappings.mapProperty(HtmlTextViewAttribute.class, "html");
    }
}
