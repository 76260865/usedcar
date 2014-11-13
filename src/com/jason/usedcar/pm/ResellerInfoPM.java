package com.jason.usedcar.pm;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.jason.usedcar.CacheUtils;
import com.jason.usedcar.CarInfo;
import com.jason.usedcar.fragment.ResellerInfoFragment;
import com.jason.usedcar.pm.view.ResellerInfoView;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-11-05.
 */
@PresentationModel
public class ResellerInfoPM {

    private Bitmap[] bitmaps = new Bitmap[3];

    private boolean[] localImageEdited = new boolean[3];

    private CarInfo carInfo;

    private ResellerInfoView resellerInfoView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public ResellerInfoPM(ResellerInfoView resellerInfoView) {
        this.carInfo = CacheUtils.getCarInfo();
        this.resellerInfoView = resellerInfoView;
        presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public void setBitmap(int position, Bitmap bitmap) {
        bitmaps[position] = bitmap;
        localImageEdited[position] = true;
    }

    public void setSrc1(Bitmap bitmap) {
        setBitmap(0, bitmap);
        presentationModelChangeSupport.firePropertyChange("src1");
    }

    public void setLicenseImageId(int position, String imageId) {
        carInfo.licenseImageIds[position] = imageId;
    }

    public void setCertificateImageId(String imageId) {
        carInfo.certificateImageId = imageId;
    }

    public Bitmap getSrc1() {
        return bitmaps[0];
    }

    public String getUrl1() {
        String url = carInfo.getLicenseImageUrl(0);
        if (localImageEdited[0] || TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    public Bitmap getSrc2() {
        return bitmaps[1];
    }

    public void setSrc2(Bitmap bitmap) {
        setBitmap(1, bitmap);
        presentationModelChangeSupport.firePropertyChange("src2");
    }


    public String getUrl2() {
        String url = carInfo.getLicenseImageUrl(1);
        if (localImageEdited[1] || TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    public Bitmap getSrc3() {
        return bitmaps[2];
    }

    public void setSrc3(Bitmap bitmap) {
        setBitmap(2, bitmap);
        presentationModelChangeSupport.firePropertyChange("src3");
    }


    public String getUrl3() {
        String url = carInfo.getCertificateImageUrl();
        if (localImageEdited[2] || TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    public String getUrl4() {
        return "";
    }

    public String getContact() {
        return carInfo.carContact;
    }

    public void setContact(String contact) {
        carInfo.carContact = contact;
    }

    public String getContactPhone() {
        return carInfo.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        carInfo.contactPhone = contactPhone;
    }

    public boolean isCheck() {
        return Boolean.TRUE == carInfo.acceptTerm;
    }

    public void setCheck(boolean check) {
        carInfo.acceptTerm = check;
    }

    public void click1() {
        resellerInfoView.takePic(ResellerInfoFragment.ID_PHOTO);
    }

    public void click2() {
        resellerInfoView.takePic(ResellerInfoFragment.ID_PHOTO2);
    }

    public void click3() {
        resellerInfoView.takePic(ResellerInfoFragment.LICENSE_PHOTO);
    }
}
