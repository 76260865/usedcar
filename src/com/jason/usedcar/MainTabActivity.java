package com.jason.usedcar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.jason.usedcar.fragment.BuyCarFragment;
import com.jason.usedcar.fragment.MoreFragment;
import com.jason.usedcar.fragment.PersonalCenterFragment;
import com.jason.usedcar.fragment.SellCarFragment;
import com.jason.usedcar.fragment.ShoppingCarFragment;
import com.jason.usedcar.request.CityRequest;
import com.jason.usedcar.request.CountyRequest;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.request.ModelRequest;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.request.SeriesRequest;
import com.jason.usedcar.request.TokenGenerateRequest;
import com.jason.usedcar.response.LoginResponse;
import com.jason.usedcar.response.TokenGenerateResponse;
import com.jason.usedcar.response.UploadImageResponse;
import com.jason.usedcar.response.UserInfoResponse;
import java.io.ByteArrayOutputStream;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainTabActivity extends ActionBarActivity {
    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;

    private Class<?> fragmentArray[] = { BuyCarFragment.class, SellCarFragment.class,
            ShoppingCarFragment.class, PersonalCenterFragment.class, MoreFragment.class };

    private int mImageViewArray[] = { R.drawable.tab_buy_car_btn, R.drawable.tab_sell_car_btn,
            R.drawable.tab_shop_car_btn, R.drawable.tab_personal_car_btn,
            R.drawable.tab_more_car_btn };

    private String mTextviewArray[] = { "BuyCar", "ShoppingChart", "SellCar", "PersonalCenter",
            "More" };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        initView();
//        final LoginRequest request = new LoginRequest();
//        request.setPhoneOrEmail("15008488463");
//        request.setPassword("111111");
//        final RestClient client = new RestClient();
//            client.login(request, new Callback<LoginResponse>() {
//                @Override
//                public void success(LoginResponse loginResponse, Response response) {
//                    if (loginResponse.isExecutionResult()) {
//                        Application.saveAccessToken(getApplication(),
//                                loginResponse.getUserId(), loginResponse.getAccessToken());
//                        Request viewUserInfoRequest = new Request();
//                        request.setAccessToken(Application.accessToken(getApplication()));
//                        client.viewUserInfo(viewUserInfoRequest, new Callback<UserInfoResponse>() {
//                            @Override
//                            public void success(UserInfoResponse userInfoResponse, Response response) {
//                                response.getUrl();
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                error.getCause();
//                            }
//                        });
//                        final TokenGenerateRequest request2 = new TokenGenerateRequest();
//                        request2.setUserId(String.valueOf(loginResponse.getUserId()));
//                        request2.setAccessToken(loginResponse.getAccessToken());
//                        client.generateAccessToken(request2, new Callback<TokenGenerateResponse>() {
//                            @Override
//                            public void success(final TokenGenerateResponse response, final Response response2) {
//                                if (response != null && response.isExecutionResult()) {
//                                    //Application.saveAccessToken(getApplicationContext(), response.getSampleAccessToken());
//                                    ImageUploadRequest imageUploadRequest = new ImageUploadRequest();
//                                    imageUploadRequest.setAccessToken(response.getSampleAccessToken());
//                                    imageUploadRequest.setImage(stream.toByteArray());
//                                    client.uploadImage(imageUploadRequest, new Callback<UploadImageResponse>() {
//                                        @Override
//                                        public void success(final UploadImageResponse response, final Response response2) {
//                                            if (response.isExecutionResult()) {
//                                                response.getImageId();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void failure(final RetrofitError error) {
//                                            error.getBody();
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void failure(final RetrofitError error) {
//                                error.getBody();
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    error.getCause();
//                }
//            });
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }
}
