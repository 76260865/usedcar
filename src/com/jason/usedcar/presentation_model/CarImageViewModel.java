package com.jason.usedcar.presentation_model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jason.usedcar.R;
import com.jason.usedcar.response.CarResponse3;
import org.robobinding.aspects.PresentationModel;

/**
 * @author t77yq @2014-10-15.
 */
@PresentationModel
public class CarImageViewModel {

    private Context context;

    private CarResponse3 carResponse;

    public CarImageViewModel(Context context) {
        this.context = context;
    }

    public Bitmap getFrontPhoto() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }

    public Bitmap getLeftPhoto() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }

    public Bitmap getRightPhoto() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }

    public Bitmap getOtherPhoto1() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }

    public Bitmap getOtherPhoto2() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }

    public Bitmap getOtherPhoto3() {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.tips_upload);
    }
}
