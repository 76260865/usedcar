package com.jason.usedcar;

import android.widget.ImageView;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.widget.imageview.ImageViewBinding;

/**
 * @author t77yq @2014-11-04.
 */
public class ImageViewBinding2 extends ImageViewBinding {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<ImageView> mappings) {
        super.mapBindingAttributes(mappings);
        mappings.mapProperty(ImageViewAttribute2.class, "url");
        mappings.mapProperty(ImageViewAttribute3.class, "src2");
    }
}
