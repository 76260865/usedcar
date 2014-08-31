package com.jason.usedcar.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "series")
public class Series extends Model{

    @Column(name = "remote_id")
    private int seriesId;

    @Column(name = "name")
    private String seriesName;

    @Column(name = "brand_id")
    private int brandId;

	public int getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(final int brandId) {
        this.brandId = brandId;
    }
}
