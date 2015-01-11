package com.jason.usedcar.pm;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.CarInfo;
import com.jason.usedcar.pm.view.CarPicView;
import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-11-05.
 */
@PresentationModel
public class CarPicViewModel {

    private Bitmap[] imageBitmaps = new Bitmap[6];

    private boolean[] localEditImage = new boolean[6];

    private CarInfo carInfo;

    private CarPicView carPicView;

    public CarPicViewModel(CarPicView carPicView) {
        this.carInfo = CacheUtils.getCarInfo();
        this.carPicView = carPicView;
    }

    public Bitmap getFrontPhoto() {
        return getBitmap(0);
    }

    public Bitmap getLeftPhoto() {
        return getBitmap(1);
    }

    public Bitmap getRightPhoto() {
        return getBitmap(2);
    }

    public Bitmap getOtherPhoto1() {
        return getBitmap(3);
    }

    public Bitmap getOtherPhoto2() {
        return getBitmap(4);
    }

    public Bitmap getOtherPhoto3() {
        return getBitmap(5);
    }

    private Bitmap getBitmap(int position) {
        return imageBitmaps[position];
    }

    public void setFrontPhoto(Bitmap bitmap) {
        setBitmap(0, bitmap);
    }

    public void setLeftPhoto(Bitmap bitmap) {
        setBitmap(1, bitmap);
    }

    public void setRightPhoto(Bitmap bitmap) {
        setBitmap(2, bitmap);
    }

    public void setOtherPhoto1(Bitmap bitmap) {
        setBitmap(3, bitmap);
    }

    public void setOtherPhoto2(Bitmap bitmap) {
        setBitmap(4, bitmap);
    }

    public void setOtherPhoto3(Bitmap bitmap) {
        setBitmap(5, bitmap);
    }

    private void setBitmap(int position, Bitmap bitmap) {
        imageBitmaps[position] = bitmap;
        localEditImage[position] = true;
    }

    public void setImageId(int position, String imageId) {
        carInfo.imageIds[position] = imageId;
    }

    private String getUrl(int position) {
        String url = carInfo.getCarImageUrl(position);
        if (localEditImage[position] || TextUtils.isEmpty(url)) {
            return "";
        }
        return url;
    }

    public String getUrl1() {
        return getUrl(0);
    }

    public String getUrl2() {
        return getUrl(1);
    }

    public String getUrl3() {
        return getUrl(2);
    }

    public String getUrl4() {
        return getUrl(3);
    }

    public String getUrl5() {
        return getUrl(4);
    }

    public String getUrl6() {
        return getUrl(5);
    }

    public void url1() {
        carPicView.pickPhoto(FRONT_PHOTO);
    }

    public void url2() {
        carPicView.pickPhoto(LEFT_PHOTO);
    }

    public void url3() {
        carPicView.pickPhoto(RIGHT_PHOTO);
    }

    public void url4() {
        carPicView.pickPhoto(OTHER_PHOTO_1);
    }

    public void url5() {
        carPicView.pickPhoto(OTHER_PHOTO_2);
    }

    public void url6() {
        carPicView.pickPhoto(OTHER_PHOTO_3);
    }

    private static final int FRONT_PHOTO = 1;

    private static final int LEFT_PHOTO = 2;

    private static final int RIGHT_PHOTO = 3;

    private static final int OTHER_PHOTO_1 = 4;

    private static final int OTHER_PHOTO_2 = 5;

    private static final int OTHER_PHOTO_3 = 6;
}
