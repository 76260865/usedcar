package com.jason.usedcar;

import android.webkit.WebView;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

/**
 * @author t77yq @2014-10-23.
 */
public class WebViewBinding implements ViewBinding<WebView> {

    @Override
    public void mapBindingAttributes(final BindingAttributeMappings<WebView> mappings) {
        mappings.mapProperty(WebViewAttribute.class, "url");
    }
}
