package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.request.ShoppingCarOperationRequest;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.Response;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-07-31.
 */
public class CarDetailsActivity extends BaseActivity {

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
        new RestClient().getUsedCar(new CarRequest(), new Callback<CarResponse>() {
            @Override
            public void success(final CarResponse response, final retrofit.client.Response response2) {
                loadingFragment.dismiss();
                if (response != null && response.isExecutionResult()) {
                    carNameText.setText(response.getBrandName());
                    preSalePriceText.setText(response.getListPrice());
                    priceTypeText.setText(response.getPriceType());
                    buyTimeText.setText(response.getPurchaseDate());
                    mileageText.setText(response.getOdometer());
                    contactPhoneText.setText(response.getContactPhone());
                    dealPlaceText.setText(response.getProvince());
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                loadingFragment.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_to_cart, menu);
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
                startActivity(new Intent(this, CalculatorActivity.class));
                break;
        }
    }

    public void onMenuClicked(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_addToCart:
                addToCart();
                Toast.makeText(this, "Add to cart", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addToCart() {
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        ShoppingCarOperationRequest request = new ShoppingCarOperationRequest();
        request.setAddShoppingCar(true);
        request.setProductId("1");
        request.setAccessToken(Application.fromActivity(this).getAccessToken());
        new RestClient().shoppingCarOperation(request, new Callback<Response>() {
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
