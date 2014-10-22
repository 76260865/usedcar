package com.jason.usedcar.presentation_model;

import android.text.TextUtils;
import android.view.View;
import com.jason.usedcar.model.SaleCarModel;
import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.model.data.Product;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class ViewBuyCarPresentationModel extends ViewModelBase {

    private SaleCarModel model;

    private ViewBuyCarView view;

    private FooterPresentationModel footerPresentationModel;

    private PresentationModelChangeSupport changeSupport;

    private String filter;

    private BrandFilterEntity brandFilterEntity;

    private FilterEntity priceFilterEntity;

    private FilterEntity mileFilterEntity;

    private FilterEntity ageFilterEntity;

    public ViewBuyCarPresentationModel(SaleCarModel model,
                                       ViewBuyCarView view) {
        this.model = model;
        this.view = view;
        this.footerPresentationModel = new FooterPresentationModel(model);
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = CarItemPresentationModel.class,
            factoryMethod="newCarItemPresentationModel")
    public List<Product> getProducts() {
        return model.getData();
    }

    public CarItemPresentationModel newCarItemPresentationModel() {
        return new CarItemPresentationModel();
    }

    public void setBrandFilterEntity(final BrandFilterEntity brandFilterEntity) {
        this.brandFilterEntity = brandFilterEntity;
    }

    public void setPriceFilterEntity(final FilterEntity priceFilterEntity) {
        this.priceFilterEntity = priceFilterEntity;
    }

    public void setMileFilterEntity(final FilterEntity mileFilterEntity) {
        this.mileFilterEntity = mileFilterEntity;
    }

    public void setAgeFilterEntity(final FilterEntity ageFilterEntity) {
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

    public FooterPresentationModel getFooter() {
        return footerPresentationModel;
    }

    public void refreshFooter() {
        footerPresentationModel.refresh();
    }

    @PresentationModel
    public static class FooterPresentationModel {

        private SaleCarModel model;

        private PresentationModelChangeSupport changeSupport;

        public FooterPresentationModel(SaleCarModel model) {
            this.model = model;
            this.changeSupport = new PresentationModelChangeSupport(this);
        }

        public int getFull() {
            return model.hasMore() ? View.GONE : View.VISIBLE;
        }

        public int getNotFull() {
            return model.hasMore() ? View.VISIBLE : View.GONE;
        }

        public void refresh() {
            changeSupport.refreshPresentationModel();
        }
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
}
