package com.jason.usedcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.request.FavoriteCarRequest;
import com.jason.usedcar.request.ShoppingCarOperationRequest;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.CarResponse2;
import com.jason.usedcar.response.Response;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-07-31.
 */
public class CarDetailsActivity extends BaseActivity {

    private static final String[] METHODS = new String[] {
            "贷款或全款",
            "全款购买"
    };

    private TextView carNameText;

    private ImageView carShowImage;

    private TextView preSalePriceText;

    private TextView priceTypeText;

    private TextView buyTimeText;

    private TextView mileageText;

    private TextView payTypeText;

    private TextView dailyPaidText;

    private TextView contactPhoneText;

    private TextView dealPlaceText;

    private Double price;

    private String productId;

    private boolean saved;

    private MenuItem saveMenuItem;

    private String carOwnerContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        carNameText = getView(R.id.textCarName);
        carShowImage = getView(R.id.imageCarShow);
        preSalePriceText = getView(R.id.textPreSalePrice);
        priceTypeText = getView(R.id.textPriceType);
        buyTimeText = getView(R.id.textBuyTime);
        mileageText = getView(R.id.textMileage);
        payTypeText = getView(R.id.textPayType);
        dailyPaidText = getView(R.id.textDailyPaid);
        contactPhoneText = getView(R.id.textContactPhone);
        dealPlaceText = getView(R.id.textDealPlace);
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        Product product = (Product) getIntent().getSerializableExtra("product");
        productId = product.getProductId();
        CarRequest carRequest = new CarRequest();
        carRequest.setProductId(product.getProductId());
        carRequest.setAccessToken(Application.sampleAccessToken);
//        new RestClient().getUsedCar(carRequest, new Callback<CarResponse2>() {
//            @Override
//            public void success(final CarResponse2 response, final retrofit.client.Response response2) {
//                loadingFragment.dismiss();
//                if (response != null && response.isExecutionResult()) {
//                    carNameText.setText(response.getProductName());
//                    preSalePriceText.setText(response.getListPrice());
//                    priceTypeText.setText(response.getPriceType() == 0 ? "一口价" : "可议价");
//                    buyTimeText.setText(response.getPurchaseDate());
//                    payTypeText.setText(METHODS[response.getPaymentMethod()]);
//                    mileageText.setText(String.format("%1$f万公里", response.getOdometer()));
//                    carOwnerContactPhone = response.getContactPhone();
//                    contactPhoneText.setText(response.getContactPhone());
//                    //dealPlaceText.setText(response.getPlaceDetails());
//                }
//            }
//
//            @Override
//            public void failure(final RetrofitError error) {
//                loadingFragment.dismiss();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        saveMenuItem = menu.findItem(R.id.action_save);
        saveMenuItem.setTitle(saved ? "取消收藏" : "收藏");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new RestClient().cancel("shoppingCarOperation");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textCalculator:
                Intent intent = new Intent(this, CalculatorActivity.class);
                intent.putExtra("car_price", price);
                startActivity(intent);
                break;
            case R.id.btnDial:
                callCarOwner();
                break;
            case R.id.btnAddToCart:
                addToCart();
                break;
        }
    }

    public void onMenuClicked(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_save:
                if (Application.fromActivity(this).getAccessToken() == null) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (saved) {
                    cancelsSave(menuItem);
                } else {
                    save(menuItem);
                }
                break;
        }
    }

    private void save(final MenuItem menuItem) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        Application.fromActivity(this).getAccessToken();
        new RestClient().addToFavorite(productId, Application.fromActivity(this).getAccessToken(),
                android.os.Build.SERIAL, new Callback<Response>() {
            @Override
            public void success(Response response, retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response != null && response.isExecutionResult()) {
                    saved = !saved;
                    menuItem.setTitle("取消收藏");
                    Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    private void cancelsSave(final MenuItem menuItem) {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        Application.fromActivity(this).getAccessToken();
        new RestClient().deleteFavorite(productId, Application.fromActivity(this).getAccessToken(),
                android.os.Build.SERIAL, new Callback<Response>() {
                    @Override
                    public void success(Response response, retrofit.client.Response response2) {
                        loadingFragment.dismiss();
                        if (response != null && response.isExecutionResult()) {
                            saved = !saved;
                            menuItem.setTitle("收藏");
                            Toast.makeText(getApplicationContext(), "已取消", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        loadingFragment.dismiss();
                    }
                });
    }

    private void callCarOwner() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + carOwnerContactPhone)));
    }

    private void addToCart() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        new RestClient().addToCart(productId, Application.fromActivity(this).getAccessToken(),
                Build.SERIAL, new Callback<Response>() {
            @Override
            public void success(final Response response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response != null && response.isExecutionResult()) {
                    Toast.makeText(getApplicationContext(), "已添加到购物车", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }
}
