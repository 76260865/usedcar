package com.jason.usedcar.model.data;

/**
 * @author t77yq @2014-09-20.
 */
public class SProduct {

    public Product product;

    public boolean isEditable;

    public SProduct(final Product product, final boolean isEditable) {
        this.product = product;
        this.isEditable = isEditable;
    }
}
