package com.jason.usedcar.model;

import com.jason.usedcar.model.param.PublishUsedCarParam;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author t77yq @2014.06.14
 */
public class SaleCarModel extends BaseModel {

    @Getter @Setter private boolean loading;

    @Getter @Setter private List<PublishUsedCarParam> data;

    public SaleCarModel() {}

    public SaleCarModel(List<PublishUsedCarParam> data) {
        this.data = data;
    }

    public void add(List<PublishUsedCarParam> newData) {
        if (data == null) {
            this.data = newData;
        } else {
            this.data.addAll(newData);
        }
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public PublishUsedCarParam get(int position) {
        return (position < 0 || position >= size()) ? null : data.get(position);
    }
}
