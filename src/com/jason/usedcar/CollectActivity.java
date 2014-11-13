package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.CollectionPM;
import com.jason.usedcar.pm.menu.MenuCollectionPM;
import com.jason.usedcar.pm.view.ViewCollectionView;
import org.robobinding.MenuBinder;

/**
 * @author t77yq @2014-09-16.
 */
public class CollectActivity extends AbsActivity implements ViewCollectionView {

    private CollectionPM viewModel;

    private MenuCollectionPM menuCollectionViewModel;

    private boolean isEditable = false;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的收藏");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_up_white);
        getSupportActionBar().setIcon(android.R.color.transparent);
        viewModel = new CollectionPM(this);
        initContentView(R.layout.activity_collection, viewModel);
        viewModel.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshCollection();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menuCollectionViewModel = new MenuCollectionPM(this);
        MenuBinder menuBinder = createMenuBinder(menu, getMenuInflater());
        menuBinder.inflateAndBind(R.menu.collection_menu, menuCollectionViewModel);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void viewCollectionItem(Product product) {
        if (!isEditable) {
            Intent detailsIntent = new Intent(getContext(), CarDetailsActivity.class);
            detailsIntent.putExtra("product_id", product.getProductId());
            detailsIntent.putExtra("type", Constants.CarDetailsType.OTHER);
            startActivity(detailsIntent);
        }
    }

    @Override
    public void edit() {
        isEditable = true;
        viewModel.s1etEditable(true);
        viewModel.refreshCollection();
        menuCollectionViewModel.refresh();
    }

    @Override
    public void save() {
        isEditable = false;
        viewModel.s1etEditable(false);
        viewModel.refreshCollection();
        menuCollectionViewModel.refresh();
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void delete(Product product) {
        viewModel.deleteFavorite(product);
    }

    @Override
    public void start() {
        loadingFragment = LoadingFragment.newInstance("正在处理...");
        loadingFragment.show(getSupportFragmentManager());
    }

    @Override
    public void end() {
        if (loadingFragment != null) {
            loadingFragment.dismiss();
        }
    }
}
