package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.jason.usedcar.*;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.request.PublishUsedCarRequest;
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
public class ResellerInfoFragment extends BaseFragment implements Ui, OnClickListener {

    private static final int ID_PHOTO = 1;

    private static final int ID_PHOTO2 = 2;

    private static final int LICENSE_PHOTO = 3;

    private static final int LICENSE_PHOTO2 = 4;

    private ImageView idPhotoImage;

    private ImageView idPhotoImage2;

    private ImageView idPhotoImage3;

    private ImageView idPhotoImage4;

    private CheckBox checkBox;

    private EditText contact;

    private EditText contactPhone;

    private int[] imageIds = new int[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        View view = getView();
        idPhotoImage = getView(view, R.id.car_info_id_photo);
        idPhotoImage.setOnClickListener(this);
        idPhotoImage2 = getView(view, R.id.car_info_id_photo2);
        idPhotoImage2.setOnClickListener(this);
        idPhotoImage3 = getView(view, R.id.car_info_id_photo3);
        idPhotoImage3.setOnClickListener(this);
        idPhotoImage4 = getView(view, R.id.car_info_id_photo4);
        idPhotoImage4.setOnClickListener(this);
        checkBox = getView(view, R.id.car_info_agreement_check);
        contact = getView(view, R.id.carContact);
        contactPhone = getView(view, R.id.carContactPhone);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ID_PHOTO:
                    uploadImage(ID_PHOTO, idPhotoImage, (Bitmap) data.getExtras().get("data"));
                    break;
                case ID_PHOTO2:
                    uploadImage(ID_PHOTO2, idPhotoImage2, (Bitmap) data.getExtras().get("data"));
                    break;
                case LICENSE_PHOTO:
                    uploadImage(LICENSE_PHOTO, idPhotoImage3, (Bitmap) data.getExtras().get("data"));
                    break;
                case LICENSE_PHOTO2:
                    uploadImage(LICENSE_PHOTO2, idPhotoImage4, (Bitmap) data.getExtras().get("data"));
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_framgent_sell_car_3, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                if (imageIds[0] == 0) {
                    Toast.makeText(getActivity(), "请上传车辆登记证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[1] == 0) {
                    Toast.makeText(getActivity(), "请上传车辆登记证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[2] == 0) {
                    Toast.makeText(getActivity(), "请上传车辆认证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[3] == 0) {
                    Toast.makeText(getActivity(), "请上传车辆认证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (!checkBox.isChecked()) {
                    Toast.makeText(getActivity(), "请阅读并同意《服务条款》", Toast.LENGTH_SHORT).show();
                    return true;
                }
                PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();
                publishUsedCarRequest.setCarContact(String.valueOf(contact.getText()));
                publishUsedCarRequest.setContactPhone(String.valueOf(contactPhone.getText()));
                ((Action) getActivity()).action(this, imageIds, publishUsedCarRequest);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_info_id_photo:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), ID_PHOTO);
                break;
            case R.id.car_info_id_photo2:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), ID_PHOTO2);
                break;
            case R.id.car_info_id_photo3:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), LICENSE_PHOTO);
                break;
            case R.id.car_info_id_photo4:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), LICENSE_PHOTO2);
                break;
        }
    }

    @Override
    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return this;
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
                        uploadImageRequest.setAccessToken(Application.sampleAccessToken);
                        uploadImageRequest.setImage(bytes);
                        new RestClient().uploadImage(uploadImageRequest, new SimpleCallbackImpl2<UploadImageResponse>(ResellerInfoFragment.this) {
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
