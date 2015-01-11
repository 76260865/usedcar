package com.jason.usedcar.pm;

import android.text.TextUtils;
import android.view.View;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.footer.BuyFooterPM;
import com.jason.usedcar.pm.item.CarItemPM2;
import com.jason.usedcar.pm.view.ViewBuyCarView;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class BuyCarPM extends BasePM implements HasPresentationModelChangeSupport {

    private SaleCarModel model;

    private ViewBuyCarView view;

    private BuyFooterPM footerPresentationModel;

    private PresentationModelChangeSupport changeSupport;

    private String filter;

    private BrandFilterEntity brandFilterEntity;

    private FilterEntity priceFilterEntity;

    private FilterEntity mileFilterEntity;

    private FilterEntity ageFilterEntity;

    public BuyCarPM(SaleCarModel model,
                    ViewBuyCarView view) {
        this.model = model;
        this.view = view;
        this.footerPresentationModel = new BuyFooterPM(model);
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPM2.class,
            factoryMethod="newCarItemPresentationModel")
    public List<Product> getProducts() {
        return model.getData();
    }

    public CarItemPM2 newCarItemPresentationModel() {
        return new CarItemPM2(null);
    }

    public void updateBrandFilterEntity(final BrandFilterEntity brandFilterEntity) {
        this.brandFilterEntity = brandFilterEntity;
    }

    public void updatePriceFilterEntity(final FilterEntity priceFilterEntity) {
        this.priceFilterEntity = priceFilterEntity;
    }

    public void updateMileFilterEntity(final FilterEntity mileFilterEntity) {
        this.mileFilterEntity = mileFilterEntity;
    }

    public void updateAgeFilterEntity(final FilterEntity ageFilterEntity) {
        this.ageFilterEntity = ageFilterEntity;
    }

    public void refreshProducts() {
        changeSupport.firePropertyChange("products");
    }

    public void viewProductDetails(ItemClickEvent event) {
        int position = event.getPosition();
        if (position < model.size()) { // ignore footer item
            view.viewProductDetails(model.get(event.getPosition()));
        }
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(final String filter) {
        this.filter = filter;
    }

    public void fillFilter() {
        view.fillFilter();
    }

    public void search() {
        if (TextUtils.isEmpty(filter)) {
            view.showMessage("请输入搜索关键字");
            return;
        }
        view.search(filter);
    }

    public void search2() {
        view.search("");
    }

    public BuyFooterPM getFooter() {
        return footerPresentationModel;
    }

    public void refreshFooter() {
        footerPresentationModel.refresh();
    }

    public void load() {
        contentVisibility = View.GONE;
        progressVisibility = View.VISIBLE;
        nothingVisibility = View.GONE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
    }

    public void finish() {
        contentVisibility = View.VISIBLE;
        progressVisibility = View.GONE;
        nothingVisibility = View.GONE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
    }

    public void error() {
        contentVisibility = View.GONE;
        progressVisibility = View.GONE;
        nothingVisibility = View.VISIBLE;
        changeSupport.firePropertyChange("contentVisibility");
        changeSupport.firePropertyChange("progressVisibility");
        changeSupport.firePropertyChange("nothingVisibility");
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
