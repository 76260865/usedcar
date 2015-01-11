package com.jason.usedcar;

import android.webkit.WebView;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-10-23.
 */

public class WebViewAttribute3 implements PropertyViewAttribute<WebView, Boolean> {

    @Override
    public void updateView(final WebView view, final Boolean newValue) {
        view.getSettings().setUseWideViewPort(newValue);
    }
}
