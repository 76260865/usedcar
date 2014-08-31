package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */

public class ImageUploadRequest extends Request {

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }
}
