package com.jason.usedcar;

import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-10-23.
 */

public class ImageViewAttribute2 implements PropertyViewAttribute<ImageView, String> {

    @Override
    public void updateView(final ImageView view, final String newValue) {
        if (!TextUtils.isEmpty(newValue)) {
            Glide.with(view.getContext()).load(newValue).into(view);
        }
    }
}
