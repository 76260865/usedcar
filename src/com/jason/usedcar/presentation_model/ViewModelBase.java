package com.jason.usedcar.presentation_model;

import org.robobinding.aspects.PresentationModel;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class ViewModelBase {

    protected int contentVisibility;

    protected int progressVisibility;

    protected int nothingVisibility;

    public int getContentVisibility() {
        return contentVisibility;
    }

    public int getProgressVisibility() {
        return progressVisibility;
    }

    public int getNothingVisibility() {
        return nothingVisibility;
    }
}
