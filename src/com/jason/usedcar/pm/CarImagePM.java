package com.jason.usedcar.pm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jason.usedcar.R;
import com.jason.usedcar.response.CarResponse;
import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-10-15.
 */
@PresentationModel
public class CarImagePM {

    private Context context;

    private CarResponse carResponse;

    public CarImagePM(Context context) {
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
