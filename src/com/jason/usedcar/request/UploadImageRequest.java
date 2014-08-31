package com.jason.usedcar.request;

public class UploadImageRequest extends Request {

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(final byte[] image) {
        this.image = image;
    }
}
