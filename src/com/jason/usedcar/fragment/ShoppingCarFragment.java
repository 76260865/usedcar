package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jason.usedcar.CarDetailsActivity;
import com.jason.usedcar.R;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.ShoppingPM;
import com.jason.usedcar.pm.view.ShoppingCarView;

/**
 * @author t77yq @2014-09-29.
 */
public class ShoppingCarFragment extends AbsFragment implements ShoppingCarView {

    private ShoppingPM shoppingCarViewModel;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.txt_shopping_car_str);
        shoppingCarViewModel.loadData();
        ListView listView = (ListView) getView().findViewById(R.id.usedCarList);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                shoppingCarViewModel.longClick(position);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shoppingCarViewModel.refreshProducts();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_to_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addToCart:
                shoppingCarViewModel.confirmShopping();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case Constants.REQUEST_LOGIN:
                    shoppingCarViewModel.end();
                    break;
            }
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_LOGIN:
                    shoppingCarViewModel.loadData();
                    break;
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_shopping_car2;
    }

    @Override
    protected Object presentationModel() {
        if (shoppingCarViewModel == null) {
            shoppingCarViewModel = new ShoppingPM(this);
        }
        return shoppingCarViewModel;
    }

    @Override
    public void viewProductDetails(final Product product) {
        Intent detailsIntent = new Intent(getContext(), CarDetailsActivity.class);
        detailsIntent.putExtra("product_id", product.getProductId());
        detailsIntent.putExtra("type", Constants.CarDetailsType.OTHER);
        startActivity(detailsIntent);
    }

    @Override
    public void delete(final Product product) {
        shoppingCarViewModel.delete(product);
    }

    @Override
    public void check(Product product) {
        shoppingCarViewModel.check(product);
    }

    @Override
    public void confirmShopping(Product product) {
    }
}
