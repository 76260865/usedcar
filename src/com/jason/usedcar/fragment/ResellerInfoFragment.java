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
import android.widget.Toast;
import com.jason.usedcar.*;
import com.jason.usedcar.pm.ResellerInfoPM;
import com.jason.usedcar.pm.view.ResellerInfoView;
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
public class ResellerInfoFragment extends AbsFragment implements ResellerInfoView {

    public static final int ID_PHOTO = 1;

    public static final int ID_PHOTO2 = 2;

    public static final int LICENSE_PHOTO = 3;

    private static final int LICENSE_PHOTO2 = 4;

    private String[] imageIds = new String[4];

    private ResellerInfoPM resellerInfoViewModel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_step_3;
    }

    @Override
    protected Object presentationModel() {
        return resellerInfoViewModel = new ResellerInfoPM(this);
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
                            case ID_PHOTO:
                                uploadImage(ID_PHOTO, bitmap);
                                break;
                            case ID_PHOTO2:
                                uploadImage(ID_PHOTO2, bitmap);
                                break;
                            case LICENSE_PHOTO:
                                uploadImage(LICENSE_PHOTO, bitmap);
                                break;
                            case LICENSE_PHOTO2:
                                uploadImage(LICENSE_PHOTO2, bitmap);
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
        inflater.inflate(R.menu.menu_framgent_sell_car_3, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                if (imageIds[0] == null) {
                    Toast.makeText(getActivity(), "请上传车辆登记证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (imageIds[1] == null) {
                    Toast.makeText(getActivity(), "请上传车辆登记证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
//                if (TextUtils.isEmpty(contact.getText())) {
//                    Toast.makeText(getActivity(), "请填写名字", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (TextUtils.isEmpty(contactPhone.getText())) {
//                    Toast.makeText(getActivity(), "请填写联系方式", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (String.valueOf(contactPhone.getText()).length() != 11) {
//                    Toast.makeText(getActivity(), "号码不足11位", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
                if (imageIds[2] == null) {
                    Toast.makeText(getActivity(), "请上传车辆认证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
//                if (imageIds[3] == 0) {
//                    Toast.makeText(getActivity(), "请上传车辆认证图片", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                if (!checkBox.isChecked()) {
//                    Toast.makeText(getActivity(), "请阅读并同意《服务条款》", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();
//                publishUsedCarRequest.setCarContact(String.valueOf(contact.getText()));
//                publishUsedCarRequest.setContactPhone(String.valueOf(contactPhone.getText()));
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
                        uploadImageRequest.setAccessToken(Application.fromActivity(getActivity()).getAccessToken());
                        uploadImageRequest.setImage(bytes);
                        new RestClient().uploadImage(uploadImageRequest, new SimpleCallbackImpl2<UploadImageResponse>(ResellerInfoFragment.this) {
                            @Override
                            protected void onSuccess(UploadImageResponse uploadImageResponse, Response response) {
                                loadingFragment.dismiss();
                                if (uploadImageResponse.isExecutionResult()) {
                                    if (index == ID_PHOTO) {
                                        resellerInfoViewModel.setSrc1(bitmap);
                                        resellerInfoViewModel.setLicenseImageId(0, uploadImageResponse.getImageId());
                                    }
                                    else if (index == ID_PHOTO2) {
                                        resellerInfoViewModel.setSrc2(bitmap);
                                        resellerInfoViewModel.setLicenseImageId(1, uploadImageResponse.getImageId());
                                    }
                                    else if (index == LICENSE_PHOTO) {
                                        resellerInfoViewModel.setSrc3(bitmap);
                                        resellerInfoViewModel.setCertificateImageId(uploadImageResponse.getImageId());
                                    }
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

    @Override
    public void takePic(int type) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, type);
    }
}
