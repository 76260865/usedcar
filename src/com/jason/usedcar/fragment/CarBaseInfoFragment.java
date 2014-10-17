package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.jason.usedcar.*;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.response.CarImage;
import com.jason.usedcar.response.CarResponse3;
import com.jason.usedcar.response.UploadImageResponse;
import java.util.List;
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
public class CarBaseInfoFragment extends BaseFragment implements Ui, OnClickListener {

    private static final int FRONT_PHOTO = 1;

    private static final int LEFT_PHOTO = 2;

    private static final int RIGHT_PHOTO = 3;

    private static final int OTHER_PHOTO_1 = 4;

    private static final int OTHER_PHOTO_2 = 5;

    private static final int OTHER_PHOTO_3 = 6;

    private ImageView frontPhotoImage;

    private ImageView leftPhotoImage;

    private ImageView rightPhotoImage;

    private ImageView otherPhotoImage1;

    private ImageView otherPhotoImage2;

    private ImageView otherPhotoImage3;

    private int[] imageIds = new int[6];

    private CarResponse3 carResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carResponse = ((SellCarActivity) getActivity()).carResponse;
        frontPhotoImage = getView(view, R.id.car_info_front_photo);
        frontPhotoImage.setOnClickListener(this);
        leftPhotoImage = getView(view, R.id.car_info_left_photo);
        leftPhotoImage.setOnClickListener(this);
        rightPhotoImage = getView(view, R.id.car_info_right_photo);
        rightPhotoImage.setOnClickListener(this);
        otherPhotoImage1 = getView(view, R.id.car_info_other_photo_1);
        otherPhotoImage1.setOnClickListener(this);
        otherPhotoImage2 = getView(view, R.id.car_info_other_photo_2);
        otherPhotoImage2.setOnClickListener(this);
        otherPhotoImage3 = getView(view, R.id.car_info_other_photo_3);
        otherPhotoImage3.setOnClickListener(this);
        if (carResponse != null) {
            List<CarImage> imageList = carResponse.getCarImages();
            if (imageList != null && !imageList.isEmpty()) {
                int size = imageList.size();
                if (size <= 0) {
                    return;
                }
                String url = "http://112.124.62.114:80";
                String imageUrl = imageList.get(0).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load("http://c.hiphotos.baidu.com/image/pic/item/a1ec08fa513d26975bca135557fbb2fb4316d898.jpg").into(frontPhotoImage);
                }
                if (size <= 1) {
                    return;
                }
                imageUrl = imageList.get(1).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load(url + imageUrl).into(leftPhotoImage);
                }
                if (size <= 2) {
                    return;
                }
                imageUrl = imageList.get(2).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load(url + imageUrl).into(rightPhotoImage);
                }
                if (size <= 3) {
                    return;
                }
                imageUrl = imageList.get(3).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load(url + imageUrl).into(otherPhotoImage1);
                }
                if (size <= 4) {
                    return;
                }
                imageUrl = imageList.get(4).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load(url + imageUrl).into(otherPhotoImage2);
                }
                if (size <= 5) {
                    return;
                }
                imageUrl = imageList.get(5).getSizeThumbnail();
                if (imageUrl != null) {
                    Glide.with(this).load(url + imageUrl).into(otherPhotoImage3);
                }
            }
        }
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
                                uploadImage(FRONT_PHOTO, frontPhotoImage, bitmap);
                                break;
                            case LEFT_PHOTO:
                                uploadImage(LEFT_PHOTO, leftPhotoImage, bitmap);
                                break;
                            case RIGHT_PHOTO:
                                uploadImage(RIGHT_PHOTO, rightPhotoImage, bitmap);
                                break;
                            case OTHER_PHOTO_1:
                                otherPhotoImage2.setVisibility(View.VISIBLE);
                                uploadImage(OTHER_PHOTO_1, otherPhotoImage1, bitmap);
                                break;
                            case OTHER_PHOTO_2:
                                otherPhotoImage3.setVisibility(View.VISIBLE);
                                uploadImage(OTHER_PHOTO_2, otherPhotoImage2, bitmap);
                                break;
                            case OTHER_PHOTO_3:
                                uploadImage(OTHER_PHOTO_3, otherPhotoImage3, bitmap);
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
                if (imageIds[0] == 0) {
                    Toast.makeText(getActivity(), "请上传正面图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[1] == 0) {
                    Toast.makeText(getActivity(), "请上传左前45度图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[2] == 0) {
                    Toast.makeText(getActivity(), "请上传右前45度图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[3] == 0) {
                    Toast.makeText(getActivity(), "请上传一张内饰照片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                ((Action) getActivity()).action(this, imageIds);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int requestCode = 0;
        switch (v.getId()) {
            case R.id.car_info_front_photo:
                requestCode = FRONT_PHOTO;
                break;
            case R.id.car_info_left_photo:
                requestCode = LEFT_PHOTO;
                break;
            case R.id.car_info_right_photo:
                requestCode = RIGHT_PHOTO;
                break;
            case R.id.car_info_other_photo_1:
                requestCode = OTHER_PHOTO_1;
                break;
            case R.id.car_info_other_photo_2:
                requestCode = OTHER_PHOTO_2;
                break;
            case R.id.car_info_other_photo_3:
                requestCode = OTHER_PHOTO_3;
                break;
        }
        if (requestCode != 0) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, requestCode);
        }
    }

    private void uploadImage(final int index, final ImageView imageView, final Bitmap bitmap) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("上传...");
        loadingFragment.show(getFragmentManager());
        AndroidObservable
                .bindFragment(this, Observable
                        .from(bitmap)
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
                                    imageView.setImageBitmap(bitmap);
                                    imageIds[index - 1] = uploadImageResponse.getImageId();
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
}
