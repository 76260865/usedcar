package com.jason.usedcar.pm;

import android.view.View;
import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-09-29.
 */
@PresentationModel
public class BasePM {

    protected int contentVisibility = View.VISIBLE;

    protected int progressVisibility = View.GONE;

    protected int nothingVisibility = View.GONE;

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
