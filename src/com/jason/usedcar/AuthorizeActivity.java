package com.jason.usedcar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.activeandroid.query.Select;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.pm.AuthPM;
import com.jason.usedcar.pm.view.ResellerAuthView;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.response.UploadImageResponse2;
import java.io.ByteArrayOutputStream;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author t77yq @2014-09-27.
 */
public class AuthorizeActivity extends AbsActivity implements ResellerAuthView {

    private AuthPM resellerAuthViewModel;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        setTitle("认证");
        resellerAuthViewModel = new AuthPM(this);
        initContentView(R.layout.activity_authorize_1, resellerAuthViewModel);
        resellerAuthViewModel.loadData();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 100:
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                uploadImage(bitmap);
                            }
                        }
                    }
                    break;
                case 2:
                    String area = data.getStringExtra("data");
                    String[] areaList = area.split(";");
                    Province province = new Select().from(Province.class).where("remote_id = ?", areaList[0]).executeSingle();
                    City city = new Select().from(City.class).where("remote_id = ?", areaList[1]).executeSingle();
                    resellerAuthViewModel.setOriginBankAddress(province.getProvinceName(), city.getCityName());
                    break;
            }
        }
    }

    @Override
    public void before() {
        loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void after() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public boolean isReseller() {
        return Application.fromActivity(this).isReseller();
    }

    @Override
    public void pickOriginBankAddress() {
        Intent intent = new Intent(this, DealPlaceActivity.class);
        intent.putExtra("level", 2);
        startActivityForResult(intent, 2);
    }

    private void uploadImage(final Bitmap bitmap) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("上传...");
        loadingFragment.show(getSupportFragmentManager());
        AndroidObservable
                .bindActivity(this, Observable
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
                        uploadImageRequest.setAccessToken(Application.fromActivity(AuthorizeActivity.this).getAccessToken());
                        uploadImageRequest.setImage(bytes);
                        new RestClient().uploadImage2(uploadImageRequest, new SimpleCallbackImpl2<UploadImageResponse2>(AuthorizeActivity.this) {
                            @Override
                            protected void onSuccess(UploadImageResponse2 uploadImageResponse, Response response) {
                                loadingFragment.dismiss();
                                if (uploadImageResponse.isExecutionResult()) {
                                    resellerAuthViewModel.setPic(bitmap);
                                }
                            }

                            @Override
                            protected void onFailure(RetrofitError error) {
                                loadingFragment.dismiss();
                                MessageToast.makeText(AuthorizeActivity.this, "图片上传不成功，请重试").show();
                            }

                            @Override
                            protected void onAuthorized() {
                                new RestClient().uploadImage2(uploadImageRequest, this);
                            }

                            @Override
                            protected void onUnauthorized() {
                                MessageToast.makeText(AuthorizeActivity.this, "会话过期，请重新登录").show();
                                startActivity(new Intent(AuthorizeActivity.this, LoginActivity.class));
                            }
                        });
                    }
                });

    }
}
