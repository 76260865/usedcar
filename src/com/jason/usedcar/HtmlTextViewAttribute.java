package com.jason.usedcar;

import android.text.Html;
import android.widget.TextView;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-11-06.
 */
public class HtmlTextViewAttribute implements PropertyViewAttribute<TextView, String> {
    @Override
    public void updateView(TextView view, String s) {
        view.setText(Html.fromHtml(s));
    }
}
