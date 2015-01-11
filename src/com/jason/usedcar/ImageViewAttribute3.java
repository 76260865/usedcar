package com.jason.usedcar;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * @author t77yq @2014-10-23.
 */

public class ImageViewAttribute3 implements PropertyViewAttribute<ImageView, Bitmap> {

    @Override
    public void updateView(final ImageView view, final Bitmap bitmap) {
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        }
    }
}
