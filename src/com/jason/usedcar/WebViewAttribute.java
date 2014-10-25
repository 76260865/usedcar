package com.jason.usedcar;

import android.webkit.WebView;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-10-23.
 */

public class WebViewAttribute implements PropertyViewAttribute<WebView, CharSequence> {

    @Override
    public void updateView(final WebView view, final CharSequence newValue) {
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(String.valueOf(newValue));
    }
}
