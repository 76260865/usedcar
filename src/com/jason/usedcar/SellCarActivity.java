package com.jason.usedcar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.jason.usedcar.fragment.CarBaseInfoFragment;
import com.jason.usedcar.fragment.CompleteCarInfoFragment;
import com.jason.usedcar.fragment.ResellerInfoFragment;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.request.PublishUsedCarRequest;
import com.jason.usedcar.response.Response;
import com.jason.usedcar.response.UploadImageResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarActivity extends BaseActivity implements Action {

    private PublishUsedCarRequest publishUsedCarParam = new PublishUsedCarRequest();

    private List<Bitmap> bitmapList = new ArrayList<Bitmap>();

    private List<Integer> idList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_sell_car_container,
                new CompleteCarInfoFragment(), "").commit();
        PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();
        publishUsedCarRequest.setAcceptTerm(true);
        publishUsedCarRequest.setContactPhone("18080488463");
        publishUsedCarRequest.setModelId(98);
        publishUsedCarRequest.setSeriesId(1);
        publishUsedCarRequest.setBrandId(1);
        publishUsedCarRequest.setImageIds("110,111,112,113");
        publishUsedCarRequest.setLicenseImageIds("114");
        publishUsedCarRequest.setCertificateImageId("115");
        publishUsedCarRequest.setPurchaseDate("2001-11");
        publishUsedCarRequest.setOdometer(2.0);
        publishUsedCarRequest.setListPrice(2.0);
        publishUsedCarRequest.setPriceType(0);
        publishUsedCarRequest.setPaymentMethod(0);
        publishUsedCarRequest.setCarVin("12345678901234567");
        publishUsedCarRequest.setCarContact("test");
        publishUsedCarRequest.setContactPhone("13912345678");
        publishUsedCarRequest.setProvinceId(28);
        publishUsedCarRequest.setCityId(225);
        publishUsedCarRequest.setCountyId(1875);
        publishUsedCarRequest.setStreet("test");
        publishUsedCarRequest.setAccessToken(Application.sampleAccessToken);
        new RestClient().publishUsedCar(publishUsedCarRequest, new Callback<Response>() {

            @Override
            public void success(final Response response, final retrofit.client.Response response2) {

            }

            @Override
            public void failure(final RetrofitError error) {

            }
        });
    }

    @Override
    public void action(final Fragment fragment, final Object... objects) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof CompleteCarInfoFragment) {
            publishUsedCarParam = (PublishUsedCarRequest) objects[0];
            ft.replace(R.id.activity_sell_car_container,
                    new CarBaseInfoFragment()).commit();
        } else if (fragment instanceof CarBaseInfoFragment) {
            if (objects.length > 0) {
                for (Object o : objects) {
                    bitmapList.add((Bitmap) o);
                }
            }
            ft.replace(R.id.activity_sell_car_container,
                    new ResellerInfoFragment()).commit();
        } else if (fragment instanceof ResellerInfoFragment) {
            Bitmap[] bitmaps = (Bitmap[]) objects[0];
            if (bitmaps.length > 0) {
                for (Bitmap bitmap : bitmaps) {
                    bitmapList.add(bitmap);
                }
            }
            PublishUsedCarRequest r = (PublishUsedCarRequest)objects[1];
            publishUsedCarParam.setCarContact(r.getCarContact());
            publishUsedCarParam.setContactPhone(r.getContactPhone());
            AndroidObservable
                    .bindActivity(this, Observable
                            .from(bitmapList)
                            .map(new Func1<Bitmap, Integer>() {
                                @Override
                                public Integer call(final Bitmap bitmap) {
                                    if (bitmap == null) {
                                        return 0;
                                    }
                                    final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    ImageUploadRequest uploadImageRequest = new ImageUploadRequest();
                                    uploadImageRequest.setAccessToken(Application.sampleAccessToken);
                                    uploadImageRequest.setImage(stream.toByteArray());
                                    UploadImageResponse uploadImageResponse = new RestClient().uploadImage(uploadImageRequest);
                                    return uploadImageResponse.getImageId();
                                }
                            })
                            .subscribeOn(Schedulers.newThread()))
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            int idSize = idList.size();
                            String imageIds = "";
                            for (int i = idSize - 1; i >= 0; i--) {
                                if (i == idSize - 1) {
                                    publishUsedCarParam.setLicenseImageIds(String.valueOf(idList.get(i)));
                                } else if (i == idSize - 2) {
                                    publishUsedCarParam.setCertificateImageId(String.valueOf(idList.get(i)));
                                } else {
                                    imageIds = imageIds.concat(",").concat(String.valueOf(idList.get(i)));
                                }
                            }
                            publishUsedCarParam.setImageIds(imageIds.substring(imageIds.indexOf(",") + 1));
                            publishUsedCarParam.setAccessToken(Application.sampleAccessToken);
                            publishUsedCarParam.setAcceptTerm(true);
                            new RestClient().publishUsedCar(publishUsedCarParam, new Callback<Response>() {
                                @Override
                                public void success(final Response response, final retrofit.client.Response response2) {
                                    response.isExecutionResult();
                                }

                                @Override
                                public void failure(final RetrofitError error) {
                                    error.getCause();
                                }
                            });
                        }

                        @Override
                        public void onError(final Throwable e) {
                        }

                        @Override
                        public void onNext(final Integer i) {
                            if (i == 0) {
                                return;
                            }
                            idList.add(i);
                        }
                    });
        }
    }
}
