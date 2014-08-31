package com.jason.usedcar.model.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "province")
public class Province extends com.activeandroid.Model {

    @Column(name = "remote_id")
    private int provinceId;

    @Column(name = "name")
    private String provinceName;

    public Province() {}

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}
