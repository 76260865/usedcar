package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.jason.usedcar.*;
import com.jason.usedcar.pm.view.CarPicView;
import com.jason.usedcar.pm.CarPicViewModel;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.response.UploadImageResponse;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.ByteArrayOutputStream;

/**
 * @author t77yq @2014-07-13.
 */
public class CarBaseInfoFragment extends AbsFragment implements CarPicView {

    private static final int FRONT_PHOTO = 1;

    private static final int LEFT_PHOTO = 2;

    private static final int RIGHT_PHOTO = 3;

    private static final int OTHER_PHOTO_1 = 4;

    private static final int OTHER_PHOTO_2 = 5;

    private static final int OTHER_PHOTO_3 = 6;

    CarPicViewModel carPicViewModel;

    @Override
    protected int layoutId() {
        return R.layout.fragment_step_2;
    }

    @Override
    protected Object presentationModel() {
        return carPicViewModel = new CarPicViewModel(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                        switch (requestCode) {
                            case FRONT_PHOTO:
                                uploadImage(FRONT_PHOTO, bitmap);
                                break;
                            case LEFT_PHOTO:
                                uploadImage(LEFT_PHOTO, bitmap);
                                break;
                            case RIGHT_PHOTO:
                                uploadImage(RIGHT_PHOTO, bitmap);
                                break;
                            case OTHER_PHOTO_1:
                                uploadImage(OTHER_PHOTO_1, bitmap);
                                break;
                            case OTHER_PHOTO_2:
                                uploadImage(OTHER_PHOTO_2, bitmap);
                                break;
                            case OTHER_PHOTO_3:
                                uploadImage(OTHER_PHOTO_3, bitmap);
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_framgent_sell_car_1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
//                if (imageIds[0] == null) {
//                    Toast.makeText(getActivity(), "请上传正面图片", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (imageIds[1] == null) {
//                    Toast.makeText(getActivity(), "请上传左前45度图片", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (imageIds[2] == null) {
//                    Toast.makeText(getActivity(), "请上传右前45度图片", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (imageIds[3] == null) {
//                    Toast.makeText(getActivity(), "请上传一张内饰照片", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
                ((Action) getActivity()).action(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage(final int index, final Bitmap bitmap) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("上传...");
        loadingFragment.show(getFragmentManager());
        AndroidObservable
                .bindFragment(this, Observable
                        .from(new Bitmap[] { bitmap })
                        .map(new Func1<Bitmap, byte[]>() {
                            @Override
                            public byte[] call(final Bitmap bitmap) {
                                if (bitmap == null) {
                                    return null;
                                }
                                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                                return stream.toByteArray();
                            }
                        })
                        .subscribeOn(Schedulers.newThread()))
                .subscribe(new Subscriber<byte[]>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        loadingFragment.dismiss();
                    }

                    @Override
                    public void onNext(final byte[] bytes) {
                        if (bytes == null) {
                            onError(null);
                            return;
                        }
                        final ImageUploadRequest uploadImageRequest = new ImageUploadRequest();
                        uploadImageRequest.setAccessToken(Application.fromContext(getActivity()).getAccessToken());
                        uploadImageRequest.setImage(bytes);
                        new RestClient().uploadImage(uploadImageRequest, new SimpleCallbackImpl2<UploadImageResponse>(CarBaseInfoFragment.this) {
                            @Override
                            protected void onSuccess(UploadImageResponse uploadImageResponse, Response response) {
                                loadingFragment.dismiss();
                                if (uploadImageResponse.isExecutionResult()) {
                                    //imageView.setImageBitmap(bitmap);
                                    String imageId = uploadImageResponse.getImageId();
                                    switch (index) {
                                        case FRONT_PHOTO:
                                            carPicViewModel.setFrontPhoto(bitmap);
                                            carPicViewModel.setImageId(0, imageId);
                                            break;
                                        case LEFT_PHOTO:
                                            carPicViewModel.setLeftPhoto(bitmap);
                                            carPicViewModel.setImageId(1, imageId);
                                            break;
                                        case RIGHT_PHOTO:
                                            carPicViewModel.setRightPhoto(bitmap);
                                            carPicViewModel.setImageId(2, imageId);
                                            break;
                                        case OTHER_PHOTO_1:
                                            carPicViewModel.setOtherPhoto1(bitmap);
                                            carPicViewModel.setImageId(3, imageId);
                                            break;
                                        case OTHER_PHOTO_2:
                                            carPicViewModel.setOtherPhoto2(bitmap);
                                            carPicViewModel.setImageId(4, imageId);
                                            break;
                                        case OTHER_PHOTO_3:
                                            carPicViewModel.setOtherPhoto3(bitmap);
                                            carPicViewModel.setImageId(5, imageId);
                                            break;
                                    }
                                }
                            }

                            @Override
                            protected void onFailure(RetrofitError error) {
                                loadingFragment.dismiss();
                                MessageToast.makeText(getActivity(), "图片上传不成功，请重试").show();
                            }

                            @Override
                            protected void onAuthorized() {new RestClient().uploadImage(uploadImageRequest, this);
                            }

                            @Override
                            protected void onUnauthorized() {
                                MessageToast.makeText(getActivity(), "会话过期，请重新登录").show();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        });
                    }
                });
    }

    @Override
    public void pickPhoto(int photoType) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, photoType);
    }
}
