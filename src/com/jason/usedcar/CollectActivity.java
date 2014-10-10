package com.jason.usedcar;

import android.os.Bundle;
import android.view.Menu;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.presentation_model.MenuCollectionViewModel;
import com.jason.usedcar.presentation_model.ViewCollectionPresentationModel;
import com.jason.usedcar.presentation_model.ViewCollectionView;
import org.robobinding.MenuBinder;

/**
 * @author t77yq @2014-09-16.
 */
public class CollectActivity extends AbsActivity implements ViewCollectionView {

    private ViewCollectionPresentationModel viewModel;

    private MenuCollectionViewModel menuCollectionViewModel;

    private boolean isEditable = false;

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewCollectionPresentationModel(this);
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
        menuCollectionViewModel = new MenuCollectionViewModel(this);
        MenuBinder menuBinder = createMenuBinder(menu, getMenuInflater());
        menuBinder.inflateAndBind(R.menu.collection_menu, menuCollectionViewModel);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void viewCollectionItem(Product product) {
        MessageToast.makeText(this, product.getProductName()).show();
    }

    @Override
    public void edit() {
        isEditable = true;
        viewModel.setEditable(true);
        viewModel.refreshCollection();
        menuCollectionViewModel.refresh();
    }

    @Override
    public void save() {
        isEditable = false;
        viewModel.setEditable(false);
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
